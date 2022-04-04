package io.surati.gap.gtp.base.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import io.surati.gap.gtp.base.api.BudgetYears;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.cactoos.text.Joined;

public final class DbBudgetYears implements BudgetYears {

    private final DataSource src;

    public DbBudgetYears(final DataSource src) {
        this.src = src;
    }

    @Override
    public Iterable<Integer> iterate() {
        try {
            final List<Integer> items =
                new JdbcSession(this.src)
                    .sql(
                        new Joined(
                            " ",
                            "SELECT byear FROM (",
                            "SELECT DATE_PART('year', date) as byear FROM pay_payment",
                            "WHERE status_id='ISSUED'",
                            ") AS t1",
                            "GROUP BY byear",
                            "ORDER BY byear DESC"
                        ).toString()
                    )
                    .select(
                        new ListOutcome<>(
                            rset -> rset.getInt(1)
                        )
                    );
            if (items.isEmpty()) {
                return Arrays.asList(LocalDate.now().getYear());
            } else {
                return items;
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
