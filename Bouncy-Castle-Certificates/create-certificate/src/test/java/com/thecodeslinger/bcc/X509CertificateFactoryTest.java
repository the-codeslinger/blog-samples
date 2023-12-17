package com.thecodeslinger.bcc;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.Security;

import static org.assertj.core.api.Assertions.assertThat;

class X509CertificateFactoryTest {

    @BeforeAll
    static void beforeAll() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Test
    void createLeafSigning() {
        // When
        var keyAndCert = X509CertificateFactory.createLeafSigning("CN=the-codeslinger.com,C=DE");

        // Then
        var cert = keyAndCert.certificate();
        // Somewhere in the chain from creating the name to retrieving it, the order is changed.
        assertThat(cert.getSubjectX500Principal().getName()).isEqualTo("C=DE,CN=the-codeslinger.com");

        // Basic constraints contains the path length of a CA's hierarchy. This is not a CA certificate, so it
        // must be -1.
        assertThat(cert.getBasicConstraints()).isEqualTo(-1);

        // See https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/security/cert/X509Certificate.html#getKeyUsage()
        // The first item of the array is the Digital Signature key usage. Everything else is disabled.
        assertThat(cert.getKeyUsage())
                .isEqualTo(new boolean[]{true, false, false, false, false, false, false, false, false});

        var key = keyAndCert.keyPair();
        assertThat(key.getPublic().getAlgorithm()).isEqualTo("RSA");
        assertThat(key.getPrivate().getAlgorithm()).isEqualTo("RSA");
    }
}