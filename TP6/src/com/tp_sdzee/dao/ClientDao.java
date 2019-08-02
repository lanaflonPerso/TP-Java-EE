package com.tp_sdzee.dao;

import java.util.List;

import com.tp_sdzee.beans.Client;

public interface ClientDao {

    void creer( Client client ) throws DAOException;

    Client trouver( long id ) throws DAOException;

    void supprimer( Client client ) throws DAOException;

    List<Client> lister() throws DAOException;

}
