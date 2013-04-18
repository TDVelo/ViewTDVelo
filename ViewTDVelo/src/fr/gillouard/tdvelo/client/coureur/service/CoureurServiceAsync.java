package fr.gillouard.tdvelo.client.coureur.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.gillouard.tdvelo.shared.Coureur;

public interface CoureurServiceAsync {
	
	void getListeCoureur(final AsyncCallback<List<Coureur>> callback)
			throws IllegalArgumentException;
	
	void getDetailCoureur(int dossard, AsyncCallback<Coureur> callback);

}
