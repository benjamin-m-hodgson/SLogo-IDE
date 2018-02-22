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
	private List<Entry<String, Pattern>> mySymbols;
	private Exception myException; 
	
	public RegexMatcher(String fileName) {
		myFileName = fileName;
		ResourceBundle resources = ResourceBundle.getBundle(fileName);
		mySymbols = new ArrayList<Entry<String, Pattern>>();
		populateWithSymbols(mySymbols, resources);
	}
	
	private void populateWithSymbols(List<Entry<String, Pattern>> listToAddTo, ResourceBundle resourcesToAdd) {
        Enumeration<String> iter = resourcesToAdd.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            String regex = resourcesToAdd.getString(key);
            listToAddTo.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
    }
	
	public String findMatch(String text) {
		for (Entry<String, Pattern> e : mySymbols) {
            if (match(text, e.getValue())) {
                return e.getKey();
            }
        }
		// TODO FIX ARGS
		throw new MissingResourceException("Cannot find match in ResourceBundle "+myFileName+" for "+text, "", "");
	}
	
	private boolean match(String text, Pattern regex) {
        return regex.matcher(text).matches();
    }
	
}
