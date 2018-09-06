package service;

import javax.swing.JFileChooser;

public class MyFileChooser extends JFileChooser
{
	private int option;
	private String nomSelection;
	private String extension;
	private String cheminAccesFichier;
	private int activation;
	
	public int getOption()
	{
		return option;
	}

	public void setOption(int option)
	{
		this.option = option;
	}

	public MyFileChooser()
	{
		//Filtre de sélection de fichier uniquement sur le JFileChooser
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);

		//Activation du JFileChooser
		setActivation(this.showOpenDialog(getParent()));
	}
	
	public boolean verifTypeMime()
	{
		//Récupération du nom de fichier complet
		this.setNomSelection(this.getSelectedFile().getName());
		
		//On enregistre le chemin d'accès en adresse absolue du fichier choisi.
		this.setCheminAccesFichier(this.getSelectedFile().getAbsolutePath());
		
		//Découpage du nom de fichier pour définir son extension
		this.setExtension(this.getNomSelection().substring(nomSelection.lastIndexOf(".")));
		
		//Mise sous condition du fichier sélectionné selon son type MIME
		//Si la condition n'est pas respectée, on affiche un pop-up d'erreur, on ferme la fenetre précédente de sélection
		//on ouvre une nouvelle fenetre de selection pour relancer les configurations du JFileChooser		
		if(!this.getExtension().equals(".csv"))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public String getNomSelection()
	{
		return nomSelection;
	}

	public void setNomSelection(String nomSelection)
	{
		this.nomSelection = nomSelection;
	}

	public String getExtension()
	{
		return extension;
	}

	public void setExtension(String extension)
	{
		this.extension = extension;
	}

	public int getActivation()
	{
		return activation;
	}

	public void setActivation(int activation)
	{
		this.activation = activation;
	}

	public String getCheminAccesFichier()
	{
		return cheminAccesFichier;
	}

	public void setCheminAccesFichier(String cheminAccesFichier)
	{
		this.cheminAccesFichier = cheminAccesFichier;
	}
}
