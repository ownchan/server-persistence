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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

@MappedTypes({
    Point.class,
    LineString.class,
    Polygon.class,
    MultiPoint.class,
    MultiLineString.class,
    MultiPolygon.class,
    GeometryCollection.class,
    Geometry.class
})
@MappedJdbcTypes(JdbcType.OTHER)
public class GeometryTypeHandler extends BaseTypeHandler<Geometry> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Geometry parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, parameter.toText());
  }

  @Override
  public Geometry getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return toGeometry(rs.getBytes(columnName));
  }

  @Override
  public Geometry getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return toGeometry(cs.getBytes(columnIndex));
  }

  @Override
  public Geometry getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return toGeometry(rs.getBytes(columnIndex));
  }

  /*
   * the main logic for parsing the binary mysql spatial data has been found on
   * and taken from http://www.dev-garden.org/2011/11/27/loading-mysql-spatial-data-with-jdbc-and-jts-wkbreader/
   */
  private Geometry toGeometry(byte[] binaryGeometry) {
    Geometry result = null;

    if (ArrayUtils.isNotEmpty(binaryGeometry)) {
      if (binaryGeometry.length < 5) {
        throw new IllegalArgumentException("invalid binary geometry data - less than five bytes");
      }

      /**
       * First four bytes of the geometry are the SRID, followed by the actual WKB.
       *
       * Example for a POINT (lat, lon):
       *    The value length is 25 bytes, made up of these components (as can be seen from the hexadecimal value):
       *        - 4 bytes for integer SRID (0)
       *        - 1 byte for integer byte order (1 = little-endian)
       *        - 4 bytes for integer type information (1 = Point)
       *        - 8 bytes for double-precision X coordinate (1)
       *        - 8 bytes for double-precision Y coordinate (−1)
       *
       * @see https://dev.mysql.com/doc/refman/5.7/en/gis-data-formats.html
       */
      byte[] sridBytes = new byte[4];
      System.arraycopy(binaryGeometry, 0, sridBytes, 0, 4);
      boolean bigEndian = (binaryGeometry[4] == 0x00);

      int srid = 0;
      if (bigEndian) {
        for (int i = 0; i < sridBytes.length; i++) {
          srid = (srid << 8) + (sridBytes[i] & 0xff);
        }
      } else {
        for (int i = 0; i < sridBytes.length; i++) {
          srid += (sridBytes[i] & 0xff) << (8 * i);
        }
      }

      //copy the byte array, removing the first four SRID bytes
      byte[] wkb = new byte[binaryGeometry.length - 4];
      System.arraycopy(binaryGeometry, 4, wkb, 0, wkb.length);

      WKBReader wkbReader = new WKBReader(new GeometryFactory(new PrecisionModel(), srid));
      try {
        result = wkbReader.read(wkb);
      } catch (ParseException e) {
        throw new IllegalArgumentException(e.getMessage(), e);
      }
    }

    return result;
  }

}
