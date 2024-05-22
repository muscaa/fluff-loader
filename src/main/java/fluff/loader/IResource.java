package fluff.loader;

import java.net.URL;

public interface IResource {
	
	boolean load();
	
	URL getURL(String name);
	
	byte[] getBytes(String name);
}
