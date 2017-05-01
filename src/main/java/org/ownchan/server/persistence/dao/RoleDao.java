package org.ownchan.server.persistence.dao;

import java.util.List;

import org.ownchan.server.persistence.mapper.DbRoleMapper;
import org.ownchan.server.persistence.mapper.PersistableObjectMapper;
import org.ownchan.server.persistence.model.DbPrivilege;
import org.ownchan.server.persistence.model.DbRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleDao extends PersistableObjectDao<DbRole, PersistableObjectMapper<DbRole>> {

  @Autowired
  private DbRoleMapper mapper;

  @Override
  protected PersistableObjectMapper<DbRole> getMapper() {
    return mapper;
  }

  @Transactional(propagation = Propagation.NESTED)
  public void linkPrivileges(DbRole role, List<DbPrivilege> privileges) {
    // TODO first delete old links, then insert new ones
  }

}
