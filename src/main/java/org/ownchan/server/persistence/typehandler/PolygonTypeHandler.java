package org.ownchan.server.persistence.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.vividsolutions.jts.geom.Polygon;

@MappedTypes(Polygon.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class PolygonTypeHandler extends BaseGeometryTypeHandler<Polygon> {

  public PolygonTypeHandler() {
    super(Polygon.class);
  }

}
