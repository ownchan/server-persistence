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

import org.ownchan.server.persistence.mapper.DbUserBanMapper;
import org.ownchan.server.persistence.template.UserBanTemplate;
import org.ownchan.server.persistence.template.link.UserBanLinkTemplate;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbUserBan extends PersistableObject<DbUserBan, UserBanTemplate, UserBanLinkTemplate> implements UserBanTemplate, UserBanLinkTemplate {

  private static DbUserBanMapper mapper;

  private long id;

  private Long userId;

  private boolean active;

  private Date banExpirationTime;

  private String banReason;

  private Long banInitiatorId;

  private Date createTime;

  private Date updateTime;

  public DbUserBan() {
    super();
  }

  public DbUserBan(UserBanTemplate template) {
    super(template, UserBanTemplate.class);
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
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public Date getBanExpirationTime() {
    return banExpirationTime;
  }

  public void setBanExpirationTime(Date banExpirationTime) {
    this.banExpirationTime = banExpirationTime;
  }

  @Override
  public String getBanReason() {
    return banReason;
  }

  public void setBanReason(String banReason) {
    this.banReason = banReason;
  }

  @Override
  public Long getBanInitiatorId() {
    return banInitiatorId;
  }

  public void setBanInitiatorId(Long banInitiatorId) {
    this.banInitiatorId = banInitiatorId;
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
  protected DbUserBanMapper getMapper() {
    if (mapper == null) {
      mapper = StaticContextAccessor.getBean(DbUserBanMapper.class);
    }

    return mapper;
  }

}
