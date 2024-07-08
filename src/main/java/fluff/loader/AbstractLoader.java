package fluff.loader;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Abstract class that represents a loader for classes and resources.
 * Provides basic functionality for enabling/disabling the loader and setting its priority.
 */
public abstract class AbstractLoader {
	
    private IClassBuilder builder;
    private boolean enabled = true;
    private int priority = 0;
    
    /**
     * Loads the class with the specified name.
     *
     * @param className the name of the class to load
     * @param resolve   if true, resolve the class
     * @return the resulting Class object, or null if the class could not be found
     */
    public abstract Class<?> loadClass(String className, boolean resolve);
    
    /**
     * Finds the resource with the given name.
     *
     * @param name the resource name
     * @return a URL for the resource, or null if the resource could not be found
     */
    public abstract URL getResource(String name);
    
    /**
     * Returns an input stream for reading the specified resource.
     *
     * @param name the resource name
     * @return an InputStream for reading the resource, or null if the resource could not be found
     */
    public abstract InputStream getResourceAsStream(String name);
    
    /**
     * Finds the resources with the given name.
     *
     * @param list the list where to store the resources
     * @param name the resource name
     */
    public abstract void getResources(List<URL> list, String name);
    
    /**
     * Returns whether this loader is enabled.
     *
     * @return true if this loader is enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * Sets whether this loader is enabled.
     *
     * @param enabled true to enable this loader, false to disable it
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * Returns the priority of this loader.
     *
     * @return the priority of this loader
     */
    protected int getPriority() {
        return priority;
    }
    
    /**
     * Sets the priority of this loader.
     *
     * @param priority the priority to set
     */
    protected void setPriority(int priority) {
        this.priority = priority;
    }
    
    /**
     * Initializes this loader with the specified class builder.
     *
     * @param builder the class builder to use for class definition and resolution
     */
    protected void init(IClassBuilder builder) {
        this.builder = builder;
    }
    
    /**
     * Returns the class builder associated with this loader.
     *
     * @return the class builder
     */
    protected IClassBuilder builder() {
        return builder;
    }
}
