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
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Bundles;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpBundle;

import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.jooq.DSLContext;

/**
 * Bundles from database.
 *
 * @since 0.2
 */
public final class DbBundles implements Bundles {

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
	public DbBundles(final DataSource source) {
		this.source = source;
		this.ctx = new JooqContext(this.source);
	}

	@Override
	public boolean has(final String code) {
		return this.ctx
				.fetchCount(
					GtpBundle.GTP_BUNDLE,
					GtpBundle.GTP_BUNDLE.CODE.eq(code)
				) > 0;
	}

    @Override
    public Bundle get(final String code) {
    	if(
			this.ctx.fetchCount(
				GtpBundle.GTP_BUNDLE, GtpBundle.GTP_BUNDLE.CODE.eq(code)
			) == 0
    	  ) {
    			throw new IllegalArgumentException(
    				String.format("Bundle with code %s not found !", code)
    			);
    		}
    		return new DbBundle(
    			this.source,
    			code
    		);
    }

    @Override
    public Iterable<Bundle> iterate() {
    	return this.ctx
    			.selectFrom(GtpBundle.GTP_BUNDLE)
    			.orderBy(GtpBundle.GTP_BUNDLE.CODE.asc())
    			.fetch(
    				rec -> new DbBundle(
    					this.source, rec.getCode()
    				)
    			);
    }

    @Override
    public void add(final String code, final String notes) {
    	if(StringUtils.isBlank(code)) 
			throw new IllegalArgumentException("Le code ne peut être vide !");		
		if(this.has(code))
			throw new IllegalArgumentException("Le code est déjà utilisé !");
		this.ctx.insertInto(GtpBundle.GTP_BUNDLE)
			.set(GtpBundle.GTP_BUNDLE.CODE, code)
			.set(GtpBundle.GTP_BUNDLE.NOTES, notes)
			.execute();
    }

    @Override
    public void remove(final String code) {
    	this.ctx.delete(GtpBundle.GTP_BUNDLE)
		.where(GtpBundle.GTP_BUNDLE.CODE.eq(code))
		.execute();
    }
}
