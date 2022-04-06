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
import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Chapter;
import io.surati.gap.gtp.base.db.jooq.generated.tables.GtpChapter;
import io.surati.gap.gtp.base.db.jooq.generated.tables.records.GtpChapterRecord;

/**
 * Chapter from database.
 *
 * @since 0.2
 */
public final class DbChapter implements Chapter {

	/**
	 * Table of chapter.
	 */
	private static final GtpChapter GTP_CHAPTER = GtpChapter.GTP_CHAPTER;
	/**
	 * Record.
	 */
	private final GtpChapterRecord record;
	
	/**
	 * jOOQ database context.
	 */
	private final DSLContext ctx;

	/**
	 * Ctor.
	 * @param source Data source
	 * @param id Identifier
	 */
	public DbChapter(final DataSource source, final String code) {		
		this.ctx = new JooqContext(source);
		this.record = this.ctx.fetchOne(GTP_CHAPTER, GTP_CHAPTER.CODE.eq(code));
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
     public String fullName() {
		 return String.format("%s - %s", this.code(), this.name());
     }
	 
	 @Override
	 public void update(String code, String name, String notes) {
		 if(this.codeIsUsed(code))
				throw new IllegalArgumentException("Ce code est déjà utilisé.");
		 this.record.setCode(code);
		 this.record.setName(name);
		 this.record.setNotes(notes);
		 this.record.store();
	 }
	 
	 /**
	   * Checks if code is used.
	   * @param code Code
	   * @return Used or not
	 */
	 private boolean codeIsUsed(String code) {
		 return this.ctx
					.fetchCount(
						GTP_CHAPTER,
						GTP_CHAPTER.CODE.eq(code)
					) > 0;
     }  
	
}