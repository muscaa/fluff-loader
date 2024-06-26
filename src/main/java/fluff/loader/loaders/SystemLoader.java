package fluff.loader.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import fluff.loader.AbstractLoader;

/**
 * A loader that loads classes and resources using the system class loader.
 */
public class SystemLoader extends AbstractLoader {
	
    /**
     * Constructs a SystemLoader with the specified priority.
     *
     * @param priority the priority of this loader
     */
    public SystemLoader(int priority) {
        setPriority(priority);
    }
    
    @Override
    public Class<?> loadClass(String className, boolean resolve) {
        if (!isEnabled()) return null;
        
        try {
            return ClassLoader.getSystemClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {}
        return null;
    }
    
    @Override
    public URL getResource(String name) {
        if (!isEnabled()) return null;
        
        return ClassLoader.getSystemResource(name);
    }
    
    @Override
    public InputStream getResourceAsStream(String name) {
        if (!isEnabled()) return null;
        
        return ClassLoader.getSystemResourceAsStream(name);
    }
    
    @Override
    public Iterator<URL> getResources(String name) {
    	if (!isEnabled()) return null;
    	
    	try {
			return ClassLoader.getSystemResources(name).asIterator();
		} catch (IOException e) {}
    	return null;
    }
}
