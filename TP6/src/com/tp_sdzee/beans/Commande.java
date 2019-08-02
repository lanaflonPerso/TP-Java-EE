package com.tp_sdzee.beans;

import org.joda.time.DateTime;

public class Commande {

    private Long     id;
    private Client   client;
    private DateTime dateCommande;
    private double   montantCommande;
    private String   modePaiementCommande;
    private String   statutPaiementCommande;
    private String   modeLivraisonCommande;
    private String   statutLivraisonCommande;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient( Client client ) {
        this.client = client;
    }

    public DateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande( DateTime dateCommande ) {
        this.dateCommande = dateCommande;
    }

    public double getMontantCommande() {
        return montantCommande;
    }

    public void setMontantCommande( double montantCommande ) {
        this.montantCommande = montantCommande;
    }

    public String getModePaiementCommande() {
        return modePaiementCommande;
    }

    public void setModePaiementCommande( String modePaiementCommande ) {
        this.modePaiementCommande = modePaiementCommande;
    }

    public String getStatutPaiementCommande() {
        return statutPaiementCommande;
    }

    public void setStatutPaiementCommande( String statutPaiementCommande ) {
        this.statutPaiementCommande = statutPaiementCommande;
    }

    public String getModeLivraisonCommande() {
        return modeLivraisonCommande;
    }

    public void setModeLivraisonCommande( String modeLivraisonCommande ) {
        this.modeLivraisonCommande = modeLivraisonCommande;
    }

    public String getStatutLivraisonCommande() {
        return statutLivraisonCommande;
    }

    public void setStatutLivraisonCommande( String statutLivraisonCommande ) {
        this.statutLivraisonCommande = statutLivraisonCommande;
    }

}
