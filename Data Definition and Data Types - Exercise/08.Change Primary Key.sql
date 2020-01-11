ALTER TABLE `users` MODIFY `id` INT NOT NULL;
ALTER TABLE `users` DROP PRIMARY KEY;
ALTER TABLE `users` ADD `pk_users` VARCHAR(50);

ALTER TABLE `users`
ADD CONSTRAINT `pk_users` PRIMARY KEY (`id`, `username`);
