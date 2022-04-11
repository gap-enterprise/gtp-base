package io.surati.gap.gtp.base.api;

import io.surati.gap.admin.base.api.User;

public interface AnnualWarrants {

    /**
     * Get an annual warrant.
     * @param id Identifier
     * @return Warrant
     */
    AnnualWarrant get(Long id);

    /**
     * Has warranty of identifier.
     * @param id Identifier
     * @return Has or not
     */
    boolean has(Long id);
    /**
     * Iterate them all.
     * @return
     */
    Iterable<AnnualWarrant> iterate();

    /**
     * Total.
     * @return Number of warrants
     */
    Long count();
}
