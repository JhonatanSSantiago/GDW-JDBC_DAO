package com.jhssantiago.jdbccommaven.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jhons
 */
@WebServlet(urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String nome = request.getParameter("nome");
        String idade = request.getParameter("idade");
        String rem = request.getParameter("remover");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BancoComPreparedStatement</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>Teste Banco de Dados</h1>");
            Connection con = null;
            PreparedStatement stm = null;
            PreparedStatement rm = null;
            ResultSet rs = null;
            
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teste?serverTimezone=UTC","jhss", "1998");
                out.print("<p>Conectado com sucesso");
                
                if(nome != null && idade != null){
                    stm=con.prepareStatement("insert into Pessoa values(null,?,?)");
                    stm.setString(1,nome);
                    stm.setInt(2, Integer.parseInt(idade));
                    stm.executeUpdate();
                    out.print("<p>inserido com sucesso");
                }
                /**/
                if (rem != null){
                     rm=con.prepareStatement("delete from Pessoa where codigo=?");
                     rm.setInt(1, Integer.parseInt(rem));
                     rm.execute();
                }
             //  

                stm = con.prepareStatement("select * from Pessoa");
                rs = stm.executeQuery();

                out.print("<table border=1><tr>");
                ResultSetMetaData md = rs.getMetaData();
                int quant = md.getColumnCount();
                for (int i = 1; i <= quant; i++) {
                    out.print("<th>" + md.getColumnName(i) + "</th>");
                }
                out.print("</tr>");
                while (rs.next()) {
                    int codigo = rs.getInt("codigo");
                    String name = rs.getString("nome");
                    int age = rs.getInt("idade");
                    out.print("<tr><td>" + codigo + "</td><td>" + name + "</td><td>"+ age + "</td><td><a href=http://localhost:8080/JDBCcomMaven/controller?remover="+codigo+">Excluir</a></td></tr>");                   

                }
                out.print("</table>");
                
            } catch (ClassNotFoundException ex) {
                out.print(ex.toString());
            } catch (SQLException ex) {
                out.print(ex.toString());
            } catch (Exception ex) {
                out.print(ex.toString());
            }

            out.println("</body>");
            out.println("</html>");
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
