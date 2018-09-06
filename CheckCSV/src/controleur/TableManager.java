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

public class TableManager implements CreerElementSQL
{
	private static String SCRIPT_SQL = "src/script/creationTable.sql";
	private static String INSERE_CHAMP = "src/script/champTable.txt";
	private static String ADRESSE_SCRIPT_TEMP = "src/script/creationTableTemp.sql";
	private String contenuScript;
	private File scriptTemp;

	public TableManager() {}
	
	public void insererScript (Hashtable<Integer, Hashtable<String, String>> association, String nomBdd, int nbChamp)
	{
		//On définit un nouveau fichier pour charger le Script SQL
		File script = new File(SCRIPT_SQL);

		try 
		{
			System.out.println("Le script SQL est en lecture. \n");
			//On récupère le texte du script dans le contenu
			contenuScript = FileHelper.lire(script, Charset.defaultCharset());
			script = null;
			
			File insereChamp = new File(INSERE_CHAMP);
			String temp = FileHelper.lire(insereChamp, Charset.defaultCharset());
			
			String debutScript = contenuScript.substring(0, 29);
			String finScript = contenuScript.substring(31);
			
			String scriptComplet = debutScript+temp+finScript;
			System.out.println("*****************Verification script*****************");
			System.out.println(scriptComplet+"\n");
			
			debutScript = null;
			finScript = null;
			contenuScript = null;
			
			//On parcoure la Hashtable pour récupérer la clé et valeur de chaque itération.
			for (Entry<Integer, Hashtable<String, String>> index : association.entrySet())
			{
				Hashtable<String, String> indexHash = index.getValue();
				indexHash.put("nomBdd", nomBdd);
				
				for(Entry<String, String> indexInterne : indexHash.entrySet())
				{
					String cle = indexInterne.getKey();
					String valeur = indexInterne.getValue();

					//Si la clé de la Hashtable est présente dans le contenu du scriptSQL, sa valeur est remplacée par la valeur dans la Hastable
					if(scriptComplet.contains(cle))
						if(cle.equals("autoIncrement") && valeur.equals("non"))
							scriptComplet = scriptComplet.replace("autoIncrement", "");
						else if (cle.equals("autoIncrement") && valeur.equals("oui"))
							scriptComplet = scriptComplet.replace("autoIncrement", "auto_increment");
						
						if (cle.equals("notNull") && valeur.equals("non"))
							scriptComplet = scriptComplet.replace("notNull", "");
						else if (cle.equals("notNull") && valeur.equals("oui"))
							scriptComplet = scriptComplet.replace("notNull", "not_null");
						
						if(cle.equals("clePrimaire") && valeur.equals("non"))
							scriptComplet = scriptComplet.replace("PRIMARY KEY (nomChamp)", "");
						else if (cle.equals("clePrimaire") && valeur.equals("oui"))
							scriptComplet = scriptComplet.replace("PRIMARY KEY (nomChamp)", "PRIMARY KEY ("+valeur+")");
						
						scriptComplet = scriptComplet.replace(cle, valeur);
				}
			}
			//On crée le fichier modifié qui sera temporaire avec le nom "scriptTemp.sql"  et en définissant son charset.
			PrintWriter fwStream = new PrintWriter(ADRESSE_SCRIPT_TEMP, "UTF-8");
			
			//On écrit sur le fichier crée.
			fwStream.print(scriptComplet);
			
			//On ferme la variable de "Stream" pour libérer de la mémoire.
			fwStream.close();
			
			//On ouvre le nouveau fichier temporaire modifié avec les correspondances (clé = valeur)
			scriptTemp = new File (ADRESSE_SCRIPT_TEMP);
			
			System.out.println("*****************Verification script modifié*****************");
			System.out.println(scriptComplet+"\n");
		}
		catch (IOException e) {e.printStackTrace();}
		
		//On instancie un objet de type "Service" pour vérifier la disponibilité du service MySQL.
		Service mySql = new Service();
		mySql.verifMysql();
		//On libère de la mémoire.
		mySql = null;
		Connexion.getConnexion(scriptTemp, nomBdd);
		
		//On vérifie que le fichier temporaire existe bien.
		//Si c'est le cas, on le supprime après son utilisation.
		if(scriptTemp.exists())
			scriptTemp.delete();
		
		//On libère l'espace mémoire restant utilisé.
		scriptTemp = null;		
	}

	@Override
	public void insererScript(Hashtable<String, String> association)
	{}
}
