package interpreter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.regex.Pattern;

public class RegexMatcher {

	private String myFileName; 
	private ResourceBundle myResources; 
	private List<Entry<String, Pattern>> mySymbols;
	private ExceptionFactory myExceptionFactory; 
//	private Exception myException; 
	
	public RegexMatcher(ResourceBundle resourceBundle) {
		myResources = resourceBundle; 
		mySymbols = new ArrayList<Entry<String, Pattern>>();
		myExceptionFactory = new ExceptionFactory(); 
		populateWithSymbols(mySymbols, myResources);
	}
	
	public RegexMatcher(String fileName) {
		myFileName = fileName;
		myResources = ResourceBundle.getBundle(fileName);
		mySymbols = new ArrayList<Entry<String, Pattern>>();
		myExceptionFactory = new ExceptionFactory(); 
		populateWithSymbols(mySymbols, myResources);
	}
	
	private void populateWithSymbols(List<Entry<String, Pattern>> listToAddTo, ResourceBundle resourcesToAdd) {
        Enumeration<String> iter = resourcesToAdd.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            String regex = resourcesToAdd.getString(key);
            listToAddTo.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
    }
	
	public String findMatchingKey(String text) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		for (Entry<String, Pattern> e : mySymbols) {
            if (match(text, e.getValue())) {
                return e.getKey();
            }
        }
		myExceptionFactory.getException(myFileName, text);
		return ""; 
	}
	
	public String findMatchingVal(String text) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		String val = ""; 
		try {
			val = myResources.getString(text);
		}
		catch (MissingResourceException e) {
			myExceptionFactory.getException(myFileName, text);
		}
		return val; 
	}
	
	private boolean match(String text, Pattern regex) {
        return regex.matcher(text).matches();
    }
	
}
