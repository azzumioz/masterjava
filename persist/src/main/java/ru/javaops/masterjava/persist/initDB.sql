DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_flag;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');

CREATE TABLE users
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    full_name VARCHAR NOT NULL,
    email     VARCHAR NOT NULL,
    flag      user_flag NOT NULL,
    CONSTRAINT email_idx UNIQUE (email)
);

