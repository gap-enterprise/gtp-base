package io.surati.gap.gtp.base.api;

/**
 * Warrants.
 *
 * @since 0.1
 */
public interface Warrants {

    /**
     * Gets a warrant.
     * @param id Identifier
     * @return Warrant
     */
    Warrant get(Long id);

    /**
     * Iterates them all.
     * @return Iterable
     */
    Iterable<Warrant> iterate();

    /**
     * Gets count.
     * @return Total
     */
    Long count();

    Double totalAmount();

    Double amountLeft();

    boolean hasAny();

    Warrant first();
}
