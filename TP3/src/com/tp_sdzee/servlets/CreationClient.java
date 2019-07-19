package com.tp_sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tp_sdzee.beans.Client;
import com.tp_sdzee.forms.CreationClientForm;

public class CreationClient extends HttpServlet {

    // Constantes
    public static final String VUE_GET    = "/WEB-INF/creerClient.jsp";
    public static final String VUE_POST   = "/WEB-INF/afficherClient.jsp";
    public static final String ATT_FORM   = "form";
    public static final String ATT_CLIENT = "client";

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
