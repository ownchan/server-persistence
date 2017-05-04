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

import org.ownchan.server.joint.persistence.template.MessageTemplate;
import org.ownchan.server.joint.persistence.template.link.MessageLinkTemplate;
import org.ownchan.server.joint.persistence.valuetype.MessageType;
import org.ownchan.server.persistence.dao.MessageDao;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbMessage
    extends
      PersistableObject<DbMessage, MessageTemplate, MessageLinkTemplate, MessageDao>
    implements
      MessageTemplate,
      MessageLinkTemplate {

  private static MessageDao dao;

  private long id;

  private String code;

  private MessageType type;

  private boolean showHtmlEditor;

  private Date createTime;

  private Date updateTime;

  private String msgEn;

  private String msgDe;

  public DbMessage() {
    super();
  }

  public DbMessage(MessageTemplate template) {
    super(template, MessageTemplate.class);
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
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

  @Override
  public boolean isShowHtmlEditor() {
    return showHtmlEditor;
  }

  public void setShowHtmlEditor(boolean showHtmlEditor) {
    this.showHtmlEditor = showHtmlEditor;
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
  public String getMsgEn() {
    return msgEn;
  }

  public void setMsgEn(String msgEn) {
    this.msgEn = msgEn;
  }

  @Override
  public String getMsgDe() {
    return msgDe;
  }

  public void setMsgDe(String msgDe) {
    this.msgDe = msgDe;
  }

  @Override
  protected MessageDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(MessageDao.class);
    }

    return dao;
  }

}
