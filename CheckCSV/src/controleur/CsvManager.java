package controleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;

import service.FileHelper;
import service.MyFileChooser;

public class CsvManager
{
	private static final String SEPARATEUR = ";";
	private static final Hashtable<String, String> ASSOCIATION = new Hashtable<String, String>();
	private static final Hashtable <Integer, Hashtable<String, String>> ASSOCIATION_COMPLEXE = new Hashtable<Integer, Hashtable<String, String>>();
	private static final int NB_COLONNES = 8;
	
	public CsvManager() {}
	
	public Hashtable<String, String> extraireDonneesBddCSV (MyFileChooser selectionFichier)
	{
		//On initialise le fichier selon son path absolu pour éviter tout retour "FileNotFoundException"
		//Si le fichier n'existe pas il ne peut pas etre sélectionné.
		File fichierCsv = new File (selectionFichier.getCheminAccesFichier());
		FileInputStream fichier = null;
		
		try
		{
			//On ouvre le fichier en stream pour le parcourir
			fichier = new FileInputStream(fichierCsv);
			System.out.println("Le fichier est initialisé");
			
			try
			{
				//On ouvre la lecture du fichier
				fichier.read();
				System.out.println("Le fichier est en lecture. \n");
				//On lit le fichier avec un charset par défaut
				String contenu = FileHelper.lire(fichierCsv, Charset.defaultCharset());
				
				//Stockage des informations du fichiers selon un séparateur
				String [] contenuSplit = contenu.split(SEPARATEUR);
				
				//Duplication dans une ArrayList pour gérer une exception et ordonner le tableau de données en direct
				ArrayList<String> listeContenu = new ArrayList<String>();
				
				//On initialise 2 nouveaux ArrayList pour créer un tableau associatif "nomInfo:valeurInfo"
				ArrayList<String> nomInfo = new ArrayList<String>();
				ArrayList<String> valeurInfo = new ArrayList<String>();
				
				//On parcourt le tableau remplit par la lecture du fichier CSV
				for(int i = 0; i < contenuSplit.length; i++)
				{
					//Si l'élément parcourut ne contient pas la suite de caractère "\n" alors on l'ajoute à l'ArrayList
					//Sinon on sépare l'élément dans un nouveau tableau temporaire selon le caractère mis en condition.
					//On parcourt ce tableau temporaire pour remplir l'ArrayList en cohérence selon l'index.
					if(!contenuSplit[i].contains("\n"))
						listeContenu.add(contenuSplit[i]);
					else
					{
						//On crée un tableau qui comprends les éléments séparés selon le caractère "\n" pour gérer le retour de ligne
						String [] splitCellule = contenuSplit[2].split("\n");
						
						for (int j = 0; j< splitCellule.length; j++)
							listeContenu.add(splitCellule[j]);
						
						splitCellule = null;
					}
				}
				contenuSplit = null;
				System.out.println("*****************Vérification association*****************");
				//Définition de l'index de séparation entre les clés et valeurs
				int separeIndexValeur = listeContenu.size()/2;
				
				//Association des noms d'informations dans une nouvelle ArrayList
				//On boucle pour ajouter chaque valeur définie comme clé et chaque élément définie comme une valeur
				for(int i = 0; i < separeIndexValeur; i++)
					nomInfo.add(listeContenu.get(i));
					
				for (int j = separeIndexValeur; j < listeContenu.size(); j++)
					valeurInfo.add(listeContenu.get(j));
				
				listeContenu = null;
				
				//On met en relation dans une Hashtable les correspondances d'index d'ArrayList
				for (int i = 0; i < nomInfo.size(); i++)
				{
					ASSOCIATION.put(nomInfo.get(i), valeurInfo.get(i));
				}
				
				valeurInfo = null;
				nomInfo = null;
				
				System.out.println("Valeur : " + ASSOCIATION.entrySet());
				
				System.out.println("*****************Vérification association***************** \n");
				
				//Le traitement de fichier terminé, on ferme le FileInputStream pour libérer la mémoire
				try 
				{
					fichier.close();
					System.out.println("Le fichier est fermé.");
				}
				catch (IOException e1) {e1.printStackTrace();}
			}
			catch (IOException e) {e.printStackTrace();}
			
		}
		catch (FileNotFoundException e){e.printStackTrace();}

		//On libère de la mémoire
		fichierCsv = null;
		fichier = null;
				
		return ASSOCIATION;
	}
	
	public Hashtable<Integer, Hashtable<String, String>> extraireDonneesTableCSV (MyFileChooser selectionFichier)
	{
		//On initialise le fichier selon son path absolu pour éviter tout retour "FileNotFoundException"
		//Si le fichier n'existe pas il ne peut pas etre sélectionné.
		File fichierCsv = new File (selectionFichier.getCheminAccesFichier());
		FileInputStream fichier = null;
		
		try
		{
			//On ouvre le fichier en stream pour le parcourir
			fichier = new FileInputStream(fichierCsv);
			System.out.println("Le fichier est initialisé");
			
			try
			{
				//On ouvre la lecture du fichier
				fichier.read();
				System.out.println("Le fichier est en lecture.");
				//On lit le fichier avec un charset par défaut
				String contenu = FileHelper.lire(fichierCsv, Charset.defaultCharset());
				
				//Stockage des informations du fichiers selon un séparateur
				String [] contenuSplit = contenu.split(SEPARATEUR);
				
				//Duplication dans une ArrayList pour gérer une exception et ordonner le tableau de données en direct
				ArrayList<String> listeContenu = new ArrayList<String>();
				
				//On parcourt le tableau remplit par la lecture du fichier CSV
				for(int i = 0; i < contenuSplit.length; i++)
				{
					//Si l'élément parcourut ne contient pas la suite de caractère "\n" alors on l'ajoute à l'ArrayList
					//Sinon on sépare l'élément dans un nouveau tableau temporaire selon le caractère mis en condition.
					//On parcourt ce tableau temporaire pour remplir l'ArrayList en cohérence selon l'index.
					if(!contenuSplit[i].contains("\n"))
						listeContenu.add(contenuSplit[i]);
					else
					{
						//On crée un tableau qui comprends les éléments séparés selon le caractère "\n" pour gérer le retour de ligne
						String [] splitCellule = contenuSplit[i].split("\n");						
						for (int j = 0; j< splitCellule.length; j++)
							listeContenu.add(splitCellule[j]);
						
						splitCellule = null;
					}
				}
				
				//On initialise 2 nouveaux ArrayList pour créer un tableau associatif "nomInfo:valeurInfo"
				ArrayList<String> nomInfo = new ArrayList<String>();
				ArrayList<String> valeurInfo = new ArrayList<String>();
				
				//Association des noms d'informations dans une nouvelle ArrayList
				//On boucle pour ajouter chaque valeur définie comme clé et chaque élément définie comme une valeur
				for(int i = 0; i < NB_COLONNES; i++)
					nomInfo.add(listeContenu.get(i));
					
				for (int j = NB_COLONNES; j < listeContenu.size(); j++)
					valeurInfo.add(listeContenu.get(j));
					
				listeContenu = null;
				
				//On met en relation dans une Hashtable les correspondances d'index d'ArrayList
				for (int i = 0; i < nomInfo.size(); i++)
				{
					ASSOCIATION.put(nomInfo.get(i), valeurInfo.get(i));
				}
				
				valeurInfo = null;
				
				//On met en relation dans une Hashtable les correspondances d'index d'ArrayList
				for (int i = 0; i < nomInfo.size(); i++)
				{
					ASSOCIATION_COMPLEXE.put(i, ASSOCIATION);
				}
				
				nomInfo = null;
			}
			catch (IOException e) {e.printStackTrace();}
		}
		catch (FileNotFoundException e){e.printStackTrace();}

		//On libère de la mémoire
		fichierCsv = null;
		fichier = null;
		
		return ASSOCIATION_COMPLEXE;
	}
}
