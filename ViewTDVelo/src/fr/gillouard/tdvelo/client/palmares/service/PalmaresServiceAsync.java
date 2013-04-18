package fr.gillouard.tdvelo.client.palmares.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.gillouard.tdvelo.shared.Palmares;

public interface PalmaresServiceAsync {

	void getPalmaresParCategorie(String categorie,
			AsyncCallback<Palmares> callback);

}
