package fr.gillouard.tdvelo.client.epreuve.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.gillouard.tdvelo.shared.Epreuve;

public interface EpreuveServiceAsync {
	
	void changeEpreuve(boolean insert, Epreuve epreuve, AsyncCallback<Void> callback);

	void getListeEpreuve(int dossard, AsyncCallback<List<Epreuve>> callback);

}
