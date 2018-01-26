package com.hystax.demo.util;

import com.hystax.demo.wire.WireBook;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Converter {
    public static WireBook convertToBook(ResultSet rs) throws SQLException {
        return new WireBook(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5));
    }
}
