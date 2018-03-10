package interpreter;

class ExceptionFactory {
	
	public static final String DEFAULT_SYNTAX_FILENAME = "interpreter/Syntax";
	public static final String DEFAULT_LANGUAGE_FILENAME = "interpreter/English";
	public static final String DEFAULT_NUMARGS_FILENAME = "interpreter/NumArgsFoCommands";
	
	protected ExceptionFactory() {

	}
	
	protected void getException(String propertiesFile, String issue) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		if (propertiesFile.equals(DEFAULT_SYNTAX_FILENAME)) {
			throw new BadFormatException(issue);
		}
		else if (propertiesFile.equals(DEFAULT_LANGUAGE_FILENAME)) {
			throw new UnidentifiedCommandException(issue);
		}
		else if (propertiesFile.equals(DEFAULT_NUMARGS_FILENAME)) {
			throw new MissingInformationException(issue);
		}
	}
	
}
