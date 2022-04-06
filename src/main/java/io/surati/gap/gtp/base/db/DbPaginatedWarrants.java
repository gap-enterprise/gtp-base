package io.surati.gap.gtp.base.db;

import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Warrant;
import io.surati.gap.gtp.base.api.Warrants;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpWarrantView;
import java.util.Iterator;
import javax.sql.DataSource;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public final class DbPaginatedWarrants implements Warrants {

    /**
     * Data Source.
     */
    private final DataSource src;

    /**
     * Number per page.
     */
    private final Long nbperpage;

    /**
     * Page number to show
     * <p>Always starts at 1
     */
    private final Long page;

    /**
     * Filter.
     */
    private final String filter;

    /**
     * Context.
     */
    private final DSLContext ctx;

    /**
     * Ctor.
     * @param src
     */
    public DbPaginatedWarrants(final DataSource src) {
        this(src, Long.MAX_VALUE, 1L, "");
    }

    /**
     * Ctor.
     * @param src
     */
    public DbPaginatedWarrants(final DataSource src, final Long nbperpage, final Long page, final String filter) {
        this.src = src;
        this.nbperpage = nbperpage;
        this.page = page;
        this.filter = filter;
        this.ctx = new JooqContext(this.src);
    }

    @Override
    public Iterable<Warrant> iterate() {
        return this.ctx
            .selectFrom(GtpWarrantView.GTP_WARRANT_VIEW)
            .where(this.condition())
            .orderBy(GtpWarrantView.GTP_WARRANT_VIEW.ID.desc())
            .offset(this.nbperpage * (this.page - 1))
            .limit(this.nbperpage)
            .fetch(
                rec -> new DbWarrant(this.src, rec.getId())
            );
    }

    @Override
    public Long count() {
        return Long.valueOf(
           this.ctx.fetchCount(GtpWarrantView.GTP_WARRANT_VIEW, this.condition())
        );
    }

    @Override
    public Warrant get(final Long id) {
        if (!this.has(id)) {
            throw new IllegalArgumentException(
                String.format("Le mandat avec l'ID %s n'a pas été trouvé !", id)
            );
        }
        return new DbWarrant(
            this.src,
            id
        );
    }

    @Override
    public Double totalAmount() {
        return this.ctx.select(DSL.sum(GtpWarrantView.GTP_WARRANT_VIEW.AMOUNT))
            .from(GtpWarrantView.GTP_WARRANT_VIEW)
            .where(this.condition())
            .fetchOne()
            .value1().doubleValue();
    }

    @Override
    public Double amountLeft() {
        return this.ctx.select(DSL.sum(GtpWarrantView.GTP_WARRANT_VIEW.AMOUNT_LEFT))
            .from(GtpWarrantView.GTP_WARRANT_VIEW)
            .where(this.condition())
            .fetchOne()
            .value1().doubleValue();
    }

    @Override
    public boolean hasAny() {
        return this.count() > 0L;
    }

    @Override
    public Warrant first() {
        if(this.hasAny()) {
            Iterator<Warrant> it = this.iterate().iterator();
            return it.next();
        } else {
            throw new IllegalArgumentException("La sélection ne contient pas de mandat !");
        }
    }

    private boolean has(final Long id) {
        return this.ctx.fetchCount(
            GtpWarrantView.GTP_WARRANT_VIEW, this.condition(),
            GtpWarrantView.GTP_WARRANT_VIEW.ID.eq(id)
        ) > 0;
    }

    private Condition condition() {
        Condition result = DSL.trueCondition();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(filter)) {
            result = result.and(
                DSL.falseCondition()
                    .or(GtpWarrantView.GTP_WARRANT_VIEW.ABBREVIATED_ISSUER.like("%" + this.filter + "%"))
                    .or(GtpWarrantView.GTP_WARRANT_VIEW.CODE_ISSUER.like("%" + this.filter + "%"))
                    .or(GtpWarrantView.GTP_WARRANT_VIEW.NAME_ISSUER.like("%" + this.filter + "%"))
                    .or(GtpWarrantView.GTP_WARRANT_VIEW.REFERENCE.like("%" + this.filter + "%"))
                    .or(GtpWarrantView.GTP_WARRANT_VIEW.IMPUTATION.like("%" + this.filter + "%"))

            );
        }
        return result;
    }
}
