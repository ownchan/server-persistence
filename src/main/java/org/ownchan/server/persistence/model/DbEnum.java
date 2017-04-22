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
package org.ownchan.server.persistence.model;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public interface DbEnum<T> {

  short getId();

  /**
   * @throws IllegalArgumentException - if the enum type has no constant with the specified id
   * @throws NullPointerException - if id is null
   */
  public static <T extends DbEnum<T>> T valueOf(Short id, Class<T> clazz) {
    IllegalArgumentException argEx = new IllegalArgumentException(
        String.format("no matching constant found for id %s", id));

    T[] availableValues = clazz.getEnumConstants();

    if (ArrayUtils.isEmpty(availableValues)) {
      throw argEx;
    }

    return Arrays.stream(availableValues)
        .filter(id::equals)
        .findAny()
        .orElseThrow(() -> argEx);
  }

}
