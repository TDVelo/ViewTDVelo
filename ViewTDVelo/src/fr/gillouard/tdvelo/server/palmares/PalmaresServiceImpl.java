package fr.gillouard.tdvelo.server.palmares;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.gillouard.tdvelo.client.palmares.service.PalmaresService;
import fr.gillouard.tdvelo.server.DataSource;
import fr.gillouard.tdvelo.shared.Coureur;
import fr.gillouard.tdvelo.shared.Epreuve;
import fr.gillouard.tdvelo.shared.Palmares;
import fr.gillouard.tdvelo.shared.Resultat;

public class PalmaresServiceImpl extends RemoteServiceServlet implements PalmaresService {

	public static final String ROUTE = "Route";
	public static final String ADRESSE = "Adresse";
	public static final String CYCLO = "Cyclo-cross";
	public static final String VITESSE = "Vitesse";

	/** LOGGER. **/
	private static final Log LOG = LogFactory.getLog(PalmaresServiceImpl.class);

	public static String QUERRY_PALMARES = "SELECT * FROM epreuve, coureur WHERE epreuve.dossard = coureur.dossard AND coureur.categorie =?";
	private static final String REQ_ALL_COUREUR = "SELECT * FROM coureur ORDER BY categorie,dossard";

	/**
	 * UID
	 */
	private static final long serialVersionUID = -5217040857543351560L;

	@Override
	public Palmares getPalmaresParCategorie(final String categorie) throws IllegalArgumentException {
		Connection conn = null;
		PreparedStatement select = null;
		ResultSet result = null;

		try {
			// Discipline par dossard
			List<Epreuve> listEpreuvecyclocros = new ArrayList<Epreuve>();
			List<Epreuve> listEpreuveVitesse = new ArrayList<Epreuve>();
			List<Epreuve> listEpreuveRoute = new ArrayList<Epreuve>();
			List<Epreuve> listEpreuveAdresse = new ArrayList<Epreuve>();
			List<Epreuve> listEpreuve = new ArrayList<Epreuve>();

			conn = DataSource.getInstance().getConnection();
			select = conn.prepareStatement(QUERRY_PALMARES);
			select.setString(1, categorie);
			result = select.executeQuery();

			while (result.next()) {
				// Sauvegarde des disciplines par dossard
				Epreuve epreuve = new Epreuve();

				epreuve.setDiscipline(result.getString("discipline"));
				epreuve.setTemps(result.getLong("temps"));
				epreuve.setClassement(result.getInt("classement"));
				epreuve.setPenalite(result.getLong("penalite"));
				epreuve.setTempsCumule(result.getLong("temps") + result.getLong("penalite"));
				epreuve.setDossard(result.getInt("dossard"));

				if (ROUTE.equals(epreuve.getDiscipline())) {
					listEpreuveRoute.add(epreuve);
				} else if (ADRESSE.equals(epreuve.getDiscipline())) {
					listEpreuveAdresse.add(epreuve);
				} else if (CYCLO.equals(epreuve.getDiscipline())) {
					listEpreuvecyclocros.add(epreuve);
				} else if (VITESSE.equals(epreuve.getDiscipline())) {
					listEpreuveVitesse.add(epreuve);
				}
				listEpreuve.add(epreuve);
			}

			// Tri des maps
			Collections.sort(listEpreuveAdresse, new Comparator<Epreuve>() {
				@Override
				public int compare(final Epreuve o1, final Epreuve o2) {
					// tri croissant (le temps le plus faible en debut de liste
					// pour etre classé premier)
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
					// tri croissant (le temps le plus faible en debut de liste
					// pour etre classé premier)
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
					// tri croissant (le temps le plus faible en debut de liste
					// pour etre classé premier)
					if (o1.getTemps() > o2.getTemps()) {
						return 1;
					} else if (o1.getTemps() < o2.getTemps()) {
						return -1;
					} else {
						return 0;
					}
				}
			});

			System.out.println("TEST DU TRI DES EPREUVES sans Classement");
			displayEpreuves(listEpreuveVitesse);
			displayEpreuves(listEpreuveRoute);
			displayEpreuves(listEpreuvecyclocros);
			displayEpreuves(listEpreuveAdresse);

			List<Resultat> resultats = new ArrayList<Resultat>();
			List<Coureur> coureurs = this.getListeCoureur();

			for (Coureur coureur : coureurs) {
				Resultat resultat = new Resultat();
				resultat.setCoureur(coureur);
				Map<String, Epreuve> epreuves = new HashMap<String, Epreuve>();

				// Calcul des classements par epreuve
				int classementGeneral = ajouterEpreuveCoureur(epreuves, VITESSE, listEpreuveVitesse, coureur);
				classementGeneral += ajouterEpreuveCoureur(epreuves, CYCLO, listEpreuvecyclocros, coureur);
				classementGeneral += ajouterEpreuveCoureur(epreuves, ROUTE, listEpreuveRoute, coureur);
				classementGeneral += ajouterEpreuveCoureur(epreuves, ADRESSE, listEpreuveAdresse, coureur);

				resultat.setEpreuves(epreuves);
				resultat.setClassementGeneral(classementGeneral);

				resultats.add(resultat);

			}

			System.out.println("TEST DU TRI DES EPREUVES avec Classement");
			displayEpreuves(listEpreuveVitesse);
			displayEpreuves(listEpreuveRoute);
			displayEpreuves(listEpreuvecyclocros);
			displayEpreuves(listEpreuveAdresse);

			// TODO Mise a jour en base des classements ????
			
			// Tri des lignes par classement general
			Collections.sort(resultats, new Comparator<Resultat>() {
				@Override
				public int compare(final Resultat o1, final Resultat o2) {
					// tri croissant
					if (o1.getClassementGeneral() > o2.getClassementGeneral()) {
						return 1;
					} else if (o1.getClassementGeneral() < o2.getClassementGeneral()) {
						return -1;
					} else {
						return 0;
					}
				}
			});

			// Constitution du Palmares avec les classements
			Palmares palmares = new Palmares();
			palmares.setResultats(resultats);

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

	/**
	 * Ajoute dans la MAP des epreuves l'epreuve du coureur en question dans
	 * laquelle le classement est calcule
	 * 
	 * @param epreuves
	 * @param discipline
	 * @param listEpreuve
	 * @param coureur
	 * @return Classement de l'epreuve ou 1000
	 */
	private int ajouterEpreuveCoureur(Map<String, Epreuve> epreuves, final String discipline,
			final List<Epreuve> listEpreuve, Coureur coureur) {
		Epreuve epreuve = this.calculerClassementCoureur(listEpreuve, coureur);
		if (epreuve == null) {
			epreuve = creerEpreuveSansClassement(discipline, coureur);
		}
		epreuves.put(discipline, epreuve);

		return epreuve.getClassement();

	}

	private Epreuve creerEpreuveSansClassement(final String discipline, final Coureur coureur) {
		// Classement non trouve : on consolide la MAP sans resultat pour
		// permettre les calculs de classment
		Epreuve epreuve = new Epreuve();
		epreuve.setDiscipline(discipline);
		epreuve.setDossard(coureur.getDossard());
		epreuve.setClassement(1000);

		return epreuve;
	}

	private Epreuve calculerClassementCoureur(final List<Epreuve> listEpreuve, final Coureur coureur) {

		if (CollectionUtils.isEmpty(listEpreuve)) {
			return null;
		}

		// Recuperation du coureur
		Epreuve epreuveCoureur = (Epreuve) CollectionUtils.find(listEpreuve, new Predicate() {
			@Override
			public boolean evaluate(Object epreuve) {
				return ((Epreuve) epreuve).getDossard() == coureur.getDossard();
			}
		});

		// Calcul de sont classement en retrouvant sa position dans la liste
		if (epreuveCoureur != null) {
			int classement = listEpreuve.indexOf(epreuveCoureur);
			epreuveCoureur.setClassement(classement);
		}

		return epreuveCoureur;
	}

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

	public void displayEpreuves(final List<Epreuve> epreuves) {
		for (Epreuve epreuve : epreuves) {
			System.out.println(PalmaresServiceImpl.epreuveToString(epreuve));
		}
	}

	public static String coureurToString(final Coureur coureur) {
		StringBuffer display = new StringBuffer();
		display.append(coureur.getCategorie());
		display.append(" - ");
		display.append(coureur.getNom());
		display.append(" - ");
		display.append(coureur.getPrenom());
		display.append(" - ");
		display.append(coureur.getDossard());
		display.append(" - ");
		display.append(coureur.getClub());
		display.append(" - ");
		display.append(coureur.getEquipe());

		return display.toString();

	}

	public static String epreuveToString(final Epreuve epreuve) {
		StringBuffer display = new StringBuffer();

		display.append(epreuve.getDiscipline());
		display.append(" - classement : ");
		display.append(epreuve.getClassement());
		display.append(" - tps : ");
		display.append(epreuve.getTemps());
		display.append(" - penalite : ");
		display.append(epreuve.getPenalite());
		display.append(" - cumule : ");
		display.append(epreuve.getTempsCumule());
		display.append(" - dossard : ");
		display.append(epreuve.getDossard());

		return display.toString();
	}
}
