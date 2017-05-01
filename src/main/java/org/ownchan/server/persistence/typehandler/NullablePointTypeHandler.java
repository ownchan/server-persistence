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

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.ownchan.server.persistence.geom.NullablePoint;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

@MappedTypes(NullablePoint.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class NullablePointTypeHandler extends BaseGeometryTypeHandler<Point> {

  /*
   * In a spatial context, usually latitude (= y = northing) is written prior to longitude (= x = easting).
   * However, the well known text (WKT) for a POINT is defined by "POINT (x y)", so in the WKT for points
   * the longitude comes before the latitude. It's pretty easy to get this stuff in the wrong order.
   */
  private static final String DUMMY_POINT_WKT = "POINT (0 90)";

  /*
   * Unfortunately, MySql (at the time of writing this comment) doesn't allow spatially indexed columns to be null-able.
   * This is why we will use an uncommon dummy-point for storing locations that should actually be null (not set).
   * When a point, that is null in Java-context, is stored in the database, it will be stored using the location of the dummy point.
   * When it is retrieved again from the database, this class will make sure, that it is transformed to Java-null again
   * (if the location of the retrieved point equals the one of the dummy-point).
   *
   * Of course this means, that we cannot actually reference the exact location of the dummy-point in our service, but as we choose an
   * uncommon, far-north point, this should be a minor and acceptable side effect.
   */
  private static final Point DUMMY_POINT;

  static {
    WKTReader wktReader = new WKTReader(new GeometryFactory(new PrecisionModel(), DEFAULT_SRID));
    try {
      DUMMY_POINT = (Point) wktReader.read(DUMMY_POINT_WKT);
    } catch (ParseException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public NullablePointTypeHandler() {
    super(Point.class);
  }

  @Override
  public void setParameter(PreparedStatement ps, int i, Point parameter, JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      super.setParameter(ps, i, DUMMY_POINT, jdbcType);
    } else {
      super.setParameter(ps, i, parameter, jdbcType);
    }
  }

  @Override
  protected NullablePoint toGeometry(byte[] binaryGeometry) {
    NullablePoint result = null;
    Point point = super.toGeometry(binaryGeometry);

    if (point != null && !isDummyPoint(point)) {
      result = new NullablePoint(point.getCoordinateSequence(), new GeometryFactory(point.getPrecisionModel(), point.getSRID()));
    }

    return result;
  }

  private boolean isDummyPoint(Point point) {
    return DEFAULT_SRID == point.getSRID() && point.equalsExact(DUMMY_POINT);
  }

}
