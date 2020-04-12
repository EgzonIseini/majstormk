--
-- PostgreSQL database dump
--

-- Dumped from database version 11.7 (Ubuntu 11.7-0ubuntu0.19.10.1)
-- Dumped by pg_dump version 11.7 (Ubuntu 11.7-0ubuntu0.19.10.1)

-- Started on 2020-04-12 23:31:57 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 25573)
-- Name: company_user; Type: TABLE; Schema: public; Owner: majstoradmin
--

CREATE TABLE public.company_user (
    id bigint NOT NULL,
    address character varying(255),
    city integer,
    email_address character varying(255),
    name character varying(255),
    password character varying(255),
    phone_number character varying(255),
    zip_code integer,
    company_name character varying(255),
    description character varying(255)
);


ALTER TABLE public.company_user OWNER TO majstoradmin;

--
-- TOC entry 197 (class 1259 OID 25581)
-- Name: deal; Type: TABLE; Schema: public; Owner: majstoradmin
--

CREATE TABLE public.deal (
    id bigint NOT NULL,
    date timestamp without time zone,
    company_id bigint,
    listing_id bigint,
    user_id bigint,
    price real
);


ALTER TABLE public.deal OWNER TO majstoradmin;

--
-- TOC entry 207 (class 1259 OID 25700)
-- Name: global_announcements; Type: TABLE; Schema: public; Owner: majstoradmin
--

CREATE TABLE public.global_announcements (
    type character varying(31) NOT NULL,
    id bigint NOT NULL,
    announcement character varying(255),
    announcement_type integer,
    entity_id bigint,
    "timestamp" timestamp without time zone
);


ALTER TABLE public.global_announcements OWNER TO majstoradmin;

--
-- TOC entry 206 (class 1259 OID 25698)
-- Name: global_announcements_id_seq; Type: SEQUENCE; Schema: public; Owner: majstoradmin
--

CREATE SEQUENCE public.global_announcements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.global_announcements_id_seq OWNER TO majstoradmin;

--
-- TOC entry 3145 (class 0 OID 0)
-- Dependencies: 206
-- Name: global_announcements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: majstoradmin
--

ALTER SEQUENCE public.global_announcements_id_seq OWNED BY public.global_announcements.id;


--
-- TOC entry 205 (class 1259 OID 25630)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: majstoradmin
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO majstoradmin;

--
-- TOC entry 198 (class 1259 OID 25586)
-- Name: hibernate_sequences; Type: TABLE; Schema: public; Owner: majstoradmin
--

CREATE TABLE public.hibernate_sequences (
    sequence_name character varying(255) NOT NULL,
    next_val bigint
);


ALTER TABLE public.hibernate_sequences OWNER TO majstoradmin;

--
-- TOC entry 199 (class 1259 OID 25591)
-- Name: listing; Type: TABLE; Schema: public; Owner: majstoradmin
--

CREATE TABLE public.listing (
    id bigint NOT NULL,
    active boolean NOT NULL,
    city integer,
    deleted_flag boolean NOT NULL,
    description character varying(255),
    due_date timestamp without time zone,
    name character varying(255),
    price real,
    deal_id bigint,
    posted_by_id bigint
);


ALTER TABLE public.listing OWNER TO majstoradmin;

--
-- TOC entry 208 (class 1259 OID 25717)
-- Name: offer; Type: TABLE; Schema: public; Owner: majstoradmin
--

CREATE TABLE public.offer (
    id bigint NOT NULL,
    description character varying(255),
    offered_price real,
    was_accepted boolean,
    listing_id bigint,
    made_by_id bigint
);


ALTER TABLE public.offer OWNER TO majstoradmin;

--
-- TOC entry 200 (class 1259 OID 25604)
-- Name: private_user; Type: TABLE; Schema: public; Owner: majstoradmin
--

CREATE TABLE public.private_user (
    id bigint NOT NULL,
    address character varying(255),
    city integer,
    email_address character varying(255),
    name character varying(255),
    password character varying(255),
    phone_number character varying(255),
    zip_code integer,
    date_of_birth date,
    first_name character varying(255),
    last_name character varying(255)
);


ALTER TABLE public.private_user OWNER TO majstoradmin;

--
-- TOC entry 202 (class 1259 OID 25614)
-- Name: roles; Type: TABLE; Schema: public; Owner: majstoradmin
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(60)
);


ALTER TABLE public.roles OWNER TO majstoradmin;

--
-- TOC entry 201 (class 1259 OID 25612)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: majstoradmin
--

CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO majstoradmin;

--
-- TOC entry 3146 (class 0 OID 0)
-- Dependencies: 201
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: majstoradmin
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 203 (class 1259 OID 25620)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: majstoradmin
--

CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.user_roles OWNER TO majstoradmin;

--
-- TOC entry 204 (class 1259 OID 25625)
-- Name: user_user; Type: TABLE; Schema: public; Owner: majstoradmin
--

CREATE TABLE public.user_user (
    followers_id bigint NOT NULL,
    following_id bigint NOT NULL
);


ALTER TABLE public.user_user OWNER TO majstoradmin;

--
-- TOC entry 2979 (class 2604 OID 25703)
-- Name: global_announcements id; Type: DEFAULT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.global_announcements ALTER COLUMN id SET DEFAULT nextval('public.global_announcements_id_seq'::regclass);


--
-- TOC entry 2978 (class 2604 OID 25617)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 3127 (class 0 OID 25573)
-- Dependencies: 196
-- Data for Name: company_user; Type: TABLE DATA; Schema: public; Owner: majstoradmin
--

COPY public.company_user (id, address, city, email_address, name, password, phone_number, zip_code, company_name, description) FROM stdin;
\.


--
-- TOC entry 3128 (class 0 OID 25581)
-- Dependencies: 197
-- Data for Name: deal; Type: TABLE DATA; Schema: public; Owner: majstoradmin
--

COPY public.deal (id, date, company_id, listing_id, user_id, price) FROM stdin;
\.


--
-- TOC entry 3138 (class 0 OID 25700)
-- Dependencies: 207
-- Data for Name: global_announcements; Type: TABLE DATA; Schema: public; Owner: majstoradmin
--

COPY public.global_announcements (type, id, announcement, announcement_type, entity_id, "timestamp") FROM stdin;
new_listing	16	<strong>John D.</strong> објави оглас за општина <strong>Скопје</strong> со наслов: <strong></strong>	0	33	2020-04-12 20:52:07.254477
new_listing	17	<strong>John D.</strong> објави оглас за општина <strong>Скопје</strong> со наслов: <strong>Мојот нов оглас тест #1</strong>	0	34	2020-04-12 21:26:59.174082
new_offer	18	<strong>John D.</strong> објави нова понуда за огласот: <strong>Мојот нов оглас тест #1</strong>	2	34	2020-04-12 21:29:59.746303
new_offer	19	<strong>Egzon I.</strong> објави нова понуда за огласот: <strong>Мојот нов оглас тест #1</strong>	2	34	2020-04-12 21:32:54.982222
deal_reached	20	<strong>John D.</strong> и <strong>Egzon I.</strong> постигнаа договор за оглас: <strong>Мојот нов оглас тест #1</strong>	1	34	2020-04-12 21:39:00.783005
new_offer	21	<strong>John D.</strong> објави нова понуда за огласот: <strong></strong>	2	33	2020-04-12 21:44:52.028574
new_offer	22	<strong>Egzon I.</strong> објави нова понуда за огласот: <strong></strong>	2	33	2020-04-12 21:49:23.043301
new_listing	23	<strong>John D.</strong> објави оглас за општина <strong>Скопје</strong> со наслов: <strong>Мојот нов оглас тест #2</strong>	0	36	2020-04-12 22:48:50.679237
new_offer	24	<strong>John D.</strong> објави нова понуда за огласот: <strong>Мојот нов оглас тест #2</strong>	2	36	2020-04-12 22:49:10.921075
new_offer	25	<strong>John D.</strong> објави нова понуда за огласот: <strong>Мојот нов оглас тест #2</strong>	2	36	2020-04-12 22:49:31.674343
deal_reached	26	<strong>John D.</strong> и <strong>John D.</strong> постигнаа договор за оглас: <strong>Мојот нов оглас тест #2</strong>	1	36	2020-04-12 22:49:35.080257
\.


--
-- TOC entry 3129 (class 0 OID 25586)
-- Dependencies: 198
-- Data for Name: hibernate_sequences; Type: TABLE DATA; Schema: public; Owner: majstoradmin
--

COPY public.hibernate_sequences (sequence_name, next_val) FROM stdin;
default	36
\.


--
-- TOC entry 3130 (class 0 OID 25591)
-- Dependencies: 199
-- Data for Name: listing; Type: TABLE DATA; Schema: public; Owner: majstoradmin
--

COPY public.listing (id, active, city, deleted_flag, description, due_date, name, price, deal_id, posted_by_id) FROM stdin;
\.


--
-- TOC entry 3139 (class 0 OID 25717)
-- Dependencies: 208
-- Data for Name: offer; Type: TABLE DATA; Schema: public; Owner: majstoradmin
--

COPY public.offer (id, description, offered_price, was_accepted, listing_id, made_by_id) FROM stdin;
\.


--
-- TOC entry 3131 (class 0 OID 25604)
-- Dependencies: 200
-- Data for Name: private_user; Type: TABLE DATA; Schema: public; Owner: majstoradmin
--

COPY public.private_user (id, address, city, email_address, name, password, phone_number, zip_code, date_of_birth, first_name, last_name) FROM stdin;
\.


--
-- TOC entry 3133 (class 0 OID 25614)
-- Dependencies: 202
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: majstoradmin
--

COPY public.roles (id, name) FROM stdin;
1	ROLE_USER
2	ROLE_ADMIN
\.


--
-- TOC entry 3134 (class 0 OID 25620)
-- Dependencies: 203
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: majstoradmin
--

COPY public.user_roles (user_id, role_id) FROM stdin;
\.


--
-- TOC entry 3135 (class 0 OID 25625)
-- Dependencies: 204
-- Data for Name: user_user; Type: TABLE DATA; Schema: public; Owner: majstoradmin
--

COPY public.user_user (followers_id, following_id) FROM stdin;
\.


--
-- TOC entry 3147 (class 0 OID 0)
-- Dependencies: 206
-- Name: global_announcements_id_seq; Type: SEQUENCE SET; Schema: public; Owner: majstoradmin
--

SELECT pg_catalog.setval('public.global_announcements_id_seq', 26, true);


--
-- TOC entry 3148 (class 0 OID 0)
-- Dependencies: 205
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: majstoradmin
--

SELECT pg_catalog.setval('public.hibernate_sequence', 36, true);


--
-- TOC entry 3149 (class 0 OID 0)
-- Dependencies: 201
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: majstoradmin
--

SELECT pg_catalog.setval('public.roles_id_seq', 3, true);


--
-- TOC entry 2981 (class 2606 OID 25580)
-- Name: company_user company_user_pkey; Type: CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.company_user
    ADD CONSTRAINT company_user_pkey PRIMARY KEY (id);


--
-- TOC entry 2983 (class 2606 OID 25585)
-- Name: deal deal_pkey; Type: CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.deal
    ADD CONSTRAINT deal_pkey PRIMARY KEY (id);


--
-- TOC entry 2997 (class 2606 OID 25705)
-- Name: global_announcements global_announcements_pkey; Type: CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.global_announcements
    ADD CONSTRAINT global_announcements_pkey PRIMARY KEY (id);


--
-- TOC entry 2985 (class 2606 OID 25590)
-- Name: hibernate_sequences hibernate_sequences_pkey; Type: CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.hibernate_sequences
    ADD CONSTRAINT hibernate_sequences_pkey PRIMARY KEY (sequence_name);


--
-- TOC entry 2987 (class 2606 OID 25598)
-- Name: listing listing_pkey; Type: CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.listing
    ADD CONSTRAINT listing_pkey PRIMARY KEY (id);


--
-- TOC entry 2999 (class 2606 OID 25721)
-- Name: offer offer_pkey; Type: CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.offer
    ADD CONSTRAINT offer_pkey PRIMARY KEY (id);


--
-- TOC entry 2989 (class 2606 OID 25611)
-- Name: private_user private_user_pkey; Type: CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.private_user
    ADD CONSTRAINT private_user_pkey PRIMARY KEY (id);


--
-- TOC entry 2991 (class 2606 OID 25619)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 2993 (class 2606 OID 25629)
-- Name: roles uk_nb4h0p6txrmfc0xbrd1kglp9t; Type: CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT uk_nb4h0p6txrmfc0xbrd1kglp9t UNIQUE (name);


--
-- TOC entry 2995 (class 2606 OID 25624)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 3000 (class 2606 OID 25712)
-- Name: deal fkcaeh94kjysashkjiedo7qdp4h; Type: FK CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.deal
    ADD CONSTRAINT fkcaeh94kjysashkjiedo7qdp4h FOREIGN KEY (listing_id) REFERENCES public.listing(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 3004 (class 2606 OID 25657)
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 3003 (class 2606 OID 25727)
-- Name: listing fkndvhm7x90umfun7ljhwli8agq; Type: FK CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.listing
    ADD CONSTRAINT fkndvhm7x90umfun7ljhwli8agq FOREIGN KEY (deal_id) REFERENCES public.deal(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 3002 (class 2606 OID 25742)
-- Name: deal fknk4t7r9ii1djk2hlnlcv1d8yu; Type: FK CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.deal
    ADD CONSTRAINT fknk4t7r9ii1djk2hlnlcv1d8yu FOREIGN KEY (company_id) REFERENCES public.private_user(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 3001 (class 2606 OID 25737)
-- Name: deal fkq1sxuqgbhg6cmdy78mys18h6h; Type: FK CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.deal
    ADD CONSTRAINT fkq1sxuqgbhg6cmdy78mys18h6h FOREIGN KEY (user_id) REFERENCES public.private_user(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 3005 (class 2606 OID 25732)
-- Name: offer fkqsbrjj3aajgd9jmfelwo1nps6; Type: FK CONSTRAINT; Schema: public; Owner: majstoradmin
--

ALTER TABLE ONLY public.offer
    ADD CONSTRAINT fkqsbrjj3aajgd9jmfelwo1nps6 FOREIGN KEY (listing_id) REFERENCES public.listing(id) ON UPDATE CASCADE ON DELETE SET NULL;


-- Completed on 2020-04-12 23:31:57 CEST

--
-- PostgreSQL database dump complete
--

