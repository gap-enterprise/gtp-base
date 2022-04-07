/*
 * Copyright (c) 2022 Surati

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.gtp.base.db;

import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpTitle;
import io.surati.gap.gtp.base.db.jooq.generated.tables.records.GtpTitleRecord;

import javax.sql.DataSource;
import org.jooq.DSLContext;

/**
 * Title from database.
 *
 * @since 0.2
 */
public final class DbTitle implements Title {

	/**
	 * Record.
	 */
	private final GtpTitleRecord record;
	
	/**
	 * jOOQ database context.
	 */
	private final DSLContext ctx;

	/**
	 * Ctor.
	 * @param source Data source
	 * @param code Identifier
	 */
	public DbTitle(final DataSource source, final String code) {		
		this.ctx = new JooqContext(source);
		this.record = this.ctx.fetchOne(
			GtpTitle.GTP_TITLE, GtpTitle.GTP_TITLE.CODE.eq(code)
		);
	}

    @Override
    public String code() {
        return this.record.getCode();
    }

    @Override
    public String name() {
        return this.record.getName();
    }

    @Override
    public String notes() {
        return this.record.getNotes();
    }

    @Override
    public void update(final String name, final String notes) {
    	this.record.setName(name);
		this.record.setNotes(notes);
		this.record.store();
    }

	@Override
	public String fullName() {
		return String.format("%s - %s", this.code(), this.name());
	}
}
