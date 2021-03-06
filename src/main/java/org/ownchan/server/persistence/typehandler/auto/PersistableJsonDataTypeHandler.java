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

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.ownchan.server.joint.persistence.valuetype.PersistableJsonData;
import org.ownchan.server.joint.persistence.valuetype.PhysicalContentMetadataLinkYoutube;
import org.ownchan.server.joint.persistence.valuetype.PhysicalContentMetadataUploadImage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

@MappedTypes({
    PhysicalContentMetadataLinkYoutube.class,
    PhysicalContentMetadataUploadImage.class,
    PersistableJsonData.class
})
public class PersistableJsonDataTypeHandler extends BaseTypeHandler<PersistableJsonData> {

  private ObjectReader reader;

  private ObjectWriter writer;

  public PersistableJsonDataTypeHandler() {
    ObjectMapper objectMapper = new ObjectMapper();
    this.reader = objectMapper.reader();
    this.writer = objectMapper.writer();
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, PersistableJsonData parameter, JdbcType jdbcType) throws SQLException {
    try {
      ps.setString(i, writer.writeValueAsString(parameter));
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e.getMessage(), e);
    }
  }

  @Override
  public PersistableJsonData getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return createDbJsonDataFromString(rs.getString(columnName));
  }

  @Override
  public PersistableJsonData getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return createDbJsonDataFromString(rs.getString(columnIndex));
  }

  @Override
  public PersistableJsonData getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return createDbJsonDataFromString(cs.getString(columnIndex));
  }

  private PersistableJsonData createDbJsonDataFromString(String jsonString) {
    if (StringUtils.isNotBlank(jsonString)) {
      try {
        return reader.readValue(jsonString);
      } catch (IOException e) {
        throw new IllegalArgumentException(e.getMessage(), e);
      }
    }

    return null;
  }

}
