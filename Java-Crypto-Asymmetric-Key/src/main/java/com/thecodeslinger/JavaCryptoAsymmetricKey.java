package com.thecodeslinger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class JavaCryptoAsymmetricKey {

    public static void main(String[] args) {
        System.out.println("Look at the test, please.");
    }

    @Getter
    @RequiredArgsConstructor
    public static enum Algorithm {
        EC("EC"),
        RSA("RSA");

        private final String name;
    }

    @SneakyThrows
    public static PrivateKey getPrivateKeyFromDerFile(URI resourceLocation, Algorithm algorithm) {
        var privateKeyDer = Files.readAllBytes(Path.of(resourceLocation));
        var keyFactory = KeyFactory.getInstance(algorithm.getName());
        var pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyDer);
        return keyFactory.generatePrivate(pkcs8KeySpec);
    }

    @SneakyThrows
    public static PublicKey getPublicKeyFromDerFile(URI resourceLocation, Algorithm algorithm) {
        var publicKeyDer = Files.readAllBytes(Path.of(resourceLocation));
        var keyFactory = KeyFactory.getInstance(algorithm.getName());
        var encodedKeySpec = new X509EncodedKeySpec(publicKeyDer);
        return keyFactory.generatePublic(encodedKeySpec);
    }

    @SneakyThrows
    public static PublicKey getPublicKeyFromPrivateKey(PrivateKey privateKey) {
        if (privateKey instanceof RSAPrivateCrtKey rsaKey) {
            var rsaKeyFactory = KeyFactory.getInstance(Algorithm.RSA.getName());
            var rsaPublicKeySpec = new RSAPublicKeySpec(rsaKey.getModulus(), rsaKey.getPublicExponent());
            return rsaKeyFactory.generatePublic(rsaPublicKeySpec);
        } else if (privateKey instanceof ECPrivateKey ecKey) {
            // This is Bouncy Castle.
            var privateKeyInfo = PrivateKeyInfo.getInstance(ecKey.getEncoded());
            var bcPrivateKey = org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(privateKeyInfo.parsePrivateKey());

            var publicKeyBit = bcPrivateKey.getPublicKey();
            var publicKeyInfo = new SubjectPublicKeyInfo(privateKeyInfo.getPrivateKeyAlgorithm(), publicKeyBit);

            var keyFactory = KeyFactory.getInstance(ecKey.getAlgorithm());
            var keySpec = new X509EncodedKeySpec(publicKeyInfo.getEncoded());
            return keyFactory.generatePublic(keySpec);
            // End of Bouncy Castle.
        } else {
            throw new IllegalArgumentException("Unsupported private key type");
        }
    }
}