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

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
    ps.setByte(i, (byte) (parameter ? 1 : 0));
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return byteToBoolean(rs.wasNull() ? null : rs.getByte(columnName));
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return byteToBoolean(rs.wasNull() ? null : rs.getByte(columnIndex));
  }

  @Override
  public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return byteToBoolean(cs.wasNull() ? null : cs.getByte(columnIndex));
  }

  protected static Boolean byteToBoolean(Byte value) {
    return value == null ? null : value.compareTo((byte) 1) == 0;
  }
}
