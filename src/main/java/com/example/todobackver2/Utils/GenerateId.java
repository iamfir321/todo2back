package com.example.todobackver2.Utils;

import java.security.SecureRandom;
import java.util.Random;

public class GenerateId {
    private final Random RANDOM=new SecureRandom();
    private final String ALPHABET="0123456789ABCDEFGHJKLMNPQRSTVXZWYabcdefghjklmnpqrstvxzwy";

    public String generateId(int length){
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue=new StringBuilder(length);

        for (int i=0;i<length;i++)
        {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
}
