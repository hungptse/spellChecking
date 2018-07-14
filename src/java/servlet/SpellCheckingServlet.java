/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import daos.DAO;
import daos.Metaphone;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author THANH HUNG
 */
public class SpellCheckingServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String search = request.getParameter("txtSearch");
            if (search == null) {
                request.setAttribute("ERROR", "Search value can't be null!");
            } else {
                DAO dao = new DAO();
                Metaphone mp = new Metaphone();
                String meta = null;
                boolean check = search.matches("[a-zA-Z]+");
                if (check) {
                    meta = mp.encode(search);
                    List<String> data = dao.getWordList(meta);
                    boolean flag = false;
                    if (data != null) {
                        for (String string : data) {
                            if (search.equals(string)) {
                                flag = true;
                                request.setAttribute("CORRECT", string);
                                request.setAttribute("SEARCH", search);
                            }
                        }
                        List<String> result = new ArrayList<>();
                        if (!flag) {
                            for (String string : data) {
                                if (dao.editDistDP(search, string, search.length(), string.length()) == 1) {
                                    result.add(string);
                                }
                            }
                            if (!result.isEmpty()) {
                                request.setAttribute("DATA", result);
                                request.setAttribute("SEARCH", search);
                            } else {
                                request.setAttribute("ERROR", "Word can't check spelling!");

                            }
                        }
                    } else {
                        request.setAttribute("ERROR", "Word can't check spelling!");
                    }
                } else {
                    request.setAttribute("ERROR", "Word wrong format!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
