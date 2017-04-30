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

import org.ownchan.server.persistence.mapper.DbSettingMapper;
import org.ownchan.server.persistence.template.SettingTemplate;
import org.ownchan.server.persistence.template.link.SettingLinkTemplate;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbSetting extends PersistableObject<DbSetting, SettingTemplate, SettingLinkTemplate> implements SettingTemplate, SettingLinkTemplate {

  private DbSettingMapper mapper;

  private long id;

  private String name;

  private DbSettingType type;

  private Long msgIdName;

  private Long msgIdDescription;

  private boolean constrained;

  private DbSettingValueType valueType;

  private String defaultValue;

  private String minValue;

  private String maxValue;

  private Date createTime;

  private Date updateTime;

  public DbSetting() {
    super();
  }

  public DbSetting(SettingTemplate template) {
    super(template, SettingTemplate.class);
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
  public DbSettingType getType() {
    return type;
  }

  public void setType(DbSettingType type) {
    this.type = type;
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
  public boolean isConstrained() {
    return constrained;
  }

  public void setConstrained(boolean constrained) {
    this.constrained = constrained;
  }

  @Override
  public DbSettingValueType getValueType() {
    return valueType;
  }

  public void setValueType(DbSettingValueType valueType) {
    this.valueType = valueType;
  }

  @Override
  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  @Override
  public String getMinValue() {
    return minValue;
  }

  public void setMinValue(String minValue) {
    this.minValue = minValue;
  }

  @Override
  public String getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(String maxValue) {
    this.maxValue = maxValue;
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
  protected DbSettingMapper getMapper() {
    if (mapper == null) {
      mapper = StaticContextAccessor.getBean(DbSettingMapper.class);
    }

    return mapper;
  }

}
