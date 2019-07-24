package com.tp_sdzee.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tp_sdzee.beans.Commande;

public class SuppressionCommande extends HttpServlet {

    public static final String ATT_SESSION_COMMANDES = "sessionCommandes";
    public static final String ATT_DATE_COMMANDE     = "dateCommande";
    public static final String VUE                   = "/WEB-INF/listerCommandes.jsp";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        HttpSession session = request.getSession();

        Map<String, Commande> commandes = (HashMap<String, Commande>) session.getAttribute( ATT_SESSION_COMMANDES );

        String dateCommande = SuppressionClient.getValeurChamp( request, ATT_DATE_COMMANDE );

        if ( dateCommande != null && !commandes.isEmpty() ) {

            commandes.remove( dateCommande );

            session.setAttribute( ATT_DATE_COMMANDE, commandes );
        }

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

}
