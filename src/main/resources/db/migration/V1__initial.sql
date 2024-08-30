CREATE TABLE public.role
(
    id bigint NOT NULL,
    name character varying(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.course_status
(
    id bigint NOT NULL,
    status_name character varying NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE public.country
(
    id serial NOT NULL,
    country_name character varying(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.usr(
    id serial NOT NULL,
    username character varying(100) NOT NULL UNIQUE,
    password character varying(100) NOT NULL,
    email character varying(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE public.users_roles
(
	id serial PRIMARY KEY,
	user_id bigint NOT NULL,
	role_id bigint NOT NULL,
	CONSTRAINT uq_user_role UNIQUE(user_id,role_id),
	CONSTRAINT user_fk FOREIGN KEY(user_id)
	REFERENCES usr(id),
	CONSTRAINT role_fk FOREIGN KEY(role_id)
	REFERENCES role(id)
);

CREATE TABLE public.person
(
	id serial NOT NULL PRIMARY KEY,
	 name character varying(100) NOT NULL,
    country_id bigint,
    user_id bigint NOT NULL UNIQUE,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id)
    	REFERENCES public.usr (id),
    CONSTRAINT country_id_fk FOREIGN KEY (country_id)
        REFERENCES public.country (id)
);

CREATE TABLE public.course
(
    id serial NOT NULL,
    title character varying(100) NOT NULL,
    status_id bigint NOT NULL,
    price double precision NOT NULL DEFAULT 0,
    instructor_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT course_status_id_fk FOREIGN KEY (status_id)
        REFERENCES public.course_status (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT course_instructor_identity_fk FOREIGN KEY (instructor_id)
	    REFERENCES public.person (id) MATCH SIMPLE
	    ON UPDATE NO ACTION
	    ON DELETE NO ACTION
	    NOT VALID
);
CREATE TABLE public.payment_method
(
id serial primary key,
name varchar(50) not null unique
);

CREATE TABLE public.payment
(
id serial PRIMARY KEY ,
pay_method_id bigint not null ,
pay_amount double precision not null,
transaction_id TEXT not null DEFAULT('Not_Specified'),
pay_status int not null,
CONSTRAINT payment_method_fk FOREIGN KEY(pay_method_id)
REFERENCES public.payment_method(id)
);

CREATE TABLE public.course_student
(
    id serial NOT NULL,
    course_id bigint NOT NULL,
    student_id bigint NOT NULL,
    pay_id bigint NOT NULL UNIQUE,
    enrollment_date DATE NOT NULL,
    is_confirmed BOOLEAN NOT NULL DEFAULT(false),
    PRIMARY KEY (id),
    CONSTRAINT uq_enrollment UNIQUE(course_id,student_id),
    CONSTRAINT course_id_foreign_key FOREIGN KEY (course_id)
        REFERENCES public.course (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT std_id_foreign_key FOREIGN KEY (student_id)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.lesson
(
    id serial NOT NULL,
    course_id bigint NOT NULL,
    title character varying(150) NOT NULL,
    file_path TEXT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT crs_id_fk FOREIGN KEY (course_id)
        REFERENCES public.course (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.attendance
(
    id serial NOT NULL,
    student_id bigint NOT NULL,
    lesson_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT std_id_foreignk FOREIGN KEY (student_id)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT lesson_id_fk FOREIGN KEY (lesson_id)
        REFERENCES public.lesson (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);
insert into course_status values(1,'public');