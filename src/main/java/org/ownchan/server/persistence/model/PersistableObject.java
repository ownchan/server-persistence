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

import org.ownchan.server.persistence.mapper.PersistableObjectMapper;
import org.ownchan.server.persistence.template.EntityTemplate;
import org.ownchan.server.persistence.template.link.EntityLinkTemplate;
import org.springframework.beans.BeanUtils;

public abstract class PersistableObject<T extends PersistableObject<T, U, V> & EntityTemplate<U> & EntityLinkTemplate<V>, U extends EntityTemplate<U>, V extends EntityLinkTemplate<V>> {

  public PersistableObject() {

  }

  public PersistableObject(EntityTemplate<U> template, Class<U> editable) {
    if (template != null) {
      if (editable == null) {
        throw new RuntimeException("parameter \"editable\" must not be null");
      }
      BeanUtils.copyProperties(template, this, editable);
    }
  }

  public abstract long getId();

  protected abstract void setId(long id);

  /**
   * Calling this method will trigger loading
   * all the lazy properties of the persisted object.
   *
   * @see mybatis-config-server.xml
   */
  public void detach() {

  }

  /**
   * Persist the object (insert or update it in the database).
   * @return the current instance of the object, which will receive an id upon insertion.
   */
  @SuppressWarnings("unchecked")
  public T save() {
    if (getId() > 0) {
      getMapper().update((T) this);
    } else {
      getMapper().insert((T) this);
    }

    return (T) this;
  }

  /**
   * Delete the persisted object from the database.
   * After deletion, the id of the object will be set to 0.
   *
   * @return true if the number of affected rows in the database was > 0
   */
  public boolean delete() {
    if (getMapper().delete(getId()) > 0) {
      setId(0);
    }

    return false;
  }

  /**
   * Get the latest version of the persisted object from the database.
   * @return the latest version of the persisted object, according to the database
   * (may return null, if the object has been deleted after the current instance has been fetched last).
   */
  public T getLatestVersion() {
    return getMapper().get(getId());
  }

  protected abstract PersistableObjectMapper<T> getMapper();

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !this.getClass().isAssignableFrom(obj.getClass())) {
      return false;
    }

    return getId() == this.getClass().cast(obj).getId();
  }

  @Override
  public int hashCode() {
    return Long.hashCode(getId());
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[id=" + getId() + "]";
  }

}
