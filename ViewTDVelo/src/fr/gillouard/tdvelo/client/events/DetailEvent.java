package fr.gillouard.tdvelo.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class DetailEvent extends GwtEvent<DetailEventHandler> {

	private int dossard;
	
	public DetailEvent(final int dossard) {
		this.dossard = dossard;
	}
	
	public static Type<DetailEventHandler> TYPE = new Type<DetailEventHandler>();

	@Override
	public Type<DetailEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DetailEventHandler handler) {
		handler.onDetailDemand(this);
	}
	
	/**
	 * @return the dossard
	 */
	public int getDossard() {
		return dossard;
	}
}