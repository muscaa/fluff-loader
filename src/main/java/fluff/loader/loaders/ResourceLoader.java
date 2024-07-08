package fluff.loader.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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
        
        byte[] bytes;
        try (InputStream is = getResourceAsStream(className.replace('.', '/') + ".class")) {
        	if (is == null) return null;
        	
            bytes = is.readAllBytes();
        } catch (IOException e) {
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
        
        return resourcePath.getURL(name);
    }
    
    @Override
    public InputStream getResourceAsStream(String name) {
        if (!isEnabled()) return null;
        
        return resourcePath.getInputStream(name);
    }
    
    @Override
    public void getResources(List<URL> list, String name) {
        if (!isEnabled()) return;
        
        resourcePath.getURLs(list, name);
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
