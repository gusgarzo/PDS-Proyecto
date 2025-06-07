package pds.lanzador;

import javax.security.auth.login.LoginContext;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;



import pds.vista.LoginWindow;

public class PokeLingo {
	public static void main(final String[] args){
		FlatDarkLaf.setup();
		
		LoginWindow login = new LoginWindow();	
		login.setVisible(true);
	}
}
