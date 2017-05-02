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
import org.ownchan.server.joint.persistence.template.ContentTemplate;
import org.ownchan.server.joint.persistence.template.LabelTemplate;
import org.ownchan.server.joint.persistence.template.PrivateLabelTemplate;
import org.ownchan.server.joint.persistence.template.link.ContentLinkTemplate;
import org.ownchan.server.joint.persistence.valuetype.ContentStatus;
import org.ownchan.server.joint.persistence.valuetype.geom.NullablePoint;
import org.ownchan.server.joint.security.ContextUser;
import org.ownchan.server.persistence.dao.ContentDao;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbContent extends PersistableObject<DbContent, ContentTemplate, ContentLinkTemplate, ContentDao> implements DbStatusAwareContent<ContentStatus>, ContentTemplate, ContentLinkTemplate {

  private static ContentDao dao;

  private long id;

  private ContentStatus status;

  private String statusReason;

  private Long parentId;

  private Long userId;

  private String caption;

  private String countryCode;

  private String cityName;

  private NullablePoint location;

  private String contentName;

  private Date contentTime;

  private Short contentYear;

  private Byte contentMonth;

  private Byte contentDay;

  private Date createTime;

  private Date updateTime;

  private Long physicalContentId;

  private Long userClicks;

  private Long plusCount;

  private Long minusCount;

  private volatile List<DbLabel> linkedCreatorLabels;

  // there is no use for linking (all) comments here, as they will be loaded using pagination
  //private volatile List<DbContentComment> linkedComments;

  private volatile DbContent linkedParent;

  private volatile DbUser linkedUser;

  private volatile DbPhysicalContent linkedPhysicalContent;

  /*
   * As there will be a dedicated abuse-grid that will be able to group pending abuses by content-id,
   * there is no need to link all abuses for a content here.
   */
  //private volatile List<DbContentAbuse> linkedAbuses;

  public DbContent() {
    super();
  }

  public DbContent(ContentTemplate template) {
    super(template, ContentTemplate.class);
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
  public ContentStatus getStatus() {
    return status;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(ContentStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatus(ContentStatus status) {
    this.status = status;
  }

  @Override
  public String getStatusReason() {
    return statusReason;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(ContentStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatusReason(String statusReason) {
    this.statusReason = statusReason;
  }

  @Override
  public void setStatus(ContentStatus status, String statusReason) {
    this.status = status;
    this.statusReason = StringUtils.abbreviate(statusReason, MAX_LENGTH_STATUS_REASON);
  }

  @Override
  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  @Override
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  @Override
  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  @Override
  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  @Override
  public NullablePoint getLocation() {
    return location;
  }

  public void setLocation(NullablePoint location) {
    this.location = location;
  }

  @Override
  public String getContentName() {
    return contentName;
  }

  public void setContentName(String contentName) {
    this.contentName = contentName;
  }

  @Override
  public Date getContentTime() {
    return contentTime;
  }

  public void setContentTime(Date contentTime) {
    this.contentTime = contentTime;
  }

  @Override
  public Short getContentYear() {
    return contentYear;
  }

  public void setContentYear(Short contentYear) {
    this.contentYear = contentYear;
  }

  @Override
  public Byte getContentMonth() {
    return contentMonth;
  }

  public void setContentMonth(Byte contentMonth) {
    this.contentMonth = contentMonth;
  }

  @Override
  public Byte getContentDay() {
    return contentDay;
  }

  public void setContentDay(Byte contentDay) {
    this.contentDay = contentDay;
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
  public Long getPhysicalContentId() {
    return physicalContentId;
  }

  public void setPhysicalContentId(Long physicalContentId) {
    this.physicalContentId = physicalContentId;
  }

  @Override
  public Long getUserClicks() {
    return userClicks;
  }

  public void setUserClicks(Long userClicks) {
    this.userClicks = userClicks;
  }

  @Override
  public Long getPlusCount() {
    return plusCount;
  }

  public void setPlusCount(Long plusCount) {
    this.plusCount = plusCount;
  }

  @Override
  public Long getMinusCount() {
    return minusCount;
  }

  public void setMinusCount(Long minusCount) {
    this.minusCount = minusCount;
  }

  public List<DbPrivateLabel> getLinkedPrivateLabels(ContextUser contextUser) {
    return getDao().fetchAllPrivateLabels(contextUser, this, contextUser);
  }

  @Override
  public List<DbLabel> getLinkedCreatorLabels() {
    return linkedCreatorLabels;
  }

  @Override
  public DbContent getLinkedParent() {
    return linkedParent;
  }

  @Override
  public DbUser getLinkedUser() {
    return linkedUser;
  }

  @Override
  public DbPhysicalContent getLinkedPhysicalContent() {
    return linkedPhysicalContent;
  }

  public List<DbLabel> refreshLinkedCreatorLabels() {
    linkedCreatorLabels = getDao().fetchAllCreatorLabels(this);
    return linkedCreatorLabels;
  }

  public void addPrivateLabel(ContextUser contextUser, PrivateLabelTemplate privateLabel) {
    getDao().assignPrivateLabel(contextUser, this, privateLabel);
  }

  public void removePrivateLabel(ContextUser contextUser, PrivateLabelTemplate privateLabel) {
    getDao().removePrivateLabel(contextUser, this, privateLabel);
  }

  public void addCreatorLabel(ContextUser contextUser, LabelTemplate label) {
    getDao().assignCreatorLabel(contextUser, this, label);
  }

  public void removeCreatoLabel(ContextUser contextUser, LabelTemplate label) {
    getDao().removeCreatorLabel(contextUser, this, label);
  }

  public void removeAllPrivateLabels(ContextUser contextUser) {
    getDao().removeAllPrivateLabels(contextUser, this, contextUser);
  }

  public void removeAllCreatorLabels(ContextUser contextUser) {
    getDao().removeAllCreatorLabels(contextUser, this);
  }

  @Override
  protected ContentDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(ContentDao.class);
    }

    return dao;
  }

}
