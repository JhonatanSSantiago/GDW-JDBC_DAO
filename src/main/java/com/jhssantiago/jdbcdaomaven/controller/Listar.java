package com.jhssantiago.jdbcdaomaven.controller;

import com.jhssantiago.jdbcdaomaven.dao.ErroDAOException;
import com.jhssantiago.jdbcdaomaven.dao.PessoaDaoBanco;
import com.jhssantiago.jdbcdaomaven.dao.PessoaDaoInterface;
import com.jhssantiago.jdbcdaomaven.model.Pessoa;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class Listar extends HttpServlet {

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
            throws ServletException, IOException, ErroDAOException {
        response.setContentType("text/xml;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            PessoaDaoInterface dao = null;
            List<Pessoa> pessoas = null;

            try {
                dao = new PessoaDaoBanco();
                pessoas = dao.pegaPessoas();
                out.print("<pessoas>");
                for (int i = 0; i < pessoas.size(); i++) {
                    Pessoa p1 = pessoas.get(i);
                    out.println(p1);
                }
                out.print("</pessoas>");
            } catch (ErroDAOException ex) {
                out.print("<p>Erro ao tentar ler os dados</p>");
            } finally {
                try {
                    dao.sair();
                } catch (ErroDAOException ex) {
                    out.print("<erro>Erro ao tentar sair!</erro>");
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
        try {
            processRequest(request, response);
        } catch (ErroDAOException ex) {
            Logger.getLogger(Listar.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ErroDAOException ex) {
            Logger.getLogger(Listar.class.getName()).log(Level.SEVERE, null, ex);
        }
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
