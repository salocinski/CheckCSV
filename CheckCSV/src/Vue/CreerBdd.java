package Vue;

import java.awt.BorderLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controleur.BddManager;

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
		JFileChooser selectionFichier = new JFileChooser();
		
		//Filtre de sélection de fichier uniquement sur le JFileChooser
		selectionFichier.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		//Activation du JFileChooser
		int valider=selectionFichier.showOpenDialog(getParent());
		
		if(valider==JFileChooser.APPROVE_OPTION)
		{		
			//Récupération du nom de fichier complet
			String nomFichierComplet = selectionFichier.getSelectedFile().getName();
			
			//Découpage du nom de fichier pour définir son extension
			String extensionFichier = nomFichierComplet.substring(nomFichierComplet.lastIndexOf("."));
			
			//Mise sous condition du fichier sélectionné selon son type MIME
			//Si la condition n'est pas respectée, on affiche un pop-up d'erreur, on ferme la fenetre précédente de sélection
			//on ouvre une nouvelle fenetre de selection pour relancer les configurations du JFileChooser
			if(!extensionFichier.equals(".csv"))
			{
				JOptionPane.showMessageDialog(null, "Le type MIME du fichier séléctionné n'est pas compatible.", "Erreur de compatibilité", JOptionPane.ERROR_MESSAGE);
//				CreerBdd selection = new CreerBdd();
//				selection.setVisible(true);
				this.dispose();
			}
			else
			{
				BddManager bdd = new BddManager();
				bdd.verifCsv(selectionFichier);
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
