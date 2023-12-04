package com.thecodeslinger.bcc;

import org.jetbrains.annotations.NotNull;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

public record KeyPairCertificate(@NotNull KeyPair keyPair, @NotNull X509Certificate certificate) {
}
