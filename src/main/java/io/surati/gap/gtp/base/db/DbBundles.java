package io.surati.gap.gtp.base.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Bundles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.cactoos.text.Joined;

/**
 * Bundles from database.
 *
 * @since 0.1
 */
public final class DbBundles implements Bundles {

    /**
     * Data source.
     */
    private final DataSource src;
    
    /**
     * Ctor.
     * @param src Data source
     */
    public DbBundles(final DataSource src) {
        this.src = src;
    }
    
	@Override
	public boolean has(final String code) {
		try {
			return new JdbcSession(this.src)
				.sql("SELECT COUNT(*) FROM gtp_bundle WHERE code=?")
				.set(code)
				.select(new SingleOutcome<>(Long.class)) > 0;
		} catch(SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

    @Override
    public Bundle get(final String code) {
        try(
            final Connection connection = this.src.getConnection();
            final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM gtp_bundle WHERE code=?");
        ){
            pstmt.setString(1, code);
            try(final ResultSet rs = pstmt.executeQuery()){
                if(rs.next()) {
                    return new DbBundle(this.src, code);
                } else {
                    throw new IllegalArgumentException("La liasse recherchée est introuvable !");
                }
            }
        } catch(SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Iterable<Bundle> iterate() {
        try {
            return
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT code FROM gtp_bundle",
                            "ORDER BY code ASC"
                        ).toString()
                    )
                    .select(
                        new ListOutcome<>(
                            rset ->
                                new DbBundle(
                                    this.src,
                                    rset.getString(1)
                                )
                        )
                    );
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void add(final String code, final String notes) {
        if(StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Vous devez fournir un code !");
        }
        if(this.has(code)) {
			throw new IllegalArgumentException("Le code est déjà utilisé !");
		}
        try {
            new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "INSERT INTO gtp_bundle",
                        "(code, notes)",
                        "VALUES",
                        "(?, ?)"
                    ).toString()
                )
                .set(code.trim())
                .set(notes)
                .insert(Outcome.VOID);
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void remove(final String code) {
        try {
            new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "DELETE FROM gtp_bundle",
                        "WHERE code=?"
                    ).toString()
                )
                .set(code)
                .execute();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
