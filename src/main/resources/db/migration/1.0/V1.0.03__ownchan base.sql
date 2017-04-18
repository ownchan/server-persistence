-- -----------------------------------------------------
-- Table `ocn_content`
-- -----------------------------------------------------
CREATE TABLE `ocn_content` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `parent_id` BIGINT(20) UNSIGNED NULL COMMENT 'only uploads with no parents will be visible on general overview pages - the rest will only be availabe in detailed view of the parents they belong to',
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `caption` VARCHAR(128) NULL,
  `country_code` VARCHAR(2) NULL,
  `city_name` VARCHAR(128) NULL,
  `location` POINT NULL,
  `content_name` VARCHAR(255) NOT NULL COMMENT 'in case of image files this will be the filename, while for example in case of youtube videos it will be the youtube id (or the video title, if available)',
  `content_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `content_year` SMALLINT(4) UNSIGNED NOT NULL,
  `content_month` TINYINT(2) UNSIGNED NOT NULL,
  `content_day` TINYINT(2) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `content_storage_uuid` VARCHAR(36) NOT NULL,
  `external_content_link` VARCHAR(255) NULL,
  `physical_content_type` VARCHAR(128) NULL,
  `additional_metadata` VARCHAR(4096) NULL,
  `user_clicks` BIGINT(20) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `parent_FOREIGN`
    FOREIGN KEY (`parent_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `user_FOREIGN`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `parent_INDEX` ON `ocn_content` (`parent_id` ASC);

CREATE INDEX `user_INDEX` ON `ocn_content` (`user_id` ASC);

CREATE UNIQUE INDEX `uuid_per_day_UNIQUE` ON `ocn_content` (`content_year` ASC, `content_month` ASC, `content_day` ASC, `content_storage_uuid` ASC);


-- -----------------------------------------------------
-- Table `ocn_user`
-- -----------------------------------------------------
CREATE TABLE `ocn_user` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `alias` VARCHAR(24) NOT NULL,
  `display_name` VARCHAR(32) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NULL,
  `motto` VARCHAR(128) NULL,
  `external_link` VARCHAR(255) NULL,
  `avatar_content_id` BIGINT(20) UNSIGNED NULL,
  `last_password_change_time` TIMESTAMP(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `avatar_content_FOREIGN`
    FOREIGN KEY (`avatar_content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE UNIQUE INDEX `alias_UNIQUE` ON `ocn_user` (`alias` ASC);

CREATE INDEX `status_INDEX` ON `ocn_user` (`status` ASC);

CREATE INDEX `avatar_content_FOREIGN_idx` ON `ocn_user` (`avatar_content_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_msg`
-- -----------------------------------------------------
CREATE TABLE `ocn_msg` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  `type` VARCHAR(45) NOT NULL COMMENT 'can be used to differentiate between SYSTEM messages and CUSTOM messages',
  `show_html_editor` TINYINT(1) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `msg_en` LONGTEXT NOT NULL,
  `msg_de` LONGTEXT NULL,
  PRIMARY KEY (`id`))
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `type_INDEX` ON `ocn_msg` (`type` ASC);

CREATE UNIQUE INDEX `name_UNIQUE` ON `ocn_msg` (`name` ASC);


-- -----------------------------------------------------
-- Table `ocn_privilege`
-- -----------------------------------------------------
CREATE TABLE `ocn_privilege` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  `msg_id_name` BIGINT(20) UNSIGNED NOT NULL,
  `msg_id_description` BIGINT(20) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `msg_name_FOREIGN`
    FOREIGN KEY (`msg_id_name`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `msg_description_FOREIGN`
    FOREIGN KEY (`msg_id_description`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE UNIQUE INDEX `name_UNIQUE` ON `ocn_privilege` (`name` ASC);

CREATE INDEX `msg_name_INDEX` ON `ocn_privilege` (`msg_id_name` ASC);

CREATE INDEX `msg_description_INDEX` ON `ocn_privilege` (`msg_id_description` ASC);


-- -----------------------------------------------------
-- Table `ocn_role`
-- -----------------------------------------------------
CREATE TABLE `ocn_role` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  `msg_id_name` BIGINT(20) UNSIGNED NOT NULL,
  `msg_id_description` BIGINT(20) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `msg_name_FOREIGN`
    FOREIGN KEY (`msg_id_name`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `msg_description_FOREIGN`
    FOREIGN KEY (`msg_id_description`)
    REFERENCES `ocn_privilege` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE UNIQUE INDEX `name_UNIQUE` ON `ocn_role` (`name` ASC);

CREATE INDEX `msg_name_INDEX` ON `ocn_role` (`msg_id_name` ASC);

CREATE INDEX `msg_description_INDEX` ON `ocn_role` (`msg_id_description` ASC);


-- -----------------------------------------------------
-- Table `ocn_user_to_role`
-- -----------------------------------------------------
CREATE TABLE `ocn_user_to_role` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `role_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_FOREIGN`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `role_FOREIGN`
    FOREIGN KEY (`role_id`)
    REFERENCES `ocn_role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `user_INDEX` ON `ocn_user_to_role` (`user_id` ASC);

CREATE INDEX `role_INDEX` ON `ocn_user_to_role` (`role_id` ASC);

CREATE UNIQUE INDEX `user_role_UNIQE` ON `ocn_user_to_role` (`user_id` ASC, `role_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_user_ban`
-- -----------------------------------------------------
CREATE TABLE `ocn_user_ban` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `active` TINYINT(1) UNSIGNED NOT NULL,
  `ban_expiration_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ban_reason` VARCHAR(255) NOT NULL,
  `ban_initiator_id` BIGINT(20) UNSIGNED NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_FOREIGN`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `ban_initiator_FOREIGN`
    FOREIGN KEY (`ban_initiator_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `user_INDEX` ON `ocn_user_ban` (`user_id` ASC);

CREATE INDEX `ban_initiator_INDEX` ON `ocn_user_ban` (`ban_initiator_id` ASC);

CREATE INDEX `ban_expiration_INDEX` ON `ocn_user_ban` (`ban_expiration_time` ASC);

CREATE INDEX `active_and_expiration_INDEX` ON `ocn_user_ban` (`active` ASC, `ban_expiration_time` ASC);


-- -----------------------------------------------------
-- Table `ocn_role_to_privilege`
-- -----------------------------------------------------
CREATE TABLE `ocn_role_to_privilege` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` BIGINT(20) UNSIGNED NOT NULL,
  `privilege_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `role_FOREIGN`
    FOREIGN KEY (`role_id`)
    REFERENCES `ocn_role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `privilege_FOREIGN`
    FOREIGN KEY (`privilege_id`)
    REFERENCES `ocn_privilege` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `role_INDEX` ON `ocn_role_to_privilege` (`role_id` ASC);

CREATE INDEX `privilege_INDEX` ON `ocn_role_to_privilege` (`privilege_id` ASC);

CREATE UNIQUE INDEX `role_privilege_UNIQUE` ON `ocn_role_to_privilege` (`role_id` ASC, `privilege_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_setting`
-- -----------------------------------------------------
CREATE TABLE `ocn_setting` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  `type` VARCHAR(45) NOT NULL COMMENT 'can be used to differentiate between page settings and role-based settings',
  `msg_id_name` BIGINT(20) UNSIGNED NOT NULL,
  `msg_id_description` BIGINT(20) UNSIGNED NOT NULL,
  `constrained` TINYINT(1) UNSIGNED NOT NULL,
  `value_type` VARCHAR(45) NOT NULL,
  `default_value` VARCHAR(255) NULL,
  `min_value` VARCHAR(255) NULL,
  `max_value` VARCHAR(255) NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `msg_name_FOREIGN`
    FOREIGN KEY (`msg_id_name`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `msg_description_FOREIGN`
    FOREIGN KEY (`msg_id_description`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE UNIQUE INDEX `name_UNIQUE` ON `ocn_setting` (`name` ASC);

CREATE INDEX `msg_name_INDEX` ON `ocn_setting` (`msg_id_name` ASC);

CREATE INDEX `msg_description_INDEX` ON `ocn_setting` (`msg_id_description` ASC);

CREATE INDEX `type_INDEX` ON `ocn_setting` (`type` ASC);


-- -----------------------------------------------------
-- Table `ocn_setting_choice`
-- -----------------------------------------------------
CREATE TABLE `ocn_setting_choice` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `setting_id` BIGINT(20) UNSIGNED NOT NULL,
  `setting_value` VARCHAR(255) NULL,
  `msg_id_name` BIGINT(20) UNSIGNED NULL,
  `msg_id_description` BIGINT(20) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `setting_FOREIGN`
    FOREIGN KEY (`setting_id`)
    REFERENCES `ocn_setting` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `msg_name_FOREIGN`
    FOREIGN KEY (`msg_id_name`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `msg_description_FOREIGN`
    FOREIGN KEY (`msg_id_description`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `setting_INDEX` ON `ocn_setting_choice` (`setting_id` ASC);

CREATE INDEX `msg_name_INDEX` ON `ocn_setting_choice` (`msg_id_name` ASC);

CREATE INDEX `msg_description_INDEX` ON `ocn_setting_choice` (`msg_id_description` ASC);


-- -----------------------------------------------------
-- Table `ocn_content_comment`
-- -----------------------------------------------------
CREATE TABLE `ocn_content_comment` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` BIGINT(20) UNSIGNED NULL,
  `text` VARCHAR(4096) NOT NULL,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `content_id` BIGINT(20) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `embedded_content_id` BIGINT(20) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_FOREIGN`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `content_FOREIGN`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `parent_FOREIGN`
    FOREIGN KEY (`parent_id`)
    REFERENCES `ocn_content_comment` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `embedded_content_FOREIGN`
    FOREIGN KEY (`embedded_content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `user_INDEX` ON `ocn_content_comment` (`user_id` ASC);

CREATE INDEX `upload_INDEX` ON `ocn_content_comment` (`content_id` ASC);

CREATE INDEX `parent_INDEX` ON `ocn_content_comment` (`parent_id` ASC);

CREATE INDEX `embedded_content_FOREIGN_idx` ON `ocn_content_comment` (`embedded_content_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_user_to_setting`
-- -----------------------------------------------------
CREATE TABLE `ocn_user_to_setting` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `setting_id` BIGINT(20) UNSIGNED NOT NULL,
  `choice_id` BIGINT(20) UNSIGNED NULL,
  `custom_value` VARCHAR(255) NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_FOREIGN`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `setting_FOREIGN`
    FOREIGN KEY (`setting_id`)
    REFERENCES `ocn_setting` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `choice_FOREIGN`
    FOREIGN KEY (`choice_id`)
    REFERENCES `ocn_setting_choice` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `user_FOREIGN_idx` ON `ocn_user_to_setting` (`user_id` ASC);

CREATE INDEX `setting_FOREIGN_idx` ON `ocn_user_to_setting` (`setting_id` ASC);

CREATE INDEX `choice_FOREIGN_idx` ON `ocn_user_to_setting` (`choice_id` ASC);

CREATE UNIQUE INDEX `user_setting_UNIQUE` ON `ocn_user_to_setting` (`user_id` ASC, `setting_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_page_setting`
-- -----------------------------------------------------
CREATE TABLE `ocn_page_setting` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `setting_id` BIGINT(20) UNSIGNED NOT NULL,
  `choice_id` BIGINT(20) UNSIGNED NULL,
  `custom_value` VARCHAR(255) NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user_id` BIGINT(20) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `setting_FOREIGN`
    FOREIGN KEY (`setting_id`)
    REFERENCES `ocn_setting` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `choice_FOREIGN`
    FOREIGN KEY (`choice_id`)
    REFERENCES `ocn_setting_choice` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `update_user_FOREIGN`
    FOREIGN KEY (`update_user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `setting_FOREIGN_idx` ON `ocn_page_setting` (`setting_id` ASC);

CREATE INDEX `choice_FOREIGN_idx` ON `ocn_page_setting` (`choice_id` ASC);

CREATE INDEX `update_user_FOREIGN_idx` ON `ocn_page_setting` (`update_user_id` ASC);

CREATE UNIQUE INDEX `setting_UNIQUE` ON `ocn_page_setting` (`setting_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_role_to_allowed_setting`
-- -----------------------------------------------------
CREATE TABLE `ocn_role_to_allowed_setting` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` BIGINT(20) UNSIGNED NOT NULL,
  `setting_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `role_FOREIGN`
    FOREIGN KEY (`role_id`)
    REFERENCES `ocn_role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `setting_FOREIGN`
    FOREIGN KEY (`setting_id`)
    REFERENCES `ocn_setting` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `role_FOREIGN_idx` ON `ocn_role_to_allowed_setting` (`role_id` ASC);

CREATE INDEX `setting_FOREIGN_idx` ON `ocn_role_to_allowed_setting` (`setting_id` ASC);

CREATE UNIQUE INDEX `role_setting_UNIQUE` ON `ocn_role_to_allowed_setting` (`role_id` ASC, `setting_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_private_label`
-- -----------------------------------------------------
CREATE TABLE `ocn_private_label` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `text` VARCHAR(32) NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_FOREIGN`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `text_INDEX` ON `ocn_private_label` (`text` ASC);

CREATE INDEX `user_FOREIGN_idx` ON `ocn_private_label` (`user_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_content_to_private_label`
-- -----------------------------------------------------
CREATE TABLE `ocn_content_to_private_label` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content_id` BIGINT(20) UNSIGNED NOT NULL,
  `private_label_id` BIGINT(20) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `content_FOREIGN`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `private_label_FOREIGN`
    FOREIGN KEY (`private_label_id`)
    REFERENCES `ocn_private_label` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `content_FOREIGN_idx` ON `ocn_content_to_private_label` (`content_id` ASC);

CREATE INDEX `private_label_FOREIGN_idx` ON `ocn_content_to_private_label` (`private_label_id` ASC);

CREATE UNIQUE INDEX `content_private_label_UNIQUE` ON `ocn_content_to_private_label` (`content_id` ASC, `private_label_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_label`
-- -----------------------------------------------------
CREATE TABLE `ocn_label` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(24) NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `creator_id` BIGINT(20) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `creator_FOREIGN`
    FOREIGN KEY (`creator_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE UNIQUE INDEX `text_UNIQUE` ON `ocn_label` (`text` ASC);

CREATE INDEX `creator_FOREIGN_idx` ON `ocn_label` (`creator_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_content_to_creator_label`
-- -----------------------------------------------------
CREATE TABLE `ocn_content_to_creator_label` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content_id` BIGINT(20) UNSIGNED NOT NULL,
  `label_id` BIGINT(20) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `content_FOREIGN`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `label_FOREIGN`
    FOREIGN KEY (`label_id`)
    REFERENCES `ocn_label` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `content_FOREIGN_idx` ON `ocn_content_to_creator_label` (`content_id` ASC);

CREATE INDEX `label_FOREIGN_idx` ON `ocn_content_to_creator_label` (`label_id` ASC);

CREATE UNIQUE INDEX `content_label_UNIQUE` ON `ocn_content_to_creator_label` (`content_id` ASC, `label_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_cloud_label`
-- -----------------------------------------------------
CREATE TABLE `ocn_cloud_label` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(48) NOT NULL,
  `cloud_provider_name` VARCHAR(24) NOT NULL,
  `cloud_provider_label_id` VARCHAR(36) NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE UNIQUE INDEX `provider_name_label_id_UNIQUE` ON `ocn_cloud_label` (`cloud_provider_name` ASC, `cloud_provider_label_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_content_to_community_label`
-- -----------------------------------------------------
CREATE TABLE `ocn_content_to_community_label` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content_id` BIGINT(20) UNSIGNED NOT NULL,
  `label_id` BIGINT(20) UNSIGNED NOT NULL,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `content_FOREIGN`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `label_FOREIGN`
    FOREIGN KEY (`label_id`)
    REFERENCES `ocn_label` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `user_FOREIGN`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `content_FOREIGN_idx` ON `ocn_content_to_community_label` (`content_id` ASC);

CREATE INDEX `label_FOREIGN_idx` ON `ocn_content_to_community_label` (`label_id` ASC);

CREATE INDEX `user_FOREIGN_idx` ON `ocn_content_to_community_label` (`user_id` ASC);

CREATE UNIQUE INDEX `content_label_UNIQUE` ON `ocn_content_to_community_label` (`content_id` ASC, `label_id` ASC);

CREATE UNIQUE INDEX `content_user_UNIQUE` ON `ocn_content_to_community_label` (`content_id` ASC, `user_id` ASC)  COMMENT 'one label per user for a content created by a different user should be enough';


-- -----------------------------------------------------
-- Table `ocn_content_to_cloud_label`
-- -----------------------------------------------------
CREATE TABLE `ocn_content_to_cloud_label` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content_id` BIGINT(20) UNSIGNED NOT NULL,
  `cloud_label_id` BIGINT(20) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `content_FOREIGN`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `cloud_label_FOREIGN`
    FOREIGN KEY (`cloud_label_id`)
    REFERENCES `ocn_content_comment` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `content_FOREIGN_idx` ON `ocn_content_to_cloud_label` (`content_id` ASC);

CREATE INDEX `cloud_label_FOREIGN_idx` ON `ocn_content_to_cloud_label` (`cloud_label_id` ASC);

CREATE UNIQUE INDEX `content_cloud_label_UNIQUE` ON `ocn_content_to_cloud_label` (`content_id` ASC, `cloud_label_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_user_to_follower`
-- -----------------------------------------------------
CREATE TABLE `ocn_user_to_follower` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `follower_id` BIGINT(20) UNSIGNED NOT NULL,
  `following_start_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `follower_FOREIGN`
    FOREIGN KEY (`follower_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `user_FOREIGN`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE UNIQUE INDEX `user_follower_UNIQUE` ON `ocn_user_to_follower` (`user_id` ASC, `follower_id` ASC);

CREATE INDEX `follower_FOREIGN_idx` ON `ocn_user_to_follower` (`follower_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_user_to_favorite_content`
-- -----------------------------------------------------
CREATE TABLE `ocn_user_to_favorite_content` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `content_id` BIGINT(20) UNSIGNED NOT NULL,
  `fav_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_FOREIGN`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `content_FOREIGN`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `user_FOREIGN_idx` ON `ocn_user_to_favorite_content` (`user_id` ASC);

CREATE INDEX `content_FOREIGN_idx` ON `ocn_user_to_favorite_content` (`content_id` ASC);

CREATE UNIQUE INDEX `user_content_UNIQUE` ON `ocn_user_to_favorite_content` (`user_id` ASC, `content_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_content_abuse`
-- -----------------------------------------------------
CREATE TABLE `ocn_content_abuse` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content_id` BIGINT(20) UNSIGNED NOT NULL,
  `violation_type` VARCHAR(45) NOT NULL,
  `explanation` VARCHAR(4096) NULL,
  `complaining_entity_ip` VARCHAR(45) NOT NULL,
  `complaining_entity_contact` VARCHAR(255) NULL,
  `complaining_entity_user_id` BIGINT(20) UNSIGNED NULL,
  `status` VARCHAR(45) NOT NULL,
  `assignee_id` BIGINT(20) UNSIGNED NULL,
  `team_notes` VARCHAR(4096) NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `content_FOREIGN`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `assignee_FOREIGN`
    FOREIGN KEY (`assignee_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `complaining_entity_user_FOREIGN`
    FOREIGN KEY (`complaining_entity_user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
ENGINE = InnoDB;

CREATE INDEX `content_FOREIGN_idx` ON `ocn_content_abuse` (`content_id` ASC);

CREATE INDEX `assignee_FOREIGN_idx` ON `ocn_content_abuse` (`assignee_id` ASC);

CREATE INDEX `complaining_entity_user_FOREIGN_idx` ON `ocn_content_abuse` (`complaining_entity_user_id` ASC);
