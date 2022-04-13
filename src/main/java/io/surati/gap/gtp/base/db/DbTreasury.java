package io.surati.gap.gtp.base.db;

import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Treasury;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpTreasury;
import io.surati.gap.gtp.base.db.jooq.generated.tables.records.GtpTreasuryRecord;
import io.surati.gap.payment.base.api.PaymentCondition;
import io.surati.gap.payment.base.api.ThirdParty;
import io.surati.gap.payment.base.api.ThirdPartyFamily;
import io.surati.gap.payment.base.db.DbThirdParty;
import javax.sql.DataSource;

/**
 * Treasury from database.
 *
 * @since 0.3
 */
public final class DbTreasury implements Treasury {

    private final GtpTreasuryRecord record;

    private final ThirdParty origin;

    public DbTreasury(final DataSource src, final Long id) {
        this.record = new JooqContext(src)
            .fetchOne(GtpTreasury.GTP_TREASURY, GtpTreasury.GTP_TREASURY.ID.eq(id));
        this.origin = new DbThirdParty(src, id);
    }

    @Override
    public String code() {
        return this.origin.code();
    }

    @Override
    public String abbreviated() {
        return this.origin.abbreviated();
    }

    @Override
    public void update(String code, String name, String abbreviated) {
        this.origin.update(code, name, abbreviated);
    }

    @Override
    public ThirdPartyFamily family() {
        return this.origin.family();
    }

    @Override
    public void assign(final ThirdPartyFamily tpf) {
        this.origin.assign(tpf);
    }

    @Override
    public PaymentCondition paymentCondition() {
        return this.origin.paymentCondition();
    }

    @Override
    public Long id() {
        return this.origin.id();
    }

    @Override
    public String name() {
        return this.origin.name();
    }

    @Override
    public void update(final String name) {
        this.origin.update(name);
    }

    @Override
    public String representative() {
        return this.record.getRepresentative();
    }

    @Override
    public String representativePosition() {
        return this.record.getRepresentativePosition();
    }

    @Override
    public void representative(String name, String position) {
        this.record.setRepresentative(name);
        this.record.setRepresentativePosition(position);
        this.record.store();
    }
}
