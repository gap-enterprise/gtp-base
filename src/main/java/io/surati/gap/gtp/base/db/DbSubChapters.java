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

import org.jooq.DSLContext;

import com.restfb.util.StringUtils;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpSubChapter;
import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Chapter;
import io.surati.gap.gtp.base.api.Chapters;
/**
 * Sub Chapters from database.
 *
 * @since 0.2
 */

public final class DbSubChapters implements Chapters {

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
	public DbSubChapters(final DataSource source) {
		this.source = source;
		this.ctx = new JooqContext(this.source);
	}
	
	@Override
	public Chapter get(final String code) {
		if(
			this.ctx.fetchCount(
				GtpSubChapter.GTP_SUB_CHAPTER, GtpSubChapter.GTP_SUB_CHAPTER.CODE.eq(code)
			) == 0
		) {
			throw new IllegalArgumentException(
				String.format("Chapter with code %s not found !", code)
			);
		}
		return new DbSubChapter(
			this.source,
			code
		);
	}
	
	@Override
	public Iterable<Chapter> iterate() {
		return this.ctx
			.selectFrom(GtpSubChapter.GTP_SUB_CHAPTER)
			.fetch(
				rec -> new DbSubChapter(
					this.source, rec.getCode()
				)
			);
	}
	
	@Override
	public boolean has(final String code) {
		return this.ctx
			.fetchCount(
				GtpSubChapter.GTP_SUB_CHAPTER,
				GtpSubChapter.GTP_SUB_CHAPTER.CODE.eq(code)
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
		this.ctx.insertInto(GtpSubChapter.GTP_SUB_CHAPTER)
			.set(GtpSubChapter.GTP_SUB_CHAPTER.CODE, code)
			.set(GtpSubChapter.GTP_SUB_CHAPTER.NAME, name)
			.set(GtpSubChapter.GTP_SUB_CHAPTER.NOTES, notes)
			.execute();
	}
	
	@Override
	public void remove(final String code) {
		this.ctx.delete(GtpSubChapter.GTP_SUB_CHAPTER)
			.where(GtpSubChapter.GTP_SUB_CHAPTER.CODE.eq(code))
			.execute();
	}
}
