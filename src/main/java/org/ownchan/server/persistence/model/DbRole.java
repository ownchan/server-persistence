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
package org.ownchan.server.persistence.model;

import java.util.Date;
import java.util.List;

import org.ownchan.server.persistence.dao.RoleDao;
import org.ownchan.server.persistence.template.RoleTemplate;
import org.ownchan.server.persistence.template.link.RoleLinkTemplate;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbRole extends PersistableObject<DbRole, RoleTemplate, RoleLinkTemplate, RoleDao> implements RoleTemplate, RoleLinkTemplate {

  private static RoleDao dao;

  private long id;

  private String name;

  private Long msgIdName;

  private Long msgIdDescription;

  private Date createTime;

  private Date updateTime;

  private volatile List<DbPrivilege> linkedPrivileges;

  public DbRole() {
    super();
  }

  public DbRole(RoleTemplate template) {
    super(template, RoleTemplate.class);
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  protected void setId(long id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Long getMsgIdName() {
    return msgIdName;
  }

  public void setMsgIdName(Long msgIdName) {
    this.msgIdName = msgIdName;
  }

  @Override
  public Long getMsgIdDescription() {
    return msgIdDescription;
  }

  public void setMsgIdDescription(Long msgIdDescription) {
    this.msgIdDescription = msgIdDescription;
  }

  @Override
  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @Override
  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  public List<DbPrivilege> getLinkedPrivileges() {
    return linkedPrivileges;
  }

  /**
   * Save (or replace) the privileges for an already persisted Role.
   */
  public void savePrivileges(List<DbPrivilege> privileges) {
    getDao().setPrivileges(this, privileges);
    /*
     *  this is debatable ...
     *  If the field has already been lazily loaded, it will be updated -
     *  if not, it will be updated upon lazy loading anyway.
     *  However, we could just omit this line and force to user to reload
     *  the object from database, if he needs a current version of the object
     *  that reflects all the changes the user performed.
     */
    this.linkedPrivileges = privileges;
  }

  @Override
  protected RoleDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(RoleDao.class);
    }

    return dao;
  }

}
