package com.thecodeslinger.bcc;

import lombok.SneakyThrows;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509ExtensionUtils;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.FileInputStream;
import java.io.FileReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.thecodeslinger.bcc.PemConstant.*;
import static org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

public class X509CertificateFactory {

    private static final int LEAF_CERT_LIFETIME_DAYS = 1;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @SneakyThrows
    public static KeyPairCertificate createLeafSigning(String distinguishedName) {
        return createCertificate(distinguishedName, LEAF_CERT_LIFETIME_DAYS);
    }

    @SneakyThrows
    public static KeyPairCertificate loadFromFile(String certificateFile, String privateKeyFile) {

        var certFactory = CertificateFactory.getInstance("X509");
        X509Certificate cert;
        try (var certStream = new FileInputStream(certificateFile)) {
            cert = (X509Certificate) certFactory.generateCertificate(certStream);
        }

        var keyBytes = Files.readAllBytes(Paths.get(privateKeyFile));
        var keySpec = new PKCS8EncodedKeySpec(keyBytes);
        var keyFactory = KeyFactory.getInstance("RSA");
        var privateKey = keyFactory.generatePrivate(keySpec);

        return new KeyPairCertificate(new KeyPair(cert.getPublicKey(), privateKey), cert);
    }

    @SneakyThrows
    public static KeyPairCertificate loadFromPemFile(String certificateFile, String privateKeyFile) {

        // Supports DER and PEM encoded certificate files.
        var certFactory = CertificateFactory.getInstance("X509");
        X509Certificate cert;
        try (var certStream = new FileInputStream(certificateFile)) {
            cert = (X509Certificate) certFactory.generateCertificate(certStream);
        }

        // PEM must be handled separately using JDK-only classes.
        var keyBytes = Files.readAllBytes(Paths.get(privateKeyFile));
        var subKeyBytes = stripPemHeaderAndFooter(keyBytes);
        keyBytes = Base64.getMimeDecoder().decode(subKeyBytes);

        var keySpec = new PKCS8EncodedKeySpec(keyBytes);
        var keyFactory = KeyFactory.getInstance("RSA");
        var privateKey = keyFactory.generatePrivate(keySpec);

        return new KeyPairCertificate(new KeyPair(cert.getPublicKey(), privateKey), cert);
    }

    @SneakyThrows
    public static KeyPairCertificate loadFromPemFileBouncy(String certificateFile, String privateKeyFile) {

        X509Certificate cert;
        try (var certPemParser = new PEMParser(new FileReader(certificateFile))) {
            var certObject = (X509CertificateHolder)certPemParser.readObject();
            var certConverter = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider());
            cert = certConverter.getCertificate(certObject);
        }

        PrivateKeyInfo keyInfo;
        try (var keyPemParser = new PEMParser(new FileReader(privateKeyFile))) {
            var pemObject = keyPemParser.readObject();
            keyInfo = switch (pemObject) {
                case PEMKeyPair pemKeyPair -> pemKeyPair.getPrivateKeyInfo();
                case PrivateKeyInfo privateKeyInfo -> privateKeyInfo;
                // Should not happen in this limited and controlled scenario.
                default -> throw new IllegalStateException("Unexpected value: " + pemObject);
            };
        }

        var privateKey = new JcaPEMKeyConverter().getPrivateKey(keyInfo);
        return new KeyPairCertificate(new KeyPair(cert.getPublicKey(), privateKey), cert);
    }

    @SneakyThrows
    private static KeyPairCertificate createCertificate(String distinguishedName, int lifetime) {

        var keyPair = generateKeyPair("RSA");
        var publicKeyInfo = getPublicKeyInfo(keyPair);
        var issuerKeyInfo = publicKeyInfo;

        var ownerName = new X500Name(distinguishedName);
        var issuerName = ownerName;

        var extensions = generateExtensions(publicKeyInfo, issuerKeyInfo);

        var serialNumber = BigInteger.valueOf(Math.abs(SECURE_RANDOM.nextInt()));
        var validFrom = Date.from(Instant.now());
        var validTo = Date.from(Instant.now().plus(lifetime, ChronoUnit.DAYS));

        var certBuilder = new X509v3CertificateBuilder(
                issuerName, serialNumber,   // Usually generated by a CA.
                validFrom, validTo,         // Lifetime, also generated by a CA.
                ownerName, publicKeyInfo);  // Owner information.

        // Nicer than a lambda because of the exception handling.
        for (var extension : extensions) {
            certBuilder.addExtension(extension);
        }

        var issuerKeyPair = keyPair;
        var contentSigner = new JcaContentSignerBuilder("SHA1WithRSAEncryption").setProvider(PROVIDER_NAME)
                .build(issuerKeyPair.getPrivate());

        var certConverter = new JcaX509CertificateConverter().setProvider(PROVIDER_NAME);
        var certificate = certConverter.getCertificate(certBuilder.build(contentSigner));

        return new KeyPairCertificate(keyPair, certificate);
    }

    @SneakyThrows
    private static KeyPair generateKeyPair(String algorithm) {
        var keyPairGen = KeyPairGenerator.getInstance(algorithm, PROVIDER_NAME);
        keyPairGen.initialize(2048, SECURE_RANDOM);
        return keyPairGen.generateKeyPair();
    }

    private static SubjectPublicKeyInfo getPublicKeyInfo(KeyPair keyPair) {
        return SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
    }

    @SneakyThrows
    private static List<Extension> generateExtensions(
            SubjectPublicKeyInfo ownerKeyInfo, SubjectPublicKeyInfo issuerKeyInfo) {

        var keyUsageExt = new KeyUsage(KeyUsage.digitalSignature);
        var basicConstraintsExt = new BasicConstraints(false);

        // Need to use an SHA-1 based algorithm to conform to RFC-5280.
        var digestCalc = new BcDigestCalculatorProvider().get(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1));
        var extensionUtils = new X509ExtensionUtils(digestCalc);
        var subjectKeyIdentifier = extensionUtils.createSubjectKeyIdentifier(ownerKeyInfo);
        var issuerKeyIdentifier = extensionUtils.createAuthorityKeyIdentifier(issuerKeyInfo);

        return List.of(
                Extension.create(Extension.keyUsage, false, keyUsageExt),
                Extension.create(Extension.basicConstraints, false, basicConstraintsExt),
                Extension.create(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier),
                Extension.create(Extension.authorityKeyIdentifier, false, issuerKeyIdentifier));
    }

    /**
     * Bouncy Castle exports as "BEGIN RSA PRIVATE KEY".
     */
    private static byte[] stripPemHeaderAndFooter(byte[] keyBytes) {
        var length = BEGIN_PRIVATE_KEY.length();
        if (0 == Arrays.compare(
                keyBytes, 0, length,
                BEGIN_PRIVATE_KEY.getBytes(StandardCharsets.UTF_8), 0, length)) {
            return Arrays.copyOfRange(keyBytes, length,
                    keyBytes.length - END_PRIVATE_KEY.length());
        }

        length = BEGIN_RSA_PRIVATE_KEY.length();
        if (0 == Arrays.compare(
                keyBytes, 0, length,
                BEGIN_RSA_PRIVATE_KEY.getBytes(StandardCharsets.UTF_8), 0, length)) {
            return Arrays.copyOfRange(keyBytes, length,
                    keyBytes.length - END_RSA_PRIVATE_KEY.length());
        }

        // Should not happen in our examples. Real code should throw a meaningful exception.
        return keyBytes;
    }

    /**
     * Bouncy Castle exports as "BEGIN RSA PRIVATE KEY".

    private static boolean isPemRsaPrivateKey(byte[] keyBytes) {
        var length = BEGIN_RSA_PRIVATE_KEY.length();

        if (keyBytes.length > length) {
            return 0 == Arrays.compare(
                    keyBytes, 0, length,
                    BEGIN_RSA_PRIVATE_KEY.getBytes(StandardCharsets.UTF_8), 0, length);
        } else {
            return false;
        }
    }*/
}
