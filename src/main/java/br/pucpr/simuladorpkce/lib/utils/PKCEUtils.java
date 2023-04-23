package br.pucpr.simuladorpkce.lib.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PKCEUtils {
    public String generateRandomString(int length){
        Random r = new Random();

        return r.ints(48, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // fonte: https://www.baeldung.com/sha-256-hashing-java
    public String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
