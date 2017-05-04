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
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.ownchan.server.joint.persistence.template.PhysicalContentTemplate;
import org.ownchan.server.joint.persistence.template.link.PhysicalContentLinkTemplate;
import org.ownchan.server.joint.persistence.valuetype.PersistableJsonData;
import org.ownchan.server.joint.persistence.valuetype.PhysicalContentStatus;
import org.ownchan.server.joint.persistence.valuetype.PhysicalContentType;
import org.ownchan.server.persistence.dao.PhysicalContentDao;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbPhysicalContent
    extends
      PersistableObject<DbPhysicalContent, PhysicalContentTemplate, PhysicalContentLinkTemplate, PhysicalContentDao>
    implements
      DbStatusAwareContent<PhysicalContentStatus>,
      PhysicalContentTemplate,
      PhysicalContentLinkTemplate {

  private static PhysicalContentDao dao;

  private long id;

  private PhysicalContentType type;

  private PhysicalContentStatus status;

  private String statusReason;

  private Short storageFolderYear;

  private Byte storageFolderMonth;

  private Byte storageFolderDay;

  private UUID storageFolderUuid;

  private String contentChecksum;

  private Date createTime;

  private Date updateTime;

  private String physicalContentType;

  private String externalContentLink;

  private PersistableJsonData additionalMetadata;

  public DbPhysicalContent() {
    super();
  }

  public DbPhysicalContent(PhysicalContentTemplate template) {
    super(template, PhysicalContentTemplate.class);
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
  public PhysicalContentType getType() {
    return type;
  }

  public void setType(PhysicalContentType type) {
    this.type = type;
  }

  @Override
  public PhysicalContentStatus getStatus() {
    return status;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(PhysicalContentStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatus(PhysicalContentStatus status) {
    this.status = status;
  }

  @Override
  public String getStatusReason() {
    return statusReason;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(PhysicalContentStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatusReason(String statusReason) {
    this.statusReason = statusReason;
  }

  @Override
  public void setStatus(PhysicalContentStatus status, String statusReason) {
    this.status = status;
    this.statusReason = StringUtils.abbreviate(statusReason, MAX_LENGTH_STATUS_REASON);
  }

  @Override
  public Short getStorageFolderYear() {
    return storageFolderYear;
  }

  public void setStorageFolderYear(Short storageFolderYear) {
    this.storageFolderYear = storageFolderYear;
  }

  @Override
  public Byte getStorageFolderMonth() {
    return storageFolderMonth;
  }

  public void setStorageFolderMonth(Byte storageFolderMonth) {
    this.storageFolderMonth = storageFolderMonth;
  }

  @Override
  public Byte getStorageFolderDay() {
    return storageFolderDay;
  }

  public void setStorageFolderDay(Byte storageFolderDay) {
    this.storageFolderDay = storageFolderDay;
  }

  @Override
  public UUID getStorageFolderUuid() {
    return storageFolderUuid;
  }

  public void setStorageFolderUuid(UUID storageFolderUuid) {
    this.storageFolderUuid = storageFolderUuid;
  }

  @Override
  public String getContentChecksum() {
    return contentChecksum;
  }

  public void setContentChecksum(String contentChecksum) {
    this.contentChecksum = contentChecksum;
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
  public String getPhysicalContentType() {
    return physicalContentType;
  }

  public void setPhysicalContentType(String physicalContentType) {
    this.physicalContentType = physicalContentType;
  }

  @Override
  public String getExternalContentLink() {
    return externalContentLink;
  }

  public void setExternalContentLink(String externalContentLink) {
    this.externalContentLink = externalContentLink;
  }

  @Override
  public PersistableJsonData getAdditionalMetadata() {
    return additionalMetadata;
  }

  public void setAdditionalMetadata(PersistableJsonData additionalMetadata) {
    this.additionalMetadata = additionalMetadata;
  }

  @Override
  protected PhysicalContentDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(PhysicalContentDao.class);
    }

    return dao;
  }

}
