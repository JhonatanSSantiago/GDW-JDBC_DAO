/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhssantiago.jdbcdaomaven.dao;

/**
 *
 * @author jhons
 */
public class ErroBancoException extends Exception{
    public ErroBancoException() {
        super("Erro na base de dados");
    }

    public ErroBancoException(String message) {
        super(message);
    }

    public ErroBancoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroBancoException(Throwable cause) {
        super(cause);
    }

    public ErroBancoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
