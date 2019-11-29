--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2
-- Dumped by pg_dump version 11.2

-- Started on 2019-11-29 19:14:30

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 198 (class 1255 OID 25264)
-- Name: insert_update_notify(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.insert_update_notify() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    declare
        payload JSON;
    BEGIN
        payload = json_build_object('oldId', OLD.id, 'newId', NEW.id);
        perform pg_notify('insert_update_notify', payload::text); 
        RETURN NULL;
    END;
$$;


ALTER FUNCTION public.insert_update_notify() OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 25262)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 25257)
-- Name: some_object; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.some_object (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.some_object OWNER TO postgres;

--
-- TOC entry 2810 (class 0 OID 25257)
-- Dependencies: 196
-- Data for Name: some_object; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.some_object (id, name) FROM stdin;
1	test_84
\.


--
-- TOC entry 2817 (class 0 OID 0)
-- Dependencies: 197
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, true);


--
-- TOC entry 2687 (class 2606 OID 25261)
-- Name: some_object some_object_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.some_object
    ADD CONSTRAINT some_object_pkey PRIMARY KEY (id);


--
-- TOC entry 2688 (class 2620 OID 25265)
-- Name: some_object insert_update_notify; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insert_update_notify AFTER INSERT OR UPDATE ON public.some_object FOR EACH ROW EXECUTE PROCEDURE public.insert_update_notify();


-- Completed on 2019-11-29 19:14:31

--
-- PostgreSQL database dump complete
--

