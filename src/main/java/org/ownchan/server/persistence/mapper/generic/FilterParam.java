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

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class FilterParam {

  public static enum ComparisonType {
    NORMAL("NORMAL"), UPPER_CASE("UPPER"), LOWER_CASE("LOWER");

    private String shortName;

    private ComparisonType(String shortName) {
      this.shortName = shortName;
    }

    @Override
    public String toString() {
      return StringUtils.defaultString(shortName, name());
    }

  }

  public static enum ValuePlaceholderType {
    PREPARED_STATEMENT("PRE"), RAW_SQL("RAW");

    private String shortName;

    private ValuePlaceholderType(String shortName) {
      this.shortName = shortName;
    }

    @Override
    public String toString() {
      return StringUtils.defaultString(shortName, name());
    }

  }

  private String column;

  private String operator;

  private List<Object> values;

  private ComparisonType comparisonType;

  private ValuePlaceholderType valuePlaceholderType;

  public FilterParam(String column, String operator, Object... values) {
    this(column, operator, ComparisonType.NORMAL, ValuePlaceholderType.PREPARED_STATEMENT, values);
  }

  public FilterParam(String column, String operator, ValuePlaceholderType valuePlaceholderType, Object... values) {
    this(column, operator, ComparisonType.NORMAL, valuePlaceholderType, values);
  }

  public FilterParam(String column, String operator, ComparisonType comparisonType, Object... values) {
    this(column, operator, comparisonType, ValuePlaceholderType.PREPARED_STATEMENT, values);
  }

  public FilterParam(String column, String operator, ComparisonType comparisonType, ValuePlaceholderType valuePlaceholderType, Object... values) {
    this.column = column;
    this.operator = operator;
    this.values = Arrays.asList(values);
    this.comparisonType = comparisonType;
    this.valuePlaceholderType = valuePlaceholderType;
  }

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public List<Object> getValues() {
    return values;
  }

  public void setValues(List<Object> values) {
    this.values = values;
  }

  public ValuePlaceholderType getValuePlaceholderType() {
    return valuePlaceholderType;
  }

  public void setValuePlaceholderType(ValuePlaceholderType valuePlaceholderType) {
    this.valuePlaceholderType = valuePlaceholderType;
  }

  public ComparisonType getComparisonType() {
    return comparisonType;
  }

  public void setComparisonType(ComparisonType comparisonType) {
    this.comparisonType = comparisonType;
  }

}
