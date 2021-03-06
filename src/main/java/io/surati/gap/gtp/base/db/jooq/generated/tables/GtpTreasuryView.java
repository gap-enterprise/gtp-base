/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.gtp.base.db.jooq.generated.tables;


import io.surati.gap.gtp.base.db.jooq.generated.Public;
import io.surati.gap.gtp.base.db.jooq.generated.tables.records.GtpTreasuryViewRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GtpTreasuryView extends TableImpl<GtpTreasuryViewRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.gtp_treasury_view</code>
     */
    public static final GtpTreasuryView GTP_TREASURY_VIEW = new GtpTreasuryView();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GtpTreasuryViewRecord> getRecordType() {
        return GtpTreasuryViewRecord.class;
    }

    /**
     * The column <code>public.gtp_treasury_view.id</code>.
     */
    public final TableField<GtpTreasuryViewRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.gtp_treasury_view.code</code>.
     */
    public final TableField<GtpTreasuryViewRecord, String> CODE = createField(DSL.name("code"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.gtp_treasury_view.abbreviated</code>.
     */
    public final TableField<GtpTreasuryViewRecord, String> ABBREVIATED = createField(DSL.name("abbreviated"), SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>public.gtp_treasury_view.family_id</code>.
     */
    public final TableField<GtpTreasuryViewRecord, Long> FAMILY_ID = createField(DSL.name("family_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.gtp_treasury_view.payment_deadline</code>.
     */
    public final TableField<GtpTreasuryViewRecord, Short> PAYMENT_DEADLINE = createField(DSL.name("payment_deadline"), SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>public.gtp_treasury_view.name</code>.
     */
    public final TableField<GtpTreasuryViewRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>public.gtp_treasury_view.representative</code>.
     */
    public final TableField<GtpTreasuryViewRecord, String> REPRESENTATIVE = createField(DSL.name("representative"), SQLDataType.VARCHAR(25), this, "");

    /**
     * The column <code>public.gtp_treasury_view.representative_position</code>.
     */
    public final TableField<GtpTreasuryViewRecord, String> REPRESENTATIVE_POSITION = createField(DSL.name("representative_position"), SQLDataType.VARCHAR(25), this, "");

    private GtpTreasuryView(Name alias, Table<GtpTreasuryViewRecord> aliased) {
        this(alias, aliased, null);
    }

    private GtpTreasuryView(Name alias, Table<GtpTreasuryViewRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.view("create view \"gtp_treasury_view\" as  SELECT tp.id,\n    tp.code,\n    tp.abbreviated,\n    tp.family_id,\n    tp.payment_deadline,\n    tp.name,\n    tr.representative,\n    tr.representative_position\n   FROM (gtp_treasury tr\n     LEFT JOIN pay_third_party_view tp ON ((tp.id = tr.id)));"));
    }

    /**
     * Create an aliased <code>public.gtp_treasury_view</code> table reference
     */
    public GtpTreasuryView(String alias) {
        this(DSL.name(alias), GTP_TREASURY_VIEW);
    }

    /**
     * Create an aliased <code>public.gtp_treasury_view</code> table reference
     */
    public GtpTreasuryView(Name alias) {
        this(alias, GTP_TREASURY_VIEW);
    }

    /**
     * Create a <code>public.gtp_treasury_view</code> table reference
     */
    public GtpTreasuryView() {
        this(DSL.name("gtp_treasury_view"), null);
    }

    public <O extends Record> GtpTreasuryView(Table<O> child, ForeignKey<O, GtpTreasuryViewRecord> key) {
        super(child, key, GTP_TREASURY_VIEW);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public GtpTreasuryView as(String alias) {
        return new GtpTreasuryView(DSL.name(alias), this);
    }

    @Override
    public GtpTreasuryView as(Name alias) {
        return new GtpTreasuryView(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GtpTreasuryView rename(String name) {
        return new GtpTreasuryView(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GtpTreasuryView rename(Name name) {
        return new GtpTreasuryView(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Long, String, String, Long, Short, String, String, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
