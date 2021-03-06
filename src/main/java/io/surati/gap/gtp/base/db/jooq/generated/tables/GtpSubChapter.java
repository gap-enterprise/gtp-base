/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.gtp.base.db.jooq.generated.tables;


import io.surati.gap.gtp.base.db.jooq.generated.Keys;
import io.surati.gap.gtp.base.db.jooq.generated.Public;
import io.surati.gap.gtp.base.db.jooq.generated.tables.records.GtpSubChapterRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GtpSubChapter extends TableImpl<GtpSubChapterRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.gtp_sub_chapter</code>
     */
    public static final GtpSubChapter GTP_SUB_CHAPTER = new GtpSubChapter();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GtpSubChapterRecord> getRecordType() {
        return GtpSubChapterRecord.class;
    }

    /**
     * The column <code>public.gtp_sub_chapter.code</code>.
     */
    public final TableField<GtpSubChapterRecord, String> CODE = createField(DSL.name("code"), SQLDataType.VARCHAR(10).nullable(false), this, "");

    /**
     * The column <code>public.gtp_sub_chapter.name</code>.
     */
    public final TableField<GtpSubChapterRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>public.gtp_sub_chapter.notes</code>.
     */
    public final TableField<GtpSubChapterRecord, String> NOTES = createField(DSL.name("notes"), SQLDataType.VARCHAR(250), this, "");

    private GtpSubChapter(Name alias, Table<GtpSubChapterRecord> aliased) {
        this(alias, aliased, null);
    }

    private GtpSubChapter(Name alias, Table<GtpSubChapterRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.gtp_sub_chapter</code> table reference
     */
    public GtpSubChapter(String alias) {
        this(DSL.name(alias), GTP_SUB_CHAPTER);
    }

    /**
     * Create an aliased <code>public.gtp_sub_chapter</code> table reference
     */
    public GtpSubChapter(Name alias) {
        this(alias, GTP_SUB_CHAPTER);
    }

    /**
     * Create a <code>public.gtp_sub_chapter</code> table reference
     */
    public GtpSubChapter() {
        this(DSL.name("gtp_sub_chapter"), null);
    }

    public <O extends Record> GtpSubChapter(Table<O> child, ForeignKey<O, GtpSubChapterRecord> key) {
        super(child, key, GTP_SUB_CHAPTER);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<GtpSubChapterRecord> getPrimaryKey() {
        return Keys.GTP_SUB_CHAPTER_PKEY;
    }

    @Override
    public List<UniqueKey<GtpSubChapterRecord>> getKeys() {
        return Arrays.<UniqueKey<GtpSubChapterRecord>>asList(Keys.GTP_SUB_CHAPTER_PKEY);
    }

    @Override
    public GtpSubChapter as(String alias) {
        return new GtpSubChapter(DSL.name(alias), this);
    }

    @Override
    public GtpSubChapter as(Name alias) {
        return new GtpSubChapter(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GtpSubChapter rename(String name) {
        return new GtpSubChapter(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GtpSubChapter rename(Name name) {
        return new GtpSubChapter(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
