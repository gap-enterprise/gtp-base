package io.surati.gap.gtp.base.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.gtp.base.api.Bundle;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.cactoos.text.Joined;

/**
 * Bundle from database.
 *
 * @since 0.1
 */
public final class DbBundle implements Bundle {

    /**
     * Data source.
     */
    private final DataSource src;

    /**
     * Code.
     */
    private final String code;
    
    /**
     * Ctor.
     * @param src Data source
     * @param code Code
     */
    public DbBundle(final DataSource src, final String code) {
        this.src = src;
        this.code = code;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String notes() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT notes FROM gtp_bundle",
                        "WHERE code=?"
                    ).toString()
                )
                .set(this.code)
                .select(new SingleOutcome<>(String.class));
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void update(final String notes) {
        try {
            new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "UPDATE gtp_bundle",
                        "SET code=?,notes=?",
                        "WHERE code=?"
                    ).toString()
                )
                .set(code)
                .set(notes)
                .set(this.code)
                .execute();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
