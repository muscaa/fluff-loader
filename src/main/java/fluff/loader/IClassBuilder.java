package fluff.loader;

import java.net.URL;

/**
 * Defines methods for dynamically creating and resolving classes and packages.
 */
public interface IClassBuilder {
	
    /**
     * Defines and optionally seals a package.
     *
     * @param name the name of the package
     * @param specTitle the specification title of the package
     * @param specVersion the specification version of the package
     * @param specVendor the specification vendor of the package
     * @param implTitle the implementation title of the package
     * @param implVersion the implementation version of the package
     * @param implVendor the implementation vendor of the package
     * @param sealBase if not null, specifies the URL for the seal base of the package
     * @return the newly defined package, or an existing package with the same name if already defined
     */
    Package definePackage(String name,
                          String specTitle, String specVersion, String specVendor,
                          String implTitle, String implVersion, String implVendor,
                          URL sealBase);
    
    /**
     * Defines a class from the specified byte array.
     *
     * @param name the name of the class
     * @param b the byte array containing the class data
     * @param off the offset into the byte array at which the class data begins
     * @param len the length of the class data
     * @return the newly defined class
     */
    Class<?> defineClass(String name, byte[] b, int off, int len);
    
    /**
     * Resolves the specified class.
     *
     * @param c the class to resolve
     */
    void resolveClass(Class<?> c);
}
