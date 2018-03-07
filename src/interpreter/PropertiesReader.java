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

class PropertiesReader {

	private String myFilePath; 
	private Properties myProps;

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
		return readInProperties; 
	}

}
