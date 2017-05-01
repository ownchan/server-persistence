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
package org.ownchan.server.persistence.typehandler.auto;

import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.ownchan.server.joint.persistence.valuetype.PersistableEnum;

public abstract class PersistableEnumTypeHandler<T extends PersistableEnum<T>> extends BaseTypeHandler<T> {

  protected Class<T> targetClass;

  @SuppressWarnings("unchecked")
  protected PersistableEnumTypeHandler() {
    this.targetClass = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
    ps.setShort(i, parameter.getId());
  }

  @Override
  public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
    short value = rs.getShort(columnName);
    return getResultOrNull(rs.wasNull() ? null : value);
  }

  @Override
  public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    short value = rs.getShort(columnIndex);
    return getResultOrNull(rs.wasNull() ? null : value);
  }

  @Override
  public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    short value = cs.getShort(columnIndex);
    return getResultOrNull(cs.wasNull() ? null : value);
  }

  protected T getResultOrNull(Short constantId) {
    if (constantId != null) {
      return PersistableEnum.valueOf(constantId, targetClass);
    }

    return null;
  }

}
