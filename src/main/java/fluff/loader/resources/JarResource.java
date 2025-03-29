package fluff.loader.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import fluff.loader.AbstractContentsResource;

/**
 * Represents a jar resource.
 */
public class JarResource extends AbstractContentsResource {
	
	public static final int TYPE_FILE = 0;
	public static final int TYPE_URL = 1;
	
	protected final boolean preload;
	protected final int type;
	protected File file;
	protected URL url;
	
	/**
	 * Constructs a JarResource with the specified preload value and type.
	 * 
	 * @param preload whether to preload the classes in the jar (better
	 * performance at the cost of an overhead and higher memory usage)
	 * @param type the type of the jar resource
	 */
	protected JarResource(boolean preload, int type) {
		this.preload = preload;
		this.type = type;
	}
	
	/**
	 * Constructs a JarResource with the specified preload value and file.
	 * 
	 * @param preload whether to preload the classes in the jar (better
	 * performance at the cost of an overhead and higher memory usage)
	 * @param file the file to load the jar from
	 */
	public JarResource(boolean preload, File file) {
		this(preload, TYPE_FILE);
		
		this.file = file;
	}
	
	/**
	 * Constructs a JarResource with the specified preload value and url.
	 * 
	 * @param preload whether to preload the classes in the jar (better
	 * performance at the cost of an overhead and higher memory usage)
	 * @param url the url to load the jar from
	 */
	public JarResource(boolean preload, URL url) {
		this(preload, TYPE_URL);

		this.url = url;
	}
	
	@Override
	public boolean init() {
		try {
			return switch (type) {
				case TYPE_FILE -> loadFile(file);
				case TYPE_URL -> loadInputStream(url.openStream(), getJarURL(url));
				default -> false;
			};
		} catch (IOException e) {}
		
		return false;
	}
	
	/**
	 * Loads the file into this jar resource.
	 * 
	 * @param file the file to load
	 * @return whether the file was loaded successfully
	 * @throws IOException if an I/O error occurs
	 */
	protected boolean loadFile(File file) throws IOException {
		try (JarFile jar = new JarFile(file)) {
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.isDirectory()) continue;
				
				String name = entry.getName();
				
				if (!name.endsWith(".class")) {
					if (!register(name, new JarEntryResource(name, file, name))) {
						return false;
					}
					continue;
				}
				
				if (!preload) {
					if (!register(name, new ClassResource(name, file, name))) {
						return false;
					}
					continue;
				}
				
				try (InputStream is = jar.getInputStream(entry)) {
					byte[] data = is.readAllBytes();
					
					if (!register(name, new ClassResource(name, data))) {
						return false;
					}
				}
			}
		} catch (IOException e) {}
		
		return false;
	}
	
	/**
	 * Loads the input stream into this jar resource.
	 * 
	 * @param inputStream the input stream to load
	 * @param jarUrl the url of the jar
	 * @return whether the input stream was loaded successfully
	 * @throws IOException if an I/O error occurs
	 */
	protected boolean loadInputStream(InputStream inputStream, URL jarUrl) throws IOException {
		try (JarInputStream jis = new JarInputStream(inputStream)) {
			JarEntry entry;
			while ((entry = jis.getNextJarEntry()) != null) {
				if (entry.isDirectory()) continue;
				
				String name = entry.getName();
				URL url = new URL(jarUrl, name);
				
				if (!name.endsWith(".class")) {
					if (!register(name, new URLResource(name, url))) {
						return false;
					}
					continue;
				}
				
				if (!preload) {
					if (!register(name, new ClassResource(name, url))) {
						return false;
					}
					continue;
				}
				
				byte[] data = jis.readAllBytes();
				
				if (!register(name, new ClassResource(name, data))) {
					return false;
				}
			}
			
			return true;
		} catch (IOException e) {}
		
		return false;
	}
	
	/**
	 * Returns whether the classes in this jar resource are preloaded.
	 * 
	 * @return whether the classes in this jar resource are preloaded
	 */
	public boolean isPreload() {
		return preload;
	}
	
	/**
	 * Returns the type of this jar resource.
	 * 
	 * @return the type of this jar resource
	 */
	public int getType() {
		return type;
	}
	
	/**
     * Returns the file of this jar resource.
     * 
     * @return the file of this jar resource
     */
	public File getFile() {
		return file;
	}
	
	/**
	 * Returns the url of this jar resource.
	 * 
	 * @return the url of this jar resource
	 */
	public URL getURL() {
		return url;
	}
	
	/**
	 * Returns the jar url of the specified url.
	 * 
	 * @param url the url to get the jar url of
	 * @return the jar url of the specified url
	 * @throws MalformedURLException if the jar url is invalid
	 */
	public static URL getJarURL(URL url) throws MalformedURLException {
		return new URL("jar:" + url + "!/");
	}
}
