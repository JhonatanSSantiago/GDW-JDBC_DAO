/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhssantiago.jdbcdaomaven.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jhons
 */
public class FabricaConexao {
    public Connection pegaConexao() throws ErroDAOException {
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/banco_testes?serverTimezone=UTC", "jhss", "1998");           
        } catch (ClassNotFoundException | SQLException ex) {
            throw new ErroDAOException("Erro ao tentar se conectar", ex);
        }
        return con;
    }
}
