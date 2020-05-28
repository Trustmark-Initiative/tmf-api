package edu.gatech.gtri.trustmark.v1_0.impl.juel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.el.ELException;
import javax.el.ExpressionFactory;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.tree.ExpressionNode;
import de.odysseus.el.tree.Tree;
import de.odysseus.el.tree.TreeStore;

/**
 * An implementation of the Java Expression Language {@link ExpressionFactory}
 * that makes use of the JDD BDD library.
 * 
 * @author GTRI Trustmark Team
 *
 */
public class ELExpressionFactoryJDD extends ExpressionFactoryImpl {

	protected final TreeStore store;
	
	public ELExpressionFactoryJDD() {
		super();
		Properties properties = loadProperties("el.properties");
		Profile profile = Profile.JEE6;
		store = createTreeStore(1000, profile, properties);
	}
	
	/**
	 * Body copied from {@link ExpressionFactoryImpl}.
	 * @param path
	 * @return
	 */
	protected Properties loadProperties(String path) {
		Properties properties = new Properties(loadDefaultProperties());

		// try to find and load properties
		InputStream input = null;
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		} catch (SecurityException e) {
			input = ClassLoader.getSystemResourceAsStream(path);
		}
		if (input != null) {
			try {
				properties.load(input);
			} catch (IOException e) {
				throw new ELException("Cannot read EL properties", e);
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					// ignore...
				}
			}
		}

		return properties;
	}
	
	/**
	 * Body copied from {@link ExpressionFactoryImpl}
	 */
	protected Properties loadDefaultProperties() {
		String home = System.getProperty("java.home");
		String path = home + File.separator + "lib" + File.separator + "el.properties";
		File file = new File(path);
		try {
			if (file.exists()) {
				Properties properties = new Properties();
				InputStream input = null;
				try {
					properties.load(input = new FileInputStream(file));
				} catch (IOException e) {
					throw new ELException("Cannot read default EL properties", e);
				} finally {
					try {
						input.close();
					} catch (IOException e) {
						// ignore...
					}
				}
				if (getClass().getName().equals(properties.getProperty("javax.el.ExpressionFactory"))) {
					return properties;
				}
			}
		} catch (SecurityException e) {
			// ignore...
		}
		if (getClass().getName().equals(System.getProperty("javax.el.ExpressionFactory"))) {
			return System.getProperties();
		}
		return null;
	}

	public Tree getTree(String expr) {
		return store.get(expr);
	}
	
	public ExpressionNode getTreeRoot(String expr) {
		return store.get(expr).getRoot();
	}
}
