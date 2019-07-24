package com.tp_sdzee.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tp_sdzee.beans.Client;

public class CreationClientForm {

    public static final String  FIELD_NAME    = "nomClient";
    public static final String  FIELD_SURNAME = "prenomClient";
    public static final String  FIELD_ADDRESS = "adresseClient";
    public static final String  FIELD_PHONE   = "telephoneClient";
    public static final String  FIELD_EMAIL   = "emailClient";

    private String              resultat;
    private Map<String, String> erreurs       = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Client creerClient( HttpServletRequest request ) {

        /*
         * Récupération des données saisies, envoyées en tant que paramètres de
         * la requête POST générée à la validation du formulaire
         */
        String nomClient = getValeurChamp( request, FIELD_NAME );
        String prenomClient = getValeurChamp( request, FIELD_SURNAME );
        String adresseClient = getValeurChamp( request, FIELD_ADDRESS );
        String telephoneClient = getValeurChamp( request, FIELD_PHONE );
        String emailClient = getValeurChamp( request, FIELD_EMAIL );

        /*
         * Création du bean Client et initialisation avec les données récupérées
         */
        Client client = new Client();

        try {
            validationNom( nomClient );
        } catch ( Exception e ) {
            setErreur( FIELD_NAME, e.getMessage() );
        }
        client.setNomClient( nomClient );

        try {
            validationPrenom( prenomClient );
        } catch ( Exception e ) {
            setErreur( FIELD_SURNAME, e.getMessage() );
        }
        client.setPrenomClient( prenomClient );

        try {
            validationAdresse( adresseClient );
        } catch ( Exception e ) {
            setErreur( FIELD_ADDRESS, e.getMessage() );
        }
        client.setAdresseClient( adresseClient );

        try {
            validationTelephone( telephoneClient );
        } catch ( Exception e ) {
            setErreur( FIELD_PHONE, e.getMessage() );
        }
        client.setTelephoneClient( telephoneClient );

        try {
            validationEmail( emailClient );
        } catch ( Exception e ) {
            setErreur( FIELD_EMAIL, e.getMessage() );
        }
        client.setEmailClient( emailClient );

        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la création du client.";
        } else {
            resultat = "Échec de la création du client.";
        }

        return client;

    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
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

    static void validationNom( String nom ) throws Exception {
        if ( nom != null ) {
            if ( nom.length() < 2 ) {
                throw new Exception( "Merci de saisir un nom d'au moins 2 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir un nom." );
        }
    }

    static void validationPrenom( String prenom ) throws Exception {
        if ( prenom != null && prenom.length() < 2 ) {
            throw new Exception( "Merci de saisir un prénom d'au moins 2 caractères." );
        }
    }

    static void validationAdresse( String adresse ) throws Exception {
        if ( adresse != null ) {
            if ( adresse.length() < 10 ) {
                throw new Exception( "Merci de saisir une adresse d'au moins 10 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir une adresse." );
        }
    }

    static void validationTelephone( String telephone ) throws Exception {
        if ( telephone != null ) {
            if ( telephone.length() > 3 ) {
                if ( !telephone.matches( "^\\d+$" ) ) {
                    throw new Exception( "Merci de rentrer uniquement des chiffres de 0 à 9" );
                }
            } else {
                throw new Exception( "Merci de saisir un numéro d'au moins 4 chiffres" );
            }
        } else {
            throw new Exception( "Merci de saisir un numéro de téléphone." );
        }
    }

    static void validationEmail( String email ) throws Exception {
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse email valide." );
        }
    }

}
