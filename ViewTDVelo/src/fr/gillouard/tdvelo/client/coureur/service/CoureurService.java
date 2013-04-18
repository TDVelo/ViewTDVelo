package fr.gillouard.tdvelo.client.coureur.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.gillouard.tdvelo.shared.Coureur;

@RemoteServiceRelativePath("coureur")
public interface CoureurService extends RemoteService {

	List<Coureur> getListeCoureur() throws IllegalArgumentException;
	Coureur getDetailCoureur(final int dossard) throws IllegalArgumentException;

}
