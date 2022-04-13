package io.surati.gap.gtp.base.api;

import io.surati.gap.payment.base.api.ReferenceDocument;

public interface Warrant extends ReferenceDocument {

    Treasury treasury();

    Title title();

    Section section();

    String imputation();

    Bundle bundle();
}
