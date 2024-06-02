package fluff.loader.loaders;

import java.io.InputStream;
import java.net.URL;

import fluff.functions.gen.Func;
import fluff.loader.AbstractLoader;

/**
 * A loader that delegates class and resource loading to another class loader.
 */
public class ExtendedLoader extends AbstractLoader {
	
    private final Func<ClassLoader> extended;
    
    /**
     * Constructs an ExtendedLoader with the specified priority and extended class loader.
     *
     * @param priority the priority of this loader
     * @param extended a function that provides the extended class loader
     */
    public ExtendedLoader(int priority, Func<ClassLoader> extended) {
        this.extended = extended;
        
        setPriority(priority);
    }
    
    @Override
    public Class<?> loadClass(String className, boolean resolve) {
        if (!isEnabled()) return null;
        
        try {
            return extended.invoke().loadClass(className);
        } catch (ClassNotFoundException e) {}
        return null;
    }
    
    @Override
    public URL getResource(String name) {
        if (!isEnabled()) return null;
        
        return extended.invoke().getResource(name);
    }
    
    @Override
    public InputStream getResourceAsStream(String name) {
        if (!isEnabled()) return null;
        
        return extended.invoke().getResourceAsStream(name);
    }
}
