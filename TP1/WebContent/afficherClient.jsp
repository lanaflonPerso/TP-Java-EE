<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Afficher le client créé</title>
		<link type="text/css" rel="stylesheet" href="inc/style.css" />
	</head>
	<body>
		<%-- Affichage de la chaîne "message" transmise par la servlet --%>
		<p class="info"> ${message} </p>
		<%-- Puis affichage des données enregistrées dans le bean "client" transmis par la servlet --%>
		<p>Nom : ${client.nomClient} </p>
		<p>Prénom : ${client.prenomClient} </p>
		<p>Adresse : ${client.adresseClient} </p>
		<p>Numéro de téléphone : ${client.telephoneClient} </p>
		<p>Email : ${client.emailClient }</p>
	</body>
</html>