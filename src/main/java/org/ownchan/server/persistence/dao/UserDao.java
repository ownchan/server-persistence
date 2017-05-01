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

import org.apache.commons.collections4.CollectionUtils;
import org.ownchan.server.persistence.mapper.DbUserMapper;
import org.ownchan.server.persistence.model.DbRole;
import org.ownchan.server.persistence.model.DbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDao extends PersistableObjectDao<DbUser, DbUserMapper, UserDao> implements DbUserMapper {

  @Autowired
  private DbUserMapper mapper;

  @Override
  protected DbUserMapper getMapper() {
    return mapper;
  }

  @Transactional(propagation = Propagation.NESTED)
  public void setRoles(DbUser user, List<DbRole> roles) {
    removeAllRoles(user);
    if (CollectionUtils.isNotEmpty(roles)) {
      roles.stream().forEach(role -> assignRole(user, role));
    }
    flushStatements();
  }

  @Override
  public long removeAllRoles(long userId) {
    return mapper.removeAllRoles(userId);
  }

  public long removeAllRoles(DbUser user) {
    return removeAllRoles(user.getId());
  }

  @Override
  public int assignRole(long userId, long roleId) {
    return mapper.assignRole(userId, roleId);
  }

  public int assignRole(DbUser user, DbRole role) {
    return assignRole(user.getId(), role.getId());
  }

}
