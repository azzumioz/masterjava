DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;
DROP TYPE IF EXISTS user_flag;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');
CREATE TYPE group_type AS ENUM ('REGISTERING', 'FINISHED', 'CURRENT');

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE cities(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    ref       TEXT NOT NULL,
    name      TEXT NOT NULL
);

CREATE TABLE users(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    full_name TEXT      NOT NULL,
    email     TEXT      NOT NULL,
    flag      user_flag NOT NULL,
    city_id   INTEGER   NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities (id)
);
CREATE UNIQUE INDEX email_idx ON users (email);

CREATE TABLE projects(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name        TEXT NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE groups(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name       TEXT       NOT NULL,
    type       group_type NOT NULL,
    project_id INTEGER    NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects (id)
);

CREATE TABLE user_group (
    user_id INTEGER NOT NULL,
    group_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES groups(id),
    CONSTRAINT user_group_idx UNIQUE (user_id, group_id)
);
