package fr.gillouard.tdvelo;

import org.junit.Test;

import fr.gillouard.tdvelo.server.palmares.PalmaresServiceImpl;

public class PalamaresServiceTest  {

	@Test
	public void test() {
		PalmaresServiceImpl palmaresSerivce = new PalmaresServiceImpl();
		
		palmaresSerivce.getPalmaresParCategorie("poussin");
		
		
	}
}
