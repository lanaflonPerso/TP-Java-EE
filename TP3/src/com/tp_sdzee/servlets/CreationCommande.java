package com.tp_sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tp_sdzee.beans.Commande;
import com.tp_sdzee.forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {

    // Constantes
    public static final String VUE_GET    = "/WEB-INF/creerCommande.jsp";
    public static final String VUE_POST   = "/WEB-INF/afficherCommande.jsp";
    public static final String ATT_FORM   = "form";
    public static final String ATT_ORDR   = "commande";
    public static final String ATT_CLIENT = "client";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE_GET ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        /* Préparation de l'objet formulaire */
        CreationCommandeForm form = new CreationCommandeForm();

        /*
         * Appel au traitement et à la validation de la requête, et récupération
         * du bean en résultant
         */
        Commande commande = form.creerCommande( request );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_ORDR, commande );
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_CLIENT, commande.getClient() );

        /*
         * Si le formulaire est valide, passe à la page d'afichage, sinon reste
         * sur la page de création
         */
        if ( form.getErreurs().isEmpty() ) {
            this.getServletContext().getRequestDispatcher( VUE_POST ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( VUE_GET ).forward( request, response );
        }

    }

}
