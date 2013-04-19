package fr.gillouard.tdvelo;

import static fr.gillouard.tdvelo.shared.Epreuve.Discipline.ADRESSE;
import static fr.gillouard.tdvelo.shared.Epreuve.Discipline.CYCLOCROSS;
import static fr.gillouard.tdvelo.shared.Epreuve.Discipline.ROUTE;
import static fr.gillouard.tdvelo.shared.Epreuve.Discipline.VITESSE;

import org.junit.Test;

import fr.gillouard.tdvelo.client.ObjectUtils;
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
			System.out.println(ObjectUtils.coureurToString(resultat.getCoureur()));
			System.out.println("---- General : " + resultat.getClassementGeneral() + " ---");
			System.out.println(ObjectUtils.epreuveToString(resultat.getEpreuves().get(VITESSE)));
			System.out.println(ObjectUtils.epreuveToString(resultat.getEpreuves().get(ADRESSE)));
			System.out.println(ObjectUtils.epreuveToString(resultat.getEpreuves().get(CYCLOCROSS)));
			System.out.println(ObjectUtils.epreuveToString(resultat.getEpreuves().get(ROUTE)));
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
