// Copyright Gosselin Quentin (c) 2012
package utils;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper for <code>Properties</code>.
 *
 * @since 1.0
 * @version 1.0
 *
 * @author Quentin GOSSELIN <quentin.gosselin@gmail.com>
 */
public final class PropertiesHelper {

	/**
	 * The unique instance of <code>PropertiesHelper</code>.
	 */
	private static PropertiesHelper instance = null;
	
	/**
	 * The defined locale.
	 */
	private static Locale locale;
	
	/**
	 * All resources files.
	 */
	private static ResourceBundle /*global,*/ application, menu, error,about/*, window*/;
	
	/**
	 * The private constructor of <code>PropertiesHelper</code>.
	 */
	private PropertiesHelper() {
		locale = Locale.getDefault();
//                locale= new Locale("ru");
		//global = ResourceBundle.getBundle("global");
		//window = ResourceBundle.getBundle("window");
	}

	/**
	 * Initialize the application with the selected language.
	 * 
	 * @param language the new language
	 */
	public static void initialize(final String language) {
		initialize();
		locale = new Locale(language);
		reload();
	}
	
	/**
	 * Initialize the application with the system's language.
	 */
	public static void initialize() {
		if (instance == null) {
			instance = new PropertiesHelper();
			reload();
		}
	}
	
	/**
	 * Reload properties with the defined locale.
	 */
	private static void reload() {
            try{
                menu = Utf8ResourceBundle.getBundle("menu", locale);
                application = Utf8ResourceBundle.getBundle("application", locale);
                about = Utf8ResourceBundle.getBundle("about", locale);
            }
            catch(Exception e){
                Logger.getLogger(PropertiesHelper.class.getName()).log(Level.INFO, null, e);
                initialize("en");
            }
//                if(menu==null||application==null||about==null){
//                    initialize("en");
//                }
//		menu = Utf8ResourceBundle.getBundle("menu", locale);
		//error = Utf8ResourceBundle.getBundle("errors", locale);

	}
	
	/**
	 * Return the global's resources.
	 * 
	 * @return global's ResourceBundle
	 */
//	public static ResourceBundle getGlobal() {
//		if (instance == null) {
//			initialize();
//		}
//		return global;
//	}
	
	/**
	 * Return the application's resources.
	 * 
	 * @return application's ResourceBundle
	 */
	public static ResourceBundle getApplication() {
		if (instance == null) {
			initialize();
		}
		return application;
	}
	
	/**
	 * Return the menu's resources.
	 * 
	 * @return menu's ResourceBundle
	 */
	public static ResourceBundle getMenu() {
		if (instance == null) {
			initialize();
		}
		return menu;
	}
	
	/**
	 * Return the error's resources.
	 * 
	 * @return error's ResourceBundle
	 */
	public static ResourceBundle getErrors() {
		if (instance == null) {
			initialize();
		}
		return error;
	}
        
        /**
	 * Return the about's resources.
	 * 
	 * @return error's ResourceBundle
	 */
	public static ResourceBundle getAbout() {
		if (instance == null) {
			initialize();
		}
		return about;
	}
	
	/**
	 * Return the window's resources.
	 * 
	 * @return window's ResourceBundle
	 */
//	public static ResourceBundle getWindow() {
//		if (instance == null) {
//			initialize();
//		}
//		return window;
//	}
 }