package com.group17.server;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;
public class SecurityFunctions {

    public static String PROVIDER ="SUN";
    public static String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    public static String HASH_FUNCTION = "SHA-256";

    private static final char[] CHARS_ARRAY = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[][] hashSaltFromPassword(String passwordString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_FUNCTION);
            SecureRandom srd = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM, PROVIDER);
            // Create random salt
            byte[] salt = new byte[20];
            srd.nextBytes(salt);
            // combine salt with a password
            String saltPlusPlainTextPassword = passwordString + new String(salt);
            // Hash password and salt
            messageDigest.update(saltPlusPlainTextPassword.getBytes(StandardCharsets.UTF_8));
            byte[][] toReturn = new byte[2][];
            toReturn[0] = messageDigest.digest();
            toReturn[1] = salt;
            return toReturn;

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static byte[] hashFromPassword(String passwordString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_FUNCTION);
            // Hash password and salt
            messageDigest.update(passwordString.getBytes(StandardCharsets.UTF_8));
            return messageDigest.digest();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean passwordsEqual(String plainPassword, String saltHexStr, String hashPswrdHexStr) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_FUNCTION);
            // Digest salt and plain password
            messageDigest.update(
                    (plainPassword + new String(hexStringToByteArray(saltHexStr))).getBytes(StandardCharsets.UTF_8));
            // Arrays with 2 passwords (from database, from user)
            byte[] hashedPasswordTocheck = messageDigest.digest();
            byte[] hashedPasswordStored = hexStringToByteArray(hashPswrdHexStr);
            return Arrays.equals(hashedPasswordTocheck, hashedPasswordStored);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }

    }
    public static String getRandomString() {
        try {
            StringBuilder sb = new StringBuilder();
            SecureRandom srd = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM, PROVIDER);
            for (int i = 0; i < 50; i++) {
                sb.append(CHARS_ARRAY[srd.nextInt(CHARS_ARRAY.length)]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }

    }

}
