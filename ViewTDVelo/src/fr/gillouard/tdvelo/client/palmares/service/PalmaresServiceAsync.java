package fr.gillouard.tdvelo.client.palmares.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.gillouard.tdvelo.shared.Epreuve;

public interface PalmaresServiceAsync {
	
	void changeEpreuve(boolean insert, Epreuve epreuve, AsyncCallback<Void> callback);

	void getListeEpreuve(int dossard, AsyncCallback<List<Epreuve>> callback);

}
