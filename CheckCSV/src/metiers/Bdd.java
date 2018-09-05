package metiers;

public class Bdd
{
	private String nomBdd;
	private String charsetBdd;
	private String collateBdd;
	
	public Bdd (String nomBdd, String charsetBdd, String collateBdd)
	{
		this.nomBdd = nomBdd;
		if(charsetBdd != null)
			this.charsetBdd = charsetBdd;
		else
			charsetBdd = "UTF8";
		
		if(collateBdd != null)
			this.collateBdd = collateBdd;
		else
			collateBdd = "UTF_8_GENERAL_CI";
	}

	public String getNomBdd()
	{
		return nomBdd;
	}

	public void setNomBdd(String nomBdd)
	{
		this.nomBdd = nomBdd;
	}

	public String getCharsetBdd()
	{
		return charsetBdd;
	}

	public void setCharsetBdd(String charsetBdd)
	{
		this.charsetBdd = charsetBdd;
	}

	public String getCollateBdd()
	{
		return collateBdd;
	}

	public void setCollateBdd(String collateBdd)
	{
		this.collateBdd = collateBdd;
	}
}
