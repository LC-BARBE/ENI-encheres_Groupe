package fr.eni.projet.projetServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projet.BusinessException;
import fr.eni.projet.BLL.UtilisateurManager;
import fr.eni.projet.BO.Utilisateur;
import fr.eni.projet.messages.LecteurMessage;
/**
 * 
 * @author RobinFerre
 * Edit Clément
 * 
 */
/**
 * Servlet implementation class ProfilServlet
 */
@WebServlet("/ProfilServlet")
public class ProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pseudo = request.getParameter("id");
		try {
			UtilisateurManager um = UtilisateurManager.getInstance();
			Utilisateur userToSearch = um.rechercheUser(pseudo);
			request.setAttribute("user", false);
			request.setAttribute("profil", userToSearch);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/Connexion/Profil.jsp");
			rd.forward(request, response);

		} catch (BusinessException e) {
			StringBuffer sb = new StringBuffer();
			sb.append("Une ou plusieurs erreurs se sont produites :");
			for(int i : e.getListeCodesErreur())
				sb.append("\n").append("CODE ").append(i).append(" - ").append(LecteurMessage.getMessageErreur(i));

			System.err.println("Probleme doGet profil");

			PrintWriter out = response.getWriter();
			RequestDispatcher rd = request.getRequestDispatcher("AccueilServlet");
			rd.forward(request, response);
			out.print(sb.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

        //On récupère les infos de l'user connecté
		HttpSession sessionUser = request.getSession();
		Utilisateur user = (Utilisateur) sessionUser.getAttribute("utilisateur");
		request.setAttribute("user", true);
		request.setAttribute("profil", user);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/Connexion/Profil.jsp");
		rd.forward(request, response);
	}
}
