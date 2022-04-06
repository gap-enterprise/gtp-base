package io.surati.gap.gtp.base.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.gtp.base.api.Title;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.cactoos.text.Joined;

/**
 * Title from database.
 *
 * @since 0.1
 */
public final class DbTitle implements Title {

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
    public DbTitle(final DataSource src, final String code) {
        this.src = src;
        this.code = code;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String name() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT name FROM gtp_title",
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
    public String notes() {
        try {
            return new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "SELECT notes FROM gtp_title",
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
    public void update(final String name, final String notes) {
        try {
            new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "UPDATE gtp_title",
                        "SET code=?,name=?,notes=?",
                        "WHERE code=?"
                    ).toString()
                )
                .set(code)
                .set(name)
                .set(notes)
                .set(this.code)
                .execute();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

	@Override
	public String fullName() {
		return String.format("%s - %s", this.code(), this.name());
	}
}
