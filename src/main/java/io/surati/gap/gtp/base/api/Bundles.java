package io.surati.gap.gtp.base.api;

/**
 * All bundles.
 *
 * @since 0.1
 */
public interface Bundles {

    /**
     * Get a bundle.
     * @param code Code
     * @return Bundle
     */
    Bundle get(String code);

    /**
     * Iterate them all.
     * @return List of Bundles
     */
    Iterable<Bundle> iterate();

    /**
     * Add a bundle.
     * @param code Code
     * @param notes Notes
     */
    void add(String code, String notes);

    /**
     * Remove a bundle.
     * @param code Code
     */
    void remove(String code);

    /**
     * Has a bundle.
     * @param code Code
     * @return True or false
     */
	boolean has(String code);

}
