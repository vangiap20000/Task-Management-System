CREATE TABLE IF NOT EXISTS `tasks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `due_date` date DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `status` tinyint DEFAULT '0' COMMENT '0: Not Started, 1: In Progress, 2: Completed',
  `priority` tinyint DEFAULT '1' COMMENT '0: Low, 1: Medium, 2: High',
  `category_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
  FOREIGN KEY (`category_id`) REFERENCES `categories`(`id`)
);