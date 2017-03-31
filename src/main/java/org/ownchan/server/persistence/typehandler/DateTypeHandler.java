/*******************************************************************************
 * @author Stefan Gündhör <stefan@guendhoer.com>
 *
 * @copyright Copyright (c) 2017, Stefan Gündhör <stefan@guendhoer.com>
 * @license AGPL-3.0
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License, version 3,
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *******************************************************************************/
package org.ownchan.server.persistence.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class DateTypeHandler extends BaseTypeHandler<Date> {

  private static final ThreadLocal<Calendar> UTC_CALENDAR_INSTANCE = new ThreadLocal<Calendar>() {

    @Override
    protected Calendar initialValue() {
      return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

  };

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setNull(i, Types.TIMESTAMP);
    } else {
      ps.setTimestamp(i, new Timestamp(parameter.getTime()), UTC_CALENDAR_INSTANCE.get());
    }
  }

  @Override
  public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return rs.getTimestamp(columnName, UTC_CALENDAR_INSTANCE.get());
  }

  @Override
  public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return rs.getTimestamp(columnIndex, UTC_CALENDAR_INSTANCE.get());
  }

  @Override
  public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return cs.getTimestamp(columnIndex, UTC_CALENDAR_INSTANCE.get());
  }

}
