package fr.gillouard.tdvelo.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface DetailEventHandler extends EventHandler {
	
	void onDetailDemand(DetailEvent detailEvent);

}
