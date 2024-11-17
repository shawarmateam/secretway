package com.utils;

import org.mindrot.jbcrypt.BCrypt;

public class GenPw {
    public static void main(String[] args) {
        String pw = "hui_penis";
        String hashed = BCrypt.hashpw(pw, BCrypt.gensalt());
        System.out.println(hashed);
    }
}
