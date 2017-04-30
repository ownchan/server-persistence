package org.ownchan.server.persistence.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.vividsolutions.jts.geom.Point;

@MappedTypes(Point.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class PointTypeHandler extends BaseGeometryTypeHandler<Point> {

  public PointTypeHandler() {
    super(Point.class);
  }

}
