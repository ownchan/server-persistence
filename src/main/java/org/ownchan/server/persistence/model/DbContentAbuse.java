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

import org.apache.commons.lang3.StringUtils;
import org.ownchan.server.persistence.dao.ContentAbuseDao;
import org.ownchan.server.persistence.template.ContentAbuseTemplate;
import org.ownchan.server.persistence.template.link.ContentAbuseLinkTemplate;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbContentAbuse extends PersistableObject<DbContentAbuse, ContentAbuseTemplate, ContentAbuseLinkTemplate, ContentAbuseDao> implements DbStatusAwareContent<DbContentAbuseStatus>, ContentAbuseTemplate, ContentAbuseLinkTemplate {

  private static ContentAbuseDao dao;

  private long id;

  private Long contentId;

  private DbContentAbuseViolationType violationType;

  private String explanation;

  private String complainingEntityIp;

  private String complainingEntityContact;

  private Long complainingEntityUserId;

  private DbContentAbuseStatus status;

  private String statusReason;

  private Long assigneeId;

  private String teamNotes;

  private Date createTime;

  private Date updateTime;

  public DbContentAbuse() {
    super();
  }

  public DbContentAbuse(ContentAbuseTemplate template) {
    super(template, ContentAbuseTemplate.class);
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
  public Long getContentId() {
    return contentId;
  }

  public void setContentId(Long contentId) {
    this.contentId = contentId;
  }

  @Override
  public DbContentAbuseViolationType getViolationType() {
    return violationType;
  }

  public void setViolationType(DbContentAbuseViolationType violationType) {
    this.violationType = violationType;
  }

  @Override
  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  @Override
  public String getComplainingEntityIp() {
    return complainingEntityIp;
  }

  public void setComplainingEntityIp(String complainingEntityIp) {
    this.complainingEntityIp = complainingEntityIp;
  }

  @Override
  public String getComplainingEntityContact() {
    return complainingEntityContact;
  }

  public void setComplainingEntityContact(String complainingEntityContact) {
    this.complainingEntityContact = complainingEntityContact;
  }

  @Override
  public Long getComplainingEntityUserId() {
    return complainingEntityUserId;
  }

  public void setComplainingEntityUserId(Long complainingEntityUserId) {
    this.complainingEntityUserId = complainingEntityUserId;
  }

  @Override
  public DbContentAbuseStatus getStatus() {
    return status;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(DbContentAbuseStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatus(DbContentAbuseStatus status) {
    this.status = status;
  }

  @Override
  public String getStatusReason() {
    return statusReason;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(DbContentAbuseStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatusReason(String statusReason) {
    this.statusReason = statusReason;
  }

  @Override
  public void setStatus(DbContentAbuseStatus status, String statusReason) {
    this.status = status;
    this.statusReason = StringUtils.abbreviate(statusReason, MAX_LENGTH_STATUS_REASON);
  }

  @Override
  public Long getAssigneeId() {
    return assigneeId;
  }

  public void setAssigneeId(Long assigneeId) {
    this.assigneeId = assigneeId;
  }

  @Override
  public String getTeamNotes() {
    return teamNotes;
  }

  public void setTeamNotes(String teamNotes) {
    this.teamNotes = teamNotes;
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
  protected ContentAbuseDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(ContentAbuseDao.class);
    }

    return dao;
  }

}
