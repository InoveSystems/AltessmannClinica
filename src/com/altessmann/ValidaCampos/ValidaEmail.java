/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.altessmann.ValidaCampos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ritiele Aldeburg
 */
public class ValidaEmail {

    public static boolean isEmail(String email) {
        //System.out.println("Metodo de validacao de email");
        if (email.equals("")) {
            return (true);
        } else {
            Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
            Matcher m = p.matcher(email);
            if (m.find()) {
                //System.out.println("O email " + email + " e valido");
                return (true);
            } else {
                //System.out.println("O E-mail " + email + " é inválido");
                return (false);
            }
        }

    }

}
