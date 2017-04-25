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

public class ColumnValue {

  private Object value;

  private ValuePlaceholderType valuePlaceholderType;

  public ColumnValue(Object value) {
    this(value, ValuePlaceholderType.PREPARED_STATEMENT);
  }

  public ColumnValue(Object value, ValuePlaceholderType valuePlaceholderType) {
    this.value = value;
    this.valuePlaceholderType = valuePlaceholderType;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public ValuePlaceholderType getValuePlaceholderType() {
    return valuePlaceholderType;
  }

  public void setValuePlaceholderType(ValuePlaceholderType valuePlaceholderType) {
    this.valuePlaceholderType = valuePlaceholderType;
  }

}
