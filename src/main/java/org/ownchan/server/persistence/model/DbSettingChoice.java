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

import org.ownchan.server.joint.persistence.template.SettingChoiceTemplate;
import org.ownchan.server.joint.persistence.template.link.SettingChoiceLinkTemplate;
import org.ownchan.server.persistence.dao.SettingChoiceDao;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbSettingChoice
    extends
      PersistableObject<DbSettingChoice, SettingChoiceTemplate, SettingChoiceLinkTemplate, SettingChoiceDao>
    implements
      SettingChoiceTemplate,
      SettingChoiceLinkTemplate {

  private static SettingChoiceDao dao;

  private long id;

  private Long settingId;

  private String settingValue;

  private Long msgIdName;

  private Long msgIdDescription;

  public DbSettingChoice() {
    super();
  }

  public DbSettingChoice(SettingChoiceTemplate template) {
    super(template, SettingChoiceTemplate.class);
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
  public String getSettingValue() {
    return settingValue;
  }

  public void setSettingValue(String settingValue) {
    this.settingValue = settingValue;
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
  protected SettingChoiceDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(SettingChoiceDao.class);
    }

    return dao;
  }

}
