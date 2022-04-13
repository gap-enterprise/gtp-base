package io.surati.gap.gtp.base.api;

import io.surati.gap.payment.base.api.ThirdParty;

/**
 * Treasury.
 *
 * @since 0.3
 */
public interface Treasury extends ThirdParty {

    String representative();

    String representativePosition();

    void representative(final String name, String position);

}
