package fr.gillouard.tdvelo.shared;

import java.io.Serializable;
import java.util.Map;

import fr.gillouard.tdvelo.shared.Epreuve.Discipline;

public class Resultat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7652425191824445990L;

	private Coureur coureur;

	public Coureur getCoureur() {
		return coureur;
	}

	public void setCoureur(Coureur coureur) {
		this.coureur = coureur;
	}

	public Map<Discipline, Epreuve> getEpreuves() {
		return epreuves;
	}

	public void setEpreuves(Map<Discipline, Epreuve> epreuves) {
		this.epreuves = epreuves;
	}

	public int getClassementGeneral() {
		return classementGeneral;
	}

	public void setClassementGeneral(int classementGeneral) {
		this.classementGeneral = classementGeneral;
	}

	private Map<Discipline, Epreuve> epreuves;

	private int classementGeneral;
}
