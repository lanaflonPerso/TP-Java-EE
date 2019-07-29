package com.tp_sdzee.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.tp_sdzee.beans.Client;

import eu.medsea.mimeutil.MimeUtil;

public class CreationClientForm {

    public static final String  FIELD_NAME    = "nomClient";
    public static final String  FIELD_SURNAME = "prenomClient";
    public static final String  FIELD_ADDRESS = "adresseClient";
    public static final String  FIELD_PHONE   = "telephoneClient";
    public static final String  FIELD_EMAIL   = "emailClient";
    private static final String FIELD_FILE    = "imageClient";
    private static final int    TAILLE_TAMPON = 10240;                        // 10ko

    private String              resultat;
    private Map<String, String> erreurs       = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Client creerClient( HttpServletRequest request, String chemin ) {

        /*
         * Récupération des données saisies, envoyées en tant que paramètres de
         * la requête POST générée à la validation du formulaire
         */
        String nomClient = getValeurChamp( request, FIELD_NAME );
        String prenomClient = getValeurChamp( request, FIELD_SURNAME );
        String adresseClient = getValeurChamp( request, FIELD_ADDRESS );
        String telephoneClient = getValeurChamp( request, FIELD_PHONE );
        String emailClient = getValeurChamp( request, FIELD_EMAIL );
        String nomImage = null;

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

        try {
            nomImage = validationImage( request, chemin );
        } catch ( Exception e ) {
            setErreur( FIELD_FILE, e.getMessage() );
        }
        client.setNomImage( nomImage );

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

    String validationImage( HttpServletRequest request, String chemin ) throws Exception {

        String nomImage = null;
        InputStream contenuFichier = null;

        /*
         * Récupération du contenu du champ fichier du formulaire via la méthode
         * getPart()
         */
        try {
            Part part = request.getPart( FIELD_FILE );
            /*
             * Il faut déterminer s'il s'agit bien d'un champ de type fichier :
             * on délègue cette opération à la méthode utilitaire
             * getNomFichier().
             */
            nomImage = getNomFichier( part );

            /*
             * Si la méthode a renvoyé quelque chose, il s'agit donc d'un champ
             * de type fichier (input type="file").
             */
            if ( nomImage != null && !nomImage.isEmpty() ) {
                /*
                 * Antibug pour Internet Explorer, qui transmet pour une raison
                 * mystique le chemin du fichier local à la machine du client...
                 * 
                 * Ex : C:/dossier/sous-dossier/fichier.ext
                 * 
                 * On doit donc faire en sorte de ne sélectionner que le nom et
                 * l'extension du fichier, et de se débarrasser du superflu.
                 */
                nomImage = nomImage.substring( nomImage.lastIndexOf( '/' ) + 1 )
                        .substring( nomImage.lastIndexOf( '\\' ) + 1 );

                /* Récupération du contenu du fichier */
                contenuFichier = part.getInputStream();

                /*
                 * Extraction du type MIME du fichier depuis l'InputStream nommé
                 * "contenuFichier"
                 */
                MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
                Collection<?> mimeTypes = MimeUtil.getMimeTypes( contenuFichier );

                /*
                 * Si le fichier est bien une image, alors son en-tête MIME
                 * commence par la chaîne "image"
                 */
                if ( mimeTypes.toString().startsWith( "image" ) ) {
                    ecrireFichier( contenuFichier, nomImage, chemin );
                } else {
                    throw new Exception( "Le fichier uploadé n'est pas une image." );
                }

            }
        } catch ( IllegalStateException e ) {
            /*
             * Exception retournée si la taille des données dépasse les limites
             * définies dans la section <multipart-config> de la déclaration de
             * notre servlet d'upload dans le fichier web.xml
             */
            e.printStackTrace();
            throw new Exception( "Le fichier envoyé ne doit pas dépasser 1 Mo" );
        } catch ( IOException e ) {
            /*
             * Exception retournée si une erreur au niveau des répertoires de
             * stockage survient (répertoire inexistant, droits d'accès
             * insuffisants, etc.)
             */
            e.printStackTrace();
            throw new Exception( "Erreur de configuration du serveur." );
        } catch ( ServletException e ) {
            /*
             * Exception retournée si la requête n'est pas de type
             * multipart/form-data. Cela ne peut arriver que si l'utilisateur
             * essaie de contacter la servlet d'upload par un formulaire
             * différent de celui qu'on lui propose... pirate ! :|
             */
            e.printStackTrace();
            throw new Exception(
                    "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier." );
        }

        return nomImage;
    }

    /*
     * Méthode utilitaire qui a pour unique but d'analyser l'en-tête
     * "content-disposition", et de vérifier si le paramètre "filename" y est
     * présent. Si oui, alors le champ traité est de type File et la méthode
     * retourne son nom, sinon il s'agit d'un champ de formulaire classique et
     * la méthode retourne null.
     */
    private static String getNomFichier( Part part ) {
        /*
         * Boucle sur chacun des paramètres de l'en-tête "content-disposition".
         */
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            /* Recherche de l'éventuelle présence du paramètre "filename". */
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                /*
                 * Si "filename" est présent, alors renvoi de sa valeur,
                 * c'est-à-dire du nom de fichier sans guillemets.
                 */
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        /* Et pour terminer, si rien n'a été trouvé... */
        return null;
    }

    private void ecrireFichier( InputStream contenu, String nomFichier, String chemin ) throws Exception {
        /* Prépare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux. */
            entree = new BufferedInputStream( contenu, TAILLE_TAMPON );
            sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ),
                    TAILLE_TAMPON );

            /*
             * Lit le fichier reçu et écrit son contenu dans un fichier sur le
             * disque.
             */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur = 0;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }
        } catch ( Exception e ) {
            throw new Exception( "Erreur lors de l'écriture du fichier sur le disque" );
        } finally {
            try {
                sortie.close();
            } catch ( IOException ignore ) {
            }
            try {
                entree.close();
            } catch ( IOException ignore ) {
            }
        }
    }

}
