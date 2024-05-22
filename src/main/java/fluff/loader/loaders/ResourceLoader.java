package fluff.loader.loaders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import fluff.loader.AbstractLoader;
import fluff.loader.ResourcePath;

public class ResourceLoader extends AbstractLoader {
	
	private final Map<String, Class<?>> classes = new HashMap<>();
	private final ResourcePath resourcePath = new ResourcePath();
	
	public ResourceLoader(int priority) {
		setPriority(priority);
	}
	
	@Override
	public Class<?> loadClass(String className, boolean resolve) {
		if (!isEnabled()) return null;
		
		if (classes.containsKey(className)) return classes.get(className);
		
		byte[] bytes = resourcePath.getBytes(className.replace('.', '/') + ".class");
		if (bytes == null) return null;
		
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
	public InputStream getResourceAsStream(String name) {
		if (!isEnabled()) return null;
		
		byte[] bytes = resourcePath.getBytes(name);
		if (bytes == null) return null;
		
		return new ByteArrayInputStream(bytes);
	}
	
	@Override
	public URL getResource(String name) {
		if (!isEnabled()) return null;
		
		URL url = resourcePath.getURL(name);
		if (url == null) return null;
		
		return url;
	}
	
	public ResourcePath getResourcePath() {
		return resourcePath;
	}
}
