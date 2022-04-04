package io.surati.gap.gtp.base.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.gtp.base.api.Section;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.cactoos.text.Joined;

/**
 * Section from database.
 *
 * @since 0.5
 */
public final class DbSection implements Section {

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
    public DbSection(final DataSource src, final String code) {
        this.src = src;
        this.code = code;
    }

    /**
     * Checks if code is used.
     * @param code Code
     * @return Used or not
     */
    private boolean codeIsUsed(String code) {
        try {
            return !this.code.equals(code) &&
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT count(*) FROM gtp_section",
                            "WHERE code=?"
                        ).toString()
                    )
                    .set(code)
                    .select(new SingleOutcome<>(Long.class)) > 0;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
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
                        "SELECT name FROM gtp_section",
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
                        "SELECT notes FROM gtp_section",
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
    public void update(final String code, final String name, final String notes) {
    	if(this.codeIsUsed(code))
			  throw new IllegalArgumentException("Ce code est déjà utilisé.");
        try {
            new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "UPDATE gtp_section",
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
