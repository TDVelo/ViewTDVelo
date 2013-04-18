package fr.gillouard.tdvelo.shared;

import java.io.Serializable;

public class Coureur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int dossard;
	private String nom;
	private String Prenom;
	private boolean Sexe;
	private String categorie;
	private String club;
	private String equipe;

	/**
	 * @return the dossard
	 */
	public int getDossard() {
		return dossard;
	}

	/**
	 * @param dossard
	 *            the dossard to set
	 */
	public void setDossard(int dossard) {
		this.dossard = dossard;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return Prenom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}

	/**
	 * @return the sexe
	 */
	public boolean isSexe() {
		return Sexe;
	}

	/**
	 * @param sexe
	 *            the sexe to set
	 */
	public void setSexe(boolean sexe) {
		Sexe = sexe;
	}

	/**
	 * @return the categorie
	 */
	public String getCategorie() {
		return categorie;
	}

	/**
	 * @param categorie
	 *            the categorie to set
	 */
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	/**
	 * @return the club
	 */
	public String getClub() {
		return club;
	}

	/**
	 * @param club
	 *            the club to set
	 */
	public void setClub(String club) {
		this.club = club;
	}

	/**
	 * @return the equipe
	 */
	public String getEquipe() {
		return equipe;
	}

	/**
	 * @param equipe
	 *            the equipe to set
	 */
	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}

}
