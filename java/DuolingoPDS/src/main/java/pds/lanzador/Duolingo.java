package pds.lanzador;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import pds.vista.DuolingoLogin;

public class Duolingo {
	public static void main(final String[] args){
		FlatDarkLaf.setup();
		DuolingoLogin login = new DuolingoLogin();
		
		login.setVisible(true);
	}
	
	
}
