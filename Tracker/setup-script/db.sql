--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: freesize; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE freesize (
    ip text NOT NULL,
    freesize bigint,
    hash text
);


ALTER TABLE public.freesize OWNER TO postgres;

--
-- Name: hooks; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hooks (
    absolutepath text NOT NULL,
    ip text NOT NULL,
    "timestamp" text,
    size integer,
    originip text
);


ALTER TABLE public.hooks OWNER TO postgres;

--
-- Name: video; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE video (
    absolutepath text NOT NULL,
    ip text NOT NULL,
    audiobitrate integer,
    videobitrate integer,
    server text,
    originip text
);


ALTER TABLE public.video OWNER TO postgres;

--
-- Data for Name: freesize; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY freesize (ip, freesize, hash) FROM stdin;
192.168.0.129	635158093824	Keshav
192.168.0.134	86490451968	AN
192.168.0.130	474108260352	undefined
\.


--
-- Data for Name: hooks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY hooks (absolutepath, ip, "timestamp", size, originip) FROM stdin;
LocalNetwork/192.168.0.130/D;;___movies___sherlock.3x02.the_sign_of_three.hdtv_x264-fov.mp4	192.168.0.134	15:31:52	641086269	192.168.0.130
LocalNetwork/192.168.0.129/C;;___Users___kesha___Desktop___hello.java	192.168.0.130	15:32:49	17	192.168.0.129
\.


--
-- Data for Name: video; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY video (absolutepath, ip, audiobitrate, videobitrate, server, originip) FROM stdin;
\.


--
-- Name: freesize_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY freesize
    ADD CONSTRAINT freesize_pkey PRIMARY KEY (ip);


--
-- Name: hooks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hooks
    ADD CONSTRAINT hooks_pkey PRIMARY KEY (absolutepath, ip);


--
-- Name: video_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY video
    ADD CONSTRAINT video_pkey PRIMARY KEY (absolutepath, ip);


--
-- Name: video_absolutepath_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY video
    ADD CONSTRAINT video_absolutepath_fkey FOREIGN KEY (absolutepath, ip) REFERENCES hooks(absolutepath, ip);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

