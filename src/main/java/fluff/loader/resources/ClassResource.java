package fluff.loader.resources;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import fluff.loader.AbstractNamedResource;
import fluff.loader.utils.JarEntryInputStream;

/**
 * Represents a class resource.
 */
public class ClassResource extends AbstractNamedResource {
	
	public static final int TYPE_FILE = 0;
	public static final int TYPE_URL = 1;
	public static final int TYPE_JAR_ENTRY = 2;
	public static final int TYPE_IN_MEMORY = 3;
	
	protected final int type;
	protected File file;
	protected URL url;
	protected File jarFile;
	protected String entryName;
	protected byte[] data;
	
	/**
	 * Constructs a ClassResource with the specified name and type.
	 * 
	 * @param name the name of the resource
	 * @param type the type of the resource
	 */
	protected ClassResource(String name, int type) {
		super(name);
		
		this.type = type;
	}
	
    /**
     * Constructs a ClassResource with the specified name and file.
     * 
     * @param name the name of the resource
     * @param file the class file
     */
	public ClassResource(String name, File file) {
		this(name, TYPE_FILE);
		
		this.file = file;
	}
	
    /**
     * Constructs a ClassResource with the specified name and URL.
     *
     * @param name the name of the resource
     * @param url the class URL
     */
	public ClassResource(String name, URL url) {
		this(name, TYPE_URL);
		
		this.url = url;
	}
	
	/**
	 * Constructs a ClassResource with the specified name, jar file and entry name.
	 * 
	 * @param name the name of the resource
	 * @param jarFile the jar file
	 * @param entryName the class entry name
	 */
	public ClassResource(String name, File jarFile, String entryName) {
		this(name, TYPE_JAR_ENTRY);
		
		this.jarFile = jarFile;
		this.entryName = entryName;
	}
	
    /**
     * Constructs an ClassResource with the specified name and data.
     *
     * @param name the name of the resource
     * @param data the class data
     */
	public ClassResource(String name, byte[] data) {
		this(name, TYPE_IN_MEMORY);
		
		this.data = data;
	}
	
	@Override
	public boolean init() {
		return true;
	}
	
	@Override
	public URL getURL() {
		try {
			return switch (type) {
				case TYPE_FILE -> file.toURI().toURL();
				case TYPE_URL -> url;
				case TYPE_JAR_ENTRY -> new URL(JarResource.getJarURL(jarFile.toURI().toURL()), entryName);
				case TYPE_IN_MEMORY -> null;
				default -> null;
			};
		} catch (IOException e) {}
		
		return null;
	}
	
	@Override
	public InputStream openInputStream() {
		try {
			return switch (type) {
				case TYPE_FILE -> new FileInputStream(file);
				case TYPE_URL -> url.openStream();
				case TYPE_JAR_ENTRY -> new JarEntryInputStream(jarFile, entryName);
				case TYPE_IN_MEMORY -> new ByteArrayInputStream(data);
				default -> null;
			};
		} catch (IOException e) {}
		
		return null;
	}
}
