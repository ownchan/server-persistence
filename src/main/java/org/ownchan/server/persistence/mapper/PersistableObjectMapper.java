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
package org.ownchan.server.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ResultHandler;
import org.ownchan.server.joint.persistence.template.EntityTemplate;
import org.ownchan.server.joint.persistence.template.link.EntityLinkTemplate;
import org.ownchan.server.persistence.mapper.generic.ColumnValue;
import org.ownchan.server.persistence.mapper.generic.FilterParam;
import org.ownchan.server.persistence.mapper.generic.LimitParam;
import org.ownchan.server.persistence.mapper.generic.SortingParam;
import org.ownchan.server.persistence.model.PersistableObject;

public interface PersistableObjectMapper<
    T extends PersistableObject<T, ?, ?, ?> & EntityTemplate<?> & EntityLinkTemplate<?>> {

  /**
   * @param updateOnDuplicateKey if true and param ignoreDuplicateKey is true as well, an update will be performed upon duplicate key
   * @return the number of affected rows (usually 1)
   */
  int insert(
      @Param("obj") T persistableObject,
      @Param("ignoreDuplicateKey") boolean ignoreDuplicateKey,
      @Param("updateOnDuplicateKey") boolean updateOnDuplicateKey);

  T get(long id);

  /**
   * @return the number of affected rows (usually 1)
   */
  int update(@Param("obj") T persistableObject);

  /**
   * @return the number of affected rows (usually 1)
   */
  int delete(long id);

  List<T> fetchAll();

  void streamAll(ResultHandler<T> resultHandler);

  List<T> fetch(
      @Param("groupedFilterParams") List<List<FilterParam>> groupedFilterParams,
      @Param("sortingParams") List<SortingParam> sortingParams,
      @Param("limitParam") LimitParam limitParam);

  void stream(
      @Param("groupedFilterParams") List<List<FilterParam>> groupedFilterParams,
      @Param("sortingParams") List<SortingParam> sortingParams,
      @Param("limitParam") LimitParam limitParam,
      ResultHandler<T> resultHandler);

  /**
   * @return the number of affected rows
   */
  long set(
      @Param("columnToValueMap") Map<String, ColumnValue> columnToValueMap,
      @Param("groupedFilterParams") List<List<FilterParam>> groupedFilterParams,
      @Param("sortingParams") List<SortingParam> sortingParams,
      @Param("limitParam") LimitParam limitParam);

  /**
   * @return the number of affected rows
   */
  long setAll(
      @Param("columnToValueMap") Map<String, ColumnValue> columnToValueMap);

  /**
   * @return the number of affected rows
   */
  long purge(
      @Param("groupedFilterParams") List<List<FilterParam>> groupedFilterParams,
      @Param("sortingParams") List<SortingParam> sortingParams,
      @Param("limitParam") LimitParam limitParam);

  long purgeAll();

  long count(
      @Param("groupedFilterParams") List<List<FilterParam>> groupedFilterParams);

  long countAll();

  @Flush
  List<BatchResult> flushStatements();

}
