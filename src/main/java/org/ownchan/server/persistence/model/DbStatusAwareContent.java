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

public interface DbStatusAwareContent<T extends DbEnum<T> & DbStatusEnum<T>> {

  short MAX_LENGTH_STATUS_REASON = 255;

  /**
   * Update the status of the db object, along with a provided reason that may be null.
   *
   * @param status the status to set onto the db object
   * @param statusReason the reason for the status, or null if none; should not exceed 255 chars
   * (which per convention is the max length for status reason values in the ownChan database)
   */
  void setStatus(T status, String statusReason);

  T getStatus();

  String getStatusReason();

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(DbEnum, String)}.
   */
  @Deprecated
  void setStatus(T status);

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(DbEnum, String)}.
   */
  @Deprecated
  void setStatusReason(String statusReason);

}
