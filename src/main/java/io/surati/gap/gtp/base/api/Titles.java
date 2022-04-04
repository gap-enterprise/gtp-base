package io.surati.gap.gtp.base.api;

/**
 * All titles.
 *
 * @since 0.1
 */
public interface Titles {

    /**
     * Get a title.
     * @param code Code
     * @return Title
     */
    Title get(String code);

    /**
     * Iterate them all.
     * @return List of Titles
     */
    Iterable<Title> iterate();

    /**
     * Add a title.
     * @param code Code
     * @param name Name
     * @param notes Notes
     */
    void add(String code, String name, String notes);

    /**
     * Remove an title.
     * @param code Code
     */
    void remove(String code);

    /**
     * Checks if has section with code.
     * @param code Code
     * @return Has or not
     */
	boolean has(String code);

}
