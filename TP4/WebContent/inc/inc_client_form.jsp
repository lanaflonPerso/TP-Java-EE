<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<legend>Informations client</legend>
    
<label for="nomClient">Nom <span class="requis">*</span></label>
<input type="text" id="nomClient" name="nomClient" value="<c:out value="${client.nomClient}" />" size="20" maxlength="20" />
<span class="erreur">${form.erreurs['nomClient']}</span>
<br />
                    
<label for="prenomClient">Prénom </label>
<input type="text" id="prenomClient" name="prenomClient" value="<c:out value="${client.prenomClient}" />" size="20" maxlength="20" />
<span class="erreur">${form.erreurs['prenomClient']}</span>
<br />
    
<label for="adresseClient">Adresse de livraison <span class="requis">*</span></label>
<input type="text" id="adresseClient" name="adresseClient" value="<c:out value="${client.adresseClient}" />" size="20" maxlength="20" />
<span class="erreur">${form.erreurs['adresseClient']}</span>
<br />
    
<label for="telephoneClient">Numéro de téléphone <span class="requis">*</span></label>
<input type="text" id="telephoneClient" name="telephoneClient" value="<c:out value="${client.telephoneClient}" />" size="20" maxlength="20" />
<span class="erreur">${form.erreurs['telephoneClient']}</span>
<br />
                    
<label for="emailClient">Adresse email</label>
<input type="email" id="emailClient" name="emailClient" value="<c:out value="${client.emailClient}" />" size="20" maxlength="60" />
<span class="erreur">${form.erreurs['emailClient']}</span>
<br />