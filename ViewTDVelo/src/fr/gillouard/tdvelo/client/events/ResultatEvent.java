package fr.gillouard.tdvelo.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class ResultatEvent extends GwtEvent<ResultatEventHandler> {

	private int dossard;
	private String categorie;

	public ResultatEvent(final int dossard, final String categorie) {
		this.dossard = dossard;
		this.categorie = categorie;
	}

	public static Type<ResultatEventHandler> TYPE = new Type<ResultatEventHandler>();

	@Override
	public Type<ResultatEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ResultatEventHandler handler) {
		handler.onResultat(this);
	}

	/**
	 * @return the dossard
	 */
	public int getDossard() {
		return dossard;
	}

	/**
	 * @return the categorie
	 */
	public String getCategorie() {
		return categorie;
	}
}