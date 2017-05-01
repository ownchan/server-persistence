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

import org.apache.commons.lang3.StringUtils;
import org.ownchan.server.joint.persistence.template.RoleTemplate;
import org.ownchan.server.joint.persistence.template.UserTemplate;
import org.ownchan.server.joint.persistence.template.link.UserLinkTemplate;
import org.ownchan.server.joint.persistence.valuetype.UserStatus;
import org.ownchan.server.joint.security.ContextUser;
import org.ownchan.server.persistence.dao.UserDao;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbUser extends PersistableObject<DbUser, UserTemplate, UserLinkTemplate, UserDao> implements DbStatusAwareContent<UserStatus>, UserTemplate, UserLinkTemplate {

  private static UserDao dao;

  private long id;

  private UserStatus status;

  private String statusReason;

  private String alias;

  private String displayName;

  private String passwordHash;

  private Date createTime;

  private Date updateTime;

  private String email;

  private String motto;

  private String externalLink;

  private Long avatarContentId;

  private Date lastPasswordChangeTime;

  private volatile List<DbRole> linkedRoles;

  public DbUser() {
    super();
  }

  public DbUser(UserTemplate template) {
    super(template, UserTemplate.class);
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
  public UserStatus getStatus() {
    return status;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(UserStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatus(UserStatus status) {
    this.status = status;
  }

  @Override
  public String getStatusReason() {
    return statusReason;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(UserStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatusReason(String statusReason) {
    this.statusReason = statusReason;
  }

  @Override
  public void setStatus(UserStatus status, String statusReason) {
    this.status = status;
    this.statusReason = StringUtils.abbreviate(statusReason, MAX_LENGTH_STATUS_REASON);
  }

  @Override
  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
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
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getMotto() {
    return motto;
  }

  public void setMotto(String motto) {
    this.motto = motto;
  }

  @Override
  public String getExternalLink() {
    return externalLink;
  }

  public void setExternalLink(String externalLink) {
    this.externalLink = externalLink;
  }

  @Override
  public Long getAvatarContentId() {
    return avatarContentId;
  }

  public void setAvatarContentId(Long avatarContentId) {
    this.avatarContentId = avatarContentId;
  }

  @Override
  public Date getLastPasswordChangeTime() {
    return lastPasswordChangeTime;
  }

  public void setLastPasswordChangeTime(Date lastPasswordChangeTime) {
    this.lastPasswordChangeTime = lastPasswordChangeTime;
  }

  @Override
  public List<DbRole> getLinkedRoles() {
    return linkedRoles;
  }

  public List<DbRole> refreshLinkedRoles() {
    linkedRoles = getDao().fetchAllRoles(this);
    return linkedRoles;
  }

  public void assignRole(ContextUser contextUser, RoleTemplate role) {
    getDao().assignRole(contextUser, this, role);
  }

  public void removeRole(ContextUser contextUser, RoleTemplate role) {
    getDao().removeRole(contextUser, this, role);
  }

  public void removeAllRoles(ContextUser contextUser) {
    getDao().removeAllRoles(contextUser, this);
  }

  @Override
  protected UserDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(UserDao.class);
    }

    return dao;
  }

}
