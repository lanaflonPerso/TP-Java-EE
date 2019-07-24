package com.tp_sdzee.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tp_sdzee.beans.Client;

public class SuppressionClient extends HttpServlet {

    public static final String ATT_SESSION_CLIENTS = "sessionClients";
    public static final String ATT_NOM_CLIENT      = "nomClient";
    public static final String VUE                 = "/WEB-INF/listerClients.jsp";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        HttpSession session = request.getSession();

        Map<String, Client> clients = (HashMap<String, Client>) session.getAttribute( ATT_SESSION_CLIENTS );

        String nomClient = getValeurChamp( request, ATT_NOM_CLIENT );

        if ( nomClient != null && !clients.isEmpty() ) {

            clients.remove( nomClient );

            session.setAttribute( ATT_SESSION_CLIENTS, clients );
        }

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }

}
