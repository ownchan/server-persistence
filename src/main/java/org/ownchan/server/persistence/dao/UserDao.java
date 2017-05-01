package org.ownchan.server.persistence.dao;

import java.util.List;

import org.ownchan.server.persistence.mapper.DbUserMapper;
import org.ownchan.server.persistence.mapper.PersistableObjectMapper;
import org.ownchan.server.persistence.model.DbRole;
import org.ownchan.server.persistence.model.DbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDao extends PersistableObjectDao<DbUser, PersistableObjectMapper<DbUser>> {

  @Autowired
  private DbUserMapper mapper;

  @Override
  protected PersistableObjectMapper<DbUser> getMapper() {
    return mapper;
  }

  @Transactional(propagation = Propagation.NESTED)
  public void linkRoles(DbUser user, List<DbRole> roles) {
    // TODO first delete old links, then insert new ones
  }

}
