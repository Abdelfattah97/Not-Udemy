create table authority(
	id serial primary key,
	name varchar(50) unique
);
create table roles_authorities(
	id serial PRIMARY KEY,
    role_id BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    constraint fk_role_id foreign key (role_id) references role(id),
    constraint fk_authority_id foreign key (authority_id) references authority(id),
    constraint fk_unique_role_authority unique (role_id, authority_id)
);
