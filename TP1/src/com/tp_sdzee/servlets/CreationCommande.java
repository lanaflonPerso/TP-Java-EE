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
		
		// Création du bean Client et initialisation avec les données reçues
		Client client = new Client();
		
		client.setNomClient(nomClient);
		client.setPrenomClient(prenomClient);
		client.setAdresseClient(adresseClient);
		client.setTelephoneClient(telephoneClient);
		client.setEmailClient(emailClient);
		
		/* Récupération de la date courante */
		DateTime date = new DateTime();
		/* Conversion de la date en String selon le format défini */
        DateTimeFormatter formatter = DateTimeFormat.forPattern( "dd/MM/yyyy HH:mm:ss" );
        String dateCommande = date.toString( formatter );
        
		Double montantCommande;
		try {
			/* Récupération du montant */
			montantCommande = Double.parseDouble(request.getParameter("montantCommande"));
		}
		catch (NumberFormatException e){
			/* Initialisation à 0 si le montant n'est pas un nombre */
			montantCommande = 0.0;
		}
		
		String modePaiementCommande = request.getParameter("modePaiementCommande");
		String statutPaiementCommande = request.getParameter("statutPaiementCommande");
		String modeLivraisonCommande = request.getParameter("modeLivraisonCommande");
		String statutLivraisonCommande = request.getParameter("statutLivraisonCommande");
		
		// Création du bean Commande et initialisation avec les données reçues
		Commande commande = new Commande();
		
		commande.setClient(client);
		commande.setDateCommande(dateCommande);
		commande.setMontantCommande(montantCommande);
		commande.setModePaiementCommande(modePaiementCommande);
		commande.setStatutPaiementCommande(statutPaiementCommande);
		commande.setModeLivraisonCommande(modeLivraisonCommande);
		commande.setStatutLivraisonCommande(statutLivraisonCommande);
		
		/*
         * Initialisation du message à afficher : si un des champs obligatoires
         * du formulaire n'est pas renseigné, alors on affiche un message
         * d'erreur, sinon on affiche un message de succès
         */
		String message = "Commande créée avec succès";
		if(nomClient == "" || adresseClient == "" || telephoneClient == "" || dateCommande == "" ||
		   montantCommande == 0.0 || modePaiementCommande == "" || modeLivraisonCommande == "")
			message = "Erreur - Vous n'avez pas rempli tous les champs obligatoires. <br/>" + 
					   "<a href=\"creerCommande.jsp\" >Cliquez ici</a> pour accéder au formulaire de création d'une commande";
		
		/* Ajout du bean et du message à l'objet requête */
		request.setAttribute("commande", commande);
		request.setAttribute("message", message);
		
		/* Transmission à la page JSP en charge de l'affichage des données */
		this.getServletContext().getRequestDispatcher("/afficherCommande.jsp").forward(request, response);
		
	}

}
