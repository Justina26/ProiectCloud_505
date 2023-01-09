CREATE TABLE `orders` (
                          `id_order` bigint NOT NULL AUTO_INCREMENT,
                          `username` varchar(60) DEFAULT NULL,
                          `id_shopping_carts` varchar(60) DEFAULT NULL,
                          `total_price` double DEFAULT NULL,
                          PRIMARY KEY (id_order),
                          KEY `id_shopping_carts` (`id_shopping_carts`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



