package org.ownchan.server.persistence.template.link;

import java.util.List;

import org.ownchan.server.persistence.model.DbPrivilege;

public interface RoleLinkTemplate extends EntityLinkTemplate<RoleLinkTemplate> {

  List<DbPrivilege> getLinkedPrivileges();

}
