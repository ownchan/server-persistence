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
package org.ownchan.server.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ResultHandler;
import org.ownchan.server.joint.persistence.template.EntityTemplate;
import org.ownchan.server.joint.persistence.template.link.EntityLinkTemplate;
import org.ownchan.server.persistence.mapper.PersistableObjectMapper;
import org.ownchan.server.persistence.mapper.generic.ColumnValue;
import org.ownchan.server.persistence.mapper.generic.FilterParam;
import org.ownchan.server.persistence.mapper.generic.LimitParam;
import org.ownchan.server.persistence.mapper.generic.SortingParam;
import org.ownchan.server.persistence.model.PersistableObject;

public abstract class PersistableObjectDao<
    T extends PersistableObject<T, ?, ?, V> & EntityTemplate<?> & EntityLinkTemplate<?>,
    U extends PersistableObjectMapper<T>,
    V extends PersistableObjectDao<T, U, V>> implements PersistableObjectMapper<T> {

  protected abstract U getMapper();

  /**
   * @return the number of affected rows (usually 1)
   */
  public int insert(T persistableObject) {
    return getMapper().insert(persistableObject, false, false);
  }

  /**
   * @param updateOnDuplicateKey if true and param ignoreDuplicateKey is true as well, an update will be performed upon duplicate key
   * @return the number of affected rows (usually 1)
   */
  @Override
  public int insert(T persistableObject, boolean ignoreDuplicateKey, boolean updateOnDuplicateKey) {
    return getMapper().insert(persistableObject, ignoreDuplicateKey, updateOnDuplicateKey);
  }

  @Override
  public T get(long id) {
    return getMapper().get(id);
  }

  /**
   * @return the number of affected rows (usually 1)
   */
  @Override
  public int update(T persistableObject) {
    return getMapper().update(persistableObject);
  }

  /**
   * @return the number of affected rows (usually 1)
   */
  @Override
  public int delete(long id) {
    return getMapper().delete(id);
  }

  /**
   * @return the number of affected rows (usually 1)
   */
  public int delete(T persistableObject) {
    return delete(persistableObject.getId());
  }

  @Override
  public List<T> fetchAll() {
    return getMapper().fetchAll();
  }

  @Override
  public void streamAll(ResultHandler<T> resultHandler) {
    getMapper().streamAll(resultHandler);
  }

  @Override
  public List<T> fetch(
      List<List<FilterParam>> groupedFilterParams,
      List<SortingParam> sortingParams,
      LimitParam limitParam) {
    return getMapper().fetch(groupedFilterParams, sortingParams, limitParam);
  }

  @Override
  public void stream(
      List<List<FilterParam>> groupedFilterParams,
      List<SortingParam> sortingParams,
      LimitParam limitParam,
      ResultHandler<T> resultHandler) {
    getMapper().stream(groupedFilterParams, sortingParams, limitParam, resultHandler);
  }

  /**
   * @return the number of affected rows
   */
  @Override
  public long set(
      Map<String, ColumnValue> columnToValueMap,
      List<List<FilterParam>> groupedFilterParams,
      List<SortingParam> sortingParams,
      LimitParam limitParam) {
    return getMapper().set(columnToValueMap, groupedFilterParams, sortingParams, limitParam);
  }

  /**
   * @return the number of affected rows
   */
  @Override
  public long setAll(
      Map<String, ColumnValue> columnToValueMap) {
    return getMapper().setAll(columnToValueMap);
  }

  /**
   * @return the number of affected rows
   */
  @Override
  public long purge(
      List<List<FilterParam>> groupedFilterParams,
      List<SortingParam> sortingParams,
      LimitParam limitParam) {
    return getMapper().purge(groupedFilterParams, sortingParams, limitParam);
  }

  /**
   * @return the number of affected rows
   */
  @Override
  public long purgeAll() {
    return getMapper().purgeAll();
  }

  @Override
  public long count(
      List<List<FilterParam>> groupedFilterParams) {
    return getMapper().count(groupedFilterParams);
  }

  @Override
  public long countAll() {
    return getMapper().countAll();
  }

  @Override
  public List<BatchResult> flushStatements() {
    return getMapper().flushStatements();
  }

}
