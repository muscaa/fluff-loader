package fluff.loader;

import java.io.InputStream;
import java.net.URL;

public abstract class AbstractLoader {
	
	private IClassBuilder builder;
	private boolean enabled = true;
	private int priority = 0;
	
    public abstract Class<?> loadClass(String className, boolean resolve);
    
    public abstract InputStream getResourceAsStream(String name);
    
    public abstract URL getResource(String name);
    
    public boolean isEnabled() {
    	return enabled;
    }
    
    public void setEnabled(boolean enabled) {
    	this.enabled = enabled;
    }
    
    protected int getPriority() {
    	return priority;
    }
    
    protected void setPriority(int priority) {
    	this.priority = priority;
    }
    
    protected void init(IClassBuilder builder) {
    	this.builder = builder;
    }
    
    public IClassBuilder builder() {
    	return builder;
    }
}
