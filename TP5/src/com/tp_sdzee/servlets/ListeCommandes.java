package com.tp_sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListeCommandes extends HttpServlet {

    public static final String VUE = "/WEB-INF/listerCommandes.jsp";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /*
         * Juste besoin de rediriger sur la JSP car on va accéder aux paramètres
         * de la session et non pas de la requête
         */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

}
