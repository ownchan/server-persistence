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

import org.ownchan.server.joint.persistence.template.PrivilegeTemplate;
import org.ownchan.server.joint.persistence.template.RoleTemplate;
import org.ownchan.server.joint.security.ContextUser;
import org.ownchan.server.joint.security.Privilege;
import org.ownchan.server.persistence.mapper.DbRoleMapper;
import org.ownchan.server.persistence.model.DbPrivilege;
import org.ownchan.server.persistence.model.DbRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class RoleDao extends PersistableObjectDao<DbRole, DbRoleMapper, RoleDao> implements DbRoleMapper {

  private static final String PERM_QUERY_MANAGE_PRIVILEGES = "hasPermission(#contextUser, '" + Privilege.PRIV_MANAGE_PRIVILEGES + "')";

  @Autowired
  private DbRoleMapper mapper;

  @Override
  protected DbRoleMapper getMapper() {
    return mapper;
  }

  @Override
  public long removeAllPrivileges(long roleId) {
    return mapper.removeAllPrivileges(roleId);
  }

  public long removeAllPrivileges(RoleTemplate role) {
    return removeAllPrivileges(role.getId());
  }

  @PreAuthorize(PERM_QUERY_MANAGE_PRIVILEGES)
  public long removeAllPrivileges(ContextUser contextUser, RoleTemplate role) {
    return removeAllPrivileges(role.getId());
  }

  @Override
  public int grantPrivilege(long roleId, long privilegeId) {
    return mapper.grantPrivilege(roleId, privilegeId);
  }

  public int grantPrivilege(RoleTemplate role, PrivilegeTemplate privilege) {
    return grantPrivilege(role.getId(), privilege.getId());
  }

  @PreAuthorize(PERM_QUERY_MANAGE_PRIVILEGES)
  public int grantPrivilege(ContextUser contextUser, RoleTemplate role, PrivilegeTemplate privilege) {
    return grantPrivilege(role.getId(), privilege.getId());
  }

  @Override
  public List<DbPrivilege> fetchAllPrivileges(long roleId) {
    return mapper.fetchAllPrivileges(roleId);
  }

  public List<DbPrivilege> fetchAllPrivileges(RoleTemplate role) {
    return fetchAllPrivileges(role.getId());
  }

  @Override
  public int removePrivilege(long roleId, long privilegeId) {
    return mapper.removePrivilege(roleId, privilegeId);
  }

  public int removePrivilege(RoleTemplate role, PrivilegeTemplate privilege) {
    return removePrivilege(role.getId(), privilege.getId());
  }

  @PreAuthorize(PERM_QUERY_MANAGE_PRIVILEGES)
  public int removePrivilege(ContextUser contextUser, RoleTemplate role, PrivilegeTemplate privilege) {
    return removePrivilege(role.getId(), privilege.getId());
  }

}
