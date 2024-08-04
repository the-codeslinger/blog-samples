# Read And Write Asymmetric Keys With Java

Create keys with OpenSSL.

## RSA

_The GENPKEY Way_

Generate the private key and save it in PEM encoded format.
Convert the private key to DER encoding for Java to read.

```shell
openssl genpkey -algorithm RSA -out genpkey_rsa_private_key.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -inform PEM -in genpkey_rsa_private_key.pem -outform DER -out genpkey_rsa_private_key.der
```

Extracted the public key from the private key and save it in DER encoded format.

```shell
openssl rsa -pubout -inform PEM -in genpkey_rsa_private_key.pem -outform DER -out genpkey_rsa_public_key.der
```

_The GENRSA Way_

Only the initial statement is different, the rest is the same.

```shell
openssl genrsa -out genrsa_private_key.pem 2048
```

On the difference of `genrsa` vs `genpkey`: https://unix.stackexchange.com/a/415996

However, the latest version installed on my system (3.2.1) generates the same output for both commands, i.e., PKCS8.

## Elliptic Curve

Generate the private key and save it in PEM encoded format.
Convert the private key to DER encoding for Java to read.

```shell
openssl genpkey -algorithm EC -pkeyopt ec_paramgen_curve:prime256v1 -out genpkey_ec_private_key.pem
openssl pkcs8 -topk8 -nocrypt -inform PEM -in genpkey_ec_private_key.pem -outform DER -out genpkey_ec_private_key.der
```

Extracted the public key from the private key and save it in DER encoded format.

```shell
openssl ec -pubout -inform PEM -in genpkey_ec_private_key.pem -outform DER -out genpkey_ec_public_key.der
```

The following **does not** generate the correct result.
You must use the `pkcs8` command to convert the private key to DER encoding.

```shell
openssl ec -inform PEM -in genpkey_ec_private_key.pem -outform DER -out genpkey_ec_private_key.der
```

_The ECPARAM Way_

Using the `ecparam` command to generate the private key does not generate a PKCS8 encoded key.

```shell
openssl ecparam -name prime256v1 -genkey -noout -out ecparam_private_key.pem
```

Using the `pkcs8` command as shown earlier still creates a valid DER encoded private key from the `ecparam` output.