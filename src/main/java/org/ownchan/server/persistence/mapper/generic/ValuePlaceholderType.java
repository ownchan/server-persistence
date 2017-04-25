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
package org.ownchan.server.persistence.mapper.generic;

import org.apache.commons.lang3.StringUtils;

public enum ValuePlaceholderType {
  PREPARED_STATEMENT("PRE"), RAW_SQL("RAW");

  private String shortName;

  private ValuePlaceholderType(String shortName) {
    this.shortName = shortName;
  }

  public String getShortName() {
    return StringUtils.defaultString(shortName, name());
  }

  @Override
  public String toString() {
    return getShortName();
  }

}
