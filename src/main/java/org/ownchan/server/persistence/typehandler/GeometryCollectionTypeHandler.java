package org.ownchan.server.persistence.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.vividsolutions.jts.geom.GeometryCollection;

@MappedTypes(GeometryCollection.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class GeometryCollectionTypeHandler extends BaseGeometryTypeHandler<GeometryCollection> {

  public GeometryCollectionTypeHandler() {
    super(GeometryCollection.class);
  }

}
