/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhssantiago.jdbcdaomaven.dao;

import com.jhssantiago.jdbcdaomaven.model.Pessoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jhons
 */
public class PessoaDaoBanco implements PessoaDaoInterface {
 Connection con;
    public PessoaDaoBanco() throws ErroDAOException
    {
        FabricaConexao fabrica=new FabricaConexao();
        con=fabrica.pegaConexao();
    }
    @Override
    public void criaPessoa(Pessoa p) throws ErroDAOException {
        
        try {
            PreparedStatement ps=con.prepareStatement("insert into Pessoa values(null,?,?)");
            ps.setString(1,p.getNome());
            ps.setInt(2, p.getIdade());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new ErroDAOException(ex);
        }
    }

    @Override
    public List<Pessoa> pegaPessoas() throws ErroDAOException {
        try {
            ArrayList<Pessoa> grupo=new ArrayList<>();
            PreparedStatement ps=con.prepareStatement("select * from Pessoa");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                Pessoa p=new Pessoa();
                p.setCodigo(rs.getInt("codigo"));
                p.setIdade(rs.getInt("idade"));
                p.setNome(rs.getString("nome"));
                grupo.add(p);
            }
            return grupo;
        } catch (SQLException ex) {
            throw new ErroDAOException(ex);
        }
    }
    public void sair() throws ErroDAOException 
    {
        try {
            con.close();
        } catch (SQLException ex) {
            throw new ErroDAOException("Erro ao sair",ex);
        }
    }

    @Override
    public Pessoa pegaPessoa(int codigo) throws ErroDAOException {
        /*PreparedStatement ps=con.prepareStatement("select * from Pessoa where codigo=?");
        ps.setInt(1, codigo);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            Pessoa p=new Pessoa();
            p.setCodigo(rs.getInt("codigo"));
            p.setIdade(rs.getInt("idade"));
            p.setNome(rs.getString("nome"));
            return p;
        }*/
        return null;
    }

    @Override
    public void deletarPessoa(int codigo) throws ErroDAOException {
        /*PreparedStatement ps=con.prepareStatement("delete from Pessoa where codigo=?");
        ps.setInt(1, codigo);
        ps.executeUpdate();
        ps.close();*/
    }

    @Override
    public void deletarPessoa(Pessoa p) throws ErroDAOException {
        //deletarPessoa(p.getCodigo());
    }

    @Override
    public void editarPessoa(Pessoa p) throws ErroDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {
        try {
            PessoaDaoBanco dao=new PessoaDaoBanco();
            System.out.println("conectado com sucesso");
            Pessoa p=new Pessoa();
            /*p.setNome("José");
            p.setIdade(40);
            dao.criaPessoa(p);
            System.out.println("inserido com sucesso");
            */
            List<Pessoa> pessoas=dao.pegaPessoas();
            for(int i=0;i<pessoas.size();i++)
            {
                System.out.println(pessoas.get(i));
            }
            System.out.print("\n\n\nPessoa de código: 3");
            System.out.print(dao.pegaPessoa(3));
            
            //dao.deletarPessoa(5);
            
            //p.setCodigo(4);
            //dao.deletarPessoa(p);
            
            dao.sair();
            System.out.println("Saiu com sucesso");
        } catch (ErroDAOException ex) {
            ex.printStackTrace();
        }
    }

}
