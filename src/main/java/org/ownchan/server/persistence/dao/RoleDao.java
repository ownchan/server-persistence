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
import org.ownchan.server.persistence.mapper.DbRoleMapper;
import org.ownchan.server.persistence.model.DbPrivilege;
import org.ownchan.server.persistence.model.DbRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleDao extends PersistableObjectDao<DbRole, DbRoleMapper, RoleDao> implements DbRoleMapper {

  @Autowired
  private DbRoleMapper mapper;

  @Override
  protected DbRoleMapper getMapper() {
    return mapper;
  }

  @Transactional(propagation = Propagation.NESTED)
  public void setPrivileges(DbRole role, List<DbPrivilege> privileges) {
    removeAllPrivileges(role);
    if (CollectionUtils.isNotEmpty(privileges)) {
      privileges.stream().forEach(privilege -> grantPrivilege(role, privilege));
    }
    flushStatements();
  }

  @Override
  public long removeAllPrivileges(long roleId) {
    return mapper.removeAllPrivileges(roleId);
  }

  public long removeAllPrivileges(DbRole role) {
    return removeAllPrivileges(role.getId());
  }

  @Override
  public int grantPrivilege(long roleId, long privilegeId) {
    return mapper.grantPrivilege(roleId, privilegeId);
  }

  public int grantPrivilege(DbRole role, DbPrivilege privilege) {
    return grantPrivilege(role.getId(), privilege.getId());
  }

}
