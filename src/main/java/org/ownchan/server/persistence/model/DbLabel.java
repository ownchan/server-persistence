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

import org.ownchan.server.persistence.dao.LabelDao;
import org.ownchan.server.persistence.template.LabelTemplate;
import org.ownchan.server.persistence.template.link.LabelLinkTemplate;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbLabel extends PersistableObject<DbLabel, LabelTemplate, LabelLinkTemplate, LabelDao> implements LabelTemplate, LabelLinkTemplate {

  private static LabelDao dao;

  private long id;

  private String text;

  private Date createTime;

  private Long creatorId;

  public DbLabel() {
    super();
  }

  public DbLabel(LabelTemplate template) {
    super(template, LabelTemplate.class);
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
  public Long getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(Long creatorId) {
    this.creatorId = creatorId;
  }

  @Override
  protected LabelDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(LabelDao.class);
    }

    return dao;
  }

}
