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

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ownchan.server.persistence.model.DbContent;
import org.ownchan.server.persistence.model.DbLabel;
import org.ownchan.server.persistence.model.DbPrivateLabel;

@Mapper
public interface DbContentMapper extends PersistableObjectMapper<DbContent> {

  List<DbPrivateLabel> fetchAllPrivateLabels(@Param("contentId") long contentId, @Param("userId") long userId);

  long removeAllPrivateLabels(@Param("contentId") long contentId, @Param("userId") long userId);

  int assignPrivateLabel(@Param("contentId") long contentId, @Param("privateLabelId") long privateLabelId);

  int removePrivateLabel(@Param("contentId") long contentId, @Param("privateLabelId") long privateLabelId);

  List<DbLabel> fetchAllCreatorLabels(long contentId);

  long removeAllCreatorLabels(long contentId);

  int assignCreatorLabel(@Param("contentId") long contentId, @Param("labelId") long labelId);

  int removeCreatorLabel(@Param("contentId") long contentId, @Param("labelId") long labelId);

}
