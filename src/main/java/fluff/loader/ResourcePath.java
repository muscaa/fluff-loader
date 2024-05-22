package fluff.loader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResourcePath {
	
	private final List<IResource> resources = new ArrayList<>();
	
	public boolean add(IResource resource) {
		if (resource.load()) {
			resources.add(resource);
			return true;
		}
		return false;
	}
	
	public URL getURL(String name) {
		for (IResource r : resources) {
			URL url = r.getURL(name);
			
			if (url != null) return url;
		}
		return null;
	}
	
	public byte[] getBytes(String name) {
		for (IResource r : resources) {
			byte[] bytes = r.getBytes(name);
			
			if (bytes != null) return bytes;
		}
		return null;
	}
}
