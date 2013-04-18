package fr.gillouard.tdvelo.server.palmares;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.gillouard.tdvelo.client.palmares.service.PalmaresService;
import fr.gillouard.tdvelo.server.DataSource;
import fr.gillouard.tdvelo.shared.Coureur;
import fr.gillouard.tdvelo.shared.Epreuve;
import fr.gillouard.tdvelo.shared.Palmares;
import fr.gillouard.tdvelo.shared.Resultat;

public class PalmaresServiceImpl extends RemoteServiceServlet implements
		PalmaresService {

	private static final String ROUTE = "Route";
	private static final String ADRESSE = "Adresse";
	private static final String CYCLO = "Cyclo-cross";
	private static final String VITESSE = "Vitesse";

	/** LOGGER. **/
	private static final Log LOG = LogFactory.getLog(PalmaresServiceImpl.class);

	public static String QUERRY_PALMARES = "SELECT * FROM epreuve, coureur "
			+ "WHERE epreuve.dossard = coureur.dossard AND coureur.categorie =";
	public static String ORDER_LISTE = " ORDER BY discipline,dossard";

	/**
	 * UID
	 */
	private static final long serialVersionUID = -5217040857543351560L;

	@Override
	public Palmares getPalmaresParCategorie(final String categorie)
			throws IllegalArgumentException {
		Connection conn = null;
		Statement select = null;
		ResultSet result = null;

		try {
			// Discipline par dossard
			List<Epreuve> listEpreuvecyclocros = new ArrayList<Epreuve>();
			List<Epreuve> listEpreuveVitesse = new ArrayList<Epreuve>();
			List<Epreuve> listEpreuveRoute = new ArrayList<Epreuve>();
			List<Epreuve> listEpreuveAdresse = new ArrayList<Epreuve>();
			List<Epreuve> listEpreuve = new ArrayList<Epreuve>();

			conn = DataSource.getInstance().getConnection();
			select = conn.createStatement();
			result = select.executeQuery(QUERRY_PALMARES + categorie
					+ ORDER_LISTE);

			while (result.next()) {
				// Sauvegarde des disciplines par dossard
				Epreuve epreuve = new Epreuve();

				epreuve.setDiscipline(result.getString("discipline"));
				epreuve.setTemps(result.getLong("temps"));
				epreuve.setClassement(result.getInt("classement"));
				epreuve.setPenalite(result.getLong("penalite"));
				epreuve.setTempsCumule(result.getLong("temps")
						+ result.getLong("penalite"));
				epreuve.setDossard(result.getInt("dossard"));

				if (ROUTE.equals(epreuve)) {
					listEpreuveRoute.add(epreuve);
				} else if (ADRESSE.equals(epreuve)) {
					listEpreuveAdresse.add(epreuve);
				} else if (CYCLO.equals(epreuve)) {
					listEpreuvecyclocros.add(epreuve);
				} else if (VITESSE.equals(epreuve)) {
					listEpreuveVitesse.add(epreuve);
				}
				listEpreuve.add(epreuve);
			}

			// Tri des maps
			Collections.sort(listEpreuveAdresse, new Comparator<Epreuve>() {
				@Override
				public int compare(final Epreuve o1, final Epreuve o2) {
					// tri croissant
					if (o1.getTempsCumule() > o2.getTempsCumule()) {
						return 1;
					} else if (o1.getTempsCumule() < o2.getTempsCumule()) {
						return -1;
					} else {
						return 0;
					}
				}
			});

			Collections.sort(listEpreuvecyclocros, new Comparator<Epreuve>() {
				@Override
				public int compare(final Epreuve o1, final Epreuve o2) {
					// tri croissant
					if (o1.getTemps() > o2.getTemps()) {
						return 1;
					} else if (o1.getTemps() < o2.getTemps()) {
						return -1;
					} else {
						return 0;
					}
				}
			});

			Collections.sort(listEpreuveRoute, new Comparator<Epreuve>() {
				@Override
				public int compare(final Epreuve o1, final Epreuve o2) {
					// tri croissant
					if (o1.getClassement() > o2.getClassement()) {
						return 1;
					} else if (o1.getClassement() < o2.getClassement()) {
						return -1;
					} else {
						return 0;
					}
				}
			});

			Collections.sort(listEpreuveVitesse, new Comparator<Epreuve>() {
				@Override
				public int compare(final Epreuve o1, final Epreuve o2) {
					// tri croissant
					if (o1.getTemps() > o2.getTemps()) {
						return 1;
					} else if (o1.getTemps() < o2.getTemps()) {
						return -1;
					} else {
						return 0;
					}
				}
			});

			Palmares palmares = new Palmares();
			List<Resultat> resultats = new ArrayList<Resultat>();
			palmares.setResultats(resultats);

			List<Coureur> coureurs = this.getListeCoureur();

			for (Coureur coureur : coureurs) {
				Resultat resultat = new Resultat();
				resultat.setCoureur(coureur);
				Map<String, Epreuve> epreuves = new HashMap<String, Epreuve>();
				// TODO calculer le classement grace a l'ordre de la liste
				int i = 0;
				boolean go = true;
				Epreuve epreuve = null;
				while (i <= listEpreuveVitesse.size() && go) {
					epreuve = listEpreuveVitesse.get(i++);
					if (epreuve.getDossard() == coureur.getDossard()) {
						epreuve.setClassement(i);
						go = false;
					}
				}
				epreuves.put(VITESSE, epreuve);

				resultat.setEpreuves(epreuves);
				resultat.setClassementGeneral(epreuve.getClassement());

				palmares.setResultats(resultats);
			}
			return palmares;

		} catch (SQLException e) {
			LOG.error("Erreur SQL : " + QUERRY_PALMARES, e);
			return null;
		} finally {
			if (select != null) {
				try {
					select.close();
				} catch (SQLException e) {
					LOG.error("Erreur SQL : " + QUERRY_PALMARES, e);
				}
			}
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					LOG.error("Erreur SQL : " + QUERRY_PALMARES, e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LOG.error("Erreur SQL : " + QUERRY_PALMARES, e);
				}
			}
		}

	}

	private static final String REQ_ALL_COUREUR = "SELECT * FROM coureur ORDER BY categorie,dossard";
	private static final String REQ_COUREUR = "SELECT * FROM coureur WHERE dossard =";

	private List<Coureur> getListeCoureur() throws IllegalArgumentException {

		final List<Coureur> lstCoureur = new ArrayList<Coureur>();

		try {
			Connection conn = DataSource.getInstance().getConnection();
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(REQ_ALL_COUREUR);
			while (result.next()) {
				final Coureur coureur = new Coureur();
				coureur.setDossard(result.getInt("dossard"));
				coureur.setNom(result.getString("nom"));
				coureur.setPrenom(result.getString("prenom"));
				if ("M".equals(result.getString("sexe"))) {
					coureur.setSexe(true);
				} else {
					coureur.setSexe(false);
				}
				coureur.setCategorie(result.getString("categorie"));
				coureur.setClub(result.getString("club"));
				coureur.setEquipe(result.getString("equipe"));
				lstCoureur.add(coureur);
			}
			select.close();
			result.close();
			conn.close();
		} catch (SQLException e) {
			LOG.error("Erreur SQL : " + REQ_ALL_COUREUR, e);
		}
		return lstCoureur;
	}

}
