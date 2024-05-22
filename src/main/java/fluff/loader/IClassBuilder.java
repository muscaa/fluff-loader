package fluff.loader;

import java.net.URL;

public interface IClassBuilder {
	
	Package definePackage(String name,
			String specTitle, String specVersion, String specVendor,
            String implTitle, String implVersion, String implVendor,
            URL sealBase);
	
	Class<?> defineClass(String name, byte[] b, int off, int len);
	
	void resolveClass(Class<?> c);
}
