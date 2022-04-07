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
import io.surati.gap.gtp.base.api.Sections;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpSection;

import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.jooq.DSLContext;

/**
 * Sections from database.
 *
 * @since 0.2
 */
public final class DbSections implements Sections {
	
	/**
	 * jOOQ database context.
	 */
	private final DSLContext ctx;
	
	/**
	 * DataSource source.
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source
	 */
	public DbSections(final DataSource source) {
		this.source = source;
		this.ctx = new JooqContext(this.source);
	}
    
	@Override
	public boolean has(final String code) {
		return this.ctx
				.fetchCount(
					GtpSection.GTP_SECTION,
					GtpSection.GTP_SECTION.CODE.eq(code)
				) > 0;
	}

    @Override
    public Section get(final String code) {
    	if(!this.has(code)){
			throw new IllegalArgumentException(
				String.format("La Section avec le code %s est introuvable !", code)
			);
		}
		return new DbSection(
			this.source,
			code
		);
    }

    @Override
    public Iterable<Section> iterate() {
    	return this.ctx
    			.selectFrom(GtpSection.GTP_SECTION)
    			.orderBy(GtpSection.GTP_SECTION.CODE.asc())
    			.fetch(
    				rec -> new DbSection(
    					this.source, rec.getCode()
    				)
    			);
    }

    @Override
    public void add(final String code, final String name, final String notes) {
    	if(StringUtils.isBlank(code)) 
			throw new IllegalArgumentException("Le code ne peut être vide !");
		if(StringUtils.isBlank(name))
			throw new IllegalArgumentException("Le nom ne peut être vide !");
		if(this.has(code))
			throw new IllegalArgumentException(String.format("Le code %s est déjà utilisé !", code));
		this.ctx.insertInto(GtpSection.GTP_SECTION)
			.set(GtpSection.GTP_SECTION.CODE, code)
			.set(GtpSection.GTP_SECTION.NAME, name)
			.set(GtpSection.GTP_SECTION.NOTES, notes)
			.execute();
    }

    @Override
    public void remove(final String code) {
    	this.ctx.delete(GtpSection.GTP_SECTION)
		.where(GtpSection.GTP_SECTION.CODE.eq(code))
		.execute();
    }
}
