/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhssantiago.jdbcdaomaven.dao;
import com.jhssantiago.jdbcdaomaven.model.Pessoa;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author jhons
 */
public interface PessoaDaoInterface {
    public void criaPessoa(Pessoa p) throws SQLException;
    public List<Pessoa> pegaPessoas()throws SQLException;
    public Pessoa pegaPessoa(int codigo) throws SQLException;
    public void deletarPessoa(int codigo) throws SQLException;
    public void deletarPessoa(Pessoa p) throws SQLException;
    public void editarPessoa(Pessoa p) throws SQLException;
    public void sair() throws ErroBancoException;
}
