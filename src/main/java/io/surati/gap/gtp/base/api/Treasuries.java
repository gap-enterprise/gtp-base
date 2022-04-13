package io.surati.gap.gtp.base.api;

/**
 * Treasuries.
 *
 * @since 0.3
 */
public interface Treasuries {

    /**
     * Gets a treasury.
     * @param id Identifier
     * @return Warrant
     */
    Treasury get(Long id);

    /**
     * Iterates them all.
     * @return Iterable
     */
    Iterable<Treasury> iterate();

    /**
     * Gets count.
     * @return Total
     */
    Long count();

    /**
     * Removes a treasury by its id.
     * @param id Identifier
     */
    void remove(Long id);
}
