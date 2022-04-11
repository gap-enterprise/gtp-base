package io.surati.gap.gtp.base.api;

public interface AnnualWarrant extends Warrant {

    int order();

    short year();

    Double annualAmountToPay();

    Double annualAmountPaid();

    Double annualAmountLeft();

    boolean isSplit();
}
