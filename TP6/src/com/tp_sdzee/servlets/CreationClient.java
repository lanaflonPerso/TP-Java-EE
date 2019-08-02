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
import com.tp_sdzee.dao.DAOFactory;
import com.tp_sdzee.forms.CreationClientForm;

public class CreationClient extends HttpServlet {

    // Constantes
    public static final String CHEMIN              = "chemin";
    public static final String VUE_GET             = "/WEB-INF/creerClient.jsp";
    public static final String VUE_POST            = "/WEB-INF/afficherClient.jsp";
    public static final String ATT_FORM            = "form";
    public static final String ATT_CLIENT          = "client";
    public static final String ATT_SESSION_CLIENTS = "sessionClients";
    public static final String CONF_DAO_FACTORY    = "daofactory";

    private ClientDao          clientDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Client */
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE_GET ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        /*
         * Lecture du paramètre 'chemin' passé à la servlet via la déclaration
         * dans le web.xml
         */
        String chemin = this.getServletConfig().getInitParameter( CHEMIN );

        /* Préparation de l'objet formulaire */
        CreationClientForm form = new CreationClientForm( clientDao );

        /*
         * Appel au traitement et à la validation de la requête, et récupération
         * du bean en résultant
         */
        Client client = form.creerClient( request, chemin );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_CLIENT, client );

        /*
         * Si le formulaire est valide, on ajoute le client à la Map de clients
         * et on passe à la page d'afichage, sinon la Map n'est pas mise à jour
         * et on reste sur la page de création de client
         */
        if ( form.getErreurs().isEmpty() ) {
            /* Récupération de la session depuis la requête */
            HttpSession session = request.getSession();

            Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( ATT_SESSION_CLIENTS );

            /* Si la Map est vide, on l'initialise */
            if ( clients == null ) {
                clients = new HashMap<Long, Client>();
            }

            clients.put( client.getId(), client );

            /*
             * On passe la Map dans la session pour la retrouver lors du
             * prochain post
             */
            session.setAttribute( ATT_SESSION_CLIENTS, clients );

            this.getServletContext().getRequestDispatcher( VUE_POST ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( VUE_GET ).forward( request, response );
        }

    }

}
