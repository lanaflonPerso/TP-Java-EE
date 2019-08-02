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
import com.tp_sdzee.dao.CommandeDao;
import com.tp_sdzee.dao.DAOException;
import com.tp_sdzee.dao.DAOFactory;

public class SuppressionCommande extends HttpServlet {

    public static final String ATT_SESSION_COMMANDES = "sessionCommandes";
    public static final String ATT_DATE_COMMANDE     = "dateCommande";
    public static final String ATT_ID_COMMANDE       = "idCommande";
    public static final String VUE                   = "/WEB-INF/listerCommandes.jsp";
    public static final String CONF_DAO_FACTORY      = "daofactory";

    private CommandeDao        commandeDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Commande */
        this.commandeDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCommandeDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        HttpSession session = request.getSession();

        Map<Long, Commande> commandes = (HashMap<Long, Commande>) session.getAttribute( ATT_SESSION_COMMANDES );

        Long idCommande = Long.parseLong( SuppressionClient.getValeurChamp( request, ATT_ID_COMMANDE ) );

        if ( idCommande != null && !commandes.isEmpty() ) {

            try {
                commandeDao.supprimer( commandes.get( idCommande ) );
            } catch ( DAOException e ) {
                e.printStackTrace();
            }

            commandes.remove( idCommande );

            session.setAttribute( ATT_SESSION_COMMANDES, commandes );
        }

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

}
