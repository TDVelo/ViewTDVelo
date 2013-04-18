package fr.gillouard.tdvelo.client.palmares.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.gillouard.tdvelo.shared.Palmares;

@RemoteServiceRelativePath("palmares")
public interface PalmaresService extends RemoteService {
	public Palmares getPalmaresParCategorie(final String categorie)
			throws IllegalArgumentException;

}
