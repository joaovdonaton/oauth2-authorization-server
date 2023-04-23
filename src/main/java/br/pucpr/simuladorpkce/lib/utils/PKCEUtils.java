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
}
