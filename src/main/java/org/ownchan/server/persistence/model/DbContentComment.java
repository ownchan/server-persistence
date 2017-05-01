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

import org.ownchan.server.persistence.dao.ContentCommentDao;
import org.ownchan.server.persistence.template.ContentCommentTemplate;
import org.ownchan.server.persistence.template.link.ContentCommentLinkTemplate;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbContentComment extends PersistableObject<DbContentComment, ContentCommentTemplate, ContentCommentLinkTemplate, ContentCommentDao> implements ContentCommentTemplate, ContentCommentLinkTemplate {

  private static ContentCommentDao dao;

  private long id;

  private Long parentId;

  private String text;

  private Long userId;

  private Long contentId;

  private Date createTime;

  private Date updateTime;

  private Long embeddedContentId;

  public DbContentComment() {
    super();
  }

  public DbContentComment(ContentCommentTemplate template) {
    super(template, ContentCommentTemplate.class);
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
  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  @Override
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public Long getContentId() {
    return contentId;
  }

  public void setContentId(Long contentId) {
    this.contentId = contentId;
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
  public Long getEmbeddedContentId() {
    return embeddedContentId;
  }

  public void setEmbeddedContentId(Long embeddedContentId) {
    this.embeddedContentId = embeddedContentId;
  }

  @Override
  protected ContentCommentDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(ContentCommentDao.class);
    }

    return dao;
  }

}
