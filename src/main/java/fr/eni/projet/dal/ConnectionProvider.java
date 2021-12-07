package fr.eni.projet.dal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Loareos // Etienne // Copie exacte d'un ancien TP
 *
 */


public class ConnectionProvider {
	private static DataSource dataSource;
	
	/**
	 * Au chargement de la classe, la DataSource est recherchée dans l'arbre JNDI
	 */
	static
	{
		Context context;
		try {
			context = new InitialContext();
			ConnectionProvider.dataSource = (DataSource)context.lookup("java:comp/env/jdbc/pool_cnx"); // N'OUBLIEZ PAS DE FAIRE VOTRE PROPRE FICHIER CONTEXT !!!
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException("Impossible d'accéder à la base de données");
		}
	}
	
	/**
	 * Cette méthode retourne une connection opérationnelle issue du pool de connexion
	 * vers la base de données. 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException
	{
		return ConnectionProvider.dataSource.getConnection();
	}
}
