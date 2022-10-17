package com.group17.server;
import com.group17.JSONObjects.Username_Token;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class TokenList {
    private static ReentrantLock lock = new ReentrantLock();

    volatile static ArrayList<Username_Token> tokens = new ArrayList<Username_Token>();

    public static void addToken(Username_Token token) {
        lock.lock();
        System.out.println("Adding token");
        tokens.add(token);
        lock.unlock();
    }



    public static boolean isValidToken(String tokenToCheck) {
        try {
            lock.lock();
            if (tokens.isEmpty()) {
                return false;
            } else {
                for (Username_Token token : tokens) {
                    if (token.getToken().equals(tokenToCheck)) return true;
                }
                return false;
            }

        } finally {
            lock.unlock();
        }

    }
    public static boolean isValidToken(byte[] tokenToCheck) {
        String tokenString = new String(tokenToCheck);
        return isValidToken(tokenString);
    }


    public static String getUser(String tokenToCheck) {
        try {
            lock.lock();
            if (tokens.isEmpty()) {
                return null;
            } else {
                for (Username_Token token : tokens) {
                    if (token.getToken().equals(tokenToCheck)) return token.getUsername();
                }
                return null;
            }

        } finally {
            lock.unlock();
        }
    }
    public static String getUser(byte[] tokenToCheck) {
        String tokenString = new String(tokenToCheck);
        return getUser(tokenString);
    }


    public static void print() {
        lock.lock();
        if (tokens.isEmpty()) {
            System.out.println("Tokens empty");
        } else {
            for (Username_Token token : tokens) {
                System.out.println("User: " + token.getUsername() + " Token: " + token.getToken());
            }
        }
        lock.unlock();
    }

}
