package io.surati.gap.gtp.base.db;

import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Treasuries;
import io.surati.gap.gtp.base.api.Treasury;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpTreasury;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpTreasuryView;
import javax.sql.DataSource;
import org.jooq.DSLContext;

/**
 * Treasuries.
 *
 * @since 0.3
 */
public final class DbTreasuries implements Treasuries {

    private final DataSource src;

    private final DSLContext ctx;

    public DbTreasuries(final DataSource src) {
        this.ctx = new JooqContext(src);
        this.src = src;
    }

    @Override
    public Treasury get(final Long id) {
        if (
            this.ctx.fetchCount(
                GtpTreasury.GTP_TREASURY, GtpTreasury.GTP_TREASURY.ID.eq(id)
            ) == 0
        ) {
            throw new IllegalArgumentException(
                String.format(
                    "La paierie (ID=%s) n'a pas été trouvée !",
                    id
                )
            );
        }
        return new DbTreasury(this.src, id);
    }

    @Override
    public Iterable<Treasury> iterate() {
        return this.ctx
            .selectFrom(GtpTreasuryView.GTP_TREASURY_VIEW)
            .orderBy(GtpTreasuryView.GTP_TREASURY_VIEW.NAME)
            .fetch(
                rec -> new DbTreasury(this.src, rec.getId())
            );
    }

    @Override
    public Long count() {
        return Long.valueOf(
            this.ctx.fetchCount(GtpTreasury.GTP_TREASURY)
        );
    }

    @Override
    public void remove(final Long id) {
        this.ctx.deleteFrom(GtpTreasury.GTP_TREASURY)
            .where(GtpTreasury.GTP_TREASURY.ID.eq(id));
    }
}
