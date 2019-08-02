package com.tp_sdzee.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.tp_sdzee.beans.Client;
import com.tp_sdzee.beans.Commande;
import com.tp_sdzee.dao.ClientDao;
import com.tp_sdzee.dao.CommandeDao;
import com.tp_sdzee.dao.DAOException;

public class CreationCommandeForm {

    public static final String  FIELD_NAME          = "nomClient";
    public static final String  FIELD_SURNAME       = "prenomClient";
    public static final String  FIELD_ADDRESS       = "adresseClient";
    public static final String  FIELD_PHONE         = "telephoneClient";
    public static final String  FIELD_EMAIL         = "emailClient";
    public static final String  FIELD_CLIENT        = "client";
    public static final String  FIELD_AMOUNT        = "montantCommande";
    public static final String  FIELD_PAYMODE       = "modePaiementCommande";
    public static final String  FIELD_PAYSTATUS     = "statutPaiementCommande";
    public static final String  FIELD_DELIVMODE     = "modeLivraisonCommande";
    public static final String  FIELD_DELIVSTATUS   = "statutLivraisonCommande";
    private static final String FIELD_DAO_COMMANDE  = "erreurDAOCommande";
    public static final String  DATE_FORMAT         = "dd/MM/yyyy HH:mm:ss";
    public static final String  ATT_SESSION_CLIENTS = "sessionClients";

    private String              resultat;
    private Map<String, String> erreurs             = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    private ClientDao   clientDao;
    private CommandeDao commandeDao;

    public CreationCommandeForm( ClientDao clientDao, CommandeDao commandeDao ) {
        this.clientDao = clientDao;
        this.commandeDao = commandeDao;
    }

    public Commande creerCommande( HttpServletRequest request, String chemin ) {

        String clientKey = CreationClientForm.getValeurChamp( request, FIELD_CLIENT );

        Client client = new Client();

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        // On récupère les clients de la session
        Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( ATT_SESSION_CLIENTS );

        /*
         * On check si l'utilisateur a choisi un client existant, sinon on passe
         * sur la création d'un nouveau client
         */
        if ( clientKey != "" && clientKey != null ) {
            // On doit retrouver l'id du client choisi
            client = clients.get( Long.parseLong( clientKey ) );
        } else {
            // On peut réutiliser le travail déjà fait sur les clients
            CreationClientForm clientForm = new CreationClientForm( clientDao );
            client = clientForm.creerClient( request, chemin );

            // On met le client créé dans la Map clients de la session s'il est
            // valide, mais pas dans la DB car c'est déjà fait par le formulaire
            if ( clientForm.getErreurs().isEmpty() ) {
                // On initialise la Map si elle est nulle
                if ( clients == null ) {
                    clients = new HashMap<Long, Client>();
                }

                clients.put( client.getId(), client );

            }

            session.setAttribute( ATT_SESSION_CLIENTS, clients );

            // On récupère aussi les erreurs trouvées
            erreurs = clientForm.getErreurs();
        }

        // Création du bean Commande et initialisation avec les données reçues
        Commande commande = new Commande();

        /* Récupération de la date courante */
        DateTime date = new DateTime();
        /*
         * Conversion de la date en String selon le format défini, plus besoin
         * de faire ça DateTimeFormatter formatter = DateTimeFormat.forPattern(
         * DATE_FORMAT ); String dateCommande = date.toString( formatter );
         */

        // Récupération du montant sous format string pour le test
        String montant = CreationClientForm.getValeurChamp( request, FIELD_AMOUNT );
        // On essaie ensuite de le passer en double, avec 0 comme valeur par
        // défaut
        double montantCommande = 0;
        try {
            montantCommande = Double.parseDouble( montant );
        } catch ( NumberFormatException e ) {
            e.printStackTrace();
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }

        String modePaiementCommande = CreationClientForm.getValeurChamp( request, FIELD_PAYMODE );
        String statutPaiementCommande = CreationClientForm.getValeurChamp( request, FIELD_PAYSTATUS );
        String modeLivraisonCommande = CreationClientForm.getValeurChamp( request, FIELD_DELIVMODE );
        String statutLivraisonCommande = CreationClientForm.getValeurChamp( request, FIELD_DELIVSTATUS );

        // Les tests ont déjà été effectués sur le client
        commande.setClient( client );
        /*
         * On utilise maintenant l'objet DateTime pour la date, pas de
         * vérification à faire
         */
        commande.setDateCommande( date );

        // On fait le test sur la valeur en format string !
        try {
            validationMontant( montant );
        } catch ( Exception e ) {
            setErreur( FIELD_AMOUNT, e.getMessage() );
        }
        commande.setMontantCommande( montantCommande );

        try {
            validationModePaiement( modePaiementCommande );
        } catch ( Exception e ) {
            setErreur( FIELD_PAYMODE, e.getMessage() );
        }
        commande.setModePaiementCommande( modePaiementCommande );

        try {
            validationStatutPaiement( statutPaiementCommande );
        } catch ( Exception e ) {
            setErreur( FIELD_PAYSTATUS, e.getMessage() );
        }
        commande.setStatutPaiementCommande( statutPaiementCommande );

        try {
            validationModeLivraison( modeLivraisonCommande );
        } catch ( Exception e ) {
            setErreur( FIELD_DELIVMODE, e.getMessage() );
        }
        commande.setModeLivraisonCommande( modeLivraisonCommande );

        try {
            validationStatutLivraison( statutLivraisonCommande );
        } catch ( Exception e ) {
            setErreur( FIELD_DELIVSTATUS, e.getMessage() );
        }
        commande.setStatutLivraisonCommande( statutLivraisonCommande );

        try {
            if ( erreurs.isEmpty() ) {
                commandeDao.creer( commande );
                resultat = "Succès de la création de la commande.";
            } else {
                resultat = "Échec de la création de la commande.";
            }
        } catch ( DAOException e ) {
            setErreur( FIELD_DAO_COMMANDE, "Erreur imprévue lors de la création de la commande" );
            resultat = "Échec de la création de la commande: une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return commande;
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    static void validationMontant( String montant ) throws Exception {
        if ( montant != null ) {
            // un nombre, à virgule ou non
            if ( !montant.matches( "^\\d+\\.{0,1}\\d*$" ) ) {
                throw new Exception( "Merci de saisir un nombre positif." );
            }
        } else {
            throw new Exception( "Merci de saisir un montant." );
        }
    }

    static void validationModePaiement( String modePaiement ) throws Exception {
        if ( modePaiement != null ) {
            if ( modePaiement.length() < 2 ) {
                throw new Exception( "Merci de saisir un mode de paiement d'au moins 2 lettres." );
            }
        } else {
            throw new Exception( "Merci de saisir un mode de paiement." );
        }
    }

    static void validationStatutPaiement( String statutPaiement ) throws Exception {
        if ( statutPaiement != null && statutPaiement.length() < 2 ) {
            throw new Exception( "Merci de saisir un statut de paiement d'au moins 2 lettres." );
        }
    }

    static void validationModeLivraison( String modeLivraison ) throws Exception {
        if ( modeLivraison != null ) {
            if ( modeLivraison.length() < 2 ) {
                throw new Exception( "Merci de saisir un mode de livraison d'au moins 2 lettres." );
            }
        } else {
            throw new Exception( "Merci de saisir un mode de livraison." );
        }
    }

    static void validationStatutLivraison( String statutLivraison ) throws Exception {
        if ( statutLivraison != null && statutLivraison.length() < 2 ) {
            throw new Exception( "Merci de saisir un statut de livraison d'au moins 2 lettres." );
        }
    }

}
