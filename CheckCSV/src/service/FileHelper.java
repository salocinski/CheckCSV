package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
 
public class FileHelper
{
	/**
	* Lit un fichier texte depuis un flux d'octets entrant
	*/
	public static String lire(InputStream inputStream, Charset charset) throws IOException {
		return new BufferedReader(new InputStreamReader(inputStream, charset)).lines().collect(Collectors.joining("\n"));
	}

       // les mÃ©thodes qui suivent sont des mÃ©thodes de convenience qui s'appuient toutes sur la prÃ©cÃ©dente

       /**
		* Lit un fichier texte depuis le filesystem
	   */
       public static String lire(File file, Charset charset) throws IOException
	   {
			try(FileInputStream inputStream = new FileInputStream(file))
			{
			   return lire(inputStream, charset);
			}
		}

        /**
		* Lit un fichier texte depuis une URL
		*/
	public static String lire(URL url, Charset charset) throws IOException
	{
	     try(InputStream inputStream = url.openStream())
		 {
	        return lire(inputStream, charset);
	     }
	}

        /**
		* Lit un fichier depuis un path
		*/
	public static String lire(Path path, Charset charset) throws IOException {
	     try(InputStream inputStream = Files.newInputStream(path)) {
	        return lire(inputStream, charset);
	     }
	}


       /**
		* Lit un fichier depuis un fichier ZIP
		*/
       public static String lire(File file, String entryName, Charset charset) throws IOException {
		try(ZipFile zipfile = new ZipFile(file))
		{
			ZipEntry entry = zipfile.getEntry(entryName);
			
			if ( entry==null )
			{
				throw new IOException("Entry not found");
			}
			if ( entry.isDirectory() )
			{
				throw new IOException("Entry is not file");
			}
			return lire(zipfile.getInputStream(entry),charset);
		}
	}
}