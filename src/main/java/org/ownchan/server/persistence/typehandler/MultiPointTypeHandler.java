package org.ownchan.server.persistence.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.vividsolutions.jts.geom.MultiPoint;

@MappedTypes(MultiPoint.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class MultiPointTypeHandler extends BaseGeometryTypeHandler<MultiPoint> {

  public MultiPointTypeHandler() {
    super(MultiPoint.class);
  }

}
