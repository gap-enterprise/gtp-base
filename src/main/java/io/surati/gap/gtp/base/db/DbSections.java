package io.surati.gap.gtp.base.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Sections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.cactoos.text.Joined;

/**
 * Sections from database.
 *
 * @since 0.5
 */
public final class DbSections implements Sections {

    /**
     * Data source.
     */
    private final DataSource src;
    
    /**
     * Ctor.
     * @param src Data source
     */
    public DbSections(final DataSource src) {
        this.src = src;
    }
    
	@Override
	public boolean has(final String code) {
		try {
			return new JdbcSession(this.src)
				.sql("SELECT COUNT(*) FROM gtp_section WHERE code=?")
				.set(code)
				.select(new SingleOutcome<>(Long.class)) > 0;
		} catch(SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

    @Override
    public Section get(final String code) {
        try(
            final Connection connection = this.src.getConnection();
            final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM gtp_section WHERE code=?");
        ){
            pstmt.setString(1, code);
            try(final ResultSet rs = pstmt.executeQuery()){
                if(rs.next()) {
                    return new DbSection(this.src, code);
                } else {
                    throw new IllegalArgumentException("La section recherchée est introuvable !");
                }
            }
        } catch(SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Iterable<Section> iterate() {
        try {
            return
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT code FROM gtp_section",
                            "ORDER BY code ASC"
                        ).toString()
                    )
                    .select(
                        new ListOutcome<>(
                            rset ->
                                new DbSection(
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
    public void add(final String code, final String name, final String notes) {
        if(StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Vous devez fournir un code !");
        }
        if(StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Vous devez renseigner le libellé !");
        }
        if(this.has(code)) {
			throw new IllegalArgumentException("Le code est déjà utilisé !");
        }
        try {
            new JdbcSession(this.src)
                .sql(
                    new Joined(
                        " ",
                        "INSERT INTO gtp_section",
                        "(code, name, notes)",
                        "VALUES",
                        "(?, ?, ?)"
                    ).toString()
                )
                .set(code.trim())
                .set(name.trim())
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
                        "DELETE FROM gtp_section",
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
