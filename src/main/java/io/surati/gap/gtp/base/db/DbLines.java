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

import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.jooq.DSLContext;
import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Line;
import io.surati.gap.gtp.base.api.Lines;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpLine;

/**
 * Lines from database.
 *
 * @since 0.2
 */
public final class DbLines implements Lines {
	
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
	public DbLines(final DataSource source) {
		this.source = source;
		this.ctx = new JooqContext(this.source);
	}
    
	@Override
	public boolean has(final String code) {
		return this.ctx
				.fetchCount(
					GtpLine.GTP_LINE,
					GtpLine.GTP_LINE.CODE.eq(code)
				) > 0;
	}

    @Override
    public Line get(final String code) {
    	if(!this.has(code)) {
			throw new IllegalArgumentException(
				String.format("La Ligne avec le code %s est introuvable !", code)
			);
		}
		return new DbLine(
			this.source,
			code
		);
    }

    @Override
    public Iterable<Line> iterate() {
    	return this.ctx
    			.selectFrom(GtpLine.GTP_LINE)
    			.orderBy(GtpLine.GTP_LINE.CODE.asc())
    			.fetch(
    				rec -> new DbLine(
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
		this.ctx.insertInto(GtpLine.GTP_LINE)
			.set(GtpLine.GTP_LINE.CODE, code)
			.set(GtpLine.GTP_LINE.NAME, name)
			.set(GtpLine.GTP_LINE.NOTES, notes)
			.execute();
    }

    @Override
    public void remove(final String code) {
    	this.ctx.delete(GtpLine.GTP_LINE)
		.where(GtpLine.GTP_LINE.CODE.eq(code))
		.execute();
    }
}
