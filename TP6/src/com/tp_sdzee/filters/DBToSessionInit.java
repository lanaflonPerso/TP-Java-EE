package com.tp_sdzee.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tp_sdzee.beans.Client;
import com.tp_sdzee.beans.Commande;
import com.tp_sdzee.dao.ClientDao;
import com.tp_sdzee.dao.CommandeDao;
import com.tp_sdzee.dao.DAOFactory;

public class DBToSessionInit implements Filter {

    public static final String CONF_DAO_FACTORY      = "daofactory";
    public static final String ATT_SESSION_CLIENTS   = "sessionClients";
    public static final String ATT_SESSION_COMMANDES = "sessionCommandes";

    private ClientDao          clientDao;
    private CommandeDao        commandeDao;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
            throws IOException, ServletException {
        /* Récupération de la session depuis la requête avec cast */
        HttpSession session = ( (HttpServletRequest) request ).getSession();

        // Si la Map clients est vide, on va aller chercher les clients dans la
        // DB
        if ( session.getAttribute( ATT_SESSION_CLIENTS ) == null ) {
            List<Client> listeClients = clientDao.lister();
            Map<Long, Client> clients = new HashMap<Long, Client>();
            for ( Client client : listeClients ) {
                Long idClient = client.getId();

                clients.put( idClient, client );
            }
            session.setAttribute( ATT_SESSION_CLIENTS, clients );
        }

        // On fait la même chose avec les commandes
        if ( session.getAttribute( ATT_SESSION_COMMANDES ) == null ) {
            List<Commande> listeCommandes = commandeDao.lister();
            Map<Long, Commande> commandes = new HashMap<Long, Commande>();
            for ( Commande commande : listeCommandes ) {
                Long idCommande = commande.getId();

                commandes.put( idCommande, commande );
            }
            session.setAttribute( ATT_SESSION_COMMANDES, commandes );
        }

        chain.doFilter( request, response );

    }

    @Override
    public void init( FilterConfig config ) throws ServletException {
        /* Récupération d'une instance de notre DAO Client */
        this.clientDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        /* Récupération d'une instance de notre DAO Commande */
        this.commandeDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) )
                .getCommandeDao();
    }

}
