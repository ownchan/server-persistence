package org.ownchan.server.persistence.template.link;

import java.util.List;

import org.ownchan.server.persistence.model.DbRole;

public interface UserLinkTemplate extends EntityLinkTemplate<UserLinkTemplate> {

  List<DbRole> getLinkedRoles();

}
