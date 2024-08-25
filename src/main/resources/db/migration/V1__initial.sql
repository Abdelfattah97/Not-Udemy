CREATE TABLE public.user_type
(
    id bigint NOT NULL,
    type_name character varying(20) NOT NULL,
    PRIMARY KEY (id)
);

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
    username character varying(100) NOT NULL,
    password character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    user_type_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT user_type_id_fk FOREIGN KEY (user_type_id)
        REFERENCES public.user_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.student
(
    id serial NOT NULL,
    name character varying(100) NOT NULL,
    country_id bigint,
    user_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT country_id_fk FOREIGN KEY (country_id)
        REFERENCES public.country (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);


CREATE TABLE public.instructor
(
    id serial NOT NULL,
    name character varying(100) NOT NULL,
    country_id bigint,
    user_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT instructor_user_id_fk FOREIGN KEY (user_id)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT instructor_country_id_fk FOREIGN KEY (country_id)
        REFERENCES public.country (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.course
(
    id serial NOT NULL,
    title character varying(100) NOT NULL,
    status_id bigint NOT NULL,
    price double precision NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT course_status_id_fk FOREIGN KEY (status_id)
        REFERENCES public.course_status (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.course_instructor
(
    id serial NOT NULL,
    course_id bigint NOT NULL,
    instructor_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT course_id_fk FOREIGN KEY (course_id)
        REFERENCES public.course (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT inst_id_fk FOREIGN KEY (instructor_id)
        REFERENCES public.instructor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.course_student
(
    id serial NOT NULL,
    course_id bigint NOT NULL,
    student_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT course_id_foreign_key FOREIGN KEY (course_id)
        REFERENCES public.course (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT std_id_foreign_key FOREIGN KEY (student_id)
        REFERENCES public.student (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.course_class
(
    id serial NOT NULL,
    course_id bigint NOT NULL,
    class_content character varying(150) NOT NULL,
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
    class_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT std_id_foreignk FOREIGN KEY (student_id)
        REFERENCES public.student (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT class_id_fk FOREIGN KEY (class_id)
        REFERENCES public.course_class (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);