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
package org.ownchan.server.persistence.geom;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

public class NullablePoint extends Point {

  private static final long serialVersionUID = -2027018234553700918L;

  /**
   * Constructs a <code>NullablePoint</code> with the given coordinate.
   *
   * @param coordinate the coordinate on which to base this <code>NullablePoint</code>, or <code>null</code> to create the empty geometry.
   * @param precisionModel the specification of the grid of allowable points for this <code>NullablePoint</code>
   * @param SRID the ID of the Spatial Reference System used by this <code>NullablePoint</code>
   * @deprecated Use GeometryFactory instead
   */
  @Deprecated
  public NullablePoint(Coordinate coordinate, PrecisionModel precisionModel, int SRID) {
    super(coordinate, precisionModel, SRID);
  }

  /**
   * @param coordinates contains the single coordinate on which to base this <code>NullablePoint</code>,
   * or <code>null</code> to create the empty geometry.
   */
  public NullablePoint(CoordinateSequence coordinates, GeometryFactory factory) {
    super(coordinates, factory);
  }

}
