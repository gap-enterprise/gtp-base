package io.surati.gap.gtp.base.db;

import com.lightweight.db.EmbeddedPostgreSQLDataSource;
import io.surati.gap.database.utils.extensions.DatabaseSetupExtension;
import io.surati.gap.gtp.base.api.Bundles;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

final class DbBundlesTest {

    @RegisterExtension
    final DatabaseSetupExtension src = new DatabaseSetupExtension(
        new GtpDatabaseBuiltWithLiquibase(
            new EmbeddedPostgreSQLDataSource()
        )
    );

    private Bundles bundles;

    @BeforeEach
    void setUp() {
        this.bundles = new DbBundles(this.src);
    }

    @Test
    void checksExistence() {
        final String code = "2";
        MatcherAssert.assertThat(
            "Should not contain code",
            this.bundles.has(code),
            Matchers.is(false)
        );
        this.bundles.add(code, "RAS");
        MatcherAssert.assertThat(
            "Should contain code",
            this.bundles.has(code),
            Matchers.is(true)
        );
    }
}
