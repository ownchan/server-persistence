-- -----------------------------------------------------
-- Table `ocn_physical_content`
-- -----------------------------------------------------
CREATE TABLE `ocn_physical_content` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` SMALLINT(5) UNSIGNED NOT NULL,
  `status` SMALLINT(5) UNSIGNED NOT NULL,
  `status_reason` VARCHAR(255) NULL,
  `storage_folder_year` SMALLINT(4) UNSIGNED NOT NULL,
  `storage_folder_month` TINYINT(2) UNSIGNED NOT NULL,
  `storage_folder_day` TINYINT(2) UNSIGNED NOT NULL,
  `storage_folder_uuid` VARCHAR(36) NOT NULL,
  `content_checksum` VARCHAR(64) NULL COMMENT 'In case of uploads, this field should contain a checksum calculated from the physical file, while for linked contents, like youtube videos, the checksum might be calculated from the linked url.\nIdeally, we would have a UNIQUE constraint on this field, but the checksums will be calculated asynchronously by the worker instances.',
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `physical_content_type` VARCHAR(128) NULL,
  `external_content_link` VARCHAR(255) NULL,
  `additional_metadata` VARCHAR(4096) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = 'links a logical content to a storage directory with physical files, like preview images or thumbnails, or, in case of uploads, the original file';

CREATE UNIQUE INDEX `uuid_per_day_UNIQUE` ON `ocn_physical_content` (`storage_folder_year` ASC, `storage_folder_month` ASC, `storage_folder_day` ASC, `storage_folder_uuid` ASC);

CREATE INDEX `type_INDEX` ON `ocn_physical_content` (`type` ASC);

CREATE INDEX `status_INDEX` ON `ocn_physical_content` (`status` ASC);


-- -----------------------------------------------------
-- Table `ocn_content`
-- -----------------------------------------------------
CREATE TABLE `ocn_content` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `status` SMALLINT(5) UNSIGNED NOT NULL,
  `status_reason` VARCHAR(255) NULL,
  `parent_id` BIGINT(20) UNSIGNED NULL COMMENT 'only contents with no parents will be visible on general overview pages - the rest will only be availabe in detailed view of the direct parents they belong to',
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `caption` VARCHAR(128) NULL,
  `country_code` VARCHAR(2) NULL,
  `city_name` VARCHAR(128) NULL,
  `location` POINT NOT NULL COMMENT 'Unfortunately, spatially indexed columns in Mysql cannot be NULL, else this column would be nullable.\nAs a workaround, when we actually want to store NULL, we will store a pre-defined point with an unusual dummy-location from a WKT like \"POINT (0 90)\"  (lon/lat - WGS84)  instead.',
  `content_name` VARCHAR(255) NOT NULL COMMENT 'in case of image files this will be the filename, while for example in case of youtube videos it will be the youtube id (or the video title, if available)',
  `content_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `content_year` SMALLINT(4) UNSIGNED NOT NULL,
  `content_month` TINYINT(2) UNSIGNED NOT NULL,
  `content_day` TINYINT(2) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `physical_content_id` BIGINT(20) UNSIGNED NOT NULL,
  `user_clicks` BIGINT(20) UNSIGNED NOT NULL DEFAULT 0,
  `plus_count` BIGINT(20) UNSIGNED NOT NULL DEFAULT 0,
  `minus_count` BIGINT(20) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_content_parent`
    FOREIGN KEY (`parent_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_content_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_content_physical_content`
    FOREIGN KEY (`physical_content_id`)
    REFERENCES `ocn_physical_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `parent_INDEX` ON `ocn_content` (`parent_id` ASC);

CREATE INDEX `user_INDEX` ON `ocn_content` (`user_id` ASC);

CREATE INDEX `physical_content_FOREIGN_idx` ON `ocn_content` (`physical_content_id` ASC);

CREATE INDEX `user_and_status_INDEX` ON `ocn_content` (`user_id` ASC, `status` ASC);

CREATE SPATIAL INDEX `location_INDEX` ON `ocn_content` (`location`);


-- -----------------------------------------------------
-- Table `ocn_user`
-- -----------------------------------------------------
CREATE TABLE `ocn_user` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `alias` VARCHAR(24) NOT NULL,
  `display_name` VARCHAR(32) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `status` SMALLINT(5) UNSIGNED NOT NULL,
  `status_reason` VARCHAR(255) NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `email` VARCHAR(255) NULL,
  `motto` VARCHAR(128) NULL,
  `external_link` VARCHAR(255) NULL,
  `avatar_content_id` BIGINT(20) UNSIGNED NULL,
  `last_password_change_time` TIMESTAMP(2) NULL DEFAULT NULL,
  `beacon_time` TIMESTAMP(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_avatar_content`
    FOREIGN KEY (`avatar_content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE UNIQUE INDEX `alias_UNIQUE` ON `ocn_user` (`alias` ASC);

CREATE INDEX `status_INDEX` ON `ocn_user` (`status` ASC);

CREATE INDEX `avatar_content_FOREIGN_idx` ON `ocn_user` (`avatar_content_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_msg`
-- -----------------------------------------------------
CREATE TABLE `ocn_msg` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(128) NOT NULL,
  `type` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'can be used to differentiate between SYSTEM messages and CUSTOM messages',
  `show_html_editor` TINYINT(1) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `msg_en` LONGTEXT NOT NULL,
  `msg_de` LONGTEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `type_INDEX` ON `ocn_msg` (`type` ASC);

CREATE UNIQUE INDEX `code_UNIQUE` ON `ocn_msg` (`code` ASC);


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
  CONSTRAINT `fk_privilege_msg_name`
    FOREIGN KEY (`msg_id_name`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_privilege_msg_description`
    FOREIGN KEY (`msg_id_description`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_role_msg_name`
    FOREIGN KEY (`msg_id_name`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_msg_description`
    FOREIGN KEY (`msg_id_description`)
    REFERENCES `ocn_privilege` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_user_to_role_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_to_role_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `ocn_role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_user_ban_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_ban_ban_initiator`
    FOREIGN KEY (`ban_initiator_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_role_to_privilege_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `ocn_role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_role_to_privilege_privilege`
    FOREIGN KEY (`privilege_id`)
    REFERENCES `ocn_privilege` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `role_INDEX` ON `ocn_role_to_privilege` (`role_id` ASC);

CREATE INDEX `privilege_INDEX` ON `ocn_role_to_privilege` (`privilege_id` ASC);

CREATE UNIQUE INDEX `role_privilege_UNIQUE` ON `ocn_role_to_privilege` (`role_id` ASC, `privilege_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_setting`
-- -----------------------------------------------------
CREATE TABLE `ocn_setting` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  `type` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'can be used to differentiate page settings and role-based settings',
  `msg_id_name` BIGINT(20) UNSIGNED NOT NULL,
  `msg_id_description` BIGINT(20) UNSIGNED NOT NULL,
  `constrained` TINYINT(1) UNSIGNED NOT NULL,
  `value_type` SMALLINT(5) UNSIGNED NOT NULL,
  `default_value` VARCHAR(255) NULL,
  `min_value` VARCHAR(255) NULL,
  `max_value` VARCHAR(255) NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_setting_msg_name`
    FOREIGN KEY (`msg_id_name`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_setting_msg_description`
    FOREIGN KEY (`msg_id_description`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_setting_choice_setting`
    FOREIGN KEY (`setting_id`)
    REFERENCES `ocn_setting` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_setting_choice_msg_name`
    FOREIGN KEY (`msg_id_name`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_setting_choice_msg_description`
    FOREIGN KEY (`msg_id_description`)
    REFERENCES `ocn_msg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_content_comment_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_content_comment_content`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_content_comment_parent`
    FOREIGN KEY (`parent_id`)
    REFERENCES `ocn_content_comment` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_content_comment_embedded_content`
    FOREIGN KEY (`embedded_content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_user_to_setting_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_to_setting_setting`
    FOREIGN KEY (`setting_id`)
    REFERENCES `ocn_setting` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_to_setting_choice`
    FOREIGN KEY (`choice_id`)
    REFERENCES `ocn_setting_choice` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `setting_FOREIGN_idx` ON `ocn_user_to_setting` (`setting_id` ASC);

CREATE INDEX `choice_FOREIGN_idx` ON `ocn_user_to_setting` (`choice_id` ASC);

CREATE UNIQUE INDEX `user_setting_UNIQUE` ON `ocn_user_to_setting` (`user_id` ASC, `setting_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_system_setting`
-- -----------------------------------------------------
CREATE TABLE `ocn_system_setting` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `setting_id` BIGINT(20) UNSIGNED NOT NULL,
  `choice_id` BIGINT(20) UNSIGNED NULL,
  `custom_value` VARCHAR(255) NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user_id` BIGINT(20) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_system_setting_setting`
    FOREIGN KEY (`setting_id`)
    REFERENCES `ocn_setting` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_system_setting_choice`
    FOREIGN KEY (`choice_id`)
    REFERENCES `ocn_setting_choice` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_system_setting_update_user`
    FOREIGN KEY (`update_user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `choice_FOREIGN_idx` ON `ocn_system_setting` (`choice_id` ASC);

CREATE INDEX `update_user_FOREIGN_idx` ON `ocn_system_setting` (`update_user_id` ASC);

CREATE UNIQUE INDEX `setting_UNIQUE` ON `ocn_system_setting` (`setting_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_role_to_allowed_setting`
-- -----------------------------------------------------
CREATE TABLE `ocn_role_to_allowed_setting` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` BIGINT(20) UNSIGNED NOT NULL,
  `setting_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_role_to_allowed_setting_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `ocn_role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_role_to_allowed_setting_setting`
    FOREIGN KEY (`setting_id`)
    REFERENCES `ocn_setting` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_private_label_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_content_to_private_label_content`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_content_to_private_label_private_label`
    FOREIGN KEY (`private_label_id`)
    REFERENCES `ocn_private_label` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_label_creator`
    FOREIGN KEY (`creator_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_content_to_creator_label_content`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_content_to_creator_label_label`
    FOREIGN KEY (`label_id`)
    REFERENCES `ocn_label` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `label_FOREIGN_idx` ON `ocn_content_to_creator_label` (`label_id` ASC);

CREATE UNIQUE INDEX `content_label_UNIQUE` ON `ocn_content_to_creator_label` (`content_id` ASC, `label_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_cloud_label`
-- -----------------------------------------------------
CREATE TABLE `ocn_cloud_label` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(48) NOT NULL,
  `status` SMALLINT(5) UNSIGNED NOT NULL COMMENT 'might be used to determine labels that need to be translated from the output language of the cloud service provider to the desired language of the ownchan instance',
  `status_reason` VARCHAR(255) NULL,
  `initial_text` VARCHAR(48) NOT NULL COMMENT 'the initial label as it has been provided by the cloud service provider',
  `cloud_provider` SMALLINT(5) UNSIGNED NOT NULL,
  `cloud_provider_label_id` VARCHAR(36) NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user_id` BIGINT(20) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cloud_label_update_user`
    FOREIGN KEY (`update_user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE UNIQUE INDEX `provider_label_id_UNIQUE` ON `ocn_cloud_label` (`cloud_provider` ASC, `cloud_provider_label_id` ASC);

CREATE INDEX `status_INDEX` ON `ocn_cloud_label` (`status` ASC);

CREATE INDEX `text_INDEX` ON `ocn_cloud_label` (`text` ASC);

CREATE INDEX `update_user_FOREIGN_idx` ON `ocn_cloud_label` (`update_user_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_physical_content_to_community_label`
-- -----------------------------------------------------
CREATE TABLE `ocn_physical_content_to_community_label` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `physical_content_id` BIGINT(20) UNSIGNED NOT NULL,
  `label_id` BIGINT(20) UNSIGNED NOT NULL,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_phyco_to_cola_physical_content`
    FOREIGN KEY (`physical_content_id`)
    REFERENCES `ocn_physical_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_phyco_to_cola_label`
    FOREIGN KEY (`label_id`)
    REFERENCES `ocn_label` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_phyco_to_cola_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `label_FOREIGN_idx` ON `ocn_physical_content_to_community_label` (`label_id` ASC);

CREATE INDEX `user_FOREIGN_idx` ON `ocn_physical_content_to_community_label` (`user_id` ASC);

CREATE UNIQUE INDEX `physical_content_label_UNIQUE` ON `ocn_physical_content_to_community_label` (`physical_content_id` ASC, `label_id` ASC);

CREATE UNIQUE INDEX `physical_content_user_UNIQUE` ON `ocn_physical_content_to_community_label` (`physical_content_id` ASC, `user_id` ASC)  COMMENT 'one label per user for a content created by a different user should be enough';


-- -----------------------------------------------------
-- Table `ocn_physical_content_to_cloud_label`
-- -----------------------------------------------------
CREATE TABLE `ocn_physical_content_to_cloud_label` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `physical_content_id` BIGINT(20) UNSIGNED NOT NULL,
  `cloud_label_id` BIGINT(20) UNSIGNED NOT NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_phyco_to_cloud_label_physical_content`
    FOREIGN KEY (`physical_content_id`)
    REFERENCES `ocn_physical_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_phyco_to_clouad_label_cloud_label`
    FOREIGN KEY (`cloud_label_id`)
    REFERENCES `ocn_cloud_label` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE UNIQUE INDEX `physical_content_cloud_label_UNIQUE` ON `ocn_physical_content_to_cloud_label` (`physical_content_id` ASC, `cloud_label_id` ASC);

CREATE INDEX `cloud_label_FOREIGN_idx` ON `ocn_physical_content_to_cloud_label` (`cloud_label_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_user_to_follower`
-- -----------------------------------------------------
CREATE TABLE `ocn_user_to_follower` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `follower_id` BIGINT(20) UNSIGNED NOT NULL,
  `following_start_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_to_follower_follower`
    FOREIGN KEY (`follower_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_to_follower_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

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
  CONSTRAINT `fk_user_to_favco_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_to_favco_content`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `content_FOREIGN_idx` ON `ocn_user_to_favorite_content` (`content_id` ASC);

CREATE UNIQUE INDEX `user_content_UNIQUE` ON `ocn_user_to_favorite_content` (`user_id` ASC, `content_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_content_abuse`
-- -----------------------------------------------------
CREATE TABLE `ocn_content_abuse` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content_id` BIGINT(20) UNSIGNED NOT NULL,
  `violation_type` SMALLINT(5) UNSIGNED NOT NULL,
  `explanation` VARCHAR(4096) NULL,
  `complaining_entity_ip` VARCHAR(45) NOT NULL,
  `complaining_entity_contact` VARCHAR(255) NULL,
  `complaining_entity_user_id` BIGINT(20) UNSIGNED NULL,
  `status` SMALLINT(5) UNSIGNED NOT NULL,
  `status_reason` VARCHAR(255) NULL,
  `assignee_id` BIGINT(20) UNSIGNED NULL,
  `team_notes` VARCHAR(4096) NULL,
  `create_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP(2) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_content_abuse_content`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_content_abuse_assignee`
    FOREIGN KEY (`assignee_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_content_abuse_complaining_entity_user`
    FOREIGN KEY (`complaining_entity_user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `content_FOREIGN_idx` ON `ocn_content_abuse` (`content_id` ASC);

CREATE INDEX `assignee_FOREIGN_idx` ON `ocn_content_abuse` (`assignee_id` ASC);

CREATE INDEX `complaining_entity_user_FOREIGN_idx` ON `ocn_content_abuse` (`complaining_entity_user_id` ASC);

CREATE INDEX `status_and_create_time_INDEX` ON `ocn_content_abuse` (`status` ASC, `create_time` ASC);


-- -----------------------------------------------------
-- Table `ocn_user_to_content_voting_cache`
-- -----------------------------------------------------
CREATE TABLE `ocn_user_to_content_voting_cache` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `content_id` BIGINT(20) UNSIGNED NOT NULL,
  `voting_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_us_co_voting_cache_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_us_co_voting_cache_content`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = 'This table can be used to remember, for only a couple of hours, which contents have voted for by which users.\nIt is not intended for permanent storage of voting data, but rather as a means to  improve the accuracy of a content\'s voting count.\nA cronjob needs to delete all stored voting-data after X hours, which means after X hours a user\'s repeated voting for a content will be counted again.';

CREATE UNIQUE INDEX `user_content_UNIQUE` ON `ocn_user_to_content_voting_cache` (`user_id` ASC, `content_id` ASC);

CREATE INDEX `fk_us_co_voting_cache_content_idx` ON `ocn_user_to_content_voting_cache` (`content_id` ASC);

CREATE INDEX `voting_time_INDEX` ON `ocn_user_to_content_voting_cache` (`voting_time` ASC);


-- -----------------------------------------------------
-- Table `ocn_user_to_content_click_cache`
-- -----------------------------------------------------
CREATE TABLE `ocn_user_to_content_click_cache` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `content_id` BIGINT(20) UNSIGNED NOT NULL,
  `click_time` TIMESTAMP(2) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_us_co_click_cache_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_us_co_click_cache_content`
    FOREIGN KEY (`content_id`)
    REFERENCES `ocn_content` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = 'This table can be used to remember, for only a couple of hours, which contents have been click by which users.\nIt is not intended for permanent storage of click data, but rather as a means to  improve the accuracy of a content\'s click count.\nA cronjob needs to delete all stored click-data after X hours, which means after X hours a user\'s repeated click on a content will be counted again.';

CREATE UNIQUE INDEX `user_content_UNIQUE` ON `ocn_user_to_content_click_cache` (`user_id` ASC, `content_id` ASC);

CREATE INDEX `click_time_INDEX` ON `ocn_user_to_content_click_cache` (`click_time` ASC);

CREATE INDEX `fk_us_co_click_cache_content_idx` ON `ocn_user_to_content_click_cache` (`content_id` ASC);


-- -----------------------------------------------------
-- Table `ocn_rtc_conversation`
-- -----------------------------------------------------
CREATE TABLE `ocn_rtc_conversation` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_a_id` BIGINT(20) UNSIGNED NOT NULL,
  `user_b_id` BIGINT(20) UNSIGNED NOT NULL,
  `user_a_beacon_time` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_b_beacon_time` TIMESTAMP(2) NULL DEFAULT NULL,
  `user_a_offer` VARCHAR(1024) NOT NULL,
  `user_b_answer` VARCHAR(1024) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_rtc_conversation_user_a`
    FOREIGN KEY (`user_a_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_rtc_conversation_user_b`
    FOREIGN KEY (`user_b_id`)
    REFERENCES `ocn_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `users_and_beacon_times_INDEX` ON `ocn_rtc_conversation` (`user_a_id` ASC, `user_b_id` ASC, `user_a_beacon_time` ASC, `user_b_beacon_time` ASC);

CREATE UNIQUE INDEX `users_UNIQUE` ON `ocn_rtc_conversation` (`user_a_id` ASC, `user_b_id` ASC);

CREATE INDEX `fk_rtc_conversation_user_b_idx` ON `ocn_rtc_conversation` (`user_b_id` ASC);
