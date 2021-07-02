package ru.bibarsov.telegram.bots.common.jdbc;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contract-formal replacement for vanilla ResultSet
 */
@ParametersAreNonnullByDefault
public class ResultSetReader {

    private final ResultSet resultSet;

    public ResultSetReader(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Nullable
    public String getStringNullable(String columnLabel) throws SQLException {
        return resultSet.getString(columnLabel);
    }

    @Nullable
    public Integer getIntegerNullable(String columnLabel) throws SQLException {
        Integer anInt = resultSet.getInt(columnLabel);
        if (!resultSet.wasNull()) {
            return anInt;
        }
        return null;
    }

    @Nullable
    public Long getLongNullable(String columnLabel) throws SQLException {
        Long aLong = resultSet.getLong(columnLabel);
        if (!resultSet.wasNull()) {
            return aLong;
        }
        return null;
    }

    @Nullable
    public BigDecimal getBigDecimalNullable(String columnLabel) throws SQLException {
        return resultSet.getBigDecimal(columnLabel);
    }

    @Nullable
    public Double getDoubleNullable(String columnLabel) throws SQLException {
        return resultSet.getDouble(columnLabel);
    }

    @Nullable
    public Boolean getBooleanNullable(String columnLabel) throws SQLException {
        boolean aBoolean = resultSet.getBoolean(columnLabel);
        if (!resultSet.wasNull()) {
            return aBoolean;
        }
        return null;
    }

    @Nullable
    public byte[] getByteArrayNullable(String columnLabel) throws SQLException {
        return resultSet.getBytes(columnLabel);
    }

    public boolean next() throws SQLException {
        return resultSet.next();
    }

    /**
     * @return original ResultSet
     */
    public ResultSet getOrigin() {
        return resultSet;
    }
}
