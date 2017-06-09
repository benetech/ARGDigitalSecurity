--
-- PostgreSQL database
CREATE DATABASE argdb
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--Open new connection window to argdb and execute since this point to the end of the script
-- Name: benetech; Type: SCHEMA; Schema: -; Owner: arg_qa
--

CREATE SCHEMA benetech;


ALTER SCHEMA benetech OWNER TO arg_qa;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = benetech, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: audit_trail; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE audit_trail (
    id integer NOT NULL,
    object_type text NOT NULL,
    object_id integer NOT NULL,
    action text NOT NULL,
    status text NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text
);


ALTER TABLE benetech.audit_trail OWNER TO arg_qa;

--
-- Name: conditions; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE conditions (
    id integer NOT NULL,
    name text NOT NULL,
    timer text NOT NULL,
    true_outcome text NOT NULL,
    false_outcome text NOT NULL,
    step_id integer NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text NOT NULL,
    modified_date timestamp without time zone,
    modified_by text,
    deleted_date timestamp without time zone,
    deleted_by text,
    retries integer
);


ALTER TABLE benetech.conditions OWNER TO arg_qa;

--
-- Name: parameters; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE parameters (
    id integer NOT NULL,
    name text NOT NULL,
    value text NOT NULL,
    step_id integer NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text NOT NULL,
    modified_date timestamp without time zone,
    modified_by text,
    deleted_date timestamp without time zone,
    deleted_by text
);


ALTER TABLE benetech.parameters OWNER TO arg_qa;

--
-- Name: roles; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE roles (
    id integer NOT NULL,
    created_by character varying(255),
    created_date date,
    deleted_by character varying(255),
    deleted_date character varying(255),
    description character varying(255),
    is_active boolean,
    modified_by character varying(255),
    modified_date date,
    name character varying(255)
);


ALTER TABLE benetech.roles OWNER TO arg_qa;

--
-- Name: steps; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE steps (
    id integer NOT NULL,
    name text NOT NULL,
    description text,
    status text NOT NULL,
    training_module_id integer NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text NOT NULL,
    modified_date timestamp without time zone,
    modified_by text,
    deleted_date timestamp without time zone,
    deleted_by text,
    sequence_id integer
);


ALTER TABLE benetech.steps OWNER TO arg_qa;

--
-- Name: teams; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE teams (
    id integer NOT NULL,
    name text NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text NOT NULL,
    modified_date timestamp without time zone,
    modified_by text,
    deleted_date timestamp without time zone,
    deleted_by text,
    id_active boolean
);


ALTER TABLE benetech.teams OWNER TO arg_qa;

--
-- Name: traced_actions; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE traced_actions (
    id integer NOT NULL,
    action text NOT NULL,
    user_id integer NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text NOT NULL,
    modified_date timestamp without time zone,
    modified_by text,
    deleted_date timestamp without time zone,
    deleted_by text,
    module_id integer,
    processed boolean,
	sequence_id integer
);


ALTER TABLE benetech.traced_actions OWNER TO arg_qa;

--
-- Name: training_modules; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE training_modules (
    id integer NOT NULL,
    name text NOT NULL,
    description text NOT NULL,
    model text NOT NULL,
    status text NOT NULL,
    duration integer NOT NULL,
    time_unit text NOT NULL,
    retries integer,
    is_active boolean DEFAULT true NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text NOT NULL,
    modified_date timestamp without time zone,
    modified_by text,
    deleted_date timestamp without time zone,
    deleted_by text,
    start_date timestamp without time zone
);


ALTER TABLE benetech.training_modules OWNER TO arg_qa;

--
-- Name: user_conditions; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE user_conditions (
    id integer NOT NULL,
    user_id integer NOT NULL,
    step_id integer NOT NULL,
    condition_id integer NOT NULL,
    status text NOT NULL,
    pending_retries integer NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text NOT NULL,
    modified_date timestamp without time zone,
    modified_by text,
    deleted_date timestamp without time zone,
    deleted_by text,
    timer integer
);


ALTER TABLE benetech.user_conditions OWNER TO arg_qa;

--
-- Name: user_steps; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE user_steps (
    id integer NOT NULL,
    user_id integer NOT NULL,
    step_id integer NOT NULL,
    status text NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text NOT NULL,
    modified_date timestamp without time zone,
    modified_by text,
    deleted_date timestamp without time zone,
    deleted_by text
);


ALTER TABLE benetech.user_steps OWNER TO arg_qa;

--
-- Name: user_teams; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE user_teams (
    id integer NOT NULL,
    user_id integer NOT NULL,
    team_id integer NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text NOT NULL,
    modified_date timestamp without time zone,
    modified_by text,
    deleted_date timestamp without time zone,
    deleted_by text
);


ALTER TABLE benetech.user_teams OWNER TO arg_qa;

--
-- Name: user_training_modules; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE user_training_modules (
    id integer NOT NULL,
    user_id integer NOT NULL,
    training_module_id integer NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_date timestamp without time zone DEFAULT ('now'::text)::date NOT NULL,
    created_by text NOT NULL,
    modified_date timestamp without time zone,
    modified_by text,
    deleted_date timestamp without time zone,
    deleted_by text,
    completed_date date
);


ALTER TABLE benetech.user_training_modules OWNER TO arg_qa;

--
-- Name: users; Type: TABLE; Schema: benetech; Owner: arg_qa; Tablespace:
--

CREATE TABLE users (
    id integer NOT NULL,
    account_status boolean,
    attempts integer NOT NULL,
    created_by character varying(255),
    created_date date,
    deleted_by character varying(255),
    deleted_date date,
    email character varying(255),
    first_name character varying(255),
    is_active boolean,
    last_name character varying(255),
    modified_by character varying(255),
    modified_date date,
    password character varying(255),
    username character varying(255),
    role_id integer NOT NULL,
    phone character varying(255)
);


ALTER TABLE benetech.users OWNER TO arg_qa;

--
-- Name: userteamssequence; Type: SEQUENCE; Schema: benetech; Owner: benetech
--

CREATE SEQUENCE userteamssequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE benetech.userteamssequence OWNER TO arg_qa;

SET search_path = public, pg_catalog;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: benetech_user
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO arg_qa;

--
-- Name: roles_users; Type: TABLE; Schema: public; Owner: arg_qa; Tablespace:
--

CREATE TABLE roles_users (
    roles_id integer NOT NULL,
    users_id integer NOT NULL
);


ALTER TABLE public.roles_users OWNER TO arg_qa;

--
-- Name: test; Type: TABLE; Schema: public; Owner: arg_qa; Tablespace:
--

CREATE TABLE test (
    id integer NOT NULL,
    description character varying(255),
    price real
);


ALTER TABLE public.test OWNER TO arg_qa;

SET search_path = benetech, pg_catalog;


--
-- Data for Name: roles; Type: TABLE DATA; Schema: benetech; Owner: arg_qa
--

INSERT INTO benetech.roles(id, created_by, created_date, description, is_active, name)
    VALUES (43,'daniela depablos', current_date, 'user-management is encharged to handle team management and the admin accounts', true, 'user_management');
INSERT INTO benetech.roles(id, created_by, created_date, description, is_active, name)
    VALUES (44, 'daniela depablos', current_date, 'field-workers will be enrolled by the user-management in the teams and trainning modules', true,  'field-workers');
INSERT INTO benetech.roles(id, created_by, created_date, description, is_active, name)
    VALUES (245, 'daniela depablos', current_date, 'admin will have access to all', true, 'admin');


--
-- Data for Name: teams; Type: TABLE DATA; Schema: benetech; Owner: arg_qa
--

INSERT INTO benetech.teams( id, name, is_active, created_date, created_by, id_active)
    VALUES (71, 'Team-admin@email.com', true, current_date, 'daniela.depablos', true);
INSERT INTO benetech.teams( id, name, is_active, created_date, created_by, id_active)
    VALUES (77, 'Team-admin@email.com', true, current_date, 'daniela.depablos', true);


--
-- Data for Name: user_teams; Type: TABLE DATA; Schema: benetech; Owner: arg_qa
--
INSERT INTO benetech.user_teams(id, user_id, team_id, is_active, created_date, created_by)
    VALUES (72, 46, 71, true, current_date, 'daniela.depablos');
INSERT INTO benetech.user_teams(id, user_id, team_id, is_active, created_date, created_by)
    VALUES (78, 46, 71, true, current_date, 'daniela.depablos');


--
-- Data for Name: users; Type: TABLE DATA; Schema: benetech; Owner: arg_qa
--

INSERT INTO benetech.users(id, account_status, attempts, created_by, created_date, email, first_name, is_active, last_name, modified_by, modified_date, password, username, role_id)
    VALUES (46, true, 1, 'daniela depablos', current_date, 'admin@email.com',  'admin', true, 'admin', 'testuser', current_date, '$2a$10$EUYfmND.5UkYgqoNxxQNcOKTVSUFBw/eRJvBdHev3lBPMyEwzkOSa', 'admin', 245);


--
-- Name: userteamssequence; Type: SEQUENCE SET; Schema: benetech; Owner: benetech
--

SELECT pg_catalog.setval('userteamssequence', 1, false);


SET search_path = public, pg_catalog;

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: benetech_user
--

SELECT pg_catalog.setval('hibernate_sequence', 931, true);


SET search_path = benetech, pg_catalog;

--
-- Name: audit_trail_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY audit_trail
    ADD CONSTRAINT audit_trail_pkey PRIMARY KEY (id);


--
-- Name: conditions_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY conditions
    ADD CONSTRAINT conditions_pkey PRIMARY KEY (id);


--
-- Name: parameters_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY parameters
    ADD CONSTRAINT parameters_pkey PRIMARY KEY (id);


--
-- Name: roles_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: steps_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY steps
    ADD CONSTRAINT steps_pkey PRIMARY KEY (id);


--
-- Name: teams_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT teams_pkey PRIMARY KEY (id);


--
-- Name: traced_actions_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY traced_actions
    ADD CONSTRAINT traced_actions_pkey PRIMARY KEY (id);


--
-- Name: training_modules_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY training_modules
    ADD CONSTRAINT training_modules_pkey PRIMARY KEY (id);


--
-- Name: user_conditions_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY user_conditions
    ADD CONSTRAINT user_conditions_pkey PRIMARY KEY (id);


--
-- Name: user_steps_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY user_steps
    ADD CONSTRAINT user_steps_pkey PRIMARY KEY (id);


--
-- Name: user_teams_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY user_teams
    ADD CONSTRAINT user_teams_pkey PRIMARY KEY (id);


--
-- Name: user_training_modules_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY user_training_modules
    ADD CONSTRAINT user_training_modules_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: benetech; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


SET search_path = public, pg_catalog;

--
-- Name: roles_users_pkey; Type: CONSTRAINT; Schema: public; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY roles_users
    ADD CONSTRAINT roles_users_pkey PRIMARY KEY (roles_id, users_id);


--
-- Name: roles_users_users_id_key; Type: CONSTRAINT; Schema: public; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY roles_users
    ADD CONSTRAINT roles_users_users_id_key UNIQUE (users_id);


--
-- Name: test_pkey; Type: CONSTRAINT; Schema: public; Owner: arg_qa; Tablespace:
--

ALTER TABLE ONLY test
    ADD CONSTRAINT test_pkey PRIMARY KEY (id);


SET search_path = benetech, pg_catalog;

--
-- Name: conditions_step_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY conditions
    ADD CONSTRAINT conditions_step_id_fkey FOREIGN KEY (step_id) REFERENCES steps(id);


--
-- Name: fk6a68e08e180f204; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08e180f204 FOREIGN KEY (role_id) REFERENCES roles(id);


--
-- Name: fk735a144286abb5e4; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY user_teams
    ADD CONSTRAINT fk735a144286abb5e4 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: parameters_step_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY parameters
    ADD CONSTRAINT parameters_step_id_fkey FOREIGN KEY (step_id) REFERENCES steps(id);


--
-- Name: steps_training_module_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY steps
    ADD CONSTRAINT steps_training_module_id_fkey FOREIGN KEY (training_module_id) REFERENCES training_modules(id);


--
-- Name: traced_actions_user_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY traced_actions
    ADD CONSTRAINT traced_actions_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: user_conditions_condition_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY user_conditions
    ADD CONSTRAINT user_conditions_condition_id_fkey FOREIGN KEY (condition_id) REFERENCES conditions(id);


--
-- Name: user_conditions_step_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY user_conditions
    ADD CONSTRAINT user_conditions_step_id_fkey FOREIGN KEY (step_id) REFERENCES steps(id);


--
-- Name: user_conditions_user_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY user_conditions
    ADD CONSTRAINT user_conditions_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: user_steps_step_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY user_steps
    ADD CONSTRAINT user_steps_step_id_fkey FOREIGN KEY (step_id) REFERENCES steps(id);


--
-- Name: user_steps_user_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY user_steps
    ADD CONSTRAINT user_steps_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: user_teams_team_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY user_teams
    ADD CONSTRAINT user_teams_team_id_fkey FOREIGN KEY (team_id) REFERENCES teams(id);


--
-- Name: user_training_modules_training_module_id_fkey; Type: FK CONSTRAINT; Schema: benetech; Owner: arg_qa
--

ALTER TABLE ONLY user_training_modules
    ADD CONSTRAINT user_training_modules_training_module_id_fkey FOREIGN KEY (training_module_id) REFERENCES training_modules(id);


SET search_path = public, pg_catalog;

--
-- Name: fk2e94be067f996a5d; Type: FK CONSTRAINT; Schema: public; Owner: arg_qa
--

ALTER TABLE ONLY roles_users
    ADD CONSTRAINT fk2e94be067f996a5d FOREIGN KEY (roles_id) REFERENCES benetech.roles(id);


--
-- Name: fk2e94be067f9c8e87; Type: FK CONSTRAINT; Schema: public; Owner: arg_qa
--

ALTER TABLE ONLY roles_users
    ADD CONSTRAINT fk2e94be067f9c8e87 FOREIGN KEY (users_id) REFERENCES benetech.users(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: arg_qa
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM arg_qa;
GRANT ALL ON SCHEMA public TO arg_qa;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--