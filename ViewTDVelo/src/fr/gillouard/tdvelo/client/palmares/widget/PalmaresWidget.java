package fr.gillouard.tdvelo.client.palmares.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;

import fr.gillouard.tdvelo.client.ObjectUtils;
import fr.gillouard.tdvelo.client.palmares.service.PalmaresService;
import fr.gillouard.tdvelo.client.palmares.service.PalmaresServiceAsync;
import fr.gillouard.tdvelo.shared.Palmares;
import fr.gillouard.tdvelo.shared.Resultat;

public class PalmaresWidget {

	/**
	 * Timer pour les traitements a intervalles reguliers
	 */
	private final Timer timer;

	ContentPanel panel = new ContentPanel();

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final PalmaresServiceAsync epreuveService = GWT.create(PalmaresService.class);

	private Map<Integer, String> categories = new HashMap<Integer, String>();
	
	private int index = 0;
	
	
	public PalmaresWidget() {
		// TODO Ajouter les differentes categories a afficher (enum ?)
	categories.put(0, "Poussin");
	categories.put(1, "POUSSIN");

		// thread d'interrogation du serveur pour remonter les resultats
		this.timer = new Timer() {

			public void run() {
				
				epreuveService.getPalmaresParCategorie(categories.get(index), new AsyncCallback<Palmares>() {

					@Override
					public void onSuccess(Palmares palmares) {
						draw(palmares);
						nextDisplay();

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

	public void nextDisplay() {
		index ++;
		if (index > categories.size() ) {
			index = 0;
		}
	}
	public Widget asWidget() {
		draw(null);
		return this.panel;
	}

	private void draw(Palmares palmares) {
		this.panel.clear();

		if (palmares == null || palmares.getResultats().isEmpty()) {
			Label label = new Label("Aucun resultat sur les epreuves en cours");
			this.panel.add(label);
			return;
		}

		// TODO A revoir juste pour les tests 
		List<Resultat> resultats = palmares.getResultats();
		final VerticalPanel panelV = new VerticalPanel();
		panelV.add(new Label("====> CATEGORIE " + categories.get(index)));
		
		for (Resultat resultat : resultats) {
			final HorizontalPanel panelH = new HorizontalPanel();
		
			panelH.add(new Label(ObjectUtils.coureurToString(resultat.getCoureur())));
			panelH.add(new Label("Classement " + resultat.getClassementGeneral()));
			panelV.add(panelH);
		}
	
			this.panel.add(panelV);

	}

}
