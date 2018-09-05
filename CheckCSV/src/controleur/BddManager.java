package controleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import javax.swing.JFileChooser;

import connexion.Connexion;
import service.FileHelper;
import service.Service;

public class BddManager
{
	private static String SEPARATEUR = ";";
	private static String SCRIPT_SQL = "src/script/creationBdd.sql";
	private File scriptTemp;
	private static final String ADRESSE_SCRIPT_TEMP = "src/script/scriptTemp.sql";
	private static Hashtable<String, String> ASSOCIATION = new Hashtable<String, String>();
	private String contenu;
	
	public BddManager (){}
	
	public void verifCsv (JFileChooser selectionFichier)
	{
		//On initialise le fichier selon son path absolu pour éviter tout retour FileNotFoundException
		//Si le fichier n'existe pas il ne peut pas etre sélectionné
		File fichierCsv = new File (selectionFichier.getSelectedFile().getAbsolutePath());
		FileInputStream fichier = null;
		try
		{
			fichier = new FileInputStream(fichierCsv);
			System.out.println("Le fichier est initialisé");
			
			try
			{
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
					//On parcourt ce tableau temporaire pour remplir l'ArrayList avec cohérence.
					if(!contenuSplit[i].contains("\n"))
						listeContenu.add(contenuSplit[i]);
					else
					{
						String [] splitCellule = contenuSplit[2].split("\n");
						
						for (int j = 0; j< splitCellule.length; j++)
							listeContenu.add(splitCellule[j]);
						
						splitCellule = null;
					}
				}
				
				System.out.println("*****************Vérification association*****************");
				//Définition de l'index de séparation entre les clés et valeurs
				int separeIndexValeur = listeContenu.size()/2;
				
				//Association des noms d'informations dans une nouvelle ArrayList
				//On boucle pour ajouter chaque valeur définie comme clé et chaque élément définie comme une valeur
				for(int i = 0; i < separeIndexValeur; i++)
					nomInfo.add(listeContenu.get(i));
					
				for (int j = separeIndexValeur; j < listeContenu.size(); j++)
					valeurInfo.add(listeContenu.get(j));
				
				//On met en relation dans une Hashtable les correspondances d'index d'ArrayList
				for (int i = 0; i < nomInfo.size(); i++)
				{
					ASSOCIATION.put(nomInfo.get(i), valeurInfo.get(i));
				}
				
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
		
		//On définit un nouveau fichier pour charger le Script SQL
		File script = new File(SCRIPT_SQL);
		
		//On récupère le texte du script dans le contenu
		try 
		{
			System.out.println("Le script SQL est en lecture. \n");
			contenu = FileHelper.lire(script, Charset.defaultCharset());
			
			System.out.println("*****************Verification script*****************");
			System.out.println("valeur script : "+contenu);
			System.out.println("*****************Verification script***************** \n");
			
			for(Entry<String, String> entry : ASSOCIATION.entrySet())
			{
				String cle = entry.getKey();
				String valeur = entry.getValue();
				
				if(contenu.contains(cle))
					contenu = contenu.replace(cle, valeur);
			}
			System.out.println("*****************Verification script modifié*****************");
			System.out.println("valeur script après modification : "+contenu);
			
			//On crée le fichier avec le nom "scriptTemp.sql"
			PrintWriter fwStream = new PrintWriter(ADRESSE_SCRIPT_TEMP, "UTF-8");
			fwStream.print(contenu);
			fwStream.close();
			scriptTemp = new File (ADRESSE_SCRIPT_TEMP);
			
			//On ferme le FileWriter et le Buffered pour libérer de l'espace mémoire car ils ne seront plus utilisés.
			System.out.println("*****************Verification script modifié***************** \n");
		} 
		catch (IOException e) {e.printStackTrace();}
		
		try 
		{
			Service mySql = new Service();
			mySql.verifMysql();
			Connexion.getConnexion(scriptTemp);
			
			if(scriptTemp.exists())
				scriptTemp.delete();
		}
		catch (IOException e) {e.printStackTrace();}
	}
}