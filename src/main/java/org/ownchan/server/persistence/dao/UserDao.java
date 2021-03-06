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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.ownchan.server.joint.persistence.template.RoleTemplate;
import org.ownchan.server.joint.persistence.template.UserTemplate;
import org.ownchan.server.joint.security.ContextUser;
import org.ownchan.server.joint.security.Privilege;
import org.ownchan.server.persistence.mapper.DbUserMapper;
import org.ownchan.server.persistence.mapper.generic.ColumnValue;
import org.ownchan.server.persistence.mapper.generic.FilterParam;
import org.ownchan.server.persistence.mapper.generic.LimitParam;
import org.ownchan.server.persistence.mapper.generic.ValuePlaceholderType;
import org.ownchan.server.persistence.model.DbRole;
import org.ownchan.server.persistence.model.DbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserDao extends PersistableObjectDao<DbUser, DbUserMapper, UserDao> implements DbUserMapper {

  private static final String PERM_QUERY_MANAGE_ROLES = "hasPermission(#contextUser, '" + Privilege.PRIV_MANAGE_ROLES + "')";

  @Autowired
  private DbUserMapper mapper;

  @Override
  protected DbUserMapper getMapper() {
    return mapper;
  }

  public DbUser getByAlias(String alias) {
    FilterParam filterParam = new FilterParam(DbUser.DB_FIELD_ALIAS, "=", alias);
    List<DbUser> matches = mapper.fetch(Arrays.asList(Arrays.asList(filterParam)), null, null);
    if (CollectionUtils.isNotEmpty(matches)) {
      return matches.get(0);
    }

    return null;
  }

  @Override
  public long removeAllRoles(long userId) {
    return mapper.removeAllRoles(userId);
  }

  public long removeAllRoles(UserTemplate user) {
    return mapper.removeAllRoles(user.getId());
  }

  @PreAuthorize(PERM_QUERY_MANAGE_ROLES)
  public long removeAllRoles(ContextUser contextUser, UserTemplate user) {
    return mapper.removeAllRoles(user.getId());
  }

  @Override
  public int assignRole(long userId, long roleId) {
    return mapper.assignRole(userId, roleId);
  }

  public int assignRole(UserTemplate user, RoleTemplate role) {
    return mapper.assignRole(user.getId(), role.getId());
  }

  @PreAuthorize(PERM_QUERY_MANAGE_ROLES)
  public int assignRole(ContextUser contextUser, UserTemplate user, RoleTemplate role) {
    return mapper.assignRole(user.getId(), role.getId());
  }

  @Override
  public List<DbRole> fetchAllRoles(long userId) {
    return mapper.fetchAllRoles(userId);
  }

  public List<DbRole> fetchAllRoles(UserTemplate user) {
    return mapper.fetchAllRoles(user.getId());
  }

  @Override
  public int removeRole(long userId, long roleId) {
    return mapper.removeRole(userId, roleId);
  }

  public int removeRole(UserTemplate user, RoleTemplate role) {
    return mapper.removeRole(user.getId(), role.getId());
  }

  @PreAuthorize(PERM_QUERY_MANAGE_ROLES)
  public int removeRole(ContextUser contextUser, UserTemplate user, RoleTemplate role) {
    return mapper.removeRole(user.getId(), role.getId());
  }

  public long updateBeaconTime(ContextUser contextUser) {
    FilterParam filterParam = new FilterParam(DbUser.DB_FIELD_ID, DbUser.DB_OPERATOR_EQUALS, contextUser.getId());
    Map<String, ColumnValue> columnToValueMap = new HashMap<>();
    columnToValueMap.put(DbUser.DB_FIELD_BEACON_TIME, new ColumnValue(DbUser.DB_VALUE_CURRENT_TIMESTAMP, ValuePlaceholderType.RAW_SQL));

    return mapper.set(columnToValueMap, Arrays.asList(Arrays.asList(filterParam)), null, new LimitParam(1L));
  }

}
