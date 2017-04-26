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
import org.ownchan.server.persistence.mapper.DbCloudLabelMapper;
import org.ownchan.server.persistence.template.CloudLabelTemplate;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbCloudLabel extends PersistableObject<DbCloudLabel, CloudLabelTemplate> implements DbStatusAwareContent<DbCloudLabelStatus>, CloudLabelTemplate {

  private static DbCloudLabelMapper mapper;

  private long id;

  private String text;

  private DbCloudLabelStatus status;

  private String statusReason;

  private String initialText;

  private DbCloudLabelProvider cloudProvider;

  private String cloudProviderLabelId;

  private Date createTime;

  private Date updateTime;

  private Long updateUserId;

  @Override
  public long getId() {
    return id;
  }

  @Override
  protected void setId(long id) {
    this.id = id;
  }

  @Override
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public DbCloudLabelStatus getStatus() {
    return status;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(DbCloudLabelStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatus(DbCloudLabelStatus status) {
    this.status = status;
  }

  @Override
  public String getStatusReason() {
    return statusReason;
  }

  /**
   * @deprecated Preferably, set both status and reason at once by using {@link #setStatus(DbCloudLabelStatus, String)}.
   */
  @Deprecated
  @Override
  public void setStatusReason(String statusReason) {
    this.statusReason = statusReason;
  }

  @Override
  public void setStatus(DbCloudLabelStatus status, String statusReason) {
    this.status = status;
    this.statusReason = StringUtils.abbreviate(statusReason, MAX_LENGTH_STATUS_REASON);
  }

  @Override
  public String getInitialText() {
    return initialText;
  }

  public void setInitialText(String initialText) {
    this.initialText = initialText;
  }

  @Override
  public DbCloudLabelProvider getCloudProvider() {
    return cloudProvider;
  }

  public void setCloudProvider(DbCloudLabelProvider cloudProvider) {
    this.cloudProvider = cloudProvider;
  }

  @Override
  public String getCloudProviderLabelId() {
    return cloudProviderLabelId;
  }

  public void setCloudProviderLabelId(String cloudProviderLabelId) {
    this.cloudProviderLabelId = cloudProviderLabelId;
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
  public Long getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(Long updateUserId) {
    this.updateUserId = updateUserId;
  }

  @Override
  public DbCloudLabelMapper getMapper() {
    if (mapper == null) {
      mapper = StaticContextAccessor.getBean(DbCloudLabelMapper.class);
    }

    return mapper;
  }

}
