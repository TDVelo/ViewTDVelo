package fr.gillouard.tdvelo.client.desktop.widget;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;

import fr.gillouard.tdvelo.client.palmares.widget.PalmaresWidget;

public class DesktopWidget implements IsWidget {

	@Override
	public Widget asWidget() {

		final PalmaresWidget epreuveWid = new PalmaresWidget();

		// wid.asWidget();
		final HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
		hlc.setPixelSize(1020, 750);

		final ContentPanel cp1 = new ContentPanel();
		cp1.setHeadingText("Epreuves");
		cp1.setPixelSize(1010, 740);
		cp1.setWidget(epreuveWid.asWidget());
		hlc.add(cp1);

		return hlc;
	}

}
