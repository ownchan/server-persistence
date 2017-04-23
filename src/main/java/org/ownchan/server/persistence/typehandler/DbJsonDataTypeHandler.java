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

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.ownchan.server.persistence.model.DbJsonData;
import org.ownchan.server.persistence.model.DbJsonData.KnownImplementingBeanClass;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public class DbJsonDataTypeHandler extends BaseTypeHandler<DbJsonData> {

  private ObjectMapper objectMapper;

  private ObjectReader unspecificReader;

  private Map<Class<? extends DbJsonData>, ObjectReader> readerMap;

  private Map<Class<? extends DbJsonData>, ObjectWriter> writerMap;

  public DbJsonDataTypeHandler() {
    int targetReaderWriterCacheSize = 2 * KnownImplementingBeanClass.values().length;
    this.readerMap = new ConcurrentHashMap<>(targetReaderWriterCacheSize);
    this.writerMap = new ConcurrentHashMap<>(targetReaderWriterCacheSize);
    this.objectMapper = new ObjectMapper();
    this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    this.objectMapper.setPropertyInclusion(JsonInclude.Value.construct(Include.NON_ABSENT, Include.NON_ABSENT));
    this.unspecificReader = objectMapper.readerFor(DbJsonData.class);
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, DbJsonData parameter, JdbcType jdbcType) throws SQLException {
    try {
      KnownImplementingBeanClass knownImplementingClass = KnownImplementingBeanClass.fromImplementingBeanClass(parameter.getClass());
      parameter.setKnownImplementingBeanClass(knownImplementingClass);
      Class<? extends DbJsonData> targetClass = knownImplementingClass.getImplementingBeanClass();

      ObjectWriter targetWriter = writerMap.get(targetClass);
      if (targetWriter == null) {
        targetWriter = objectMapper.writerFor(targetClass);
        writerMap.put(targetClass, targetWriter);
      }

      ps.setString(i, targetWriter.writeValueAsString(parameter));
    } catch (IllegalArgumentException | JsonProcessingException e) {
      throw new SQLException(e.getMessage(), e);
    }
  }

  @Override
  public DbJsonData getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return createDbJsonDataFromString(rs.getString(columnName));
  }

  @Override
  public DbJsonData getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return createDbJsonDataFromString(rs.getString(columnIndex));
  }

  @Override
  public DbJsonData getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return createDbJsonDataFromString(cs.getString(columnIndex));
  }

  private DbJsonData createDbJsonDataFromString(String jsonString) throws SQLException {
    if (StringUtils.isNotBlank(jsonString)) {
      try {
        // TODO try to find a way to avoid parsing the string two times
        DbJsonData jsonContent = unspecificReader.readValue(jsonString);
        DbJsonData.KnownImplementingBeanClass knownImplementingBeanClass = jsonContent.getKnownImplementingBeanClass();
        Class<? extends DbJsonData> targetClass = knownImplementingBeanClass.getImplementingBeanClass();

        ObjectReader targetReader = readerMap.get(targetClass);
        if (targetReader == null) {
          targetReader = objectMapper.readerFor(targetClass);
          readerMap.put(targetClass, targetReader);
        }

        return targetReader.readValue(jsonString);
      } catch (IOException e) {
        throw new SQLException(e.getMessage(), e);
      }
    }

    return null;
  }

}
