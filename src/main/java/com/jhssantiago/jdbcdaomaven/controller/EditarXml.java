/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhssantiago.jdbcdaomaven.controller;

import com.jhssantiago.jdbcdaomaven.dao.ErroDAOException;
import com.jhssantiago.jdbcdaomaven.dao.PessoaDaoInterface;
import com.jhssantiago.jdbcdaomaven.dao.PessoaDaoXml;
import com.jhssantiago.jdbcdaomaven.model.Pessoa;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jhons
 */
public class EditarXml extends HttpServlet {

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

            String codigoText = request.getParameter("editar");
            String nome = request.getParameter("nome");
            int idade = Integer.parseInt(request.getParameter("idade"));

            Pessoa p = null;

            PessoaDaoInterface dao = null;
            try {
                dao = new PessoaDaoXml(caminho);
                int codigo = Integer.parseInt(codigoText);
                p = dao.pegaPessoa(codigo);
                p.setIdade(idade);
                p.setNome(nome);
                dao.editarPessoa(p);
                out.print("Editado com sucesso");

            } catch (ErroDAOException ex) {
                out.print("Erro ao tentar editar");
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
