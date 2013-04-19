package fr.gillouard.tdvelo;

import org.junit.Test;

import fr.gillouard.tdvelo.server.palmares.PalmaresServiceImpl;
import fr.gillouard.tdvelo.shared.Palmares;
import fr.gillouard.tdvelo.shared.Resultat;

public class PalamaresServiceTest {

	@Test
	public void test() {
		PalmaresServiceImpl palmaresService = new PalmaresServiceImpl();

		Palmares palmares = palmaresService.getPalmaresParCategorie("poussin");

		System.out.println(this.displayEntete());
		for (Resultat resultat : palmares.getResultats()) {
			System.out.println(PalmaresServiceImpl.coureurToString(resultat.getCoureur()));
			System.out.println("---- General : " + resultat.getClassementGeneral() + " ---");
			System.out.println(PalmaresServiceImpl.epreuveToString(resultat.getEpreuves().get(
					PalmaresServiceImpl.VITESSE)));
			System.out.println(PalmaresServiceImpl.epreuveToString(resultat.getEpreuves().get(
					PalmaresServiceImpl.ADRESSE)));
			System.out.println(PalmaresServiceImpl
					.epreuveToString(resultat.getEpreuves().get(PalmaresServiceImpl.CYCLO)));
			System.out.println(PalmaresServiceImpl
					.epreuveToString(resultat.getEpreuves().get(PalmaresServiceImpl.ROUTE)));
			System.out.println("---------------------------------------------------------");
		}

	}

	private String displayEntete() {
		StringBuffer display = new StringBuffer();
		display.append("Categorie");
		display.append(" - ");
		display.append("Nom");
		display.append(" - ");
		display.append("Prenom");
		display.append(" - ");
		display.append("Dossard");
		display.append(" - ");
		display.append("Club");
		display.append(" - ");
		display.append("Equipe");

		return display.toString();

	}

}
