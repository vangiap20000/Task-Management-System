CREATE TABLE IF NOT EXISTS `task_labels` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_id` int NOT NULL,
  `label_id` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`task_id`) REFERENCES `tasks`(`id`),
  FOREIGN KEY (`label_id`) REFERENCES `labels`(`id`)
);
