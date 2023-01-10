CREATE TABLE `categories` (
                              `id_categories` bigint NOT NULL AUTO_INCREMENT,
                              `name` varchar(100) DEFAULT NULL,
                              `description` varchar(100) DEFAULT NULL,
                              PRIMARY KEY (`id_categories`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `products` (
                            `id_products` bigint NOT NULL AUTO_INCREMENT,
                            `name` varchar(100) DEFAULT NULL,
                            `description` varchar(100) DEFAULT NULL,
                            `price` double DEFAULT NULL,
                            `id_categories` bigint DEFAULT NULL,
                            PRIMARY KEY (`id_products`),
                            KEY `id_categories` (`id_categories`),
                            CONSTRAINT `products_ibfk_1` FOREIGN KEY (`id_categories`) REFERENCES `categories` (`id_categories`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;