package com.headray.core.data.mgr;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;

public class DyRowMapper implements RowMapper
{
	ResultSetMetaData meta;

	public Object mapRow(ResultSet rs, int row) throws SQLException
	{
		if (meta == null)
		{
			meta = rs.getMetaData();
		}
		int columns = meta.getColumnCount();

		DynamicObject obj = new DynamicObject();
		for (int i = 0; i < columns; i++)
		{
			obj.setAttr(meta.getColumnName(i + 1).toLowerCase(), StringToolKit.formatText(rs.getString(i + 1)));
		}

		return obj;
	}

}
