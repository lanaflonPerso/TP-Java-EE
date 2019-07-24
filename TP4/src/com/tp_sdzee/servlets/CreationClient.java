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
import com.tp_sdzee.forms.CreationClientForm;

public class CreationClient extends HttpServlet {

    // Constantes
    public static final String VUE_GET             = "/WEB-INF/creerClient.jsp";
    public static final String VUE_POST            = "/WEB-INF/afficherClient.jsp";
    public static final String ATT_FORM            = "form";
    public static final String ATT_CLIENT          = "client";
    public static final String ATT_SESSION_CLIENTS = "sessionClients";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE_GET ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        /* Préparation de l'objet formulaire */
        CreationClientForm form = new CreationClientForm();

        /*
         * Appel au traitement et à la validation de la requête, et récupération
         * du bean en résultant
         */
        Client client = form.creerClient( request );

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

            Map<String, Client> clients = (HashMap<String, Client>) session.getAttribute( ATT_SESSION_CLIENTS );

            /* Si la Map est vide, on l'initialise */
            if ( clients == null ) {
                clients = new HashMap<String, Client>();
            }

            clients.put( client.getNomClient(), client );

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
