package ru.bibarsov.telegram.bots.common.jdbc.repository.sqlite;

import ru.bibarsov.telegram.bots.common.jdbc.ResultSetExtractor;
import ru.bibarsov.telegram.bots.common.jdbc.StatementPreparator;

import javax.annotation.ParametersAreNonnullByDefault;
import java.sql.*;

@ParametersAreNonnullByDefault
public class JdbcRepository {

    private final String connectionString;
    private final int transactionLevel;

    public JdbcRepository(String connectionString, int transactionLevel) {
        this.connectionString = connectionString;
        this.transactionLevel = transactionLevel;
    }

    public <T> T query(
        String querySql,
        StatementPreparator statementPreparator,
        ResultSetExtractor<T> resultSetExtractor
    ) {
        try (
            Connection c = getConnection();
            PreparedStatement stmt = c.prepareStatement(querySql)
        ) {
            statementPreparator.prepare(stmt);
            ResultSet rs = stmt.executeQuery();
            return resultSetExtractor.extractData(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T query(
        String querySql,
        ResultSetExtractor<T> resultSetExtractor
    ) {
        try (
            Connection c = getConnection();
            PreparedStatement stmt = c.prepareStatement(querySql)
        ) {
            ResultSet rs = stmt.executeQuery();
            return resultSetExtractor.extractData(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String updateSql, StatementPreparator statementPreparator) {
        try (
            Connection c = getConnection();
            PreparedStatement stmt = c.prepareStatement(updateSql)
        ) {
            statementPreparator.prepare(stmt);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(String updateSql) {
        try (
            Connection c = getConnection();
            Statement stmt = c.createStatement()
        ) {
            stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(connectionString);
        connection.setAutoCommit(true);
        connection.setTransactionIsolation(transactionLevel);
        return connection;
    }
}
