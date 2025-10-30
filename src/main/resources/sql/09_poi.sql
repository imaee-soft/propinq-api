DROP TABLE IF EXISTS poi;
CREATE TABLE IF NOT EXISTS poi (
                                   id  CHAR(36)     NOT NULL PRIMARY KEY,
                                   type VARCHAR(64) NOT NULL,
                                   name VARCHAR(255) NULL,
                                   latitude  DOUBLE      NOT NULL,
                                   longitude  DOUBLE      NOT NULL,
                                   KEY idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;