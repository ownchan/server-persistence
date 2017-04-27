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
package org.ownchan.server.persistence.template;

import java.util.Date;

import org.ownchan.server.persistence.model.DbContentStatus;

import com.vividsolutions.jts.geom.Point;

public interface ContentTemplate extends EntityTemplate<ContentTemplate> {

  long getId();

  DbContentStatus getStatus();

  String getStatusReason();

  Long getParentId();

  Long getUserId();

  String getCaption();

  String getCountryCode();

  String getCityName();

  Point getLocation();

  String getContentName();

  Date getContentTime();

  Short getContentYear();

  Byte getContentMonth();

  Byte getContentDay();

  Date getCreateTime();

  Date getUpdateTime();

  Long getPhysicalContentId();

  Long getUserClicks();

}
