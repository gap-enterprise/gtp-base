package io.surati.gap.gtp.base.db;

import io.surati.gap.admin.base.api.ReferenceDocumentType;
import io.surati.gap.admin.base.api.User;
import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.gtp.base.api.Treasury;
import io.surati.gap.gtp.base.api.Warrant;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpWarrant;
import io.surati.gap.gtp.base.db.jooq.generated.tables.records.GtpWarrantRecord;
import io.surati.gap.payment.base.api.Payment;
import io.surati.gap.payment.base.api.PaymentOrder;
import io.surati.gap.payment.base.api.ReferenceDocument;
import io.surati.gap.payment.base.api.ReferenceDocumentStatus;
import io.surati.gap.payment.base.api.ReferenceDocumentStep;
import io.surati.gap.payment.base.api.ThirdParty;
import io.surati.gap.payment.base.db.DbReferenceDocument;
import java.time.LocalDate;
import javax.sql.DataSource;

/**
 * Warrant from database.
 *
 * @since 0.1
 */
public final class DbWarrant implements Warrant {

    /**
     * Record.
     */
    private final GtpWarrantRecord record;

    /**
     * Data source.
     */
    private final DataSource src;

    /**
     * Origin.
     */
    private final ReferenceDocument origin;

    /**
     * Ctor.
     * @param src Data source
     * @param id Identifier
     */
    public DbWarrant(final DataSource src, final Long id) {
        this.src = src;
        this.record = new JooqContext(this.src)
            .fetchOne(GtpWarrant.GTP_WARRANT, GtpWarrant.GTP_WARRANT.ID.eq(id));
        this.origin = new DbReferenceDocument(this.src, id);
    }

    @Override
    public Treasury treasury() {
        return new DbTreasury(this.src, this.record.getTreasuryId());
    }

    @Override
    public Title title() {
        return new DbTitle(this.src, this.record.getTitle());
    }

    @Override
    public Section section() {
        return new DbSection(this.src, this.record.getSection());
    }

    @Override
    public String imputation() {
        return this.record.getImputation();
    }

    @Override
    public Bundle bundle() {
        return new DbBundle(this.src, this.record.getBundle());
    }

    @Override
    public Long id() {
        return this.origin.id();
    }

    @Override
    public ThirdParty issuer() {
        return this.origin.issuer();
    }

    @Override
    public ReferenceDocumentType type() {
        return this.origin.type();
    }

    @Override
    public LocalDate date() {
        return this.origin.date();
    }

    @Override
    public String reference() {
        return this.origin.reference();
    }

    @Override
    public String otherReference() {
        return this.origin.otherReference();
    }

    @Override
    public String object() {
        return this.origin.object();
    }

    @Override
    public String place() {
        return this.origin.place();
    }

    @Override
    public LocalDate depositDate() {
        return this.origin.depositDate();
    }

    @Override
    public LocalDate entryDate() {
        return this.origin.entryDate();
    }

    @Override
    public Double amount() {
        return this.origin.amount();
    }

    @Override
    public Double amountPaid() {
        return this.origin.amountPaid();
    }

    @Override
    public Double amountLeft() {
        return this.origin.amountLeft();
    }

    @Override
    public ReferenceDocumentStatus status() {
        return this.origin.status();
    }

    @Override
    public void type(final ReferenceDocumentType type) {
        this.origin.type(type);
    }

    @Override
    public void amount(final Double aDouble, final Double aDouble1) {
        this.origin.amount(aDouble, aDouble1);
    }

    @Override
    public void update(final LocalDate localDate, final String s, final String s1, final String s2) {
        this.origin.update(localDate, s, s1, s2);
    }

    @Override
    public void update(final String s) {
        this.origin.update(s);
    }

    @Override
    public void update(final LocalDate localDate, final LocalDate localDate1) {
        this.origin.update(localDate, localDate1);
    }

    @Override
    public Iterable<Payment> payments() {
        return this.origin.payments();
    }

    @Override
    public ReferenceDocumentStep step() {
        return this.origin.step();
    }

    @Override
    public void sendToTreatment() {
        this.origin.sendToTreatment();
    }

    @Override
    public void sendBackInPreparation() {
        this.origin.sendBackInPreparation();
    }

    @Override
    public void sendInPayment() {
        this.origin.sendInPayment();
    }

    @Override
    public void archive() {
        this.origin.archive();
    }

    @Override
    public void updateState() {
        this.origin.updateState();
    }

    @Override
    public User author() {
        return this.origin.author();
    }

    @Override
    public PaymentOrder preparePayment(final User user) {
        return this.origin.preparePayment(user);
    }

    @Override
    public void beneficiary(final ThirdParty thirdParty) {
        this.origin.beneficiary(thirdParty);
    }
}
