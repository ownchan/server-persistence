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

import org.ownchan.server.persistence.dao.PrivilegeDao;
import org.ownchan.server.persistence.template.PrivilegeTemplate;
import org.ownchan.server.persistence.template.link.PrivilegeLinkTemplate;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbPrivilege extends PersistableObject<DbPrivilege, PrivilegeTemplate, PrivilegeLinkTemplate, PrivilegeDao> implements PrivilegeTemplate, PrivilegeLinkTemplate {

  private static PrivilegeDao dao;

  private long id;

  private String name;

  private Long msgIdName;

  private Long msgIdDescription;

  private Date createTime;

  private Date updateTime;

  public DbPrivilege() {
    super();
  }

  public DbPrivilege(PrivilegeTemplate template) {
    super(template, PrivilegeTemplate.class);
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
  protected PrivilegeDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(PrivilegeDao.class);
    }

    return dao;
  }

}
