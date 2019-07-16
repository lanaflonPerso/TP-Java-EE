package com.tp_sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tp_sdzee.beans.Client;

public class CreationClient extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
         * Récupération des données saisies, envoyées en tant que paramètres de
         * la requête GET générée à la validation du formulaire
         */
		String nomClient = request.getParameter("nomClient");
		String prenomClient = request.getParameter("prenomClient");
		String adresseClient = request.getParameter("adresseClient");
		String telephoneClient = request.getParameter("telephoneClient");
		String emailClient = request.getParameter("emailClient");
		
		/*
         * Création du bean Client et initialisation avec les données récupérées
         */
		Client client = new Client();
		
		client.setNomClient(nomClient);
		client.setPrenomClient(prenomClient);
		client.setAdresseClient(adresseClient);
		client.setTelephoneClient(telephoneClient);
		client.setEmailClient(emailClient);
		
		/*
         * Initialisation du message à afficher : si un des champs obligatoires
         * du formulaire n'est pas renseigné, alors on affiche un message
         * d'erreur, sinon on affiche un message de succès
         */
		String message = "Client créé avec succès";
		if(nomClient == "" || adresseClient == "" || telephoneClient == "")
			message = "Erreur - Vous n'avez pas rempli tous les champs obligatoires. <br/>" + 
					   "<a href=\"creerClient.jsp\" >Cliquez ici</a> pour accéder au formulaire de création d'un client";
		
		 /* Ajout du bean et du message à l'objet requête */
		request.setAttribute("client", client);
		request.setAttribute("message", message);
		
		/* Transmission à la page JSP en charge de l'affichage des données */
		this.getServletContext().getRequestDispatcher("/afficherClient.jsp").forward(request, response);
		
	}

}
