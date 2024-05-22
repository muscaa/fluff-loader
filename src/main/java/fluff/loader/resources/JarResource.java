package fluff.loader.resources;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import fluff.loader.IResource;

public class JarResource implements IResource {
	
	private final Map<String, byte[]> contents = new HashMap<>();
	
	private final URL url;
	private final URL jarUrl;
	
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
				
                contents.put(jarEntry.getName(), jis.readAllBytes());
			}
			
			return true;
		} catch (IOException e) {}
		
		return false;
	}
	
	@Override
	public URL getURL(String name) {
		if (!contents.containsKey(name)) return null;
		
		try {
			return new URL(jarUrl, name);
		} catch (MalformedURLException e) {}
		
		return null;
	}
	
	@Override
	public byte[] getBytes(String name) {
		return contents.containsKey(name) ? contents.get(name) : null;
	}
}
