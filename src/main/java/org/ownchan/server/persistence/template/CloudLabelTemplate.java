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

import org.ownchan.server.persistence.model.DbCloudLabelProvider;
import org.ownchan.server.persistence.model.DbCloudLabelStatus;

public interface CloudLabelTemplate extends EntityTemplate<CloudLabelTemplate> {

  long getId();

  String getText();

  DbCloudLabelStatus getStatus();

  String getStatusReason();

  String getInitialText();

  DbCloudLabelProvider getCloudProvider();

  String getCloudProviderLabelId();

  Date getCreateTime();

  Date getUpdateTime();

  Long getUpdateUserId();

}
