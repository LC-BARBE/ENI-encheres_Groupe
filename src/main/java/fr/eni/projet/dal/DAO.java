package fr.eni.projet.dal;
/**
 * @author Loareos // Etienne
 *
 */


public interface DAO<T> {
	public void insert(T obj) throws DALException;
	public void update(T obj) throws DALException;
	public T selectById(int idObj) throws DALException;
	public void delete(int idObj) throws DALException;
}
