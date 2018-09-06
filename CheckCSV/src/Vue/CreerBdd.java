package Vue;

import java.awt.BorderLayout;
import java.util.Hashtable;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controleur.BddManager;
import controleur.CsvManager;
import service.MyFileChooser;

public class CreerBdd extends JFrame
{	
	private static String TITRE = "Créer une Bdd depuis un .csv";
	private static int L_FENETRE = 450;
	private static int H_FENETRE = 450;
	
	public CreerBdd ()
	{
		//Init de la frame
		this.setSize(L_FENETRE, H_FENETRE);
		this.setTitle(TITRE);
		MyFileChooser selectionFichier = new MyFileChooser();
		
		//Filtre de sélection de fichier uniquement sur le JFileChooser
		selectionFichier.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		//Activation du JFileChooser
		int option = selectionFichier.getOption();
		
		if(option == JFileChooser.APPROVE_OPTION)
		{
			if(selectionFichier.verifTypeMime())
			{
				//On utilisera la variable bdd pour inserer le script SQL
				BddManager bdd = new BddManager();
				//L'objet csv est crée pour permettre d'extraire les données du fichier csv
				CsvManager csv = new CsvManager();
				Hashtable<String, String> association =	csv.extraireDonneesBddCSV(selectionFichier);
				bdd.insererScript(association);
				this.dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Le type MIME du fichier séléctionné n'est pas compatible.", "Erreur de compatibilité", JOptionPane.ERROR_MESSAGE);
				CreerBdd selection = new CreerBdd();
				selection.setVisible(true);
				this.dispose();
			}
		}
		
		//Définition du panel
		JPanel contenu = new JPanel();
		contenu.setLayout(new BorderLayout());
		
		//Ajout des éléments
		contenu.add(selectionFichier);
		this.add(contenu);
	}
}
