<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Afficher la commande</title>
		<link type="text/css" rel="stylesheet" href="inc/style.css" />
	</head>
	<body>
		<p class="info">${message} </p>
		<p>Client </p>
		<p>Nom : ${commande.client.nomClient} </p>
		<p>Prénom : ${commande.client.prenomClient} </p>
		<p>Adresse : ${commande.client.adresseClient} </p>
		<p>Numéro de téléphone : ${commande.client.telephoneClient} </p>
		<p>Email : ${commande.client.emailClient} </p>
		<p>Commande </p>
		<p>Date : ${commande.dateCommande} </p>
		<p>Montant : ${commande.montantCommande} </p>
		<p>Mode de paiement : ${commande.modePaiementCommande} </p>
		<p>Statut du paiement : ${commande.statutPaiementCommande} </p>
		<p>Mode de livraison : ${commande.modeLivraisonCommande} </p>
		<p>Statut de la livraison : ${commande.statutLivraisonCommande} </p>
	</body>
</html>