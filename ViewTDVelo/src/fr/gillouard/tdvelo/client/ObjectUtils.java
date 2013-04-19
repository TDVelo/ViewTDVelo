package fr.gillouard.tdvelo.client;

import fr.gillouard.tdvelo.shared.Coureur;
import fr.gillouard.tdvelo.shared.Epreuve;

public class ObjectUtils {

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

}
