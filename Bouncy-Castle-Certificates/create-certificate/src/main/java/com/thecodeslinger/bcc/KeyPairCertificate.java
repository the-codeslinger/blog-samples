package com.thecodeslinger.bcc;

import lombok.SneakyThrows;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Base64;

import static com.thecodeslinger.bcc.PemConstant.*;

/**
 * Decode certificate with: openssl x509 -text -noout -in [file]
 * Decode private key with: openssl.exe rsa -check -noout -text -in [file]
 *
 * @param keyPair
 * @param certificate
 */
public record KeyPairCertificate(@NotNull KeyPair keyPair, @NotNull X509Certificate certificate) {

    private static final byte[] LINE_SEP = System.lineSeparator().getBytes(StandardCharsets.UTF_8);

    @SneakyThrows
    public void writeCertAsDer(String outFile) {
        writeEncoded(outFile, certificate.getEncoded());
    }

    @SneakyThrows
    public void writePrivateKeyAsDer(String outFile) {
        writeEncoded(outFile, keyPair().getPrivate().getEncoded());
    }

    @SneakyThrows
    public void writeCertAsPem(String outFile) {
        writePem(outFile, BEGIN_CERTIFICATE, END_CERTIFICATE, certificate.getEncoded());
    }

    @SneakyThrows
    public void writePrivateKeyAsPem(String outFile) {
        writePem(outFile, BEGIN_PRIVATE_KEY, END_PRIVATE_KEY, keyPair().getPrivate().getEncoded());
    }

    @SneakyThrows
    public void writeCertAsPemBouncy(String outFile) {
        writePemBouncy(outFile, certificate);
    }

    @SneakyThrows
    public void writePrivateKeyAsPemBouncy(String outFile) {
        writePemBouncy(outFile, keyPair().getPrivate());
    }

    @SneakyThrows
    private void writeEncoded(String outFile, byte[] encoded) {
        try (var writer = new FileOutputStream(outFile)) {
            writer.write(encoded);
        }
    }

    @SneakyThrows
    public void writePem(String outFile, String fileBegin, String fileEnd, byte[] encoded) {

        // PEM format must only contain 64 characters per line.
        var encoder = Base64.getMimeEncoder(64, LINE_SEP);
        try (var writer = new FileOutputStream(outFile)) {
            writer.write(fileBegin.getBytes(StandardCharsets.UTF_8));
            writer.write(LINE_SEP);
            writer.write(encoder.encode(encoded));
            writer.write(LINE_SEP);
            writer.write(fileEnd.getBytes(StandardCharsets.UTF_8));
        }
    }

    @SneakyThrows
    public void writePemBouncy(String outFile, Object outObject) {
        try (var pemWriter = new JcaPEMWriter(new FileWriter(outFile))) {
            pemWriter.writeObject(outObject);
        }
    }
}
