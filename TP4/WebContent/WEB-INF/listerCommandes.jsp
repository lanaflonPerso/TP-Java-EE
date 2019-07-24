<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<link type="text/css" rel="stylesheet" href=<c:url value="/inc/style.css"/> />
	</head>
	<body>
		<c:import url="/inc/menu.jsp"/>
		<c:choose>
			<c:when test="${not empty sessionScope.sessionCommandes}">
				<table>
					<thead>
						<th>Client</th>
						<th>Date</th>
						<th>Montant</th>
						<th>Mode de paiement</th>
						<th>Statut de paiement</th>
						<th>Mode de livraison</th>
						<th>Statut de livraison</th>
						<th class="action">Action</th>
					</thead>
					<tbody>
					<c:forEach items="${sessionScope.sessionCommandes}" var="commande" varStatus="status">
						<tr class="${status.index % 2 == 0 ? 'pair' : 'impair'}">
							<td><c:out value="${commande.value.client.prenomClient}
							${commande.value.client.nomClient}" /></td>
							<td><c:out value="${commande.value.dateCommande}" /></td>
							<td><c:out value="${commande.value.montantCommande}" /></td>
							<td><c:out value="${commande.value.modePaiementCommande}" /></td>
							<td><c:out value="${commande.value.statutPaiementCommande}" /></td>
							<td><c:out value="${commande.value.modeLivraisonCommande}" /></td>
							<td><c:out value="${commande.value.statutLivraisonCommande}" /></td>
							<td class="action"><a href="<c:url value="/suppressionCommande">
							<c:param name="dateCommande" value="${commande.value.dateCommande}" />
							</c:url>"><img src="./inc/supprimer.png" /></a></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<span class="erreur">Aucune commande enregistrée.</span>
			</c:otherwise>
		</c:choose>
	</body>
</html>