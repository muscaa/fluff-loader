package fluff.loader.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import fluff.loader.IResource;

/**
 * Represents a file resource that can be loaded from a URL.
 */
public class FileResource implements IResource {
    
    private final String name;
    private final URL url;
    
    /**
     * Constructs a FileResource with the specified name and URL.
     *
     * @param name the name of the resource
     * @param url the URL of the resource
     */
    public FileResource(String name, URL url) {
        this.name = name;
        this.url = url;
    }
    
    @Override
    public boolean load() {
        return true;
    }
    
    @Override
    public URL getURL(String name) {
        return this.name.equals(name) ? url : null;
    }
    
    @Override
    public InputStream getInputStream(String name) {
    	if (!this.name.equals(name)) return null;
    	
        try {
			return url.openStream();
		} catch (IOException e) {}
        
        return null;
    }
    
    @Override
    public Iterator<URL> getURLs(String name) {
    	URL url = getURL(name);
    	
    	return url != null ? List.of(url).iterator() : null;
    }
}
