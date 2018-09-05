package metiers;

public class Table
{
	private String nomTable;
	private String nomChamp;
	private int tailleChamp;
	private String autoIncrement;
	private String notNull;
	
	public Table (String nomTable, String nomChamp, int tailleChamp, String autoIncrement, String notNull)
	{
		this.nomTable = nomTable;
		this.nomChamp = nomChamp;
		this.tailleChamp = tailleChamp;
		
		if(autoIncrement.equalsIgnoreCase("oui"))
			autoIncrement = "Auto_increment";
		else
			autoIncrement = null;
		
		if (notNull.equalsIgnoreCase("oui"))
			notNull = "NOT NULL ";
		else
			notNull = null;
	}

	public String getNomTable()
	{
		return nomTable;
	}

	public void setNomTable(String nomTable)
	{
		this.nomTable = nomTable;
	}

	public String getNomChamp()
	{
		return nomChamp;
	}

	public void setNomChamp(String nomChamp)
	{
		this.nomChamp = nomChamp;
	}

	public int getTailleChamp()
	{
		return tailleChamp;
	}

	public void setTailleChamp(int tailleChamp)
	{
		this.tailleChamp = tailleChamp;
	}

	public String getAutoIncrement()
	{
		return autoIncrement;
	}

	public void setAutoIncrement(String autoIncrement)
	{
		this.autoIncrement = autoIncrement;
	}

	public String getNotNull()
	{
		return notNull;
	}

	public void setNotNull(String notNull)
	{
		this.notNull = notNull;
	}
}
