CREATE TABLE public.role
(
    id serial NOT NULL,
    name character varying(20) NOT NULL,
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
    status integer NOT NULL,
    price double precision NOT NULL DEFAULT 0,
    instructor_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT course_instructor_identity_fk FOREIGN KEY (instructor_id)
	    REFERENCES public.person (id) MATCH SIMPLE
	    ON UPDATE NO ACTION
	    ON DELETE NO ACTION
	    NOT VALID
);

CREATE TABLE public.payment
(
	id serial PRIMARY KEY ,
	pay_method integer  not null ,
	pay_amount Integer not null,
	currency integer not null,
	payment_status integer not null,
	product_type int not null,
	transaction_id varchar(250)not null,
	buyer_id bigint not null ,
	created_date timestamp not null,
	CONSTRAINT payment_buyer_fk FOREIGN KEY(buyer_id)
		REFERENCES person(id)
);

CREATE TABLE public.course_student
(
    id serial NOT NULL,
    course_id bigint NOT NULL,
    student_id bigint NOT NULL,
    pay_id bigint NOT NULL UNIQUE,
    enrollment_date DATE NOT NULL,
    enrollment_status integer NOT NULL,
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
    content_type integer NOT NULL,
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

--CREATE TABLE public.quiz
--(
--    id serial NOT NULL,
--    quiz_name character varying(100) NOT NULL,
--    course_id bigint NOT NULL,
--    PRIMARY KEY (id),
--    CONSTRAINT course_quiz_id_fk FOREIGN KEY (course_id)
--        REFERENCES public.course (id) MATCH SIMPLE
--        ON UPDATE NO ACTION
--        ON DELETE NO ACTION
--        NOT VALID
--);

CREATE TABLE public.question
(
    id serial NOT NULL,
    quiz_id bigint NOT NULL,
    content TEXT NOT NULL,
    answer_a TEXT NOT NULL,
    answer_b TEXT NOT NULL,
    answer_c TEXT NOT NULL,
    answer_d TEXT,
    correct_answer character(1) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT quiz_id_fk FOREIGN KEY (quiz_id)
        REFERENCES public.lesson (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

create table wallet (
	id serial primary key,
	transaction_amount INTEGER NOT NULL,
	person_id BIGINT NOT NULL,
	created_date TIMESTAMP NOT NULL,
	CONSTRAINT person_wallet_fk FOREIGN KEY (person_id)
    	REFERENCES public.person (id)
);
