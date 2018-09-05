package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author utilisateur
 */
public class Service
{
	public void verifMysql()
	{
		//------------------------- ADRESSE DES PORTS PAR DEFAUT -------------------------//
		String portStandard = "0.0.0.0:3306";
		String portDefaut = "0.0.0.0:8080";
		//------------------------- ADRESSE DES PORTS PAR DEFAUT -------------------------//
		
		//------------------------- LANCEMENT D'UNE APPLICATION DE L'ENVIRONNEMENT -------------------------//
		Runtime runtime = Runtime.getRuntime();
		//------------------------- LANCEMENT D'UNE APPLICATION DE L'ENVIRONNEMENT -------------------------//
		
		//------------------------- CONFIGURATION DE LA COMMANDE SYSTEME -------------------------//
			//ouverture de netstat natif sur linux et windows
			//-a : Affiche toutes les connexions et tous les ports d'Ã©coute.
		String commandeOuvertureNetStat = "netstat -a";
		//------------------------- CONFIGURATION DE LA COMMANDE SYSTEME -------------------------//
		
		Process processus;
		try
		{
			//------------------------- LANCEMENT DU PROCESSUS DE L'INVITE DE COMMANDE -------------------------//
			processus = runtime.exec(commandeOuvertureNetStat);
			//------------------------- LANCEMENT DU PROCESSUS DE L'INVITE DE COMMANDE -------------------------//
			
			//------------------------- ENREGISTREMENT DE LA REPONSE -------------------------//
			BufferedReader lecteur = new BufferedReader(new InputStreamReader(processus.getInputStream())); 
			StringBuffer buffer = new StringBuffer();
			String reponse = "";
			//------------------------- ENREGISTREMENT DE LA REPONSE -------------------------//
			while ((reponse = lecteur.readLine()) != null)
				buffer.append(reponse);
			
			String result = buffer.toString();
			
			//------------------------- VERIFICATION PRESENCE PORT STANDARD / DEFAUT -------------------------//
			int position = 0;
			position = result.indexOf(portStandard);
			//SI LA CHAINE N'EST PAS PRÃ‰SENTE --> RENVOI -1
			
			if(position == -1)
			{
				position = result.indexOf(portDefaut);
				if(position == -1)
				{
					JOptionPane.showMessageDialog(null, "Le service de gestion de base de données n'est pas lançé. \n Veuillez lancer votre serveur local et relancer l'application.", "Information d'utilisation", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			}
			//------------------------- VERIFICATION PRESENCE PORT STANDARD / DEFAUT  -------------------------//
		}
		catch (IOException ex)
		{
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
		}		
	}
}
