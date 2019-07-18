package com.tp_sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.tp_sdzee.beans.Client;
import com.tp_sdzee.beans.Commande;

public class CreationCommande extends HttpServlet {

    // Constantes
    public static final String VUE               = "/afficherCommande.jsp";
    public static final String FIELD_NAME        = "nomClient";
    public static final String FIELD_SURNAME     = "prenomClient";
    public static final String FIELD_ADDRESS     = "adresseClient";
    public static final String FIELD_PHONE       = "telephoneClient";
    public static final String FIELD_EMAIL       = "emailClient";
    public static final String FIELD_AMOUNT      = "montantCommande";
    public static final String FIELD_PAYMODE     = "modePaiementCommande";
    public static final String FIELD_PAYSTATUS   = "statutPaiementCommande";
    public static final String FIELD_DELIVMODE   = "modeLivraisonCommande";
    public static final String FIELD_DELIVSTATUS = "statutLivraisonCommande";
    public static final String DATE_FORMAT       = "dd/MM/yyyy HH:mm:ss";
    public static final String ATT_MSG           = "message";
    public static final String ATT_ORDR          = "commande";
    public static final String ATT_ISFILLED      = "isFilled";
    public static final String SUCC_MSG          = "Commande créée avec succès";
    public static final String ERR_MSG           = "Erreur - Vous n'avez pas rempli tous les champs obligatoires. <br/>"
            +
            "<a href=\"creerCommande.jsp\" >Cliquez ici</a> pour accéder au formulaire de création d'une commande";

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

        // Création du bean Client et initialisation avec les données reçues
        Client client = new Client();

        client.setNomClient( nomClient );
        client.setPrenomClient( prenomClient );
        client.setAdresseClient( adresseClient );
        client.setTelephoneClient( telephoneClient );
        client.setEmailClient( emailClient );

        /* Récupération de la date courante */
        DateTime date = new DateTime();
        /* Conversion de la date en String selon le format défini */
        DateTimeFormatter formatter = DateTimeFormat.forPattern( DATE_FORMAT );
        String dateCommande = date.toString( formatter );

        Double montantCommande;
        try {
            /* Récupération du montant */
            montantCommande = Double.parseDouble( request.getParameter( FIELD_AMOUNT ) );
        } catch ( NumberFormatException e ) {
            /* Initialisation à 0 si le montant n'est pas un nombre */
            montantCommande = 0.0;
        }

        String modePaiementCommande = request.getParameter( FIELD_PAYMODE );
        String statutPaiementCommande = request.getParameter( FIELD_PAYSTATUS );
        String modeLivraisonCommande = request.getParameter( FIELD_DELIVMODE );
        String statutLivraisonCommande = request.getParameter( FIELD_DELIVSTATUS );

        // Création du bean Commande et initialisation avec les données reçues
        Commande commande = new Commande();

        commande.setClient( client );
        commande.setDateCommande( dateCommande );
        commande.setMontantCommande( montantCommande );
        commande.setModePaiementCommande( modePaiementCommande );
        commande.setStatutPaiementCommande( statutPaiementCommande );
        commande.setModeLivraisonCommande( modeLivraisonCommande );
        commande.setStatutLivraisonCommande( statutLivraisonCommande );

        /*
         * Initialisation du message à afficher : si un des champs obligatoires
         * du formulaire n'est pas renseigné, alors on affiche un message
         * d'erreur, sinon on affiche un message de succès
         */
        boolean isFilled = true;
        String message = SUCC_MSG;
        if ( nomClient == "" || adresseClient == "" || telephoneClient == "" || dateCommande == "" ||
                montantCommande == 0.0 || modePaiementCommande == "" || modeLivraisonCommande == "" ) {
            message = ERR_MSG;
            isFilled = false;
        }

        /* Ajout du bean et du message à l'objet requête */
        request.setAttribute( ATT_ORDR, commande );
        request.setAttribute( ATT_MSG, message );
        request.setAttribute( ATT_ISFILLED, isFilled );

        /* Transmission à la page JSP en charge de l'affichage des données */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

    }

}
