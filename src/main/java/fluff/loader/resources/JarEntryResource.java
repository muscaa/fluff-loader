package fluff.loader.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import fluff.loader.AbstractNamedResource;
import fluff.loader.utils.JarEntryInputStream;

/**
 * Represents a jar entry resource.
 */
public class JarEntryResource extends AbstractNamedResource {
	
	protected final File jarFile;
	protected final String entryName;
	
	/**
	 * Constructs a JarEntryResource with the specified name, jar file and entry name.
	 * 
	 * @param name the name of the resource
	 * @param jarFile the jar file
	 * @param entryName the entry name
	 */
	public JarEntryResource(String name, File jarFile, String entryName) {
		super(name);
		
		this.jarFile = jarFile;
		this.entryName = entryName;
	}
	
	@Override
	public boolean init() {
		return true;
	}
	
	@Override
	public URL getURL() {
		try {
			return new URL(JarResource.getJarURL(jarFile.toURI().toURL()), entryName);
		} catch (MalformedURLException e) {}
		
		return null;
	}
	
	@Override
	public InputStream openInputStream() {
		try {
			return new JarEntryInputStream(jarFile, entryName);
		} catch (IOException e) {}
		
		return null;
	}
}
