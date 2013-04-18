package fr.gillouard.tdvelo.shared;

import java.io.Serializable;

public class Epreuve implements Serializable {

	/**
	 * Version Uid
	 */
	private static final long serialVersionUID = 7740070213682863866L;

	private int dossard;

	public int getDossard() {
		return dossard;
	}

	public void setDossard(int dossard) {
		this.dossard = dossard;
	}

	private String discipline;

	private long temps;

	private long penalite;
	
	private long tempsCumule;

	public long getTempsCumule() {
		return tempsCumule;
	}

	public void setTempsCumule(long tempsCumule) {
		this.tempsCumule = tempsCumule;
	}

	private int classement;

	public int getClassement() {
		return classement;
	}

	public void setClassement(int classement) {
		this.classement = classement;
	}

	/**
	 * @return the discipline
	 */
	public String getDiscipline() {
		return discipline;
	}

	/**
	 * @param discipline
	 *            the discipline to set
	 */
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	/**
	 * @return the temps
	 */
	public long getTemps() {
		return temps;
	}

	/**
	 * @param temps
	 *            the temps to set
	 */
	public void setTemps(long temps) {
		this.temps = temps;
	}

	/**
	 * @return the penalite
	 */
	public long getPenalite() {
		return penalite;
	}

	/**
	 * @param penalite
	 *            the penalite to set
	 */
	public void setPenalite(long penalite) {
		this.penalite = penalite;
	}

}
