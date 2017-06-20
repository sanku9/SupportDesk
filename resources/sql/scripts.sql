CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 2157 (class 1262 OID 12135)
-- Dependencies: 2156
-- Name: postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2158 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 197 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2160 (class 0 OID 0)
-- Dependencies: 197
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 196 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 2161 (class 0 OID 0)
-- Dependencies: 196
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 172 (class 1259 OID 16397)
-- Name: tbl_application; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_application (
    appl_id bigint NOT NULL,
    appl_name character varying(100),
    appl_type character varying(100),
    appl_class_type integer
);


ALTER TABLE tbl_application OWNER TO admin;

--
-- TOC entry 173 (class 1259 OID 16400)
-- Name: tbl_application_appl_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE tbl_application_appl_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_application_appl_id_seq OWNER TO admin;

--
-- TOC entry 2162 (class 0 OID 0)
-- Dependencies: 173
-- Name: tbl_application_appl_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE tbl_application_appl_id_seq OWNED BY tbl_application.appl_id;


--
-- TOC entry 174 (class 1259 OID 16402)
-- Name: tbl_change_request; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_change_request (
    change_id character varying(100) NOT NULL,
    risk_level character varying(100),
    status character varying(100),
    summary character varying(1000),
    note text,
    priority character varying(50),
    scheduled_start_date timestamp with time zone,
    actual_start_date timestamp with time zone,
    scheduled_end_date timestamp with time zone,
    actual_end_date timestamp with time zone,
    requested_end_date timestamp with time zone,
    submit_date timestamp with time zone,
    completed_date timestamp with time zone,
    target_date timestamp with time zone,
    last_modified_date timestamp with time zone,
    change_coordinator character varying(200),
    change_manager character varying(200),
    change_coordinator_group bigint,
    manager_group character varying(200),
    request_id character varying(100),
    application_id bigint,
    total_time_spent integer,
    service_level character varying(100)
);


ALTER TABLE tbl_change_request OWNER TO admin;

--
-- TOC entry 175 (class 1259 OID 16408)
-- Name: tbl_class_type; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_class_type (
    class_id bigint NOT NULL,
    class_type character varying(500) NOT NULL,
    service_hour_start timestamp with time zone,
    service_hour_end timestamp with time zone
);


ALTER TABLE tbl_class_type OWNER TO admin;

--
-- TOC entry 176 (class 1259 OID 16414)
-- Name: tbl_class_type_class_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE tbl_class_type_class_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_class_type_class_id_seq OWNER TO admin;

--
-- TOC entry 2163 (class 0 OID 0)
-- Dependencies: 176
-- Name: tbl_class_type_class_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE tbl_class_type_class_id_seq OWNED BY tbl_class_type.class_id;


--
-- TOC entry 177 (class 1259 OID 16416)
-- Name: tbl_entity_comment; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_entity_comment (
    comment_id integer NOT NULL,
    entity_id character varying(100),
    comment text,
    user_id integer,
    created_date timestamp without time zone DEFAULT now()
);


ALTER TABLE tbl_entity_comment OWNER TO admin;

--
-- TOC entry 178 (class 1259 OID 16423)
-- Name: tbl_entity_comment_comment_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE tbl_entity_comment_comment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_entity_comment_comment_id_seq OWNER TO admin;

--
-- TOC entry 2164 (class 0 OID 0)
-- Dependencies: 178
-- Name: tbl_entity_comment_comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE tbl_entity_comment_comment_id_seq OWNED BY tbl_entity_comment.comment_id;


--
-- TOC entry 179 (class 1259 OID 16425)
-- Name: tbl_incident_slm; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_incident_slm (
    assigned_group integer NOT NULL,
    assigned_support_company character varying(200),
    assigned_support_organization character varying(100),
    closed_date timestamp without time zone,
    effort_duration_hours integer,
    escalated character varying(50),
    sla_breach_flag character varying(50),
    group_transfers integer,
    incident_id character varying(50) NOT NULL,
    individual_transfers integer,
    last_date_duration_calculated timestamp without time zone,
    last_modified_date timestamp without time zone,
    last_resolved_date timestamp without time zone,
    measurement_status_response character varying(100),
    measurement_status_resolve character varying(100),
    ola_hold character varying(100),
    overall_start_date timestamp without time zone,
    overall_end_date timestamp without time zone,
    owner_group integer NOT NULL,
    owner_support_company character varying(200),
    owner_support_organization character varying(100),
    priority character varying(20),
    application_id integer NOT NULL,
    reopened_date timestamp without time zone,
    reason_code character varying(100),
    reason_description character varying(1000),
    reported_date timestamp without time zone,
    resolution character varying(1000),
    responded_date timestamp without time zone,
    sla_hold character varying(20),
    slm_priority character varying(20),
    slm_response_status character varying(1000),
    slm_resolve_status character varying(1000),
    service_request_id character varying(100),
    incident_status character varying(50),
    submit_date timestamp without time zone,
    ticket_type character varying(100),
    response_effort_in_sec integer,
    resolve_effort_in_sec integer,
    assignee_name character varying(300),
    cust_first_name character varying(100),
    incident_summary character varying(1000),
    response_sla_due_date timestamp without time zone,
    resolve_sla_due_date timestamp without time zone,
    sla_breach_reason character varying(2000),
    comments text,
    reopened_count integer
);


ALTER TABLE tbl_incident_slm OWNER TO admin;

--
-- TOC entry 180 (class 1259 OID 16431)
-- Name: tbl_notify_email; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_notify_email (
    id integer NOT NULL,
    mailtype character varying(100),
    email character varying(500),
    tickettype character varying(100)
);


ALTER TABLE tbl_notify_email OWNER TO admin;

--
-- TOC entry 181 (class 1259 OID 16437)
-- Name: tbl_notify_email_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE tbl_notify_email_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_notify_email_id_seq OWNER TO admin;

--
-- TOC entry 2165 (class 0 OID 0)
-- Dependencies: 181
-- Name: tbl_notify_email_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE tbl_notify_email_id_seq OWNED BY tbl_notify_email.id;


--
-- TOC entry 182 (class 1259 OID 16439)
-- Name: tbl_queue; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_queue (
    queue_id bigint NOT NULL,
    queue_name character varying(500)
);


ALTER TABLE tbl_queue OWNER TO admin;

--
-- TOC entry 183 (class 1259 OID 16445)
-- Name: tbl_queue_queue_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE tbl_queue_queue_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_queue_queue_id_seq OWNER TO admin;

--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 183
-- Name: tbl_queue_queue_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE tbl_queue_queue_id_seq OWNED BY tbl_queue.queue_id;


--
-- TOC entry 184 (class 1259 OID 16447)
-- Name: tbl_sla; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_sla (
    class_type_id integer,
    priority character varying(10) NOT NULL,
    resolve_sla_hours double precision DEFAULT 0 NOT NULL,
    response_sla_hours double precision,
    support_hours character varying(10),
    sla_id bigint NOT NULL
);


ALTER TABLE tbl_sla OWNER TO admin;

--
-- TOC entry 185 (class 1259 OID 16451)
-- Name: tbl_sla_sla_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE tbl_sla_sla_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_sla_sla_id_seq OWNER TO admin;

--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 185
-- Name: tbl_sla_sla_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE tbl_sla_sla_id_seq OWNED BY tbl_sla.sla_id;


--
-- TOC entry 186 (class 1259 OID 16453)
-- Name: tbl_task; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_task (
    task_id character varying(100) NOT NULL,
    task text,
    status character varying(100),
    assignee character varying(500),
    created_by integer,
    created_date timestamp without time zone DEFAULT now(),
    updated_by integer,
    updated_date timestamp without time zone DEFAULT now(),
    start_date timestamp without time zone,
    end_date timestamp without time zone,
    effort_hours character varying(500)
);


ALTER TABLE tbl_task OWNER TO admin;

--
-- TOC entry 187 (class 1259 OID 16461)
-- Name: tbl_task_id; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tbl_task_id (
    id integer NOT NULL
);


ALTER TABLE tbl_task_id OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 16464)
-- Name: tbl_task_id_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_task_id_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_task_id_id_seq OWNER TO postgres;

--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 188
-- Name: tbl_task_id_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tbl_task_id_id_seq OWNED BY tbl_task_id.id;


--
-- TOC entry 189 (class 1259 OID 16466)
-- Name: tbl_topic; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_topic (
    topic_id integer NOT NULL,
    topic text,
    details text,
    user_id integer,
    created_date timestamp without time zone DEFAULT now(),
    modified_date timestamp without time zone DEFAULT now(),
    updated_by integer
);


ALTER TABLE tbl_topic OWNER TO admin;

--
-- TOC entry 190 (class 1259 OID 16474)
-- Name: tbl_topic_topic_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE tbl_topic_topic_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_topic_topic_id_seq OWNER TO admin;

--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 190
-- Name: tbl_topic_topic_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE tbl_topic_topic_id_seq OWNED BY tbl_topic.topic_id;


--
-- TOC entry 191 (class 1259 OID 16476)
-- Name: tbl_user; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_user (
    user_id bigint NOT NULL,
    username character varying(100) NOT NULL,
    password character varying(500) NOT NULL,
    first_name character varying(50),
    last_name character varying(50),
    email character varying(200),
    default_queue_id integer,
    is_admin boolean,
    id character varying(100),
    alstom_id character varying(100),
    contact character varying(100),
    is_active boolean
);


ALTER TABLE tbl_user OWNER TO admin;

--
-- TOC entry 192 (class 1259 OID 16482)
-- Name: tbl_user_queue; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_user_queue (
    user_queue_id bigint NOT NULL,
    user_id bigint NOT NULL,
    queue_id bigint NOT NULL
);


ALTER TABLE tbl_user_queue OWNER TO admin;

--
-- TOC entry 193 (class 1259 OID 16485)
-- Name: tbl_user_queue_user_queue_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE tbl_user_queue_user_queue_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_user_queue_user_queue_id_seq OWNER TO admin;

--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 193
-- Name: tbl_user_queue_user_queue_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE tbl_user_queue_user_queue_id_seq OWNED BY tbl_user_queue.user_queue_id;


--
-- TOC entry 194 (class 1259 OID 16487)
-- Name: tbl_user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE tbl_user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_user_user_id_seq OWNER TO admin;

--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 194
-- Name: tbl_user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE tbl_user_user_id_seq OWNED BY tbl_user.user_id;


--
-- TOC entry 195 (class 1259 OID 16489)
-- Name: tbl_work_order; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE tbl_work_order (
    work_order_id character varying(100) NOT NULL,
    company character varying(100),
    status character varying(100),
    summary character varying(1000),
    priority character varying(50),
    submit_date timestamp with time zone,
    completed_date timestamp with time zone,
    last_modified_date timestamp with time zone,
    alstom_business_entity character varying(100),
    assignee_support_company character varying(100),
    assignee_support_group_name bigint,
    assignee_support_organization character varying(100),
    associated_request_id character varying(100),
    request_id character varying(100),
    detailed_description character varying(1000),
    next_target_date timestamp with time zone,
    application_id bigint,
    reassign_counter integer,
    assignee character varying(200),
    slm_status character varying(200),
    service_level character varying(100),
    cust_first_name character varying(200),
    cust_last_name character varying(200),
    breachreason character varying(200),
    wo_comment character varying(2000),
    ticket_type character varying(100)
);


ALTER TABLE tbl_work_order OWNER TO admin;

--
-- TOC entry 1962 (class 2604 OID 16495)
-- Name: appl_id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_application ALTER COLUMN appl_id SET DEFAULT nextval('tbl_application_appl_id_seq'::regclass);


--
-- TOC entry 1963 (class 2604 OID 16496)
-- Name: class_id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_class_type ALTER COLUMN class_id SET DEFAULT nextval('tbl_class_type_class_id_seq'::regclass);


--
-- TOC entry 1965 (class 2604 OID 16497)
-- Name: comment_id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_entity_comment ALTER COLUMN comment_id SET DEFAULT nextval('tbl_entity_comment_comment_id_seq'::regclass);


--
-- TOC entry 1966 (class 2604 OID 16498)
-- Name: id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_notify_email ALTER COLUMN id SET DEFAULT nextval('tbl_notify_email_id_seq'::regclass);


--
-- TOC entry 1967 (class 2604 OID 16499)
-- Name: queue_id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_queue ALTER COLUMN queue_id SET DEFAULT nextval('tbl_queue_queue_id_seq'::regclass);


--
-- TOC entry 1969 (class 2604 OID 16500)
-- Name: sla_id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_sla ALTER COLUMN sla_id SET DEFAULT nextval('tbl_sla_sla_id_seq'::regclass);


--
-- TOC entry 1972 (class 2604 OID 16501)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_task_id ALTER COLUMN id SET DEFAULT nextval('tbl_task_id_id_seq'::regclass);


--
-- TOC entry 1975 (class 2604 OID 16502)
-- Name: topic_id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_topic ALTER COLUMN topic_id SET DEFAULT nextval('tbl_topic_topic_id_seq'::regclass);


--
-- TOC entry 1976 (class 2604 OID 16503)
-- Name: user_id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_user ALTER COLUMN user_id SET DEFAULT nextval('tbl_user_user_id_seq'::regclass);


--
-- TOC entry 1977 (class 2604 OID 16504)
-- Name: user_queue_id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_user_queue ALTER COLUMN user_queue_id SET DEFAULT nextval('tbl_user_queue_user_queue_id_seq'::regclass);


--
-- TOC entry 1980 (class 2606 OID 16506)
-- Name: tbl_application_appl_name_key; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_application
    ADD CONSTRAINT tbl_application_appl_name_key UNIQUE (appl_name);


--
-- TOC entry 1982 (class 2606 OID 16508)
-- Name: tbl_application_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_application
    ADD CONSTRAINT tbl_application_pkey PRIMARY KEY (appl_id);


--
-- TOC entry 1984 (class 2606 OID 16510)
-- Name: tbl_change_request_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_change_request
    ADD CONSTRAINT tbl_change_request_pkey PRIMARY KEY (change_id);


--
-- TOC entry 1986 (class 2606 OID 16512)
-- Name: tbl_class_type_key; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_class_type
    ADD CONSTRAINT tbl_class_type_key UNIQUE (class_type);


--
-- TOC entry 1988 (class 2606 OID 16514)
-- Name: tbl_class_type_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_class_type
    ADD CONSTRAINT tbl_class_type_pkey PRIMARY KEY (class_id);


--
-- TOC entry 1991 (class 2606 OID 16516)
-- Name: tbl_entity_comment_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_entity_comment
    ADD CONSTRAINT tbl_entity_comment_pkey PRIMARY KEY (comment_id);


--
-- TOC entry 1996 (class 2606 OID 16518)
-- Name: tbl_incident_slm_pk; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_incident_slm
    ADD CONSTRAINT tbl_incident_slm_pk PRIMARY KEY (incident_id);


--
-- TOC entry 1998 (class 2606 OID 16520)
-- Name: tbl_notify_email_pk; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_notify_email
    ADD CONSTRAINT tbl_notify_email_pk PRIMARY KEY (id);


--
-- TOC entry 2000 (class 2606 OID 16522)
-- Name: tbl_queue_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_queue
    ADD CONSTRAINT tbl_queue_pkey PRIMARY KEY (queue_id);


--
-- TOC entry 2002 (class 2606 OID 16524)
-- Name: tbl_queue_queue_name_key; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_queue
    ADD CONSTRAINT tbl_queue_queue_name_key UNIQUE (queue_name);


--
-- TOC entry 2005 (class 2606 OID 16526)
-- Name: tbl_sla_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_sla
    ADD CONSTRAINT tbl_sla_pkey PRIMARY KEY (sla_id);


--
-- TOC entry 2011 (class 2606 OID 16528)
-- Name: tbl_task_id_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tbl_task_id
    ADD CONSTRAINT tbl_task_id_pkey PRIMARY KEY (id);


--
-- TOC entry 2009 (class 2606 OID 16530)
-- Name: tbl_task_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_task
    ADD CONSTRAINT tbl_task_pkey PRIMARY KEY (task_id);


--
-- TOC entry 2015 (class 2606 OID 16532)
-- Name: tbl_topic_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_topic
    ADD CONSTRAINT tbl_topic_pkey PRIMARY KEY (topic_id);


--
-- TOC entry 2017 (class 2606 OID 16534)
-- Name: tbl_user_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_user
    ADD CONSTRAINT tbl_user_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2023 (class 2606 OID 16536)
-- Name: tbl_user_queue_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_user_queue
    ADD CONSTRAINT tbl_user_queue_pkey PRIMARY KEY (user_queue_id);


--
-- TOC entry 2019 (class 2606 OID 16538)
-- Name: tbl_user_uniqkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_user
    ADD CONSTRAINT tbl_user_uniqkey UNIQUE (username);


--
-- TOC entry 2027 (class 2606 OID 16540)
-- Name: tbl_work_order_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY tbl_work_order
    ADD CONSTRAINT tbl_work_order_pkey PRIMARY KEY (work_order_id);


--
-- TOC entry 1978 (class 1259 OID 16541)
-- Name: fki_tbl_application_fk_class; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_application_fk_class ON tbl_application USING btree (appl_class_type);


--
-- TOC entry 1989 (class 1259 OID 16542)
-- Name: fki_tbl_comment_incident_fk; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_comment_incident_fk ON tbl_entity_comment USING btree (entity_id);


--
-- TOC entry 1992 (class 1259 OID 16543)
-- Name: fki_tbl_incident_slm_fk_queue1; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_incident_slm_fk_queue1 ON tbl_incident_slm USING btree (assigned_group);


--
-- TOC entry 1993 (class 1259 OID 16544)
-- Name: fki_tbl_incident_slm_fk_queue2; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_incident_slm_fk_queue2 ON tbl_incident_slm USING btree (owner_group);


--
-- TOC entry 2003 (class 1259 OID 16545)
-- Name: fki_tbl_sla_fk_class_type; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_sla_fk_class_type ON tbl_sla USING btree (class_type_id);


--
-- TOC entry 2006 (class 1259 OID 16546)
-- Name: fki_tbl_task_fk_created_by; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_task_fk_created_by ON tbl_task USING btree (created_by);


--
-- TOC entry 2007 (class 1259 OID 16547)
-- Name: fki_tbl_task_fk_updated_by; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_task_fk_updated_by ON tbl_task USING btree (updated_by);


--
-- TOC entry 2012 (class 1259 OID 16548)
-- Name: fki_tbl_topic_fk_updated_by; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_topic_fk_updated_by ON tbl_topic USING btree (updated_by);


--
-- TOC entry 2013 (class 1259 OID 16549)
-- Name: fki_tbl_topic_fk_user; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_topic_fk_user ON tbl_topic USING btree (user_id);


--
-- TOC entry 2020 (class 1259 OID 16550)
-- Name: fki_tbl_user_queue_fk_queue; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_user_queue_fk_queue ON tbl_user_queue USING btree (queue_id);


--
-- TOC entry 2021 (class 1259 OID 16551)
-- Name: fki_tbl_user_queue_fk_user; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_user_queue_fk_user ON tbl_user_queue USING btree (user_id);


--
-- TOC entry 2024 (class 1259 OID 16552)
-- Name: fki_tbl_work_order_fk_app_id; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_work_order_fk_app_id ON tbl_work_order USING btree (application_id);


--
-- TOC entry 2025 (class 1259 OID 16553)
-- Name: fki_tbl_work_order_fk_queue_id; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_tbl_work_order_fk_queue_id ON tbl_work_order USING btree (assignee_support_group_name);


--
-- TOC entry 1994 (class 1259 OID 16554)
-- Name: tbl_incident_slm_fk_appl_name; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX tbl_incident_slm_fk_appl_name ON tbl_incident_slm USING btree (application_id);


--
-- TOC entry 2028 (class 2606 OID 16555)
-- Name: tbl_application_fk_class; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_application
    ADD CONSTRAINT tbl_application_fk_class FOREIGN KEY (appl_class_type) REFERENCES tbl_class_type(class_id);


--
-- TOC entry 2029 (class 2606 OID 16560)
-- Name: tbl_change_request_fk_app_id; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_change_request
    ADD CONSTRAINT tbl_change_request_fk_app_id FOREIGN KEY (application_id) REFERENCES tbl_application(appl_id);


--
-- TOC entry 2030 (class 2606 OID 16565)
-- Name: tbl_change_request_fk_queue_id; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_change_request
    ADD CONSTRAINT tbl_change_request_fk_queue_id FOREIGN KEY (change_coordinator_group) REFERENCES tbl_queue(queue_id);


--
-- TOC entry 2031 (class 2606 OID 16570)
-- Name: tbl_incident_slm_fk_appl_name; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_incident_slm
    ADD CONSTRAINT tbl_incident_slm_fk_appl_name FOREIGN KEY (application_id) REFERENCES tbl_application(appl_id);


--
-- TOC entry 2032 (class 2606 OID 16575)
-- Name: tbl_incident_slm_fk_queue1; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_incident_slm
    ADD CONSTRAINT tbl_incident_slm_fk_queue1 FOREIGN KEY (assigned_group) REFERENCES tbl_queue(queue_id);


--
-- TOC entry 2033 (class 2606 OID 16580)
-- Name: tbl_incident_slm_fk_queue2; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_incident_slm
    ADD CONSTRAINT tbl_incident_slm_fk_queue2 FOREIGN KEY (owner_group) REFERENCES tbl_queue(queue_id);


--
-- TOC entry 2034 (class 2606 OID 16585)
-- Name: tbl_sla_fk_class_type; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_sla
    ADD CONSTRAINT tbl_sla_fk_class_type FOREIGN KEY (class_type_id) REFERENCES tbl_class_type(class_id);


--
-- TOC entry 2035 (class 2606 OID 16590)
-- Name: tbl_task_fk_created_by; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_task
    ADD CONSTRAINT tbl_task_fk_created_by FOREIGN KEY (created_by) REFERENCES tbl_user(user_id);


--
-- TOC entry 2036 (class 2606 OID 16595)
-- Name: tbl_task_fk_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_task
    ADD CONSTRAINT tbl_task_fk_updated_by FOREIGN KEY (updated_by) REFERENCES tbl_user(user_id);


--
-- TOC entry 2037 (class 2606 OID 16600)
-- Name: tbl_topic_fk_updated_by; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_topic
    ADD CONSTRAINT tbl_topic_fk_updated_by FOREIGN KEY (updated_by) REFERENCES tbl_user(user_id);


--
-- TOC entry 2038 (class 2606 OID 16605)
-- Name: tbl_topic_fk_user; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_topic
    ADD CONSTRAINT tbl_topic_fk_user FOREIGN KEY (user_id) REFERENCES tbl_user(user_id);


--
-- TOC entry 2039 (class 2606 OID 16610)
-- Name: tbl_user_queue_fk_queue; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_user_queue
    ADD CONSTRAINT tbl_user_queue_fk_queue FOREIGN KEY (queue_id) REFERENCES tbl_queue(queue_id);


--
-- TOC entry 2040 (class 2606 OID 16615)
-- Name: tbl_user_queue_fk_user; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_user_queue
    ADD CONSTRAINT tbl_user_queue_fk_user FOREIGN KEY (user_id) REFERENCES tbl_user(user_id);


--
-- TOC entry 2041 (class 2606 OID 16620)
-- Name: tbl_work_order_fk_app_id; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_work_order
    ADD CONSTRAINT tbl_work_order_fk_app_id FOREIGN KEY (application_id) REFERENCES tbl_application(appl_id);


--
-- TOC entry 2042 (class 2606 OID 16625)
-- Name: tbl_work_order_fk_queue_id; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY tbl_work_order
    ADD CONSTRAINT tbl_work_order_fk_queue_id FOREIGN KEY (assignee_support_group_name) REFERENCES tbl_queue(queue_id);


--
-- TOC entry 2159 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
