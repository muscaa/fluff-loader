package fluff.loader.loaders;

import java.io.InputStream;
import java.net.URL;

import fluff.functions.gen.Func;
import fluff.loader.AbstractLoader;

public class ExtendedLoader extends AbstractLoader {
	
	private final Func<ClassLoader> extended;
	
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
	public InputStream getResourceAsStream(String name) {
		if (!isEnabled()) return null;
		
		return extended.invoke().getResourceAsStream(name);
	}
	
	@Override
	public URL getResource(String name) {
		if (!isEnabled()) return null;
		
		return extended.invoke().getResource(name);
	}
}
