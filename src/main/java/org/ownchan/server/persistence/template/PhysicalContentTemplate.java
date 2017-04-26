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
import java.util.UUID;

import org.ownchan.server.persistence.model.DbJsonData;
import org.ownchan.server.persistence.model.DbPhysicalContentStatus;
import org.ownchan.server.persistence.model.DbPhysicalContentType;

public interface PhysicalContentTemplate extends EntityTemplate<PhysicalContentTemplate> {

  long getId();

  DbPhysicalContentType getType();

  DbPhysicalContentStatus getStatus();

  String getStatusReason();

  Short getStorageFolderYear();

  Byte getStorageFolderMonth();

  Byte getStorageFolderDay();

  UUID getStorageFolderUuid();

  String getContentChecksum();

  Date getCreateTime();

  Date getUpdateTime();

  String getPhysicalContentType();

  String getExternalContentLink();

  DbJsonData getAdditionalMetadata();

}
