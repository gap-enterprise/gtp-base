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
import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Chapter;
import io.surati.gap.gtp.base.api.Chapters;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpChapter;

/**
 * Chapters from database.
 *
 * @since 0.2
 */
public final class DbChapters implements Chapters {

	/**
	 * Table of chapter.
	 */
	private static final GtpChapter GTP_CHAPTER = GtpChapter.GTP_CHAPTER;
	
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
	public DbChapters(final DataSource source) {
		this.source = source;
		this.ctx = new JooqContext(this.source);
	}
	
	@Override
	public Chapter get(final String code) {
		if(this.ctx.fetchCount(GTP_CHAPTER, GTP_CHAPTER.CODE.eq(code)) == 0) {
			throw new IllegalArgumentException(
				String.format("Chapter with code %s not found !", code)
			);
		}
		return new DbChapter(
			this.source,
			code
		);
	}
	
	@Override
	public Iterable<Chapter> iterate() {
		return this.ctx
			.selectFrom(GTP_CHAPTER)
			.fetch(
				rec -> new DbChapter(
					this.source, rec.getCode()
				)
			);
	}
	
	@Override
	public boolean has(final String code) {
		return this.ctx
			.fetchCount(
				GTP_CHAPTER,
				GTP_CHAPTER.CODE.eq(code)
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
		this.ctx.insertInto(GTP_CHAPTER)
			.set(GTP_CHAPTER.CODE, code)
			.set(GTP_CHAPTER.NAME, name)
			.set(GTP_CHAPTER.NOTES, notes)
			.execute();
	}
	
	@Override
	public void remove(final String code) {
		if(StringUtils.isBlank(code)) 
			throw new IllegalArgumentException("Le code ne peut être vide !");
		this.ctx.delete(GTP_CHAPTER)
			.where(GTP_CHAPTER.CODE.eq(code))
			.execute();
	}
}
