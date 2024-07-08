package fluff.loader.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import fluff.loader.IResource;

/**
 * Represents a jar resource that can be loaded from a URL.
 */
public class JarResource implements IResource {
	
	private final Map<String, IResource> contents = new HashMap<>();
	
	private final URL url;
	private final URL jarUrl;
	
    /**
     * Constructs a JarResource with the specified URL.
     *
     * @param url the URL of the resource
     */
	public JarResource(URL url) throws MalformedURLException {
		this.url = url;
		this.jarUrl = new URL("jar:" + url.toString() + "!/");
	}
	
	@Override
	public boolean load() {
		try (JarInputStream jis = new JarInputStream(url.openStream())) {
			JarEntry jarEntry;
			while ((jarEntry = jis.getNextJarEntry()) != null) {
				if (jarEntry.isDirectory()) continue;
				
				String name = jarEntry.getName();
				URL url = new URL(jarUrl, name);
				
            	IResource r = name.endsWith(".class") ?
            			new ClassResource(name, url, jis.readAllBytes())
            			: new FileResource(name, url);
				
				contents.put(name, r);
			}
			
			return true;
		} catch (IOException e) {}
		
		return false;
	}
	
	@Override
	public URL getURL(String name) {
		return contents.containsKey(name) ? contents.get(name).getURL(name) : null;
	}
	
	@Override
	public InputStream getInputStream(String name) {
		return contents.containsKey(name) ? contents.get(name).getInputStream(name) : null;
	}
	
	@Override
	public void getURLs(List<URL> list, String name) {
    	for (Map.Entry<String, IResource> e : contents.entrySet()) {
    		e.getValue().getURLs(list, name);
    	}
	}
}
