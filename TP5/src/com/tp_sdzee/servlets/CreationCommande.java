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
import com.tp_sdzee.forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {

    // Constantes
    public static final String CHEMIN                = "chemin";
    public static final String VUE_GET               = "/WEB-INF/creerCommande.jsp";
    public static final String VUE_POST              = "/WEB-INF/afficherCommande.jsp";
    public static final String ATT_FORM              = "form";
    public static final String ATT_ORDR              = "commande";
    public static final String ATT_CLIENT            = "client";
    public static final String ATT_SESSION_COMMANDES = "sessionCommandes";

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
        CreationCommandeForm form = new CreationCommandeForm();

        /*
         * Appel au traitement et à la validation de la requête, et récupération
         * du bean en résultant
         */
        Commande commande = form.creerCommande( request, chemin );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_ORDR, commande );
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_CLIENT, commande.getClient() );

        /*
         * Si le formulaire est valide, on ajoute la commande à la Map de
         * commandes et on passe à la page d'afichage, sinon la Map n'est pas
         * mise à jour et on reste sur la page de création de commande
         */
        if ( form.getErreurs().isEmpty() ) {
            /* Récupération de la session depuis la requête */
            HttpSession session = request.getSession();

            Map<String, Commande> commandes = (HashMap<String, Commande>) session.getAttribute( ATT_SESSION_COMMANDES );

            /* Si la Map est vide, on l'initialise */
            if ( commandes == null ) {
                commandes = new HashMap<String, Commande>();
            }

            commandes.put( commande.getDateCommande(), commande );

            /*
             * On passe la Map dans la session pour la retrouver lors du
             * prochain post
             */
            session.setAttribute( ATT_SESSION_COMMANDES, commandes );

            this.getServletContext().getRequestDispatcher( VUE_POST ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( VUE_GET ).forward( request, response );
        }

    }

}
