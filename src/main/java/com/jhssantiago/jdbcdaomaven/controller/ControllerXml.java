/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhssantiago.jdbcdaomaven.controller;

import com.jhssantiago.jdbcdaomaven.dao.ErroDAOException;
import com.jhssantiago.jdbcdaomaven.dao.PessoaDaoXml;
import com.jhssantiago.jdbcdaomaven.dao.PessoaDaoInterface;
import com.jhssantiago.jdbcdaomaven.model.Pessoa;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jhons
 */
public class ControllerXml extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String caminho = getServletContext().getRealPath("/WEB-INF/pessoas.xml");
        try ( PrintWriter out = response.getWriter()) {

            String nome = request.getParameter("nome");
            int idade = Integer.parseInt(request.getParameter("idade"));
            String codigoText = request.getParameter("codigo");

            Pessoa p = new Pessoa();
            p.setIdade(idade);
            p.setNome(nome);

            PessoaDaoInterface dao = null;
            try {
                dao = new PessoaDaoXml(caminho);
                
                if (codigoText != null) {
                    int codigo = Integer.parseInt(codigoText);
                    dao.deletarPessoa(codigo);
                    out.print("Deletado com sucesso");
                }
                
                dao.criaPessoa(p);
                out.print("Inserido com sucesso");               
            } catch (ErroDAOException ex) {
                out.print("Erro ao tentar inserir");
            } finally {
                try {
                    dao.sair();
                } catch (ErroDAOException ex) {
                    out.print("Erro ao tentar fechar");
                }
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
