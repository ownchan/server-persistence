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

import org.ownchan.server.persistence.mapper.DbRtcConversationMapper;
import org.ownchan.server.persistence.model.DbRtcConversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RtcConversationDao extends PersistableObjectDao<DbRtcConversation, DbRtcConversationMapper, RtcConversationDao> implements DbRtcConversationMapper {

  @Autowired
  private DbRtcConversationMapper mapper;

  @Override
  protected DbRtcConversationMapper getMapper() {
    return mapper;
  }

}
