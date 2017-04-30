package org.ownchan.server.persistence.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.vividsolutions.jts.geom.LineString;

@MappedTypes(LineString.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class LineStringTypeHandler extends BaseGeometryTypeHandler<LineString> {

  public LineStringTypeHandler() {
    super(LineString.class);
  }

}
