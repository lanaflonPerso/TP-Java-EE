package com.tp_sdzee.beans;

import org.joda.time.DateTime;

public class Commande {
	
	private Client client;
	private String dateCommande;
	private double montantCommande;
	private String modePaiementCommande;
	private String statutPaiementCommande;
	private String modeLivraisonCommande;
	private String statutLivraisonCommande;
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public String getDateCommande() {
		return dateCommande;
	}
	
	public void setDateCommande(String dateCommande) {
		this.dateCommande = dateCommande;
	}
	
	public double getMontantCommande() {
		return montantCommande;
	}
	
	public void setMontantCommande(double montantCommande) {
		this.montantCommande = montantCommande;
	}
	
	public String getModePaiementCommande() {
		return modePaiementCommande;
	}
	
	public void setModePaiementCommande(String modePaiementCommande) {
		this.modePaiementCommande = modePaiementCommande;
	}
	
	public String getStatutPaiementCommande() {
		return statutPaiementCommande;
	}
	
	public void setStatutPaiementCommande(String statutPaiementCommande) {
		this.statutPaiementCommande = statutPaiementCommande;
	}
	
	public String getModeLivraisonCommande() {
		return modeLivraisonCommande;
	}
	
	public void setModeLivraisonCommande(String modeLivraisonCommande) {
		this.modeLivraisonCommande = modeLivraisonCommande;
	}
	
	public String getStatutLivraisonCommande() {
		return statutLivraisonCommande;
	}
	
	public void setStatutLivraisonCommande(String statutLivraisonCommande) {
		this.statutLivraisonCommande = statutLivraisonCommande;
	}

}
