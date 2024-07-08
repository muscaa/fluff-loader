package fluff.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import fluff.loader.loaders.ExtendedLoader;
import fluff.loader.loaders.SystemLoader;
import fluff.loader.utils.EnumerationUtils;

/**
 * Abstract class that provides a customizable ClassLoader implementation with support for multiple loaders.
 * Loaders can be added with a specific priority, allowing for flexible class and resource loading strategies.
 */
public abstract class AbstractClassLoader extends ClassLoader {
	
    private final List<AbstractLoader> loaders = new CopyOnWriteArrayList<>();
    
    /**
     * Provides an implementation of the {@link IClassBuilder} interface for defining classes and packages.
     */
    protected final IClassBuilder builder = new IClassBuilder() {
        @Override
        public Package definePackage(String name, String specTitle, String specVersion, String specVendor,
                                     String implTitle, String implVersion, String implVendor, URL sealBase) {
            return AbstractClassLoader.this.definePackage(name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, sealBase);
        }
        
        @Override
        public Class<?> defineClass(String name, byte[] b, int off, int len) {
            return AbstractClassLoader.this.defineClass(name, b, off, len);
        }
        
        @Override
        public void resolveClass(Class<?> c) {
            AbstractClassLoader.this.resolveClass(c);
        }
    };
    
    private final SystemLoader systemLoader = new SystemLoader(0);
    private final ExtendedLoader contextLoader = new ExtendedLoader(systemLoader.getPriority() + 10, Thread.currentThread()::getContextClassLoader);
    private final ExtendedLoader parentLoader = new ExtendedLoader(contextLoader.getPriority() + 10, this::getParent);
    private final ExtendedLoader currentLoader = new ExtendedLoader(parentLoader.getPriority() + 10, this.getClass()::getClassLoader);
    
    /**
     * Constructs an AbstractClassLoader with the specified parent class loader.
     *
     * @param parent the parent class loader
     */
    public AbstractClassLoader(ClassLoader parent) {
        super(parent);
        
        addLoader(systemLoader);
        addLoader(contextLoader);
        addLoader(parentLoader);
        addLoader(currentLoader);
    }
    
    /**
     * Adds a loader to the list of loaders used by this class loader.
     *
     * @param loader the loader to add
     */
    public void addLoader(AbstractLoader loader) {
        loader.init(builder);
        
        for (int i = 0; i < loaders.size(); i++) {
            if (loader.getPriority() < loaders.get(i).getPriority()) {
                loaders.add(i, loader);
                return;
            }
        }
        
        loaders.add(loader);
    }
    
    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return loadClass(className, true);
    }
    
    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        if (className == null || className.isBlank()) return null;
        
        for (AbstractLoader l : loaders) {
            Class<?> clazz = l.loadClass(className, resolve);
            
            if (clazz != null) return clazz;
        }
        
        throw new ClassNotFoundException(className);
    }
    
    @Override
    public URL getResource(String name) {
        if (name == null || name.isBlank()) return null;
        
        for (AbstractLoader l : loaders) {
            URL url = l.getResource(name);
            
            if (url != null) return url;
        }
        
        return null;
    }
    
    @Override
    public InputStream getResourceAsStream(String name) {
        if (name == null || name.isBlank()) return null;
        
        for (AbstractLoader l : loaders) {
            InputStream is = l.getResourceAsStream(name);
            
            if (is != null) return is;
        }
        
        return null;
    }
    
    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        if (name == null || name.isBlank()) return EnumerationUtils.empty();
        
        List<URL> list = new LinkedList<>();
        
        for (AbstractLoader l : loaders) {
            l.getResources(list, name);
        }
        
        return EnumerationUtils.iterator(list.iterator());
    }
    
    /**
     * Returns the SystemLoader used by this class loader.
     *
     * @return the SystemLoader
     */
    public SystemLoader getSystemLoader() {
        return systemLoader;
    }
    
    /**
     * Returns the ExtendedLoader for the context class loader.
     *
     * @return the context class loader's ExtendedLoader
     */
    public ExtendedLoader getContextLoader() {
        return contextLoader;
    }
    
    /**
     * Returns the ExtendedLoader for the parent class loader.
     *
     * @return the parent class loader's ExtendedLoader
     */
    public ExtendedLoader getParentLoader() {
        return parentLoader;
    }
    
    /**
     * Returns the ExtendedLoader for the current class loader.
     *
     * @return the current class loader's ExtendedLoader
     */
    public ExtendedLoader getCurrentLoader() {
        return currentLoader;
    }
}
