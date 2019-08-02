package com.tp_sdzee.dao;

import static com.tp_sdzee.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.tp_sdzee.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tp_sdzee.beans.Client;

public class ClientDaoImpl implements ClientDao {

    private static final String SQL_SELECT        = "SELECT id, nom, prenom, adresse, telephone, email, image FROM Client ORDER BY id";
    private static final String SQL_SELECT_PAR_ID = "SELECT id, nom, prenom, adresse, telephone, email, image FROM Client WHERE id = ?";
    private static final String SQL_INSERT        = "INSERT INTO Client (nom, prenom, adresse, telephone, email, image) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Client WHERE id = ?";

    private DAOFactory          daoFactory;

    ClientDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void creer( Client client ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, client.getNomClient(),
                    client.getPrenomClient(), client.getAdresseClient(), client.getTelephoneClient(),
                    client.getEmailClient(), client.getNomImage() );
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création du client, base de données non modifiée." );
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                /*
                 * Puis initialisation de la propriété id du bean Client avec sa
                 * valeur
                 */
                client.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException(
                        "Échec de la création du client en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }

    }

    @Override
    public Client trouver( long id ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Client client = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, id );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            if ( resultSet.next() ) {
                client = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return client;
    }

    @Override
    public void supprimer( Client client ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        // ResultSet resultSet = null; inutile ici

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, false, client.getId() );
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression du client, aucune ligne enlevée dans la table." );
            } else {
                client.setId( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }

    }

    @Override
    public List<Client> lister() throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Client> clients = new ArrayList<Client>();
        Client client = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT, false );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours des lignes de données de l'éventuel ResulSet retourné
             */
            while ( resultSet.next() ) {
                client = map( resultSet );
                clients.add( client );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return clients;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des clients (un ResultSet) et
     * un bean Client.
     */
    private static Client map( ResultSet resultSet ) throws SQLException {
        Client client = new Client();
        client.setId( resultSet.getLong( "id" ) );
        client.setNomClient( resultSet.getString( "nom" ) );
        client.setPrenomClient( resultSet.getString( "prenom" ) );
        client.setAdresseClient( resultSet.getString( "adresse" ) );
        client.setTelephoneClient( resultSet.getString( "telephone" ) );
        client.setEmailClient( resultSet.getString( "email" ) );
        client.setNomImage( resultSet.getString( "image" ) );
        return client;
    }

}
