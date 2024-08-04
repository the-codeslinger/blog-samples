package com.thecodeslinger;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;

import static com.thecodeslinger.JavaCryptoAsymmetricKey.Algorithm.RSA;
import static org.assertj.core.api.Assertions.assertThat;

public class RsaTest {

    // openssl rsa -noout -text -in genpkey_rsa_private_key.pem
    private static final String RSA_MODULUS = "009eb531a1916346ba724b4f17d3acf5b917c1970b6f9f336f79fbdf267dd149fb4d30d1c41cf8b0f91adda474b76823c1d62dacd9f755759f0423d99b709ae10aee6e5409da06a9e6f2993e13927dd76d7fdb7d586f4b16816722190b94af86bcff7c79e989caea9e97d18df82a4e14e7b4ca8bdebb1f103541d25e63f237e1e20a581cb66bd3614f9f24241602ecaac47a0a08dee7c96d12c890ed0cfc35cedda45323bcf2ce53051f73a33008018152c15ff14f37063f82d0cfe1b58e745cabd797ecb41d6ec781fab9e46760ea2e0beb6b79980bfc935d2ea3e021acd553b66ea10fe30a31edeaa8256f2bc8f3fd8b80c63b87010c6178f0ef34acefd678f3";
    private static final String RSA_PRIVATE_EXPONENT = "29cf00ce97bec7e498f456d72787a249cac4d0560e6729851de4e8b1baa6562f71f3c0e6c15d103de742692ee81bf2837f3914166d6e2168ad0e06a6c8a0bc55055fa21ae0492e1bed5fca371cb9b2f5212000a2b9dad5886c5352b6b6ca109c1c4fbdcbf46534ae67bf27ffaea158277bfc79a148b913ab7bf5ffb0a39fee0c943abb8a3691ea10b6f0677a71efa7c480f8c70f19a534a6cd7af28cc5636e8b04f001416fd09ae24142f3b12b29ac55443c0a16fef50137b33645c5eaef3be96c1c77e065886af213a9810d108aafbfa160debb9b1d5a8af9c6fb72aa4d19983eae4a838cc9bd2e699b29af1e486a44bab633f6a49d4ec1469ea20feadda5c1";
    private static final String RSA_PRIME_1 = "00d0a4474735cf3ee31ed05cb805281318719f4af60dac507335bfca33ab170dca66535a97355d03ed1c28c86cdcb80016c4557e0c4e2e790792d9e475cacdaafbaac1aa4fa6c7b4289176f4b2d907d3410c2493a6b9ada158036587c148bca0fccb161c8fc32849080ad130c09132164fffc269fccfd395bf521e40e39ac173c1";
    private static final String RSA_PRIME_2 = "00c2bb5cc7627fa4ac0df4cc6b19c12820097ec8155ed22108d9e632f2f0d6926b524c6a21357f71d2c5547e56423c69625aed8ba17f555a1f365e8ae269290afcfa1e770c9052a6eb3319bf83472047aca0dba511ec634f398f2f8751f5a53418689d8512bdb5d0544ff28b632c8de6795c180a9d924b7b2d71d43dcec2b0c9b3";
    private static final String RSA_EXPONENT_1 = "00c9fc201d2a54c696edffdda3312ac8c1c34de92e27b287f7e1331937c80c7b875464a1b0fd325d58b10832706185a860621639ff03b92de64d1eb073f0b00735b8ebe04b5834c18efcb462d4ba133f4b1bcc553cf548c8880251ca892e379ecadc793d9157e7aa3c24ecbbfbd114e250f4536c741966c6f3ff35d6b001e40281";
    private static final String RSA_EXPONENT_2 = "4bf88cd9bcb3cfaad04710f937b303d47bdfda6f7beebeb4954e19d26de3487c563f39a87b169d717d4ad97f0d984404fb3471a52fe83ca0b0b571b9a30d401c31f870b99614a8ca59c551d2058db268c6d8ea2d73ebca9cca1fe0cb38ea9ac86b080942a680a8263c8a8396cd6b0edf89ee5735e23d50710a57a34566ef51c1";
    private static final String RSA_COEFFICIENT = "00b5c130e3d4e8fffad35c7bdf925622da7b134633413f9b71ee10b27bfa9901adf259c3aaeb58d12de34f2f53c9be8d680d857364c2932e184f8e395990c08502d58c8403eb8d2c197873b4653313529f41075723656de189418df6b78dcdb8b5829dac44a2a35c33590ca2a15ac7cd2ab23c6c588a02ec93c489592d2828c85c";
    private static final String PUBLIC_EXPONENT = "10001";

    @Test
    @SneakyThrows
    void getPrivateKeyFromDerFile_rsa() {
        // GIVEN
        var resource = getClass().getClassLoader().getResource("genpkey_rsa_private_key.der");
        assertThat(resource).isNotNull();

        // WHEN
        var privateKey = JavaCryptoAsymmetricKey.getPrivateKeyFromDerFile(resource.toURI(), RSA);

        // THEN
        assertPrivateKey(privateKey);
    }

    @Test
    @SneakyThrows
    void getPublicKeyFromDerFile_rsa() {
        // GIVEN
        var resource = getClass().getClassLoader().getResource("genpkey_rsa_public_key.der");
        assertThat(resource).isNotNull();

        // WHEN
        var publicKey = JavaCryptoAsymmetricKey.getPublicKeyFromDerFile(resource.toURI(), RSA);

        // THEN
        assertPublicKey(publicKey);
    }

    @Test
    @SneakyThrows
    void getPublicKeyFromPrivateKey_rsa() {
        // GIVEN
        var resource = getClass().getClassLoader().getResource("genpkey_rsa_private_key.der");
        assertThat(resource).isNotNull();

        var privateKey = JavaCryptoAsymmetricKey.getPrivateKeyFromDerFile(resource.toURI(), RSA);

        // WHEN
        var publicKey = JavaCryptoAsymmetricKey.getPublicKeyFromPrivateKey(privateKey);

        // THEN
        assertPublicKey(publicKey);
    }

    private static void assertPrivateKey(PrivateKey privateKey) {
        assertThat(privateKey).isNotNull();
        assertThat(privateKey).isInstanceOf(RSAPrivateCrtKey.class);

        var rsaKey = (RSAPrivateCrtKey) privateKey;
        assertThat(rsaKey.getModulus()).isEqualTo(new BigInteger(RSA_MODULUS, 16));
        assertThat(rsaKey.getPrivateExponent()).isEqualTo(new BigInteger(RSA_PRIVATE_EXPONENT, 16));
        assertThat(rsaKey.getPrimeP()).isEqualTo(new BigInteger(RSA_PRIME_1, 16));
        assertThat(rsaKey.getPrimeQ()).isEqualTo(new BigInteger(RSA_PRIME_2, 16));
        assertThat(rsaKey.getPrimeExponentP()).isEqualTo(new BigInteger(RSA_EXPONENT_1, 16));
        assertThat(rsaKey.getPrimeExponentQ()).isEqualTo(new BigInteger(RSA_EXPONENT_2, 16));
        assertThat(rsaKey.getCrtCoefficient()).isEqualTo(new BigInteger(RSA_COEFFICIENT, 16));
    }

    private static void assertPublicKey(PublicKey publicKey) {
        assertThat(publicKey).isNotNull();
        assertThat(publicKey).isInstanceOf(RSAPublicKey.class);

        var rsaKey = (RSAPublicKey) publicKey;
        assertThat(rsaKey.getModulus()).isEqualTo(new BigInteger(RSA_MODULUS, 16));
        assertThat(rsaKey.getPublicExponent()).isEqualTo(new BigInteger(PUBLIC_EXPONENT, 16));
    }
}
