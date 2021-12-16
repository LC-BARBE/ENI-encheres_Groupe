/**
 * 
 */
package fr.eni.projet.BLL;

import java.time.LocalDate;

import fr.eni.projet.BusinessException;
import fr.eni.projet.BO.ArticleVendu;
import fr.eni.projet.BO.Categorie;
import fr.eni.projet.BO.Enchere;
import fr.eni.projet.BO.Retrait;
import fr.eni.projet.BO.Utilisateur;
import fr.eni.projet.dal.ArticleDAO;
import fr.eni.projet.dal.CategorieDAO;
import fr.eni.projet.dal.DAOFactory;
import fr.eni.projet.dal.EnchereDAO;
import fr.eni.projet.dal.RetraitDAO;
import fr.eni.projet.dal.UtilisateurDAO;

/**
 * @author /!\ /!\ /!\ /!\ /!\ /!\ Clement /!\ /!\ /!\ /!\ /!\ 
 *	Modif by
 */
public class EnchereManager {
	
	private EnchereDAO enchereDAO;
	private UtilisateurDAO userDao;
	private ArticleDAO articleDAO;

	//======================================================================//
	//								INSTANCE
	//======================================================================//

	private static EnchereManager instance;

	//=================================getInstance===========================================	

	public static EnchereManager getInstance() throws BusinessException{
		if(instance == null) {
			try {
				instance = new EnchereManager();
			} catch (Exception e) {
				e.printStackTrace();
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatBLL.GET_INSTANCE);
				throw businessException;
			}
		}
		return instance;
	}

	//======================================================================//
	//								Constructeurs
	//======================================================================//
	/**
	 *		Constructeur Vide
	 */

	public EnchereManager() throws BusinessException{
		enchereDAO = DAOFactory.getEnchereDAO();
		userDao = DAOFactory.getUserDAO();
		articleDAO = DAOFactory.getArticleDAO();
	}

	//==============================  AjouterEnchere  ===========================
	public Enchere ajouterEnchere(Utilisateur acheteur, ArticleVendu article, LocalDate dateEnchere, Integer montant) throws BusinessException {
		BusinessException exception = new BusinessException();

		Enchere enchere = new Enchere(acheteur, article, dateEnchere, montant);

		verif(enchere, exception);
		if (!this.userDao.idExist(acheteur.getNoUtilisateur()))
			exception.ajouterErreur(CodesResultatBLL.VENDEUR_INCONNU);
		if (!this.articleDAO.idExist(article.getNoArticle()))
			exception.ajouterErreur(CodesResultatBLL.ARTICLE_INCONNU);

		if (!exception.hasErreurs()) {
			if (this.enchereDAO.enchereExist(acheteur.getNoUtilisateur(), article.getNoArticle())) {
				this.enchereDAO.update(enchere);
			}
			if (!this.enchereDAO.enchereExist(acheteur.getNoUtilisateur(), article.getNoArticle())) {
				this.enchereDAO.insert(enchere);
			}
			return enchere;
		}else
			throw exception;
		
		
	}


	//========  MODIFICATION  ===========================================	
	
		public void modifEnchere(Integer noUtilisateur, Integer noArticle, LocalDate dateEnchere, Integer montant) throws BusinessException{
			BusinessException exception = new BusinessException();
			Retrait retrait = new Retrait(noArticle, rue, code_postal, ville);
			verif(retrait, exception);
			if(!exception.hasErreurs()) {
				this.retraitDAO.update(retrait);
			}else
				throw exception;
		}
		
	//========  SELECT BY SEARCH  ===========================================	
		
		public Retrait RetraitById(Integer id) throws BusinessException {
			BusinessException exception = new BusinessException();
	
			if(this.retraitDAO.retraitExiste(id))
				return this.retraitDAO.selectById(id);
			else
				exception.ajouterErreur(CodesResultatBLL.POINT_RETRAIT_INCONNU);
			throw exception;
		}
		
		
	//========  SUPPRESSION  ===========================================	
	
		public void suppressionUser(Retrait retrait) throws BusinessException {
			this.retraitDAO.delete(retrait.getNoArticle());
		}
	
		
//	========  VERIFICATIONS  ===========================================	
		
		private static void verif(Enchere enchere, BusinessException exception) {
			if (enchere.getMontant_enchere() < 0)
				exception.ajouterErreur(CodesResultatBLL.CREDIT_NEGATIF);		
		}
}
