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

import org.ownchan.server.joint.persistence.template.SystemSettingTemplate;
import org.ownchan.server.joint.persistence.template.link.SystemSettingLinkTemplate;
import org.ownchan.server.persistence.dao.SystemSettingDao;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbSystemSetting
    extends
      PersistableObject<DbSystemSetting, SystemSettingTemplate, SystemSettingLinkTemplate, SystemSettingDao>
    implements
      SystemSettingTemplate,
      SystemSettingLinkTemplate {

  private static SystemSettingDao dao;

  private long id;

  private Long settingId;

  private Long choiceId;

  private String customValue;

  private Date createTime;

  private Date updateTime;

  private Long updateUserId;

  public DbSystemSetting() {
    super();
  }

  public DbSystemSetting(SystemSettingTemplate template) {
    super(template, SystemSettingTemplate.class);
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
  public Long getSettingId() {
    return settingId;
  }

  public void setSettingId(Long settingId) {
    this.settingId = settingId;
  }

  @Override
  public Long getChoiceId() {
    return choiceId;
  }

  public void setChoiceId(Long choiceId) {
    this.choiceId = choiceId;
  }

  @Override
  public String getCustomValue() {
    return customValue;
  }

  public void setCustomValue(String customValue) {
    this.customValue = customValue;
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
  protected SystemSettingDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(SystemSettingDao.class);
    }

    return dao;
  }

}
