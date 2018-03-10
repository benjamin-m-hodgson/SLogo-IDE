package interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 * @author susiechoi
 * Used for obtaining information about the keys and values in a Properties file. 
 * Useful for obtaining ColorPalette or saved UserCommand/Variable information, which is manipulated throughout the course of the program. 
 * Use by creating a PropertiesReader object with the filepath as a String argument. From there, can get a map of 
 * keys->vals in the PropertiesFile, search for a given key/val in the file, etc. 
 */
class PropertiesReader {

	private String myFilePath; 
	private Properties myProps;
	private Map<String, String> myReadInProperties; 

	protected PropertiesReader(String filepath) {
		myFilePath = filepath;
		myProps = new Properties(); 
		FileInputStream in = null;

		try {
			in = new FileInputStream(new File(myFilePath));
			myProps.load(in);
		} catch (FileNotFoundException e) {
			throw new MissingResourceException(myFilePath, "", "");
		} catch (IOException e) {
			throw new MissingResourceException(myFilePath, "", "");
		}
		
	}

	protected Map<String, String> read() {
		Map<String, String> readInProperties = new HashMap<String, String>(); 
		for (Enumeration<?> e = myProps.propertyNames(); e.hasMoreElements(); ) {
			String key = (String)e.nextElement();
			String val = myProps.getProperty(key);
//			System.out.println(key+" "+val);
			readInProperties.put(key, val);
		}
		myReadInProperties = readInProperties;
		return readInProperties; 
	}
	
	protected String findVal(String target) {
		read();
		if (myReadInProperties.containsKey(target)) {
			return myReadInProperties.get(target);
		}
		return ""; 
	}
	
	protected String findKey(String targetVal) {
		read();
		for (String key : myReadInProperties.keySet()) {
			if (myReadInProperties.get(key).equals(targetVal)) {
				return key;
			}
		}
		return ""; 
	}
	
	protected boolean containsKey(String target) {
		read();
		return (myReadInProperties.containsKey(target));
	}

}
