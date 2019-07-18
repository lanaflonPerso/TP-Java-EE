<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Afficher la commande</title>
		<link type="text/css" rel="stylesheet" href=<c:url value="inc/style.css"/> />
	</head>
	<body>
		<c:import url="inc/menu.jsp"/>
		<p class="info"> ${message} </p>
		<c:if test="${isFilled}">
			<p>Client </p>
			<p>Nom : <c:out value="${commande.client.nomClient}"/> </p>
			<p>Prénom : <c:out value="${commande.client.prenomClient}"/> </p>
			<p>Adresse : <c:out value="${commande.client.adresseClient}"/> </p>
			<p>Numéro de téléphone : <c:out value="${commande.client.telephoneClient}"/> </p>
			<p>Email : <c:out value="${commande.client.emailClient}"/> </p>
			<p>Commande </p>
			<p>Date : <c:out value="${commande.dateCommande}"/> </p>
			<p>Montant : <c:out value="${commande.montantCommande}"/> </p>
			<p>Mode de paiement : <c:out value="${commande.modePaiementCommande}"/> </p>
			<p>Statut du paiement : <c:out value="${commande.statutPaiementCommande}"/> </p>
			<p>Mode de livraison : <c:out value="${commande.modeLivraisonCommande}"/> </p>
			<p>Statut de la livraison : <c:out value="${commande.statutLivraisonCommande}"/> </p>
		</c:if>
	</body>
</html>