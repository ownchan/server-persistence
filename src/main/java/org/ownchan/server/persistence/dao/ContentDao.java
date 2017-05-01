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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.ownchan.server.persistence.mapper.DbContentMapper;
import org.ownchan.server.persistence.model.DbContent;
import org.ownchan.server.persistence.model.DbPrivateLabel;
import org.ownchan.server.persistence.model.DbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContentDao extends PersistableObjectDao<DbContent, DbContentMapper, ContentDao> implements DbContentMapper {

  @Autowired
  private DbContentMapper mapper;

  @Override
  protected DbContentMapper getMapper() {
    return mapper;
  }

  @Transactional(propagation = Propagation.NESTED)
  public void setPrivateLabels(DbContent content, DbUser user, List<DbPrivateLabel> privateLabels) {
    if (CollectionUtils.isNotEmpty(privateLabels)) {
      List<DbPrivateLabel> labelsForTargetUser = privateLabels.stream()
          .filter(label -> Objects.equals(label.getUserId(), user.getId()))
          .collect(Collectors.toList());
      if (CollectionUtils.isNotEmpty(labelsForTargetUser)) {
        List<DbPrivateLabel> currentPrivateLabels = fetchAllPrivateLabels(content, user);
        labelsForTargetUser.stream().forEach(label -> {
          boolean alreadyAssigned = currentPrivateLabels.remove(label);
          if (!alreadyAssigned) {
            assignPrivateLabel(content, label);
          }
        });
        // what's left over in currentPrivateLabels are in fact the labels that need to be removed
        if (CollectionUtils.isNotEmpty(currentPrivateLabels)) {
          currentPrivateLabels.stream().forEach(label -> removePrivateLabel(content, label));
        }
      } else {
        removeAllPrivateLabels(content, user);
      }
    } else {
      removeAllPrivateLabels(content, user);
    }
    flushStatements();
  }

  @Override
  public List<DbPrivateLabel> fetchAllPrivateLabels(long contentId, long userId) {
    return mapper.fetchAllPrivateLabels(contentId, userId);
  }

  public List<DbPrivateLabel> fetchAllPrivateLabels(DbContent content, DbUser user) {
    return fetchAllPrivateLabels(content.getId(), user.getId());
  }

  @Override
  public long removeAllPrivateLabels(long contentId, long userId) {
    return mapper.removeAllPrivateLabels(contentId, userId);
  }

  public long removeAllPrivateLabels(DbContent content, DbUser user) {
    return removeAllPrivateLabels(content.getId(), user.getId());
  }

  @Override
  public int assignPrivateLabel(long contentId, long privateLabelId) {
    return mapper.assignPrivateLabel(contentId, privateLabelId);
  }

  public int assignPrivateLabel(DbContent content, DbPrivateLabel privateLabel) {
    return assignPrivateLabel(content.getId(), privateLabel.getId());
  }

  @Override
  public int removePrivateLabel(long contentId, long privateLabelId) {
    return mapper.removePrivateLabel(contentId, privateLabelId);
  }

  public int removePrivateLabel(DbContent content, DbPrivateLabel privateLabel) {
    return removePrivateLabel(content.getId(), privateLabel.getId());
  }

}
