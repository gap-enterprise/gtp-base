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
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpSection;
import io.surati.gap.gtp.base.db.jooq.generated.tables.records.GtpSectionRecord;

import javax.sql.DataSource;
import org.jooq.DSLContext;

/**
 * Section from database.
 *
 * @since 0.2
 */
public final class DbSection implements Section {

	/**
	 * Record.
	 */
	private final GtpSectionRecord record;
	
	/**
	 * jOOQ database context.
	 */
	private final DSLContext ctx;

	/**
	 * Ctor.
	 * @param source Data source
	 * @param code Identifier
	 */
	public DbSection(final DataSource source, final String code) {		
		this.ctx = new JooqContext(source);
		this.record = this.ctx.fetchOne(
			GtpSection.GTP_SECTION, GtpSection.GTP_SECTION.CODE.eq(code)
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
		this.record.setNotes(name);
		this.record.setNotes(notes);
		this.record.store();
    }
    
	@Override
	public String fullName() {
		return String.format("%s - %s", this.code(), this.name());
	}
}
