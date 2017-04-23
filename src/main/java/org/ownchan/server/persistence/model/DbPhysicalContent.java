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

public class DbPhysicalContent extends PersistableObject<DbPhysicalContent> implements DbStatusAwareContent<DbPhysicalContentStatus> {

  private long id;

  private DbPhysicalContentType type;

  private DbPhysicalContentStatus status;

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

  private DbJsonData additionalMetadata;

  @Override
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public DbPhysicalContentType getType() {
    return type;
  }

  public void setType(DbPhysicalContentType type) {
    this.type = type;
  }

  @Override
  public DbPhysicalContentStatus getStatus() {
    return status;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(DbPhysicalContentStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatus(DbPhysicalContentStatus status) {
    this.status = status;
  }

  @Override
  public String getStatusReason() {
    return statusReason;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(DbPhysicalContentStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatusReason(String statusReason) {
    this.statusReason = statusReason;
  }

  public Short getStorageFolderYear() {
    return storageFolderYear;
  }

  public void setStorageFolderYear(Short storageFolderYear) {
    this.storageFolderYear = storageFolderYear;
  }

  public Byte getStorageFolderMonth() {
    return storageFolderMonth;
  }

  public void setStorageFolderMonth(Byte storageFolderMonth) {
    this.storageFolderMonth = storageFolderMonth;
  }

  public Byte getStorageFolderDay() {
    return storageFolderDay;
  }

  public void setStorageFolderDay(Byte storageFolderDay) {
    this.storageFolderDay = storageFolderDay;
  }

  public UUID getStorageFolderUuid() {
    return storageFolderUuid;
  }

  public void setStorageFolderUuid(UUID storageFolderUuid) {
    this.storageFolderUuid = storageFolderUuid;
  }

  public String getContentChecksum() {
    return contentChecksum;
  }

  public void setContentChecksum(String contentChecksum) {
    this.contentChecksum = contentChecksum;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getPhysicalContentType() {
    return physicalContentType;
  }

  public void setPhysicalContentType(String physicalContentType) {
    this.physicalContentType = physicalContentType;
  }

  public String getExternalContentLink() {
    return externalContentLink;
  }

  public void setExternalContentLink(String externalContentLink) {
    this.externalContentLink = externalContentLink;
  }

  public DbJsonData getAdditionalMetadata() {
    return additionalMetadata;
  }

  public void setAdditionalMetadata(DbJsonData additionalMetadata) {
    this.additionalMetadata = additionalMetadata;
  }

  @Override
  public void setStatus(DbPhysicalContentStatus status, String statusReason) {
    this.status = status;
    this.statusReason = StringUtils.abbreviate(statusReason, MAX_LENGTH_STATUS_REASON);
  }

}
