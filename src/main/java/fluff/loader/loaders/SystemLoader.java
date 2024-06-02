package fluff.loader.loaders;

import java.io.InputStream;
import java.net.URL;

import fluff.functions.gen.obj.TFunc1;
import fluff.loader.AbstractLoader;

/**
 * A loader that loads classes and resources using the system class loader.
 */
public class SystemLoader extends AbstractLoader {
	
    private final TFunc1<Class<?>, String, ClassNotFoundException> findSystemClass;
    
    /**
     * Constructs a SystemLoader with the specified priority and function to find system classes.
     *
     * @param priority the priority of this loader
     * @param findSystemClass the function to find system classes
     */
    public SystemLoader(int priority, TFunc1<Class<?>, String, ClassNotFoundException> findSystemClass) {
        this.findSystemClass = findSystemClass;
        
        setPriority(priority);
    }
    
    @Override
    public Class<?> loadClass(String className, boolean resolve) {
        if (!isEnabled()) return null;
        try {
            return findSystemClass.invoke(className);
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
}
