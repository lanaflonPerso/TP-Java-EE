package com.tp_sdzee.dao;

import java.util.List;

import com.tp_sdzee.beans.Commande;

public interface CommandeDao {

    void creer( Commande commande ) throws DAOException;

    Commande trouver( long id ) throws DAOException;

    void supprimer( Commande commande ) throws DAOException;

    List<Commande> lister() throws DAOException;

}
