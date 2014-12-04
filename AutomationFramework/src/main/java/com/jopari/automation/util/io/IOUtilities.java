package com.jopari.automation.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Rajnish.Verma
 * 
 */
public class IOUtilities
{

	/**
	 * Creates a new directory in a directory
	 * 
	 * @param baseDirectoryName
	 *            The directory in which the new directory will be created
	 * @param directoryName
	 *            Name of the directory to be created
	 * @return <code>true</code> if the directory is successfully created else
	 *         <code>false</code>
	 * @throws IOException
	 **/
	public static boolean createDirectory(String baseDirectoryName,
			String directoryName) throws IOException
	{
		File baseDirectory = new File(baseDirectoryName);
		boolean isDirectoryCreated = false;
		if (baseDirectory.exists() && baseDirectory.isDirectory())
		{
			File directory = new File(baseDirectory.getAbsolutePath()
					+ File.separator + directoryName);
			isDirectoryCreated = directory.mkdir();
		}
		return isDirectoryCreated;
	}

	// ////new one
	public static void deleteDirectory(File baseDirectory) throws IOException
	{
		// File baseDirectory = new File(baseDirectoryName);

		if (baseDirectory.isDirectory())
		{

			// directory is empty, then delete it
			if (baseDirectory.list().length == 0)
			{

				baseDirectory.delete();
				System.out.println("Directory is deleted : "
						+ baseDirectory.getAbsolutePath());

			} else
			{

				// list all the directory contents
				String files[] = baseDirectory.list();

				for (String temp : files)
				{
					// construct the file structure
					File fileDelete = new File(baseDirectory, temp);

					// recursive delete
					deleteDirectory(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (baseDirectory.list().length == 0)
				{
					baseDirectory.delete();
					System.out.println("Directory is deleted : "
							+ baseDirectory.getAbsolutePath());
				}
			}

		} else
		{
			// if file, then delete it
			baseDirectory.delete();
			System.out.println("File is deleted : "
					+ baseDirectory.getAbsolutePath());
		}

	}

	/**
	 * Loads a properties file and returns properties object for the same
	 * 
	 * @param propertiesFile
	 *            Name of the file which is to be loaded
	 * @return Properties object
	 * @throws FileNotFoundException
	 *             If the file given by <code>propertiesFile</code> doesn't
	 *             exist.
	 * @throws IOException
	 *             if an error occurred when reading from the
	 *             <code>propertiesFile</code>
	 */
	public static Properties loadProperties(String propertiesFile)
			throws FileNotFoundException, IOException
	{
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFile));
		return properties;
	}

	/**
	 * Loads a properties file from a xml file and returns properties object for
	 * the same
	 * 
	 * @param propertiesFile
	 *            Name of the file which is to be loaded
	 * @return Properties object
	 * @throws FileNotFoundException
	 *             If the file given by <code>propertiesFile</code> doesn't
	 *             exist.
	 * @throws IOException
	 *             if an error occurred when reading from the
	 *             <code>propertiesFile</code>
	 */
	public static Properties loadPropertiesFromXML(String propertiesFile)
			throws FileNotFoundException, IOException
	{
		Properties properties = new Properties();
		properties.loadFromXML(new FileInputStream(propertiesFile));
		return properties;
	}

	/**
	 * Read the passed file name line by line
	 * 
	 * @param fileName
	 *            - The name of file, which need to parsed
	 * @return - The <code>List of string</code>, in this each object will
	 *         define one line of file.
	 * @throws IOException
	 *             - If file not found or error in reading the file
	 */
	public static List<String> readTextLinesInFile(String fileName)
			throws IOException
	{
		List<String> textLinesInFile = new ArrayList<String>();
		if (fileName != null && fileName.length() > 0)
		{
			File filetoBeRead = new File(fileName);
			if (!filetoBeRead.exists())
			{
				throw new FileNotFoundException("The file " + fileName
						+ " can not be found.");
			}

			try (BufferedReader reader = new BufferedReader(new FileReader(
					filetoBeRead)))
			{
				String line = null;
				while ((line = reader.readLine()) != null)
				{
					if (line.trim().length() > 0)
					{
						textLinesInFile.add(line);
					}
				}

			} catch (IOException e)
			{
				throw e;
			}
		}
		return textLinesInFile;
	}

	/**
	 * This method checks whether the input parameter contains any special
	 * character or not and return boolean value
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isContainSpecialChar(String value)
	{
		Pattern pattern = Pattern.compile("[^a-z0-9 ]",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(value);
		boolean isConatinSpecialCharacter = matcher.find();
		return isConatinSpecialCharacter;
	}

	/**
	 * This method checks whether the input parameter contains any white Space
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isContainsWhiteSpace(String value)
	{
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(value);
		boolean found = matcher.find();
		return found;
	}
}
