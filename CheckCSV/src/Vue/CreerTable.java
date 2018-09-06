package Vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.CsvManager;
import controleur.TableManager;
import service.MyFileChooser;

public class CreerTable extends JFrame
{
	private static String TITRE = "Ajouter une table à une BDD";
	private static int L_FENETRE = 450;
	private static int H_FENETRE = 450;

	public CreerTable()
	{
		//Init de la frame
		this.setSize(L_FENETRE, H_FENETRE);
		this.setTitle(TITRE);
		
		//Déclaration du panel de la fenetre et du Layout
		JPanel contenu = new JPanel();
		contenu.setLayout(new BorderLayout());
		
		//Déclaration des élements de la Frame
		JPanel panelSaisie = new JPanel();
		JLabel nomBdd = new JLabel("Ajouter une table sur quelle Bdd ?");
		JTextField saisie = new JTextField();
		saisie.setPreferredSize(new Dimension(75, 20));
		saisie.setMinimumSize(new Dimension(75,20));
		saisie.setMaximumSize(new Dimension(75,20));
	
		//Ajout du contenu au panel de saisie
		panelSaisie.add(nomBdd);
		panelSaisie.add(saisie);
		
		JPanel panelBouton = new JPanel();
		JButton boutonValider = new JButton("Valider");
		JButton boutonRetour = new JButton("Retour");
		
		//Ajout du contenu au panel de saisie
		panelBouton.add(boutonValider);
		panelBouton.add(boutonRetour);
		
		//Ajout des différents panels sur le contenu de la Frame.
		contenu.add(panelSaisie, BorderLayout.NORTH);
		contenu.add(panelBouton, BorderLayout.SOUTH);
		
		this.add(contenu);
		
		boutonValider.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				MyFileChooser selectionFichier = new MyFileChooser();
				
				//Filtre de sélection de fichier uniquement sur le JFileChooser
				selectionFichier.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				//Activation du JFileChooser
				int option = selectionFichier.getOption();
				
				if(option == JFileChooser.APPROVE_OPTION)
				{
					if(selectionFichier.verifTypeMime())
					{
						//Récupération de la saisie
						String saisieBdd = saisie.getText();
						
						TableManager table = new TableManager();
						CsvManager csv = new CsvManager();
						
						Hashtable<Integer, Hashtable<String, String>> association = csv.extraireDonneesTableCSV(selectionFichier);
						int nbChamp = association.size();
						table.insererScript(association, saisieBdd, nbChamp);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Le type MIME du fichier séléctionné n'est pas compatible.", "Erreur de compatibilité", JOptionPane.ERROR_MESSAGE);
						CreerBdd selection = new CreerBdd();
						selection.setVisible(true);
					}
				}				
			}
		});
		
		boutonRetour.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Accueil main = new Accueil();
				main.setVisible(true);
				
			}
		});
	}
}

