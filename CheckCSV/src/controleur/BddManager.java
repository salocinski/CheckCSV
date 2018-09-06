package controleur;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Map.Entry;

import connexion.Connexion;
import service.FileHelper;
import service.Service;

public class BddManager implements CreerElementSQL
{
	private static String SCRIPT_SQL = "src/script/creationBdd.sql";
	private File scriptTemp;
	private static final String ADRESSE_SCRIPT_TEMP = "src/script/scriptTemp.sql";
	private String contenu;
	
	public BddManager (){}
	
	public void insererScript (Hashtable<String, String> association)
	{
		//On définit un nouveau fichier pour charger le Script SQL
		File script = new File(SCRIPT_SQL);

		try 
		{
			System.out.println("Le script SQL est en lecture. \n");
			//On récupère le texte du script dans le contenu
			contenu = FileHelper.lire(script, Charset.defaultCharset());
			script = null;
			
			System.out.println("*****************Verification script*****************");
			System.out.println("valeur script : "+contenu);
			System.out.println("*****************Verification script***************** \n");
			
			//On parcoure la Hashtable pour récupérer la clé et valeur de chaque itération.
			for(Entry<String, String> entry : association.entrySet())
			{
				String cle = entry.getKey();
				String valeur = entry.getValue();
				
				//Si la clé de la Hashtable est présente dans le contenu du scriptSQL, sa valeur est remplacée par la valeur dans la Hastable
				if(contenu.contains(cle))
					contenu = contenu.replace(cle, valeur);
				
				cle = null;
				valeur = null;
			}
				
			System.out.println("*****************Verification script modifié*****************");
			System.out.println("valeur script après modification : "+contenu);
			
			//On crée le fichier modifié qui sera temporaire avec le nom "scriptTemp.sql"  et en définissant son charset.
			PrintWriter fwStream = new PrintWriter(ADRESSE_SCRIPT_TEMP, "UTF-8");
			
			//On écrit sur le fichier crée.
			fwStream.print(contenu);
			
			//On ferme la variable de "Stream" pour libérer de la mémoire.
			fwStream.close();
			
			contenu = null;
			
			//On ouvre le nouveau fichier temporaire modifié avec les correspondances (clé = valeur)
			scriptTemp = new File (ADRESSE_SCRIPT_TEMP);
			System.out.println("*****************Verification script modifié***************** \n");
		} 
		catch (IOException e) {e.printStackTrace();}
		
		try 
		{
			//On instancie un objet de type "Service" pour vérifier la disponibilité du service MySQL.
			Service mySql = new Service();
			mySql.verifMysql();
			//On libère de la mémoire.
			mySql = null;
			Connexion.getConnexion(scriptTemp);
			
			//On vérifie que le fichier temporaire existe bien.
			//Si c'est le cas, on le supprime après son utilisation.
			if(scriptTemp.exists())
				scriptTemp.delete();
			
			//On libère l'espace mémoire restant utilisé.
			scriptTemp = null;
		}
		catch (IOException e) {e.printStackTrace();}
	}
}