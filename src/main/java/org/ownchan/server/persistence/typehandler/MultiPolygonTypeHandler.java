package org.ownchan.server.persistence.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.vividsolutions.jts.geom.MultiPolygon;

@MappedTypes(MultiPolygon.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class MultiPolygonTypeHandler extends BaseGeometryTypeHandler<MultiPolygon> {

  public MultiPolygonTypeHandler() {
    super(MultiPolygon.class);
  }

}
