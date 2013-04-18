package fr.gillouard.tdvelo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import fr.gillouard.tdvelo.client.desktop.widget.DesktopWidget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ViewTDVelo implements EntryPoint {


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DesktopWidget dskWid = new DesktopWidget();
		RootPanel.get().add(dskWid.asWidget());
	}
}
