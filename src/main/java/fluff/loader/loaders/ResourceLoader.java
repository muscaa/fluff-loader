package fluff.loader.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fluff.loader.AbstractLoader;
import fluff.loader.ResourcePath;

/**
 * A loader that loads classes and resources from a specified resource path.
 */
public class ResourceLoader extends AbstractLoader {
	
    private final Map<String, Class<?>> classes = new HashMap<>();
    private final ResourcePath resourcePath = new ResourcePath();
    
    /**
     * Constructs a ResourceLoader with the specified priority.
     *
     * @param priority the priority of this loader
     */
    public ResourceLoader(int priority) {
        setPriority(priority);
    }
    
    @Override
    public Class<?> loadClass(String className, boolean resolve) {
        if (!isEnabled()) return null;
        
        if (classes.containsKey(className)) return classes.get(className);
        
        InputStream is = resourcePath.getInputStream(className.replace('.', '/') + ".class");
        if (is == null) return null;
        
        byte[] bytes;
        try {
        	bytes = is.readAllBytes();
        	is.close();
        } catch (IOException e) {
        	try {
				is.close();
			} catch (IOException e1) {}
        	return null;
        }
        
        Class<?> clazz = builder().defineClass(className, bytes, 0, bytes.length);
        if (clazz == null) return null;
        
        if (clazz.getPackage() == null) {
            int dotIndex = className.lastIndexOf('.');
            String packageName = dotIndex != -1 ? className.substring(0, dotIndex) : "";
            builder().definePackage(packageName, null, null, null, null, null, null, null);
        }
        
        if (resolve) builder().resolveClass(clazz);
        
        classes.put(className, clazz);
        return clazz;
    }
    
    @Override
    public URL getResource(String name) {
        if (!isEnabled()) return null;
        
        URL url = resourcePath.getURL(name);
        if (url == null) return null;
        
        return url;
    }
    
    @Override
    public InputStream getResourceAsStream(String name) {
        if (!isEnabled()) return null;
        
        InputStream is = resourcePath.getInputStream(name);
        if (is != null) return is;
        
        return null;
    }
    
    @Override
    public Iterator<URL> getResources(String name) {
    	return null;
    }

    /**
     * Returns the resource path used by this loader.
     *
     * @return the resource path
     */
    public ResourcePath getResourcePath() {
        return resourcePath;
    }
}
