package com.tp_sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tp_sdzee.beans.Client;

public class CreationClient extends HttpServlet {

    // Constantes
    public static final String VUE           = "/afficherClient.jsp";
    public static final String FIELD_NAME    = "nomClient";
    public static final String FIELD_SURNAME = "prenomClient";
    public static final String FIELD_ADDRESS = "adresseClient";
    public static final String FIELD_PHONE   = "telephoneClient";
    public static final String FIELD_EMAIL   = "emailClient";
    public static final String ATT_MSG       = "message";
    public static final String ATT_CUST      = "client";
    public static final String ATT_ISFILLED  = "isFilled";
    public static final String SUCC_MSG      = "Client créé avec succès";
    public static final String ERR_MSG       = "Erreur - Vous n'avez pas rempli tous les champs obligatoires. <br/>" +
            "<a href=\"creerClient.jsp\" >Cliquez ici</a> pour accéder au formulaire de création d'un client";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        /*
         * Récupération des données saisies, envoyées en tant que paramètres de
         * la requête GET générée à la validation du formulaire
         */
        String nomClient = request.getParameter( FIELD_NAME );
        String prenomClient = request.getParameter( FIELD_SURNAME );
        String adresseClient = request.getParameter( FIELD_ADDRESS );
        String telephoneClient = request.getParameter( FIELD_PHONE );
        String emailClient = request.getParameter( FIELD_EMAIL );

        /*
         * Création du bean Client et initialisation avec les données récupérées
         */
        Client client = new Client();

        client.setNomClient( nomClient );
        client.setPrenomClient( prenomClient );
        client.setAdresseClient( adresseClient );
        client.setTelephoneClient( telephoneClient );
        client.setEmailClient( emailClient );

        /*
         * Initialisation du message à afficher : si un des champs obligatoires
         * du formulaire n'est pas renseigné, alors on affiche un message
         * d'erreur, sinon on affiche un message de succès
         */
        boolean isFilled = true;
        String message = SUCC_MSG;
        if ( nomClient == "" || adresseClient == "" || telephoneClient == "" ) {
            message = ERR_MSG;
            isFilled = false;
        }

        /* Ajout du bean et du message à l'objet requête */
        request.setAttribute( ATT_CUST, client );
        request.setAttribute( ATT_MSG, message );
        request.setAttribute( ATT_ISFILLED, isFilled );

        /* Transmission à la page JSP en charge de l'affichage des données */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

    }

}
