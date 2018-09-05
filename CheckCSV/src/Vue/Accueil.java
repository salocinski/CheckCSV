package Vue;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Accueil extends JFrame
{
	private static String titre = "Parsing CSV File";
	private static int lFenetre = 450;
	private static int hFenetre = 450;
	
	public static void main(String[] args)
	{
		// Déclaration et configuration de la frame
		Accueil main = new Accueil();
		main.setSize(lFenetre, hFenetre);
		main.setTitle(titre);
		main.setResizable(false);
		
		//Déclaration du panel de la fenetre et du Layout
		JPanel contenu = new JPanel();
		contenu.setLayout(new BorderLayout());
		
		//Déclaration des élements de la Frame
		JLabel titre = new JLabel("Parseur de fichier CSV");
		JButton boutonBdd = new JButton("Créer une Bdd depuis un .CSV");
//		JButton boutonTable = new JButton("Créer une Table depuis un .CSV");
		
		JLabel signature = new JLabel("Développé par Nicolas STRYJEWSKI");
		
		JButton quitter = new JButton("Quitter");
		
		//Définition du panel TITRE et configuration de ses éléments
		JPanel panelTitre = new JPanel();
		panelTitre.setSize(450, 150);
		titre.setFont(new Font ("Arial", Font.BOLD, 16));
		titre.setHorizontalAlignment(JLabel.CENTER);
		
		//Ajout des élément au panel TITRE
		panelTitre.add(titre);
		
		//Définition du panel bouton et configuration de ses éléments
		JPanel panelBouton = new JPanel();
		boutonBdd.setSize(150,50);
		quitter.setSize(150,50);
		
		//Ajout des éléments au panel BOUTON
		panelBouton.add(boutonBdd);
//		panelBouton.add(boutonTable);
		panelBouton.add(quitter);
		
		//Définition du panel SIGNATURE et configuration de ses éléments
		JPanel panelSignature = new JPanel();
		signature.setHorizontalAlignment(JLabel.CENTER);
		
		//Ajout des éléments au panel SIGNATURE
		panelSignature.add(signature);
		
		//Ajout des éléments au panel CONTENU
		contenu.add(panelTitre, BorderLayout.NORTH);
		contenu.add(panelBouton, BorderLayout.CENTER);
		contenu.add(panelSignature, BorderLayout.SOUTH);
		
		//Ajout du panel sur la frame
		main.add(contenu);
		
		/*Affichage de la frame*/
		main.setVisible(true);
	
		boutonBdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent action)
			{
				CreerBdd createurBdd = new CreerBdd();
				createurBdd.setVisible(true);
				main.dispose();
			}
		});

//		boutonTable.addActionListener(new ActionListener()
//		{
//			@Override
//			public void actionPerformed(ActionEvent action)
//			{
//				CreerTable createurtable = new CreerTable();
//				createurtable.setVisible(true);
//				main.dispose();
//			}
//		});
		
		quitter.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
	}
}
