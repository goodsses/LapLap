package com.sh.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

public class CipherUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CipherUtil.class);
    private static final String SALT = "happyNewYear209913141314";
    private static final String IV = "0000000000000000";
    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

    public CipherUtil() {
    }

    public static String encodeURIComponent(String input) {
        if (null != input && !"".equals(input.trim())) {
            int l = input.length();
            StringBuilder o = new StringBuilder(l * 3);

            try {
                for(int i = 0; i < l; ++i) {
                    String e = input.substring(i, i + 1);
                    if (!"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()".contains(e)) {
                        byte[] b = e.getBytes("utf-8");
                        o.append("%").append(byteToHex(b).toUpperCase());
                    } else {
                        o.append(e);
                    }
                }

                return o.toString();
            } catch (UnsupportedEncodingException var6) {
                var6.printStackTrace();
                return input;
            }
        } else {
            return input;
        }
    }

    public static String md5Hex(Object obj) {
        String str = "";
        if (null != obj) {
            try {
                str = byteToHex(MessageDigest.getInstance("MD5").digest(objectToByteArrayOfString(obj)));
            } catch (NoSuchAlgorithmException var3) {
                LOGGER.error("md5 error", var3);
            }
        }

        return str;
    }

    public static String decodeURIComponent(String encodedURI) {
        StringBuilder buffer = new StringBuilder();
        int sub = 0;
        int i = 0;

        for(int more = -1; i < encodedURI.length(); ++i) {
            char actualChar = encodedURI.charAt(i);
            int bytePattern;
            switch(actualChar) {
                case '%':
                    ++i;
                    actualChar = encodedURI.charAt(i);
                    int hb = (Character.isDigit(actualChar) ? actualChar - 48 : 10 + Character.toLowerCase(actualChar) - 97) & 15;
                    ++i;
                    actualChar = encodedURI.charAt(i);
                    int lb = (Character.isDigit(actualChar) ? actualChar - 48 : 10 + Character.toLowerCase(actualChar) - 97) & 15;
                    bytePattern = hb << 4 | lb;
                    break;
                case '+':
                    bytePattern = 32;
                    break;
                default:
                    bytePattern = actualChar;
            }

            if ((bytePattern & 192) == 128) {
                sub = sub << 6 | bytePattern & 63;
                --more;
                if (more == 0) {
                    buffer.append((char)sub);
                }
            } else if ((bytePattern & 128) == 0) {
                buffer.append((char)bytePattern);
            } else if ((bytePattern & 224) == 192) {
                sub = bytePattern & 31;
                more = 1;
            } else if ((bytePattern & 240) == 224) {
                sub = bytePattern & 15;
                more = 2;
            } else if ((bytePattern & 248) == 240) {
                sub = bytePattern & 7;
                more = 3;
            } else if ((bytePattern & 252) == 248) {
                sub = bytePattern & 3;
                more = 4;
            } else {
                sub = bytePattern & 1;
                more = 5;
            }
        }

        return buffer.toString();
    }

    public static String shaHex(Object obj) {
        String str = "";
        if (null != obj) {
            try {
                str = byteToHex(MessageDigest.getInstance("SHA").digest(objectToByteArrayOfString(obj)));
            } catch (NoSuchAlgorithmException var3) {
                LOGGER.error("sha error", var3);
            }
        }

        return str;
    }

    public static String sha1Hex(Object obj) {
        String str = "";
        if (null != obj) {
            try {
                str = byteToHex(MessageDigest.getInstance("SHA-1").digest(objectToByteArrayOfString(obj)));
            } catch (NoSuchAlgorithmException var3) {
                LOGGER.error("sha1 error", var3);
            }
        }

        return str;
    }

    public static String hMacSha1Hex(Object obj, Object key) {
        String str = "";
        if (null != obj) {
            try {
                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init(new SecretKeySpec(key.toString().getBytes(), "HmacSHA1"));
                byte[] macBytes = mac.doFinal(objectToByteArrayOfString(obj));
                return encryptBase64String(macBytes);
            } catch (Exception var5) {
                LOGGER.error("hMacSha1Hex error", var5);
            }
        }

        return str;
    }

    public static String encryptBase64String(Object obj) {
        return null != obj ? Base64.getEncoder().encodeToString(objectToByteArrayOfString(obj)) : "";
    }

    public static byte[] encryptBase64ByteArray(Object obj) {
        return null != obj ? Base64.getEncoder().encode(objectToByteArrayOfString(obj)) : new byte[0];
    }

    public static String decryptBase64String(Object obj) {
        return null != obj ? new String(Base64.getDecoder().decode(objectToByteArrayOfString(obj))) : "";
    }

    public static byte[] decryptBase64ByteArray(Object obj) {
        return null != obj ? Base64.getDecoder().decode(objectToByteArrayOfString(obj)) : new byte[0];
    }

    public static String encryptDesString(Object obj, String secretKey) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key key = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec("66668888".getBytes());
            cipher.init(1, key, ips);
            byte[] encryptData = cipher.doFinal(objectToByteArrayOfString(obj));
            return encryptBase64String(encryptData);
        } catch (Exception var8) {
            var8.printStackTrace();
            return String.valueOf(obj);
        }
    }

    public static String decryptDesString(Object obj, String secretKey) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
            Key key = keyFactory.generateSecret(new DESedeKeySpec(secretKey.getBytes()));
            Cipher cipher = Cipher.getInstance("desede/CBC/NoPadding");
            IvParameterSpec ips = new IvParameterSpec("66668888".getBytes());
            cipher.init(2, key, ips);
            return (new String(cipher.doFinal(decryptBase64ByteArray(obj)))).trim();
        } catch (Exception var6) {
            var6.printStackTrace();
            return String.valueOf(obj);
        }
    }

    public static String encryptAesString(String src) {
        return encryptAesString(src, "happyNewYear209913141314");
    }

    public static String encryptAesString(String src, String salt) {
        try {
            return encryptBase64String(encryptAesByteArray(src, getSecretKey(salt), getIv("0000000000000000")));
        } catch (Exception var3) {
            LOGGER.error("getSecretKey error", var3);
            return src;
        }
    }

    private static byte[] encryptAesByteArray(String src, SecretKey k, IvParameterSpec iv) {
        if (null != src) {
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(1, k, iv);
                return cipher.doFinal(src.getBytes());
            } catch (Exception var4) {
                LOGGER.error("encryptAesByteArray error", var4);
            }
        }

        return new byte[0];
    }

    public static String decryptAesString(String src) {
        return decryptAesString(src, "happyNewYear209913141314");
    }

    public static String decryptAesString(String src, String salt) {
        try {
            return new String(decryptAesByteArray(src, getSecretKey(salt), getIv("0000000000000000")));
        } catch (Exception var3) {
            LOGGER.error("getSecretKey error", var3);
            return src;
        }
    }

    private static byte[] decryptAesByteArray(String src, SecretKey k, IvParameterSpec iv) {
        if (null != src) {
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(2, k, iv);
                return cipher.doFinal(decryptBase64ByteArray(src));
            } catch (Exception var4) {
                LOGGER.error("decryptAesByteArray error", var4);
            }
        }

        return new byte[0];
    }

    public static String encryptRsaPublicString(Object obj, Object keyObj) {
        return encryptBase64String(encryptRsaPublicByteArray(obj, keyObj));
    }

    public static byte[] encryptRsaPublicByteArray(Object obj, Object keyObj) {
        if (null != obj) {
            try {
                KeyFactory factory = KeyFactory.getInstance("RSA");
                Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
                cipher.init(1, factory.generatePublic(new X509EncodedKeySpec(objectToByteArrayOfBase64(keyObj))));
                return cipher.doFinal(objectToByteArrayOfString(obj));
            } catch (Exception var4) {
                LOGGER.error("encryptRsaPublicByteArray error", var4);
            }
        }

        return new byte[0];
    }

    public static String decryptRsaPublicString(Object obj, Object keyObj) {
        return new String(decryptRsaPublicByteArray(obj, keyObj));
    }

    public static byte[] decryptRsaPublicByteArray(Object obj, Object keyObj) {
        if (null != obj) {
            try {
                KeyFactory factory = KeyFactory.getInstance("RSA");
                Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
                cipher.init(2, factory.generatePublic(new X509EncodedKeySpec(objectToByteArrayOfBase64(keyObj))));
                return cipher.doFinal(objectToByteArrayOfBase64(obj));
            } catch (Exception var4) {
                LOGGER.error("decryptRsaPublicByteArray error", var4);
            }
        }

        return new byte[0];
    }

    public static String signatureRsaPrivateString(Object obj, Object keyObj) {
        if (null != obj) {
            try {
                Signature signature = Signature.getInstance("MD5withRSA");
                signature.initSign(KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(objectToByteArrayOfBase64(keyObj))));
                signature.update(objectToByteArrayOfBase64(obj));
                return encryptBase64String(signature.sign());
            } catch (Exception var3) {
                LOGGER.error("signatureRsaPrivateString error", var3);
            }
        }

        return "";
    }

    public static boolean verifySignatureRsaPublic(Object obj, Object signObj, Object keyObj) {
        if (null != keyObj) {
            try {
                Signature signature = Signature.getInstance("MD5withRSA");
                signature.initVerify(KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(objectToByteArrayOfBase64(keyObj))));
                signature.update(objectToByteArrayOfBase64(obj));
                return signature.verify(objectToByteArrayOfBase64(signObj));
            } catch (Exception var4) {
                LOGGER.error("verifySignatureRsaPublic error", var4);
            }
        }

        return false;
    }

    public static String encryptRsaPrivateString(Object obj, Object keyObj) {
        return encryptBase64String(encryptRsaPrivateByteArray(obj, keyObj));
    }

    public static byte[] encryptRsaPrivateByteArray(Object obj, Object keyObj) {
        if (null != obj) {
            try {
                KeyFactory factory = KeyFactory.getInstance("RSA");
                Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
                cipher.init(1, factory.generatePrivate(new PKCS8EncodedKeySpec(objectToByteArrayOfBase64(keyObj))));
                return cipher.doFinal(objectToByteArrayOfString(obj));
            } catch (Exception var4) {
                LOGGER.error("", var4);
            }
        }

        return new byte[0];
    }

    public static String decryptRsaPrivateString(Object obj, Object keyObj) {
        return new String(decryptRsaPrivateByteArray(obj, keyObj));
    }

    public static byte[] decryptRsaPrivateByteArray(Object obj, Object keyObj) {
        if (null != obj) {
            try {
                KeyFactory factory = KeyFactory.getInstance("RSA");
                Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
                cipher.init(2, factory.generatePrivate(new PKCS8EncodedKeySpec(objectToByteArrayOfBase64(keyObj))));
                return cipher.doFinal(objectToByteArrayOfBase64(obj));
            } catch (Exception var4) {
                LOGGER.error("", var4);
            }
        }

        return new byte[0];
    }

    private static String byteToHex(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        byte[] var2 = bytes;
        int var3 = bytes.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            int c = b & 255;
            if (c < 16) {
                sb.append("0");
            }

            sb.append(Integer.toHexString(c));
        }

        return sb.toString();
    }

    private static byte[] hexToByte(String hex) {
        byte[] bytes = new byte[hex.length() / 2];

        for(int i = 0; i < hex.length(); i += 2) {
            bytes[i / 2] = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
        }

        return bytes;
    }

    private static SecretKey getSecretKey(String salt) throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(salt.getBytes());
        generator.init(256, secureRandom);
        return generator.generateKey();
    }

    private static IvParameterSpec getIv(String iv) {
        return new IvParameterSpec(iv.getBytes());
    }

    public static String[] generateKeyString() {
        byte[][] bytes = generateKeyByteArray();
        return new String[]{encryptBase64String(bytes[0]), encryptBase64String(bytes[1])};
    }

    private static byte[][] generateKeyByteArray() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            return new byte[][]{keyPair.getPublic().getEncoded(), keyPair.getPrivate().getEncoded()};
        } catch (NoSuchAlgorithmException var2) {
            LOGGER.error("", var2);
            return new byte[0][0];
        }
    }

    private static byte[] objectToByteArrayOfString(Object obj) {
        return obj.getClass().isArray() ? (byte[])((byte[])obj) : obj.toString().getBytes();
    }

    private static byte[] objectToByteArrayOfBase64(Object obj) {
        return obj.getClass().isArray() ? (byte[])((byte[])obj) : decryptBase64ByteArray(obj);
    }

    public static String decryptCifm(String value) {
        if (StringUtils.isNotBlank(value)) {
            String passwordPrefix = "_CIFM_";
            String secretKey = "6668888#dad@cifm#6668888";
            String iv = "66668888";
            int length = passwordPrefix.length();
            if (value.startsWith(passwordPrefix)) {
                value = value.substring(length);

                try {
                    value = URLDecoder.decode(value, "UTF-8");
                    if (value.contains(" ")) {
                        value = value.replaceAll(" ", "+");
                    }

                    DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
                    Key key = keyFactory.generateSecret(spec);
                    Cipher cipher = Cipher.getInstance("desede/CBC/NoPadding");
                    IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
                    cipher.init(2, key, ips);
                    return (new String(cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(value)), "UTF-8")).trim();
                } catch (Exception var10) {
                }
            }
        }

        return value;
    }

    public static String encryptCifmRandom(String id) {
        return encryptAesString(StringUtils.defaultString(id, UUID.randomUUID().toString().replaceAll("-", "")) + "." + System.currentTimeMillis());
    }
}
