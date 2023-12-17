package com.thecodeslinger.bcc;

import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Security;

import static org.assertj.core.api.Assertions.assertThat;

class KeyPairCertificateTest {

    static final String CERT_OUT_FILE_NAME = "certificate-test.out";
    static final String PKEY_OUT_FILE_NAME = "private-key-test.out";

    @BeforeAll
    static void beforeAll() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @AfterEach
    @SneakyThrows
    void afterEach() {
        Files.delete(Path.of(CERT_OUT_FILE_NAME));
        Files.delete(Path.of(PKEY_OUT_FILE_NAME));
    }

    @Test
    @SneakyThrows
    void writeAsDer() {
        // Given
        var keyAndCert = X509CertificateFactory.createLeafSigning("CN=the-codeslinger.com,C=DE");

        // When
        keyAndCert.writeCertAsDer(CERT_OUT_FILE_NAME);
        keyAndCert.writePrivateKeyAsDer(PKEY_OUT_FILE_NAME);

        var loadedKeyAndCert = X509CertificateFactory.loadFromFile(CERT_OUT_FILE_NAME, PKEY_OUT_FILE_NAME);

        // Then
        assertCertificateAndKey(keyAndCert, loadedKeyAndCert);
    }

    @Test
    void writeAsPem() {
        // Given
        var keyAndCert = X509CertificateFactory.createLeafSigning("CN=the-codeslinger.com,C=DE");

        // When
        keyAndCert.writeCertAsPem(CERT_OUT_FILE_NAME);
        keyAndCert.writePrivateKeyAsPem(PKEY_OUT_FILE_NAME);

        var loadedKeyAndCert = X509CertificateFactory.loadFromPemFile(CERT_OUT_FILE_NAME, PKEY_OUT_FILE_NAME);
        var loadedKeyAndCertBouncy = X509CertificateFactory.loadFromPemFileBouncy(CERT_OUT_FILE_NAME, PKEY_OUT_FILE_NAME);

        // Then
        assertCertificateAndKey(keyAndCert, loadedKeyAndCert);
        assertCertificateAndKey(keyAndCert, loadedKeyAndCertBouncy);
    }

    @Test
    void writeAsPemBouncy() {
        // Given
        var keyAndCert = X509CertificateFactory.createLeafSigning("CN=the-codeslinger.com,C=DE");

        // When
        keyAndCert.writeCertAsPemBouncy(CERT_OUT_FILE_NAME);
        keyAndCert.writePrivateKeyAsPemBouncy(PKEY_OUT_FILE_NAME);

        var loadedKeyAndCert = X509CertificateFactory.loadFromPemFile(CERT_OUT_FILE_NAME, PKEY_OUT_FILE_NAME);
        var loadedKeyAndCertBouncy = X509CertificateFactory.loadFromPemFileBouncy(CERT_OUT_FILE_NAME, PKEY_OUT_FILE_NAME);

        // Then
        assertCertificateAndKey(keyAndCert, loadedKeyAndCert);
        assertCertificateAndKey(keyAndCert, loadedKeyAndCertBouncy);
    }

    @SneakyThrows
    private void assertCertificateAndKey(KeyPairCertificate created, KeyPairCertificate loaded) {

        var keyPair = created.keyPair();
        var loadedKeyPair = loaded.keyPair();

        assertThat(keyPair.getPrivate().getEncoded()).isEqualTo(loadedKeyPair.getPrivate().getEncoded());
        assertThat(keyPair.getPublic().getEncoded()).isEqualTo(loadedKeyPair.getPublic().getEncoded());

        var cert = created.certificate();
        var loadedCert = loaded.certificate();

        assertThat(cert.getSubjectX500Principal()).isEqualTo(loadedCert.getSubjectX500Principal());
        assertThat(cert.getBasicConstraints()).isEqualTo(loadedCert.getBasicConstraints());
        assertThat(cert.getKeyUsage()).isEqualTo(loadedCert.getKeyUsage());
        assertThat(cert.getExtendedKeyUsage()).isEqualTo(loadedCert.getExtendedKeyUsage());
        assertThat(cert.getPublicKey()).isEqualTo(loadedCert.getPublicKey());
    }
}