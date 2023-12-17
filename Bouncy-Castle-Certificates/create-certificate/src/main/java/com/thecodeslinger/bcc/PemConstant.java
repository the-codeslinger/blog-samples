package com.thecodeslinger.bcc;

public abstract class PemConstant {

    // Well-known PEM headers according to <a href="https://datatracker.ietf.org/doc/html/rfc7468">RFC 7468</a>.
    public static final String BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----";
    public static final String END_CERTIFICATE = "-----END CERTIFICATE-----";

    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";

    // Created by Bouncy Castle JcaPEMWriter but not part of above RFC.
    public static final String BEGIN_RSA_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----";
    public static final String END_RSA_PRIVATE_KEY = "-----END RSA PRIVATE KEY-----";
}
