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
import com.tp_sdzee.dao.ClientDao;
import com.tp_sdzee.dao.DAOException;
import com.tp_sdzee.dao.DAOFactory;

public class SuppressionClient extends HttpServlet {

    public static final String ATT_SESSION_CLIENTS = "sessionClients";
    public static final String ATT_NOM_CLIENT      = "nomClient";
    public static final String ATT_ID_CLIENT       = "idClient";
    public static final String VUE                 = "/WEB-INF/listerClients.jsp";
    public static final String CONF_DAO_FACTORY    = "daofactory";

    private ClientDao          clientDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Client */
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        HttpSession session = request.getSession();

        Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( ATT_SESSION_CLIENTS );

        Long idClient = Long.parseLong( getValeurChamp( request, ATT_ID_CLIENT ) );

        if ( idClient != null && !clients.isEmpty() ) {

            try {
                clientDao.supprimer( clients.get( idClient ) );
            } catch ( DAOException e ) {
                e.printStackTrace();
            }

            clients.remove( idClient );

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
