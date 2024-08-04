package com.thecodeslinger;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

import static com.thecodeslinger.JavaCryptoAsymmetricKey.Algorithm.EC;
import static org.assertj.core.api.Assertions.assertThat;

class EcTest {

    // openssl ec -noout -text -in genpkey_ec_private_key.pem
    private static final String EC_PRIVATE_KEY = "b8718456bd7d4818c8823878d012ebd3c403e62b635651bccfd804040e1899e3";
    private static final String EC_PUBLIC_KEY_X = "1fdf6fd1e859dbad694f005f369bf8fbf492d1a65e3d88b16a6e103ad4527a4c";
    private static final String EC_PUBLIC_KEY_Y = "73e1b172f58a73183e1d0b25409c23daf61e8c4e21955734a8ef945cee565a63";

    @Test
    @SneakyThrows
    void getPrivateKeyFromDerFile_ec() {
        // GIVEN
        var resource = getClass().getClassLoader().getResource("genpkey_ec_private_key.der");
        assertThat(resource).isNotNull();

        // WHEN
        var privateKey = JavaCryptoAsymmetricKey.getPrivateKeyFromDerFile(resource.toURI(), EC);

        // THEN
        assertPrivateKey(privateKey);
    }

    @Test
    @SneakyThrows
    void getPublicKeyFromDerFile_ec() {
        // GIVEN
        var resource = getClass().getClassLoader().getResource("genpkey_ec_public_key.der");
        assertThat(resource).isNotNull();

        // WHEN
        var publicKey = (ECPublicKey) JavaCryptoAsymmetricKey.getPublicKeyFromDerFile(resource.toURI(), EC);

        // THEN
        assertPublicKey(publicKey);
    }

    @Test
    @SneakyThrows
    void getPublicKeyFromPrivateKey_ec() {
        // GIVEN
        var resource = getClass().getClassLoader().getResource("genpkey_ec_private_key.der");
        assertThat(resource).isNotNull();

        var privateKey = (ECPrivateKey) JavaCryptoAsymmetricKey.getPrivateKeyFromDerFile(resource.toURI(), EC);

        // WHEN
        var publicKey = (ECPublicKey) JavaCryptoAsymmetricKey.getPublicKeyFromPrivateKey(privateKey);

        // THEN
        assertPublicKey(publicKey);
    }

    private static void assertPrivateKey(PrivateKey privateKey) {
        assertThat(privateKey).isNotNull();
        assertThat(privateKey).isInstanceOf(ECPrivateKey.class);

        var ecKey = (ECPrivateKey) privateKey;
        assertThat(ecKey.getS()).isNotNull();
    }

    private static void assertPublicKey(PublicKey publicKey) {
        assertThat(publicKey).isNotNull();
        assertThat(publicKey).isInstanceOf(ECPublicKey.class);

        var ecKey = (ECPublicKey) publicKey;
        assertThat(ecKey.getW().getAffineX()).isEqualTo(new BigInteger(EC_PUBLIC_KEY_X, 16));
        assertThat(ecKey.getW().getAffineY()).isEqualTo(new BigInteger(EC_PUBLIC_KEY_Y, 16));
    }
}