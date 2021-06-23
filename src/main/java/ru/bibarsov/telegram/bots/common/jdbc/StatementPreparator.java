package ru.bibarsov.telegram.bots.common.jdbc;

import javax.annotation.ParametersAreNonnullByDefault;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ParametersAreNonnullByDefault
public interface StatementPreparator {

    void prepare(PreparedStatement preparedStatement) throws SQLException;
}
