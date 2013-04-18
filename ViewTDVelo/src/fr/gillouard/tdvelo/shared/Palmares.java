package fr.gillouard.tdvelo.shared;

import java.io.Serializable;
import java.util.List;

public class Palmares implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5851354332227147367L;
	List<Resultat> resultats;

	public List<Resultat> getResultats() {
		return resultats;
	}

	public void setResultats(List<Resultat> resultats) {
		this.resultats = resultats;
	}
}
