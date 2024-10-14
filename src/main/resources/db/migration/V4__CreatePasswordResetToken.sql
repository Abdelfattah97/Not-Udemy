CREATE TABLE password_reset_token (
id serial PRIMARY KEY ,
token varchar not null unique,
expire_date timestamp not null ,
use_date timestamp ,
user_id bigint not null unique,
CONSTRAINT user_fk FOREIGN KEY (user_id) 
REFERENCES usr(id) 
);