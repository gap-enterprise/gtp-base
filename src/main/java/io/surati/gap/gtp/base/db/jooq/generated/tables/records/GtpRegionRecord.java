/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.gtp.base.db.jooq.generated.tables.records;


import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpRegion;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GtpRegionRecord extends UpdatableRecordImpl<GtpRegionRecord> implements Record3<String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.gtp_region.code</code>.
     */
    public void setCode(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.gtp_region.code</code>.
     */
    public String getCode() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.gtp_region.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.gtp_region.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.gtp_region.notes</code>.
     */
    public void setNotes(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.gtp_region.notes</code>.
     */
    public String getNotes() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<String, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return GtpRegion.GTP_REGION.CODE;
    }

    @Override
    public Field<String> field2() {
        return GtpRegion.GTP_REGION.NAME;
    }

    @Override
    public Field<String> field3() {
        return GtpRegion.GTP_REGION.NOTES;
    }

    @Override
    public String component1() {
        return getCode();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getNotes();
    }

    @Override
    public String value1() {
        return getCode();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getNotes();
    }

    @Override
    public GtpRegionRecord value1(String value) {
        setCode(value);
        return this;
    }

    @Override
    public GtpRegionRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public GtpRegionRecord value3(String value) {
        setNotes(value);
        return this;
    }

    @Override
    public GtpRegionRecord values(String value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GtpRegionRecord
     */
    public GtpRegionRecord() {
        super(GtpRegion.GTP_REGION);
    }

    /**
     * Create a detached, initialised GtpRegionRecord
     */
    public GtpRegionRecord(String code, String name, String notes) {
        super(GtpRegion.GTP_REGION);

        setCode(code);
        setName(name);
        setNotes(notes);
    }
}