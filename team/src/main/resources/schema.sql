USE sansam;
DROP TABLE IF EXISTS tbl_mentor_review;
DROP TABLE IF EXISTS tbl_chat_content;
DROP TABLE IF EXISTS tbl_user_review;
DROP TABLE IF EXISTS tbl_notice_alarm;
DROP TABLE IF EXISTS tbl_team_member_schedule_progress;
DROP TABLE IF EXISTS tbl_team_member;
DROP TABLE IF EXISTS tbl_team_schedule;
DROP TABLE IF EXISTS tbl_team_chat;
DROP TABLE IF EXISTS tbl_team;
DROP TABLE IF EXISTS tbl_project_apply_member;
DROP TABLE IF EXISTS tbl_project_member;
DROP TABLE IF EXISTS tbl_project_board;
DROP TABLE IF EXISTS tbl_project;
DROP TABLE IF EXISTS tbl_login_log;
DROP TABLE IF EXISTS tbl_user_github_repository;
DROP TABLE IF EXISTS tbl_user;
DROP TABLE IF EXISTS tbl_building_rule;



CREATE TABLE `tbl_user`
(
    `user_seq` bigint NOT NULL AUTO_INCREMENT,
    `user_major` varchar(30) NULL,
    `project_member_major_yn` ENUM('Y','N') NULL,
    `user_id` varchar(30) NOT NULL,
    `user_name` varchar(30) NOT NULL,
    `user_nickname` varchar(50) NOT NULL,
    `user_password` varchar(100) NOT NULL,
    `user_auth` ENUM('MANAGER', 'SUBMANAGER', 'MENTOR', 'MEMBER') NOT NULL,
    `user_phone` char(11) NULL,
    `user_email` varchar(30) NOT NULL,
    `user_birth_date` char(6) NULL,
    `user_gender` char(10) NOT NULL,
    `user_github_id` varchar(30) NULL,
    `user_profile_img` varchar(255) NULL,
    `user_status` ENUM('ACTIVE', 'BAN', 'DELETE') NULL,
    `user_pwd_mod_date` timestamp NULL,
    `user_ban_date` timestamp NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    `del_date` timestamp NULL,
    `user_career_years` bigint NULL,
    `user_career_months` bigint NULL,
    PRIMARY KEY (`user_seq`)

);

CREATE TABLE `tbl_login_log`
(
    `login_log_seq` bigint NOT NULL AUTO_INCREMENT,
    `user_seq` bigint NOT NULL,
    `login_log_code` varchar(30) NOT NULL,
    `login_log_reg_date` timestamp NOT NULL,
    `login_log_ip` varchar(15) NOT NULL,
    PRIMARY KEY (`login_log_seq`),
    CONSTRAINT `FK_TBL_LOGIN_LOG_USER_SEQ` FOREIGN KEY (`user_seq`) REFERENCES `tbl_user`(`user_seq`)
);

CREATE TABLE `tbl_project`
(
    `project_seq` bigint NOT NULL AUTO_INCREMENT,
    `project_admin_seq` bigint,
    `project_title` varchar(50) NOT NULL,
    `project_content` longtext NOT NULL,
    `project_status` ENUM('PROGRESS', 'END') NOT NULL,
    `project_head_count` int NOT NULL,
    `project_img_url` varchar(255) NULL,
    `project_start_date` timestamp NOT NULL,
    `project_end_date` timestamp NOT NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    `del_date` timestamp NULL,
    PRIMARY KEY (`project_seq`),
    CONSTRAINT `FK_TBL_PROJECT_USER_SEQ` FOREIGN KEY (`project_admin_seq`) REFERENCES `tbl_user`(`user_seq`)
);

CREATE TABLE `tbl_project_board`
(
    `project_board_seq` bigint NOT NULL AUTO_INCREMENT,
    `project_board_admin_seq` bigint NOT NULL,
    `project_board_title` varchar(50) NOT NULL,
    `project_board_content` longtext NOT NULL,
    `project_board_head_count` int NOT NULL,
    `project_board_img_url` varchar(255) NULL,
    `project_board_start_date` timestamp NOT NULL,
    `project_board_end_date` timestamp NOT NULL,
    `project_board_status` ENUM('RECRUITMENT', 'DEADLINE', 'DELETE') NULL,
    `project_start_date` timestamp NULL,
    `project_end_date` timestamp NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    `del_date` timestamp NULL,
    PRIMARY KEY (`project_board_seq`),
    CONSTRAINT `FK_TBL_PROJECT_BOARD_USER_SEQ` FOREIGN KEY (`project_board_admin_seq`) REFERENCES `tbl_user`(`user_seq`)
);

CREATE TABLE `tbl_project_member`
(
    `project_member_seq` bigint NOT NULL AUTO_INCREMENT,
    `project_seq` bigint NOT NULL,
    `user_seq` bigint NOT NULL,
    `project_member_del_yn` ENUM('Y', 'N') NOT NULL,
    `project_mentor_yn` ENUM('Y', 'N') NOT NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    `project_member_develop_type` ENUM('BACKEND', 'FRONTEND') NULL,
    `project_member_commit_score` bigint NULL,
    PRIMARY KEY (`project_member_seq`),
    CONSTRAINT `FK_TBL_PROJECT_MEMBER_PROJECT_SEQ` FOREIGN KEY (`project_seq`) REFERENCES `tbl_project`(`project_seq`),
    CONSTRAINT `FK_TBL_PROJECT_MEMBER_USER_SEQ` FOREIGN KEY (`user_seq`) REFERENCES `tbl_user`(`user_seq`)
);

CREATE TABLE `tbl_project_apply_member`
(
    `project_apply_member_seq` bigint NOT NULL AUTO_INCREMENT,
    `project_board_seq` bigint NOT NULL,
    `user_seq` bigint NOT NULL,
    `project_apply_member_status` ENUM('APPLIED', 'APPROVED', 'REJECTED') NOT NULL,
    `project_member_develop_type` ENUM('BACKEND', 'FRONTEND') NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    PRIMARY KEY (`project_apply_member_seq`),
    CONSTRAINT `FK_TBL_PROJECT_APPLY_MEMBER_PROJECT_SEQ` FOREIGN KEY (`project_board_seq`) REFERENCES `tbl_project_board`(`project_board_seq`),
    CONSTRAINT `FK_TBL_PROJECT_APPLY_MEMBER_USER_SEQ` FOREIGN KEY (`user_seq`) REFERENCES `tbl_user`(`user_seq`)
);

CREATE TABLE `tbl_building_rule`
(
    `rule_seq` bigint NOT NULL AUTO_INCREMENT,
    `rule_team_count` int NOT NULL,
    `rule_major_weight` int NULL,
    `rule_career_weight` int NULL,
    `rule_github_weight` int NULL,
    `rule_team_review_weight` int NULL,
    `rule_mentor_review_weight` int NULL,
    `rule_tech_stack_yn` CHAR(1) NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    PRIMARY KEY (`rule_seq`)
);

CREATE TABLE `tbl_team`
(
    `team_seq` bigint NOT NULL AUTO_INCREMENT,
    `project_seq` bigint NOT NULL,
    `team_name` varchar(255) NOT NULL,
    `team_status` ENUM('OPEN', 'CLOSE') NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    `end_date` timestamp NULL,
    PRIMARY KEY (`team_seq`),
    CONSTRAINT `FK_TBL_TEAM_PROJECT_SEQ` FOREIGN KEY (`project_seq`) REFERENCES `tbl_project`(`project_seq`)

);

CREATE TABLE `tbl_team_chat`
(
    `team_chat_seq` bigint NOT NULL AUTO_INCREMENT,
    `team_seq` bigint NOT NULL,
    `team_chat_name` varchar(50) NOT NULL,
    `team_chat_comment` varchar(255) NULL,
    `team_chat_active` char(1) NOT NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    `del_date` timestamp NULL,
    PRIMARY KEY (`team_chat_seq`),
    CONSTRAINT `FK_TBL_TEAM_CHAT_TEAM_SEQ` FOREIGN KEY (`team_seq`) REFERENCES `tbl_team`(`team_seq`)
);

CREATE TABLE `tbl_team_schedule`
(
    `team_schedule_seq` bigint NOT NULL AUTO_INCREMENT,
    `team_seq` bigint NOT NULL,
    `team_schedule_content` varchar(4000) NOT NULL,
    `team_schedule_start_date` timestamp NOT NULL,
    `team_schedule_end_date` timestamp NOT NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    PRIMARY KEY (`team_schedule_seq`),
    CONSTRAINT `FK_TBL_TEAM_SCHEDULE_TEAM_SEQ` FOREIGN KEY (`team_seq`) REFERENCES `tbl_team`(`team_seq`)
);

CREATE TABLE `tbl_team_member`
(
    `team_member_seq` bigint NOT NULL AUTO_INCREMENT,
    `team_seq` bigint NOT NULL,
    `user_seq` bigint NOT NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    PRIMARY KEY (`team_member_seq`),
    CONSTRAINT `FK_TBL_TEAM_MEMBER_TEAM_SEQ` FOREIGN KEY (`team_seq`) REFERENCES `tbl_team`(`team_seq`),
    CONSTRAINT `FK_TBL_TEAM_MEMBER_USER_SEQ` FOREIGN KEY (`user_seq`) REFERENCES `tbl_user`(`user_seq`)
);

CREATE TABLE `tbl_team_member_schedule_progress`
(
    `team_schedule_progress_seq` bigint NOT NULL AUTO_INCREMENT,
    `team_schedule_seq` bigint NOT NULL,
    `team_member_seq` bigint NOT NULL,
    `team_schedule_progress_content` varchar(1000) NOT NULL,
    `team_schedule_progress_percent` varchar(10),
    `team_schedule_progress_feedback` varchar(1000) NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    PRIMARY KEY (`team_schedule_progress_seq`),
    CONSTRAINT `FK_TBL_TEAM_MEMBER_SCHEDULE_PROGRESS_TEAM_SCHEDULE_SEQ` FOREIGN KEY (`team_schedule_seq`) REFERENCES `tbl_team_schedule`(`team_schedule_seq`),
    CONSTRAINT `FK_TBL_TEAM_MEMBER_SCHEDULE_PROGRESS_TEAM_MEMBER_SEQ` FOREIGN KEY (`team_member_seq`) REFERENCES `tbl_team_member`(`team_member_seq`)
);

CREATE TABLE `tbl_notice_alarm`
(
    `notice_alarm_seq` bigint NOT NULL AUTO_INCREMENT,
    `project_seq` bigint NOT NULL,
    `user_seq` bigint NOT NULL,
    `notice_alarm_title` varchar(50) NOT NULL,
    `notice_alarm_content` longtext NOT NULL,
    `notice_alarm_delete_yn` char(1) NULL,
    `reg_date` timestamp NOT NULL,
    `del_date` timestamp NULL,
    PRIMARY KEY (`notice_alarm_seq`),
    CONSTRAINT `FK_TBL_NOTICE_ALARM_PROJECT_SEQ` FOREIGN KEY (`project_seq`) REFERENCES `tbl_project`(`project_seq`),
    CONSTRAINT `FK_TBL_NOTICE_ALARM_USER_SEQ` FOREIGN KEY (`user_seq`) REFERENCES `tbl_user`(`user_seq`)
);

CREATE TABLE `tbl_user_review`
(
    `user_review_seq` bigint NOT NULL AUTO_INCREMENT,
    `send_team_member_seq` bigint NOT NULL,
    `receive_team_member_seq` bigint NOT NULL,
    `team_member_review_star` double NOT NULL,
    `team_member_review_content` varchar(255) NOT NULL,
    `reg_date` TIMESTAMP NOT NULL,
    `mod_date` timestamp NULL,
    PRIMARY KEY (`user_review_seq`),
    CONSTRAINT `FK_TBL_USER_REVIEW_SEND_TEAM_MEMBER_SEQ` FOREIGN KEY (`send_team_member_seq`) REFERENCES `tbl_team_member`(`team_member_seq`),
    CONSTRAINT `FK_TBL_USER_REVIEW_RECEIVE_TEAM_MEMBER_SEQ` FOREIGN KEY (`receive_team_member_seq`) REFERENCES `tbl_team_member`(`team_member_seq`)
);

CREATE TABLE `tbl_chat_content`
(
    `chat_content_seq` bigint NOT NULL AUTO_INCREMENT,
    `chat_room_seq` bigint NOT NULL,
    `team_member_seq` bigint NOT NULL,
    `chat_content_text` varchar(4000) NOT NULL,
    `chat_content_del_yn` char(1) NOT NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    `del_date` timestamp NULL,
    PRIMARY KEY (`chat_content_seq`),
    CONSTRAINT `FK_TBL_CHAT_CONTENT_TEAM_MEMBER_SEQ` FOREIGN KEY (`team_member_seq`) REFERENCES `tbl_team_member`(`team_member_seq`),
    CONSTRAINT `FK_TBL_CHAT_CONTENT_TEAM_CHAT_SEQ` FOREIGN KEY (`chat_room_seq`) REFERENCES `tbl_team_chat`(`team_chat_seq`)
);

CREATE TABLE `tbl_mentor_review`
(
    `mentor_review_seq` bigint NOT NULL AUTO_INCREMENT,
    `project_member_seq` bigint NOT NULL,
    `project_mentor_seq` bigint NOT NULL,
    `mentor_review_star` double NOT NULL,
    `mentor_review_content` longtext NOT NULL,
    `reg_date` timestamp NOT NULL,
    `mod_date` timestamp NULL,
    PRIMARY KEY (`mentor_review_seq`),
    CONSTRAINT `FK_TBL_MENTOR_REVIEW_PROJECT_MEMBER_SEQ` FOREIGN KEY (`project_member_seq`) REFERENCES `tbl_project_member`(`project_member_seq`),
    CONSTRAINT `FK_TBL_MENTOR_REVIEW_MENTOR_USER_SEQ` FOREIGN KEY (`project_mentor_seq`) REFERENCES `tbl_user`(`user_seq`)
);

CREATE TABLE `tbl_user_github_repository`
(
    `user_repository_seq` bigint NOT NULL AUTO_INCREMENT,
    `user_repository_url` longtext NOT NULL,
    `user_repository_name` varchar(255) NOT NULL,
    `develop_type` ENUM('BACKEND', 'FRONTEND') NOT NULL,
    `user_seq` bigint NOT NULL,
    PRIMARY KEY (`user_repository_seq`),
    CONSTRAINT `FK_TBL_USER_GITHUB_REPOSITORY_USER_SEQ` FOREIGN KEY (`user_seq`) REFERENCES `tbl_user`(`user_seq`)

);