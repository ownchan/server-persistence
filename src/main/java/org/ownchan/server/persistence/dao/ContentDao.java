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
package org.ownchan.server.persistence.dao;

import static org.ownchan.server.joint.message.SystemMessageCode.EX_FORBIDDEN_CONTENT_ADD_CREATOR_LABEL;
import static org.ownchan.server.joint.message.SystemMessageCode.EX_FORBIDDEN_CONTENT_ADD_PRIVATE_LABEL;
import static org.ownchan.server.joint.message.SystemMessageCode.EX_FORBIDDEN_CONTENT_FETCH_PRIVATE_LABELS;
import static org.ownchan.server.joint.message.SystemMessageCode.EX_FORBIDDEN_CONTENT_REMOVE_CREATOR_LABEL;
import static org.ownchan.server.joint.message.SystemMessageCode.EX_FORBIDDEN_CONTENT_REMOVE_CREATOR_LABELS;
import static org.ownchan.server.joint.message.SystemMessageCode.EX_FORBIDDEN_CONTENT_REMOVE_PRIVATE_LABEL;
import static org.ownchan.server.joint.message.SystemMessageCode.EX_FORBIDDEN_CONTENT_REMOVE_PRIVATE_LABELS;

import java.util.List;
import java.util.Objects;

import org.ownchan.server.joint.exception.ServerException;
import org.ownchan.server.joint.persistence.template.ContentTemplate;
import org.ownchan.server.joint.persistence.template.LabelTemplate;
import org.ownchan.server.joint.persistence.template.PrivateLabelTemplate;
import org.ownchan.server.joint.persistence.template.UserTemplate;
import org.ownchan.server.joint.security.ContextUser;
import org.ownchan.server.persistence.mapper.DbContentMapper;
import org.ownchan.server.persistence.model.DbContent;
import org.ownchan.server.persistence.model.DbLabel;
import org.ownchan.server.persistence.model.DbPrivateLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ContentDao extends PersistableObjectDao<DbContent, DbContentMapper, ContentDao> implements DbContentMapper {

  @Autowired
  private DbContentMapper mapper;

  @Override
  protected DbContentMapper getMapper() {
    return mapper;
  }

  @Override
  public List<DbPrivateLabel> fetchAllPrivateLabels(long contentId, long userId) {
    return mapper.fetchAllPrivateLabels(contentId, userId);
  }

  public List<DbPrivateLabel> fetchAllPrivateLabels(ContentTemplate content, UserTemplate user) {
    return mapper.fetchAllPrivateLabels(content.getId(), user.getId());
  }

  public List<DbPrivateLabel> fetchAllPrivateLabels(ContextUser contextUser, ContentTemplate content, UserTemplate user) {
    if (!Objects.equals(user.getId(), contextUser.getId())) {
      throw new ServerException(
          EX_FORBIDDEN_CONTENT_FETCH_PRIVATE_LABELS,
          HttpStatus.FORBIDDEN,
          user.getId(),
          contextUser.getId());
    }

    return mapper.fetchAllPrivateLabels(content.getId(), user.getId());
  }

  @Override
  public long removeAllPrivateLabels(long contentId, long userId) {
    return mapper.removeAllPrivateLabels(contentId, userId);
  }

  public long removeAllPrivateLabels(ContentTemplate content, UserTemplate user) {
    return mapper.removeAllPrivateLabels(content.getId(), user.getId());
  }

  public long removeAllPrivateLabels(ContextUser contextUser, ContentTemplate content, UserTemplate user) {
    if (!Objects.equals(user.getId(), contextUser.getId())) {
      throw new ServerException(
          EX_FORBIDDEN_CONTENT_REMOVE_PRIVATE_LABELS,
          HttpStatus.FORBIDDEN,
          user.getId(),
          contextUser.getId());
    }

    return mapper.removeAllPrivateLabels(content.getId(), user.getId());
  }

  @Override
  public int assignPrivateLabel(long contentId, long privateLabelId) {
    return mapper.assignPrivateLabel(contentId, privateLabelId);
  }

  public int assignPrivateLabel(ContentTemplate content, PrivateLabelTemplate privateLabel) {
    return mapper.assignPrivateLabel(content.getId(), privateLabel.getId());
  }

  public int assignPrivateLabel(ContextUser contextUser, ContentTemplate content, PrivateLabelTemplate privateLabel) {
    if (!Objects.equals(privateLabel.getUserId(), contextUser.getId())) {
      throw new ServerException(
          EX_FORBIDDEN_CONTENT_ADD_PRIVATE_LABEL,
          HttpStatus.FORBIDDEN,
          privateLabel.getId(),
          contextUser.getId());
    }

    return mapper.assignPrivateLabel(content.getId(), privateLabel.getId());
  }

  @Override
  public int removePrivateLabel(long contentId, long privateLabelId) {
    return mapper.removePrivateLabel(contentId, privateLabelId);
  }

  public int removePrivateLabel(ContentTemplate content, PrivateLabelTemplate privateLabel) {
    return mapper.removePrivateLabel(content.getId(), privateLabel.getId());
  }

  public int removePrivateLabel(ContextUser contextUser, ContentTemplate content, PrivateLabelTemplate privateLabel) {
    if (!Objects.equals(privateLabel.getUserId(), contextUser.getId())) {
      throw new ServerException(
          EX_FORBIDDEN_CONTENT_REMOVE_PRIVATE_LABEL,
          HttpStatus.FORBIDDEN,
          privateLabel.getId(),
          contextUser.getId());
    }

    return mapper.removePrivateLabel(content.getId(), privateLabel.getId());
  }

  @Override
  public List<DbLabel> fetchAllCreatorLabels(long contentId) {
    return mapper.fetchAllCreatorLabels(contentId);
  }

  public List<DbLabel> fetchAllCreatorLabels(ContentTemplate content) {
    return mapper.fetchAllCreatorLabels(content.getId());
  }

  @Override
  public long removeAllCreatorLabels(long contentId) {
    return mapper.removeAllCreatorLabels(contentId);
  }

  public long removeAllCreatorLabels(ContentTemplate content) {
    return mapper.removeAllCreatorLabels(content.getId());
  }

  public long removeAllCreatorLabels(ContextUser contextUser, ContentTemplate content) {
    if (!Objects.equals(contextUser.getId(), content.getUserId())) {
      throw new ServerException(
          EX_FORBIDDEN_CONTENT_REMOVE_CREATOR_LABELS,
          HttpStatus.FORBIDDEN,
          content.getId(),
          contextUser.getId());
    }

    return mapper.removeAllCreatorLabels(content.getId());
  }

  @Override
  public int assignCreatorLabel(long contentId, long labelId) {
    return mapper.assignCreatorLabel(contentId, labelId);
  }

  public int assignCreatorLabel(ContentTemplate content, LabelTemplate label) {
    return mapper.assignCreatorLabel(content.getId(), label.getId());
  }

  public int assignCreatorLabel(ContextUser contextUser, ContentTemplate content, LabelTemplate label) {
    if (!Objects.equals(contextUser.getId(), content.getUserId())) {
      throw new ServerException(
          EX_FORBIDDEN_CONTENT_ADD_CREATOR_LABEL,
          HttpStatus.FORBIDDEN,
          content.getId(),
          contextUser.getId());
    }

    return mapper.assignCreatorLabel(content.getId(), label.getId());
  }

  @Override
  public int removeCreatorLabel(long contentId, long labelId) {
    return mapper.removeCreatorLabel(contentId, labelId);
  }

  public int removeCreatorLabel(ContentTemplate content, LabelTemplate label) {
    return mapper.removeCreatorLabel(content.getId(), label.getId());
  }

  public int removeCreatorLabel(ContextUser contextUser, ContentTemplate content, LabelTemplate label) {
    if (!Objects.equals(contextUser.getId(), content.getUserId())) {
      throw new ServerException(
          EX_FORBIDDEN_CONTENT_REMOVE_CREATOR_LABEL,
          HttpStatus.FORBIDDEN,
          content.getId(),
          contextUser.getId());
    }

    return mapper.removeCreatorLabel(content.getId(), label.getId());
  }

}
