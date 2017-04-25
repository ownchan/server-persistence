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
package org.ownchan.server.persistence.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class StaticContextAccessor {

  private static StaticContextAccessor instance;

  @Autowired
  private ApplicationContext applicationContext;

  @PostConstruct
  public void registerInstance() {
    instance = this;
  }

  public static <T> T getBean(Class<T> requiredType) {
    return instance.applicationContext.getBean(requiredType);
  }

  public static <T> T getBean(String name, Class<T> requiredType) {
    return instance.applicationContext.getBean(name, requiredType);
  }

}
