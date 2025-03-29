package fluff.loader;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a resource with contents.
 */
public abstract class AbstractContentsResource implements IResource {
	
	protected final Map<String, IResource> contents = new HashMap<>();
	
	@Override
	public void getURLs(List<URL> list, String name) {
    	for (Map.Entry<String, IResource> e : contents.entrySet()) {
    		e.getValue().getURLs(list, name);
    	}
	}
	
	@Override
	public URL getURL(String name) {
		IResource resource = contents.get(name);
		
		return resource != null ? resource.getURL(name) : null;
	}
	
	@Override
	public InputStream openInputStream(String name) {
		IResource resource = contents.get(name);
		
		return resource != null ? resource.openInputStream(name) : null;
	}
	
	/**
	 * Initializes and registers the resource with the specified name.
	 * 
	 * @param name the name of the resource
	 * @param resource the resource to register
	 * @return true if the resource was successfully registered, false otherwise
	 */
	public boolean register(String name, IResource resource) {
        if (resource.init()) {
        	contents.put(name, resource);
            return true;
        }
        return false;
	}
	
	/**
	 * Gets the contents of the resource.
	 * 
	 * @return the contents of the resource
	 */
	public Map<String, IResource> getContents() {
		return contents;
	}
}
