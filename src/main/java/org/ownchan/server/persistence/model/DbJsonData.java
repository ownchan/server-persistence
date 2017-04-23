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

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.ownchan.server.persistence.typehandler.DbJsonDataTypeHandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Parent class for persistable JSON data.
 * This class is not abstract in order to avoid running into troubles
 * during deserialization in the {@link DbJsonDataTypeHandler}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_ABSENT, content = Include.NON_ABSENT)
public class DbJsonData {

  /*
   * We do not store the real fully qualified class names in the DB.
   * This allows to rename or move the classes without breaking something.
   */
  @JsonProperty("impl")
  private KnownImplementingBeanClass knownImplementingBeanClass;

  public KnownImplementingBeanClass getKnownImplementingBeanClass() {
    return knownImplementingBeanClass;
  }

  public void setKnownImplementingBeanClass(KnownImplementingBeanClass knownImplementingBeanClass) {
    this.knownImplementingBeanClass = knownImplementingBeanClass;
  }

  /*
   * Instances of this enum will not be persisted directly via Mybatis mapping,
   * but rather via Jackson serialization. Therefore implementation of the DbEnum interface
   * is just for convenience, but not necessary for Mybatis mapping magic.
   */
  public static enum KnownImplementingBeanClass implements DbEnum<KnownImplementingBeanClass> {
    PHYSICAL_CONTENT_METADATA_UPLOAD_IMAGE((short) 1, DbPhysicalContentMetadataUploadImage.class),
    PHYSICAL_CONTENT_METADATA_LINK_YOUTUBE((short) 2, DbPhysicalContentMetadataLinkYoutube.class);

    private short id;

    private String serializationName;

    private Class<? extends DbJsonData> implementingBeanClass;

    private KnownImplementingBeanClass(short id, Class<? extends DbJsonData> implementingBeanClass) {
      this.id = id;
      this.serializationName = "class_" + id;
      this.implementingBeanClass = implementingBeanClass;
    }

    public Class<? extends DbJsonData> getImplementingBeanClass() {
      return implementingBeanClass;
    }

    public void setImplementingBeanClass(Class<? extends DbJsonData> implementingBeanClass) {
      this.implementingBeanClass = implementingBeanClass;
    }

    /**
     * @throws IllegalArgumentException if the implementingBeanClass has not yet been mapped in {@link KnownImplementingBeanClass}
     */
    public static KnownImplementingBeanClass fromImplementingBeanClass(Class<? extends DbJsonData> implementingBeanClass) {
      return Stream.of(KnownImplementingBeanClass.values())
          .filter(mappedClassValue -> mappedClassValue.implementingBeanClass.equals(implementingBeanClass))
          .findAny()
          .orElseThrow(() -> new IllegalArgumentException("serialization mapping value missing for implementingBeanClass " + String.valueOf(implementingBeanClass)));
    }

    @Override
    public short getId() {
      return id;
    }

    @JsonValue
    public String getSerializationName() {
      return serializationName;
    }

    @JsonCreator
    public static KnownImplementingBeanClass forSerializationName(String serializationName) {
      if (StringUtils.isBlank(serializationName)) {
        return null;
      } else {
        return Stream.of(KnownImplementingBeanClass.values())
            .filter(knownClass -> knownClass.getSerializationName().equalsIgnoreCase(serializationName))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("could not find a matching enum constant for serializationName " + serializationName));
      }
    }

    /*
     * Jackson's MapSerializer will use toString() for serializing the enum instances for Map keys.
     * The annotation @JsonValue alone would not be sufficient for serializing the enum instances as Map keys.
     */
    @Override
    public String toString() {
      return getSerializationName();
    }

  }

}
