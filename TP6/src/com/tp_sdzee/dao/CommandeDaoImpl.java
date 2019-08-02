package com.tp_sdzee.dao;

import static com.tp_sdzee.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.tp_sdzee.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.tp_sdzee.beans.Commande;

public class CommandeDaoImpl implements CommandeDao {

    private static final String SQL_SELECT        = "SELECT id, id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison FROM Commande ORDER BY id";
    private static final String SQL_SELECT_PAR_ID = "SELECT id, id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison FROM Commande WHERE id = ?";
    private static final String SQL_INSERT        = "INSERT INTO Commande (id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Commande WHERE id = ?";

    private static DAOFactory   daoFactory;

    CommandeDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void creer( Commande commande ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    commande.getClient().getId(), new Timestamp( commande.getDateCommande().getMillis() ),
                    commande.getMontantCommande(),
                    commande.getModePaiementCommande(), commande.getStatutPaiementCommande(),
                    commande.getModeLivraisonCommande(), commande.getStatutLivraisonCommande() );
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de la commande, base de données non modifiée." );
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                /*
                 * Puis initialisation de la propriété id du bean Commande avec
                 * sa valeur seulement si elle a pu être érite dans la DB
                 */
                commande.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException(
                        "Échec de la création de la commande en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }

    }

    @Override
    public Commande trouver( long id ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Commande commande = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, id );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            if ( resultSet.next() ) {
                commande = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return commande;
    }

    @Override
    public void supprimer( Commande commande ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, false, commande.getId() );
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression de la commande, aucune ligne enlevée dans la table." );
            } else {
                commande.setId( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

    @Override
    public List<Commande> lister() throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Commande> commandes = new ArrayList<Commande>();
        Commande commande = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT, false );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours des lignes de données de l'éventuel ResulSet retourné
             */
            while ( resultSet.next() ) {
                commande = map( resultSet );
                commandes.add( commande );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return commandes;
    }

    private Commande map( ResultSet resultSet ) throws SQLException {
        Commande commande = new Commande();
        commande.setId( resultSet.getLong( "id" ) );
        /*
         * On instancie ClientDao pour utiliser la méthode trouver et retourner
         * le client correspondant au bon id_client
         */
        ClientDao clientDao = daoFactory.getClientDao();
        commande.setClient( clientDao.trouver( resultSet.getLong( "id_client" ) ) );
        // On récupère un objet JodaTime depuis la date récupérée
        commande.setDateCommande( new DateTime( resultSet.getDate( "date" ) ) );
        commande.setMontantCommande( resultSet.getDouble( "montant" ) );
        commande.setModePaiementCommande( resultSet.getString( "mode_paiement" ) );
        commande.setStatutPaiementCommande( resultSet.getString( "statut_paiement" ) );
        commande.setModeLivraisonCommande( resultSet.getString( "mode_livraison" ) );
        commande.setStatutLivraisonCommande( resultSet.getString( "statut_livraison" ) );
        return commande;
    }

}
