package io.surati.gap.gtp.base.api;

/**
 * All sections.
 *
 * @since 0.1
 */
public interface Sections {

    /**
     * Get a section.
     * @param code Code
     * @return Section
     */
    Section get(String code);

    /**
     * Iterate them all.
     * @return List of Sections
     */
    Iterable<Section> iterate();

    /**
     * Add a section.
     * @param code Code
     * @param name Name
     * @param notes Notes
     */
    void add(String code, String name, String notes);

    /**
     * Remove a section.
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
