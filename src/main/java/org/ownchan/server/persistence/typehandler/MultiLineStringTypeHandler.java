package org.ownchan.server.persistence.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.vividsolutions.jts.geom.MultiLineString;

@MappedTypes(MultiLineString.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class MultiLineStringTypeHandler extends BaseGeometryTypeHandler<MultiLineString> {

  public MultiLineStringTypeHandler() {
    super(MultiLineString.class);
  }

}
