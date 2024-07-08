package fluff.loader.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Set;

import fluff.loader.AbstractLoader;

/**
 * A loader that delegates class and resource loading to another class loader.
 */
public class ExtendedLoader extends AbstractLoader {
	
    private final ClassLoader extended;
    
    /**
     * Constructs an ExtendedLoader with the specified used class loaders, priority and extended class loader.
     *
     * @param inUse the Set containing the class loaders already in use
     * @param priority the priority of this loader
     * @param extended the extended class loader
     */
    public ExtendedLoader(Set<ClassLoader> inUse, int priority, ClassLoader extended) {
        this.extended = extended;
        
        setPriority(priority);
        
        if (inUse.contains(extended)) {
        	setEnabled(false);
        } else {
        	inUse.add(extended);
        }
    }
    
    @Override
    public Class<?> loadClass(String className, boolean resolve) {
        if (!isEnabled()) return null;
        
        try {
            return extended.loadClass(className);
        } catch (ClassNotFoundException e) {}
        return null;
    }
    
    @Override
    public URL getResource(String name) {
        if (!isEnabled()) return null;
        
        return extended.getResource(name);
    }
    
    @Override
    public InputStream getResourceAsStream(String name) {
        if (!isEnabled()) return null;
        
        return extended.getResourceAsStream(name);
    }
    
    @Override
    public void getResources(List<URL> list, String name) {
    	if (!isEnabled()) return;
    	
    	try {
			extended.getResources(name)
					.asIterator()
					.forEachRemaining(list::add);
		} catch (IOException e) {}
    }
}
