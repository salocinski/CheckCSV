package connexion;

import service.FileHelper;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * 
 * @author Stryjewski Nicolas (stryjewski.n@gmail.com) Classe Connexion
 *         permettant de gerer, creer et cloturer la connexion a la BDD par le
 *         biais de la bibliotheque JDBC.
 *
 */
public class Connexion
{
	//------------------------- ATTRIBUTS -------------------------//
	
		//------------------------- ADRESSE DE LA BDD -------------------------//
	private static final String URL = "jdbc:mysql://localhost:3306/baseDeDonnee";
		//------------------------- ADRESSE DE LA BDD -------------------------//
		
		//------------------------- SCRIPT SQL -------------------------//
	private static final String SCRIPT_BDD = "src/script/creationBdd.sql";

		//------------------------- SCRIPT SQL -------------------------//
	
		//------------------------- LOGIN DE LA BDD -------------------------//
	private static final String LOGIN = "root";
	private static final String MDP = "root";
		//------------------------- ADRESSE DE LA BDD -------------------------//
	
		//------------------------- DRIVER DE LA BDD -------------------------//
	private static final String DRIVER = "com.mysql.jdbc.Driver";
		//------------------------- DRIVER DE LA BDD -------------------------//
	
		//------------------------- OUVERTURE DE CONNEXION -------------------------//
	private static Connection LA_CONNEXION = null;
		//------------------------- OUVERTURE DE CONNEXION -------------------------//
	
	//------------------------- ATTRIBUTS -------------------------//
	
	public Connexion()
	{
		//------------------------- CHARGEMENT DU DRIVER -------------------------//
		try
		{
			System.out.println("Chargement du driver...");
			Class.forName(DRIVER);
			System.out.println("Driver OK...");
		}
		catch (ClassNotFoundException ex)
		{
			System.out.println("Problème de driver");
		}
		//------------------------- CHARGEMENT DU DRIVER -------------------------//
		
		//------------------------- CONNEXION A LA BDD -------------------------//
		try
		{
			System.out.println("Connexion a la BDD...");
			LA_CONNEXION = DriverManager.getConnection(URL, LOGIN, MDP);
			System.out.println("Connexion OK...");
		}
		catch (SQLException ex)
		{
			System.out.println("Problème d'informations liées à la BDD");
		}
		//------------------------- CONNEXION A LA BDD -------------------------//
	}

	public static Connection getConnexion() throws IOException
	{
		if (LA_CONNEXION == null)
		{
			//------------------------- CHARGEMENT DU DRIVER -------------------------//
			try
			{
				System.out.println("Chargement du driver...");
				Class.forName(DRIVER).newInstance();
				System.out.println("Driver OK...");
			}
			catch (ClassNotFoundException ex)
			{
				System.out.println("Problème de driver");
			}
			catch (InstantiationException | IllegalAccessException ex)
			{
				Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
			}
			//------------------------- CHARGEMENT DU DRIVER -------------------------//
			
			//------------------------- CONNEXION A LA BDD -------------------------//
			try
			{
				System.out.println("Connexion a la BDD...");
				LA_CONNEXION = DriverManager.getConnection(URL, LOGIN, MDP);
				System.out.println("Connexion OK...");
			}
			catch (SQLException ex)
			{
				System.out.println("Problème d'informations liées à  la BDD");
				JOptionPane.showMessageDialog(null, "La base de donnée n'existe pas. \n Cliquez sur 'OK' pour l'auto générer.", "Création de la BDD", JOptionPane.INFORMATION_MESSAGE);
				
				try
				{
					//------------------------- CREATION AUTO BDD -------------------------//
					
						//------------------------- CONNEXION / DECONNEXION BDD PAR DEFAUT -------------------------//
					String bddTemp = "jdbc:mysql://localhost:3306/mysql";
					LA_CONNEXION = DriverManager.getConnection(bddTemp, LOGIN, MDP);
					
					File scriptBdd = new File(SCRIPT_BDD);
					String scriptSQL = FileHelper.lire(scriptBdd, Charset.defaultCharset());
					
					Connexion.creerBdd(scriptSQL);
					LA_CONNEXION = null;
						//------------------------- CONNEXION / DECONNEXION BDD PAR DEFAUT -------------------------//
						
						//------------------------- CONNEXION BDD APPLICATION -------------------------//
					LA_CONNEXION = DriverManager.getConnection(bddTemp, LOGIN, MDP);
						//------------------------- CONNEXION BDD APPLICATION -------------------------//
						
					//------------------------- CREATION AUTO BDD -------------------------//
				}
				catch (SQLException ex1)
				{
					Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex1);
				}
			}
			//------------------------- CONNEXION A LA BDD -------------------------//
		}
		return LA_CONNEXION;
	}
	
	public static Connection getConnexion(File scriptSql) throws IOException
	{
		if (LA_CONNEXION == null)
		{
			//------------------------- CHARGEMENT DU DRIVER -------------------------//
			try
			{
				System.out.println("Chargement du driver...");
				Class.forName(DRIVER).newInstance();
				System.out.println("Driver OK...");
			}
			catch (ClassNotFoundException ex)
			{
				System.out.println("Problème de driver");
			}
			catch (InstantiationException | IllegalAccessException ex)
			{
				Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
			}
			//------------------------- CHARGEMENT DU DRIVER -------------------------//
			
			//------------------------- CONNEXION A LA BDD -------------------------//
			try
			{
				System.out.println("Connexion a la BDD...");
				LA_CONNEXION = DriverManager.getConnection(URL, LOGIN, MDP);
				System.out.println("Connexion OK...");
			}
			catch (SQLException ex)
			{
				System.out.println("Problème d'informations liées à  la BDD");
				JOptionPane.showMessageDialog(null, "La base de donnée n'existe pas. \n Cliquez sur 'OK' pour l'auto générer.", "Création de la BDD", JOptionPane.INFORMATION_MESSAGE);
				
				try
				{
					//------------------------- CREATION AUTO BDD -------------------------//
					
						//------------------------- CONNEXION / DECONNEXION BDD PAR DEFAUT -------------------------//
					String bddTemp = "jdbc:mysql://localhost:3306/mysql";
					LA_CONNEXION = DriverManager.getConnection(bddTemp, LOGIN, MDP);
					
					String scriptSQL = FileHelper.lire(scriptSql, Charset.defaultCharset());
					Connexion.creerBdd(scriptSQL);
					LA_CONNEXION = null;
						//------------------------- CONNEXION / DECONNEXION BDD PAR DEFAUT -------------------------//
						
						//------------------------- CONNEXION BDD APPLICATION -------------------------//
					LA_CONNEXION = DriverManager.getConnection(bddTemp, LOGIN, MDP);
						//------------------------- CONNEXION BDD APPLICATION -------------------------//
						
					//------------------------- CREATION AUTO BDD -------------------------//
				}
				catch (SQLException ex1)
				{
					Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex1);
				}
			}
			//------------------------- CONNEXION A LA BDD -------------------------//
		}
		return LA_CONNEXION;
	}

	public void deconnexion() throws SQLException
	{
		//------------------------- FERMETURE CONNEXION -------------------------//
		LA_CONNEXION.close();
		LA_CONNEXION = null;
		//------------------------- FERMETURE CONNEXION -------------------------//
	}
	
	public static void creerBdd(String scriptSql)
	{
		Statement transmission = null;

		try 
		{
			transmission = LA_CONNEXION.createStatement();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
		}

		int resultat = 0;

		try
		{
			resultat = transmission.executeUpdate(scriptSql);
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
		}
		if(resultat == 1)
		{
			JOptionPane.showMessageDialog(null, "La base donnée a bien été créée.", "Confirmation de création", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("La base de données à bien été créée.");
		}
		else
			System.out.println("Erreur de création.");
	}
}