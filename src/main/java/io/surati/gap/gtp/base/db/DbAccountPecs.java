 /* Copyright (c) 2022 Surati

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

import org.jooq.DSLContext;

import com.restfb.util.StringUtils;

import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.AccountPec;
/**
 * Account pecs from database.
 *
 * @since 0.2
 */
import io.surati.gap.gtp.base.api.AccountPecs;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpAccountPec;

public final class DbAccountPecs implements AccountPecs {
	
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
	public DbAccountPecs(final DataSource source) {
		this.source = source;
		this.ctx = new JooqContext(this.source);
	}
	
	@Override
	public AccountPec get(final String code) {
		if(
			this.ctx.fetchCount(
				GtpAccountPec.GTP_ACCOUNT_PEC, GtpAccountPec.GTP_ACCOUNT_PEC.CODE.eq(code)
			) == 0
		) {
			throw new IllegalArgumentException(
				String.format("Chapter with code %s not found !", code)
			);
		}
		return new DbAccountPec(
			this.source,
			code
		);
	}
	
	@Override
	public Iterable<AccountPec> iterate() {
		return this.ctx
			.selectFrom(GtpAccountPec.GTP_ACCOUNT_PEC)
			.fetch(
				rec -> new DbAccountPec(
					this.source, rec.getCode()
				)
			);
	}
	
	@Override
	public boolean has(final String code) {
		return this.ctx
			.fetchCount(
				GtpAccountPec.GTP_ACCOUNT_PEC,
				GtpAccountPec.GTP_ACCOUNT_PEC.CODE.eq(code)
			) > 0;
	}
	
	@Override
	public void add(final String code, final String name, final String notes) {
		if(StringUtils.isBlank(code)) 
			throw new IllegalArgumentException("Le code ne peut être vide !");
		if(StringUtils.isBlank(name))
			throw new IllegalArgumentException("Le nom ne peut être vide !");
		if(this.has(code))
			throw new IllegalArgumentException("Ce code est déjà utilisé !");
		this.ctx.insertInto(GtpAccountPec.GTP_ACCOUNT_PEC)
			.set(GtpAccountPec.GTP_ACCOUNT_PEC.CODE, code)
			.set(GtpAccountPec.GTP_ACCOUNT_PEC.NAME, name)
			.set(GtpAccountPec.GTP_ACCOUNT_PEC.NOTES, notes)
			.execute();
	}
	
	@Override
	public void remove(final String code) {
		this.ctx.delete(GtpAccountPec.GTP_ACCOUNT_PEC)
			.where(GtpAccountPec.GTP_ACCOUNT_PEC.CODE.eq(code))
			.execute();
	}
}
