package fluff.loader.loaders;

import java.io.InputStream;
import java.net.URL;

import fluff.functions.gen.obj.TFunc1;
import fluff.loader.AbstractLoader;

public class SystemLoader extends AbstractLoader {
	
	private final TFunc1<Class<?>, String, ClassNotFoundException> findSystemClass;
	
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
	public InputStream getResourceAsStream(String name) {
		if (!isEnabled()) return null;
		
        return ClassLoader.getSystemResourceAsStream(name);
	}
	
	@Override
	public URL getResource(String name) {
		if (!isEnabled()) return null;
		
		return ClassLoader.getSystemResource(name);
	}
}
