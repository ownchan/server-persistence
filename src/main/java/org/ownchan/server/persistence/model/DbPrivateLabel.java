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

import org.ownchan.server.joint.persistence.template.PrivateLabelTemplate;
import org.ownchan.server.joint.persistence.template.link.PrivateLabelLinkTemplate;
import org.ownchan.server.persistence.dao.PrivateLabelDao;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbPrivateLabel
    extends
      PersistableObject<DbPrivateLabel, PrivateLabelTemplate, PrivateLabelLinkTemplate, PrivateLabelDao>
    implements
      PrivateLabelTemplate,
      PrivateLabelLinkTemplate {

  private static PrivateLabelDao dao;

  private long id;

  private Long userId;

  private String text;

  private Date createTime;

  public DbPrivateLabel() {
    super();
  }

  public DbPrivateLabel(PrivateLabelTemplate template) {
    super(template, PrivateLabelTemplate.class);
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
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @Override
  protected PrivateLabelDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(PrivateLabelDao.class);
    }

    return dao;
  }

}
