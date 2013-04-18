package fr.gillouard.tdvelo.client.palmares.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;

import fr.gillouard.tdvelo.client.palmares.service.PalmaresService;
import fr.gillouard.tdvelo.client.palmares.service.PalmaresServiceAsync;
import fr.gillouard.tdvelo.shared.Epreuve;

public class PalmaresWidget {

	/**
	 * Timer pour les traitements a intervalles reguliers
	 */
	private final Timer timer;

	ContentPanel panel = new ContentPanel();

	/**
	 * Liste des disciplines a afficher
	 */
	private List<String> disciplines;

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final PalmaresServiceAsync epreuveService = GWT
			.create(PalmaresService.class);

	public PalmaresWidget() {
		// TODO Constitution de la liste des disciplines à visualiser
		disciplines = new ArrayList<String>();

		// thread d'interrogation du serveur pour remonter les resultats
		this.timer = new Timer() {

			// TODO Remplacer par le bon service / une fois qu'il sera développé / il doit ramener toutes les epreuves
			// le but est de remonter tous les 5 secondes les resultats et
			// d'afficher a tour de roles les N épreuves
			public void run() {
				epreuveService.getListeEpreuve(51,
						new AsyncCallback<List<Epreuve>>() {

							@Override
							public void onSuccess(List<Epreuve> result) {
								draw(result);

							}

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Erreur lors de la remonter des épreuves");

							}
						});
			}
		};

		// Timer de 30 secondes
		this.timer.scheduleRepeating(30000);
	}

	public Widget asWidget() {
		draw(null);
		return this.panel;
	}

	private void draw(List<Epreuve> result) {
		this.panel.clear();

		if (result == null || result.isEmpty()) {
			Label label = new Label("Aucun resultat sur les epreuves en cours");
			this.panel.add(label);
			return;
		}

		// retenir uniquement l'epreuve a afficher
		
		// calculer les classements par epreuve (peut etre coter seveur ou dans wrapper) / par categorie / par sexe...

		// Ajouter tableau sencha qui liste le tout

		// Ballayer les epreuves d'une discipline donne (boucler en circulaire)

		// TEMPORAIRE
		this.panel.add(new Label(result.get(0).getDiscipline()));

	}

	private Epreuve getEpreuve(final int dossard, final String discipline,
			final long temps, final long penalite) {
		Epreuve epreuve = new Epreuve();
		epreuve.setDossard(dossard);
		epreuve.setDiscipline(discipline);
		epreuve.setTemps(temps);
		epreuve.setPenalite(penalite);
		return epreuve;
	}
}
