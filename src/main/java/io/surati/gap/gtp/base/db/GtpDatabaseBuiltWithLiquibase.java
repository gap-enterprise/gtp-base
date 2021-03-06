/*
 * Copyright (c) 2022 Surati
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.gtp.base.db;

import com.baudoliver7.easy.liquibase4j.gen.UncheckedLiquibaseDataSource;
import com.baudoliver7.jdbc.toolset.wrapper.DataSourceWrap;
import javax.sql.DataSource;

/**
 * Data source decorator that automatically build module database with liquibase.
 *
 * @since 0.1
 */
public final class GtpDatabaseBuiltWithLiquibase extends DataSourceWrap {

    /**
     * Changelog master file name.
     */
    public static final String CHANGELOG_MASTER_FILENAME =
        "io/surati/gap/gtp/base/liquibase/db.postgresql.changelog-master.xml";

    /**
     * Ctor.
     * @param src Data source
     */
    public GtpDatabaseBuiltWithLiquibase(final DataSource src) {
        this(src, "base");
    }

    /**
     * Ctor.
     * @param src Data source
     * @param contexts Contexts
     */
    public GtpDatabaseBuiltWithLiquibase(final DataSource src, final String contexts) {
        super(
            new UncheckedLiquibaseDataSource(
                src,
                GtpDatabaseBuiltWithLiquibase.CHANGELOG_MASTER_FILENAME,
                contexts
            )
        );
    }
}
