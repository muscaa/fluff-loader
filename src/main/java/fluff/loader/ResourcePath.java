package fluff.loader;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Manages a collection of resources and provides methods to add resources and retrieve data from them.
 */
public class ResourcePath {
    
    private final List<IResource> resources = new ArrayList<>();
    
    /**
     * Adds a resource to the resource path.
     *
     * @param resource the resource to add
     * @return true if the resource was added successfully, false otherwise
     */
    public boolean add(IResource resource) {
        if (resource.load()) {
            resources.add(resource);
            return true;
        }
        return false;
    }
    
    /**
     * Retrieves the URL of a resource by name.
     *
     * @param name the name of the resource
     * @return the URL of the resource, or null if not found
     */
    public URL getURL(String name) {
        for (IResource r : resources) {
            URL url = r.getURL(name);
            
            if (url != null) return url;
        }
        return null;
    }
    
    /**
     * Retrieves an InputStream for a resource by name.
     *
     * @param name the name of the resource
     * @return an InputStream for the resource, or null if not found
     */
    public InputStream getInputStream(String name) {
        for (IResource r : resources) {
            InputStream is = r.getInputStream(name);
            
            if (is != null) return is;
        }
        return null;
    }
    
    public Iterator<URL> getURLs(String name) {
    	List<URL> list = new LinkedList<>();
    	
    	for (IResource r : resources) {
    		Iterator<URL> urls = r.getURLs(name);
    		
    		if (urls != null) urls.forEachRemaining(list::add);
    	}
    	
    	return list.iterator();
    }
}
