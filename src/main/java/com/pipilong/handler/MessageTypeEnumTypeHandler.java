package com.pipilong.handler;

import com.pipilong.enums.MessageType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author pipilong
 * @createTime 2023/2/16
 * @description
 */
@MappedTypes(MessageType.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MessageTypeEnumTypeHandler extends BaseTypeHandler<MessageType> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, MessageType messageType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,messageType.getValue());
    }

    @Override
    public MessageType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return MessageType.getInstance(s);
    }

    @Override
    public MessageType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String s = resultSet.getString(i);
        return MessageType.getInstance(s);
    }

    @Override
    public MessageType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String s = callableStatement.getString(i);
        return MessageType.getInstance(s);
    }
}
