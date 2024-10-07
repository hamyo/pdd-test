CREATE TABLESPACE pddtest
  OWNER postgres
  LOCATION C:\tablespaces\pddtest;

ALTER TABLESPACE pddtest
  OWNER TO postgres;
  
CREATE DATABASE pddtest
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pddtest
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE IF NOT EXISTS public.cls_data_type
(
    cdt_id smallint NOT NULL,
    cdt_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_cdt PRIMARY KEY (cdt_id)
        USING INDEX TABLESPACE pddtest
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.cls_data_type
    OWNER to postgres;
	

CREATE TABLE IF NOT EXISTS public.cls_question_type
(
    cqt_id smallint NOT NULL,
    cqt_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_cqt PRIMARY KEY (cqt_id)
        USING INDEX TABLESPACE pddtest
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.cls_question_type
    OWNER to postgres;
	

CREATE TABLE IF NOT EXISTS public.cls_role
(
    cr_id smallint NOT NULL,
    cr_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_cr PRIMARY KEY (cr_id)
        USING INDEX TABLESPACE pddtest
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.cls_role
    OWNER to postgres;
	
CREATE SEQUENCE IF NOT EXISTS public.person_p_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.person
(
    p_id integer NOT NULL DEFAULT nextval('person_p_id_seq'::regclass),
    p_lastname character varying(50) COLLATE pg_catalog."default" NOT NULL,
    p_name character varying(50) COLLATE pg_catalog."default",
    p_patronymic character varying(50) COLLATE pg_catalog."default",
    p_is_active boolean NOT NULL DEFAULT true,
    p_telegram_id bigint,
    CONSTRAINT pk_p PRIMARY KEY (p_id)
        USING INDEX TABLESPACE pddtest
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.person
    OWNER to postgres;

alter SEQUENCE public.person_p_id_seq OWNED BY person.p_id;

CREATE INDEX IF NOT EXISTS i_telegram_id
    ON public.person USING btree
    (p_telegram_id ASC NULLS LAST)
    WITH (deduplicate_items=True)
    TABLESPACE pddtest;
	
	
CREATE TABLE IF NOT EXISTS public.person_role
(
    p_id integer NOT NULL,
    cr_id smallint NOT NULL,
    CONSTRAINT pk_pr PRIMARY KEY (p_id, cr_id)
        USING INDEX TABLESPACE pddtest,
    CONSTRAINT fk_pr_cr FOREIGN KEY (cr_id)
        REFERENCES public.cls_role (cr_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_pr_p FOREIGN KEY (p_id)
        REFERENCES public.person (p_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.person_role
    OWNER to postgres;
	
CREATE SEQUENCE IF NOT EXISTS public.available_test_at_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.available_test_at_id_seq
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.available_test
(
    at_id integer NOT NULL DEFAULT nextval('available_test_at_id_seq'::regclass),
    at_name text COLLATE pg_catalog."default" NOT NULL,
    at_show_summary boolean NOT NULL DEFAULT true,
    at_until_end boolean NOT NULL DEFAULT true,
    at_show_after_answer boolean NOT NULL DEFAULT true,
    CONSTRAINT pk_at PRIMARY KEY (at_id)
        USING INDEX TABLESPACE pddtest
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.available_test
    OWNER to postgres;

ALTER SEQUENCE public.available_test_at_id_seq OWNED BY available_test.at_id;

	
CREATE SEQUENCE IF NOT EXISTS public.question_theme_qt_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.question_theme_qt_id_seq
    OWNER TO postgres;
	
CREATE TABLE IF NOT EXISTS public.question_theme
(
    qt_id integer NOT NULL DEFAULT nextval('question_theme_qt_id_seq'::regclass),
    qt_name character varying(150) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_qt PRIMARY KEY (qt_id)
        USING INDEX TABLESPACE pddtest
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.question_theme
    OWNER to postgres;

ALTER SEQUENCE public.question_theme_qt_id_seq OWNED BY question_theme.qt_id;

CREATE SEQUENCE IF NOT EXISTS public.question_q_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.question_q_id_seq
    OWNER TO postgres;
	
CREATE TABLE IF NOT EXISTS public.question
(
    q_id bigint NOT NULL DEFAULT nextval('question_q_id_seq'::regclass),
    q_description character varying(300) COLLATE pg_catalog."default",
    q_text text COLLATE pg_catalog."default",
    q_comment text COLLATE pg_catalog."default",
    cqt_id smallint,
    CONSTRAINT pk_q PRIMARY KEY (q_id)
        USING INDEX TABLESPACE pddtest,
    CONSTRAINT fk_q_cqt FOREIGN KEY (cqt_id)
        REFERENCES public.cls_question_type (cqt_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.question
    OWNER to postgres;

ALTER SEQUENCE public.question_q_id_seq OWNED BY question.q_id;

CREATE TABLE IF NOT EXISTS public.question_question_theme
(
    qt_id integer NOT NULL,
    q_id bigint NOT NULL,
    CONSTRAINT pk_qqt PRIMARY KEY (qt_id, q_id)
        USING INDEX TABLESPACE pddtest,
    CONSTRAINT fk_question_theme_question FOREIGN KEY (q_id)
        REFERENCES public.question (q_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fk_question_theme_theme FOREIGN KEY (qt_id)
        REFERENCES public.question_theme (qt_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.question_question_theme
    OWNER to postgres;

CREATE SEQUENCE IF NOT EXISTS public.question_data_qd_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.question_data_qd_id_seq
    OWNER TO postgres;
	
CREATE TABLE IF NOT EXISTS public.question_data
(
    qd_id bigint NOT NULL DEFAULT nextval('question_data_qd_id_seq'::regclass),
    cdt_id smallint NOT NULL,
    data bytea NOT NULL,
    q_id bigint,
    CONSTRAINT pk_qd PRIMARY KEY (qd_id)
        USING INDEX TABLESPACE pddtest,
    CONSTRAINT fk_qd_cdt FOREIGN KEY (cdt_id)
        REFERENCES public.cls_data_type (cdt_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_qd_q FOREIGN KEY (q_id)
        REFERENCES public.question (q_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
        NOT VALID
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.question_data
    OWNER to postgres;

ALTER SEQUENCE public.question_data_qd_id_seq OWNED BY question_data.qd_id;
-- Index: i_qd_q_id

-- DROP INDEX IF EXISTS public.i_qd_q_id;

CREATE INDEX IF NOT EXISTS i_qd_q_id
    ON public.question_data USING btree
    (q_id ASC NULLS LAST)
    WITH (deduplicate_items=True)
    TABLESPACE pddtest;
	

CREATE TABLE IF NOT EXISTS public.available_test_theme
(
    at_id integer NOT NULL,
    qt_id integer NOT NULL,
    att_question_count smallint NOT NULL,
    CONSTRAINT pk_att PRIMARY KEY (at_id, qt_id)
        USING INDEX TABLESPACE pddtest,
    CONSTRAINT fk_available_test_available FOREIGN KEY (at_id)
        REFERENCES public.available_test (at_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT fk_available_test_theme FOREIGN KEY (qt_id)
        REFERENCES public.question_theme (qt_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.available_test_theme
    OWNER to postgres;

CREATE SEQUENCE IF NOT EXISTS public.person_test_pt_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.person_test_pt_id_seq
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.person_test
(
    pt_id bigint NOT NULL DEFAULT nextval('person_test_pt_id_seq'::regclass),
    p_id integer NOT NULL,
    pt_start_date timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    pt_finish_date timestamp without time zone,
    pt_success smallint,
    pt_error smallint,
    at_id integer NOT NULL,
    CONSTRAINT pk_pt PRIMARY KEY (pt_id)
        USING INDEX TABLESPACE pddtest,
    CONSTRAINT fk_person_test_available_test FOREIGN KEY (at_id)
        REFERENCES public.available_test (at_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
        NOT VALID,
    CONSTRAINT fk_person_test_person FOREIGN KEY (p_id)
        REFERENCES public.person (p_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.person_test
    OWNER to postgres;

ALTER SEQUENCE public.person_test_pt_id_seq OWNED BY person_test.pt_id;
-- Index: i_pt_at_id

-- DROP INDEX IF EXISTS public.i_pt_at_id;

CREATE INDEX IF NOT EXISTS i_pt_at_id
    ON public.person_test USING btree
    (at_id ASC NULLS LAST)
    WITH (deduplicate_items=True)
    TABLESPACE pddtest;
-- Index: i_pt_p_id

-- DROP INDEX IF EXISTS public.i_pt_p_id;

CREATE INDEX IF NOT EXISTS i_pt_p_id
    ON public.person_test USING btree
    (p_id ASC NULLS LAST)
    WITH (deduplicate_items=True)
    TABLESPACE pddtest;
CREATE SEQUENCE IF NOT EXISTS public.person_test_question_ptq_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.person_test_question_ptq_id_seq
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.person_test_question
(
    ptq_id bigint NOT NULL DEFAULT nextval('person_test_question_ptq_id_seq'::regclass),
    pt_id bigint NOT NULL,
    q_id bigint NOT NULL,
    ptq_answer text COLLATE pg_catalog."default",
    ptq_is_correct boolean,
    CONSTRAINT pk_ptq PRIMARY KEY (ptq_id)
        USING INDEX TABLESPACE pddtest,
    CONSTRAINT fk_person_test_question_question FOREIGN KEY (q_id)
        REFERENCES public.question (q_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT,
    CONSTRAINT fk_person_test_question_test FOREIGN KEY (pt_id)
        REFERENCES public.person_test (pt_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
)

TABLESPACE pddtest;

ALTER TABLE IF EXISTS public.person_test_question
    OWNER to postgres;

ALTER SEQUENCE public.person_test_question_ptq_id_seq OWNED BY person_test_question.ptq_id;
-- Index: i_ptq_pt_id

-- DROP INDEX IF EXISTS public.i_ptq_pt_id;

CREATE INDEX IF NOT EXISTS i_ptq_pt_id
    ON public.person_test_question USING btree
    (pt_id ASC NULLS LAST)
    WITH (deduplicate_items=True)
    TABLESPACE pddtest;
-- Index: i_ptq_q_id

-- DROP INDEX IF EXISTS public.i_ptq_q_id;

CREATE INDEX IF NOT EXISTS i_ptq_q_id
    ON public.person_test_question USING btree
    (q_id ASC NULLS LAST)
    WITH (deduplicate_items=True)
    TABLESPACE pddtest;


CREATE SEQUENCE IF NOT EXISTS public.answer_a_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.answer_a_id_seq
    OWNER TO postgres;
	
CREATE TABLE IF NOT EXISTS public.answer
(
    a_id bigint NOT NULL DEFAULT nextval('answer_a_id_seq'::regclass),
    a_description text COLLATE pg_catalog."default",
    q_id bigint NOT NULL,
    a_is_right boolean NOT NULL DEFAULT false,
    CONSTRAINT pk_a PRIMARY KEY (a_id)
        USING INDEX TABLESPACE pddtest,
    CONSTRAINT fk_a_q FOREIGN KEY (q_id)
        REFERENCES public.question (q_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pddtest;

alter sequence public.answer_a_id_seq OWNED BY answer.a_id;

ALTER TABLE IF EXISTS public.answer
    OWNER to postgres;
-- Index: i_a_q_id

-- DROP INDEX IF EXISTS public.i_a_q_id;

CREATE INDEX IF NOT EXISTS i_a_q_id
    ON public.answer USING btree
    (q_id ASC NULLS LAST)
    WITH (deduplicate_items=True)
    TABLESPACE pddtest;