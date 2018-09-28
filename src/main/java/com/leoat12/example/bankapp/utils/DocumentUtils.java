package com.leoat12.example.bankapp.utils;

import java.util.InputMismatchException;

public class DocumentUtils {

    public static Boolean isCPF(String CPF) {

        CPF = CPF.replaceAll("\\.", "");
        CPF = CPF.replaceAll("-", "");

        if(CPF.matches("(^[1]{11}$)|(^[2]{11}$)|(^[3]{11}$)|(^[4]{11}$)|(^[5]{11}$)|(^[6]{11}$)|(^[7]{11}$)|(^[8]{11}$)|(^[9]{11}$)"))
            return false;
        else if(CPF.length() < 11 || CPF.length() > 11)
            return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    public static String printCPF(String CPF) {
        return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }
}
