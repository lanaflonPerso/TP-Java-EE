<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Liste des clients</title>
		<link type="text/css" rel="stylesheet" href=<c:url value="/inc/style.css"/> />
	</head>
	<body>
		<c:import url="/inc/menu.jsp"/>
		<c:choose>
			<c:when test="${not empty sessionScope.sessionClients}">
				<table>
					<thead>
						<th>Nom</th>
						<th>Prénom</th>
						<th>Adresse</th>
						<th>Téléphone</th>
						<th>Email</th>
						<th>Image</th>
						<th class="action">Action</th>
					</thead>
					<tbody>
					<c:forEach items="${sessionScope.sessionClients}" var="client" varStatus="status">
						<tr class="${status.index % 2 == 0 ? 'pair' : 'impair'}">
							<td><c:out value="${client.value.nomClient}" /></td>
							<td><c:out value="${client.value.prenomClient}" /></td>
							<td><c:out value="${client.value.adresseClient}" /></td>
							<td><c:out value="${client.value.telephoneClient}" /></td>
							<td><c:out value="${client.value.emailClient}" /></td>
							<td><c:if test="${not empty client.value.nomImage}"><a href="
							<c:url value="/downloadImage/${client.value.nomImage}">
							</c:url>">Voir</a></td></c:if>
							<td class="action"> <a href="<c:url value="/suppressionClient">
							<c:param name="idClient" value="${client.value.id}" />
							</c:url>"><img src="./inc/supprimer.png" /></a></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<span class="erreur">Aucun client enregistré.</span>
			</c:otherwise>
		</c:choose>
	</body>
</html>