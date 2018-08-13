package com.android.common.core.cipher;

import java.security.MessageDigest;

/**
 * SHA加密
 */
public class SHA {
    public static byte[] encrypt(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(CipherType.SHA.getType());
        sha.update(data);
        return sha.digest();
    }
}
