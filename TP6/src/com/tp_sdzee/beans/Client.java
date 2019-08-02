package com.tp_sdzee.beans;

public class Client {

    private Long   id;
    private String nomClient;
    private String prenomClient;
    private String adresseClient;
    private String telephoneClient;
    private String emailClient;
    private String nomImage;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient( String nomClient ) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient( String prenomClient ) {
        this.prenomClient = prenomClient;
    }

    public String getAdresseClient() {
        return adresseClient;
    }

    public void setAdresseClient( String adresseClient ) {
        this.adresseClient = adresseClient;
    }

    public String getTelephoneClient() {
        return telephoneClient;
    }

    public void setTelephoneClient( String telephoneClient ) {
        this.telephoneClient = telephoneClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient( String emailClient ) {
        this.emailClient = emailClient;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage( String nomImage ) {
        this.nomImage = nomImage;
    }

}
