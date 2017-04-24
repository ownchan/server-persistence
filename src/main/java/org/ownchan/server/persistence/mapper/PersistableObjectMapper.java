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

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ResultHandler;
import org.ownchan.server.persistence.mapper.generic.FilterParam;
import org.ownchan.server.persistence.mapper.generic.LimitParam;
import org.ownchan.server.persistence.mapper.generic.SortingParam;
import org.ownchan.server.persistence.model.PersistableObject;

public interface PersistableObjectMapper<T extends PersistableObject<T>> {

  long insert(T persistableObject);

  T get(long id);

  void update(T persistableObject);

  void delete(long id);

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

  @Flush
  List<BatchResult> flush();

}
