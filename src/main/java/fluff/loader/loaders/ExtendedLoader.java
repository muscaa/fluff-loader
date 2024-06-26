package fluff.loader.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.function.Supplier;

import fluff.loader.AbstractLoader;

/**
 * A loader that delegates class and resource loading to another class loader.
 */
public class ExtendedLoader extends AbstractLoader {
	
    private final Supplier<ClassLoader> extended;
    
    /**
     * Constructs an ExtendedLoader with the specified priority and extended class loader.
     *
     * @param priority the priority of this loader
     * @param extended a Supplier that provides the extended class loader
     */
    public ExtendedLoader(int priority, Supplier<ClassLoader> extended) {
        this.extended = extended;
        
        setPriority(priority);
    }
    
    @Override
    public Class<?> loadClass(String className, boolean resolve) {
        if (!isEnabled()) return null;
        
        try {
            return extended.get().loadClass(className);
        } catch (ClassNotFoundException e) {}
        return null;
    }
    
    @Override
    public URL getResource(String name) {
        if (!isEnabled()) return null;
        
        return extended.get().getResource(name);
    }
    
    @Override
    public InputStream getResourceAsStream(String name) {
        if (!isEnabled()) return null;
        
        return extended.get().getResourceAsStream(name);
    }
    
    @Override
    public Iterator<URL> getResources(String name) {
    	if (!isEnabled()) return null;
    	
    	try {
			return extended.get().getResources(name).asIterator();
		} catch (IOException e) {}
    	return null;
    }
}
