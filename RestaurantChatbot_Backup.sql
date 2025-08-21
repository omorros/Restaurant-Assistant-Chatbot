--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2
-- Dumped by pg_dump version 17.2

-- Started on 2024-12-07 15:45:43

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4899 (class 1262 OID 16388)
-- Name: restaurant_chatbot; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE restaurant_chatbot WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';


ALTER DATABASE restaurant_chatbot OWNER TO postgres;

\connect restaurant_chatbot

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 226 (class 1259 OID 24712)
-- Name: conversation_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.conversation_history (
    id integer NOT NULL,
    username character varying(255) NOT NULL,
    query text NOT NULL,
    response text NOT NULL,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.conversation_history OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 24711)
-- Name: conversation_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.conversation_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.conversation_history_id_seq OWNER TO postgres;

--
-- TOC entry 4900 (class 0 OID 0)
-- Dependencies: 225
-- Name: conversation_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.conversation_history_id_seq OWNED BY public.conversation_history.id;


--
-- TOC entry 222 (class 1259 OID 24688)
-- Name: faq; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.faq (
    id integer NOT NULL,
    question text NOT NULL,
    answer text NOT NULL
);


ALTER TABLE public.faq OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 24687)
-- Name: faq_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.faq_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.faq_id_seq OWNER TO postgres;

--
-- TOC entry 4901 (class 0 OID 0)
-- Dependencies: 221
-- Name: faq_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.faq_id_seq OWNED BY public.faq.id;


--
-- TOC entry 224 (class 1259 OID 24697)
-- Name: feedback; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.feedback (
    id integer NOT NULL,
    username character varying(255) NOT NULL,
    rating integer,
    comment text,
    CONSTRAINT feedback_rating_check CHECK (((rating >= 1) AND (rating <= 5)))
);


ALTER TABLE public.feedback OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24696)
-- Name: feedback_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.feedback_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.feedback_id_seq OWNER TO postgres;

--
-- TOC entry 4902 (class 0 OID 0)
-- Dependencies: 223
-- Name: feedback_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.feedback_id_seq OWNED BY public.feedback.id;


--
-- TOC entry 220 (class 1259 OID 24676)
-- Name: reservations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reservations (
    reservation_id integer NOT NULL,
    username character varying(255) NOT NULL,
    reservation_date date NOT NULL,
    reservation_time time without time zone NOT NULL,
    guests integer NOT NULL
);


ALTER TABLE public.reservations OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 24675)
-- Name: reservations_reservation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reservations_reservation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reservations_reservation_id_seq OWNER TO postgres;

--
-- TOC entry 4903 (class 0 OID 0)
-- Dependencies: 219
-- Name: reservations_reservation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reservations_reservation_id_seq OWNED BY public.reservations.reservation_id;


--
-- TOC entry 218 (class 1259 OID 24663)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    full_name character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    phone_number character varying(15),
    preferences text
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24662)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 4904 (class 0 OID 0)
-- Dependencies: 217
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 4719 (class 2604 OID 24715)
-- Name: conversation_history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conversation_history ALTER COLUMN id SET DEFAULT nextval('public.conversation_history_id_seq'::regclass);


--
-- TOC entry 4717 (class 2604 OID 24691)
-- Name: faq id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.faq ALTER COLUMN id SET DEFAULT nextval('public.faq_id_seq'::regclass);


--
-- TOC entry 4718 (class 2604 OID 24700)
-- Name: feedback id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feedback ALTER COLUMN id SET DEFAULT nextval('public.feedback_id_seq'::regclass);


--
-- TOC entry 4716 (class 2604 OID 24679)
-- Name: reservations reservation_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations ALTER COLUMN reservation_id SET DEFAULT nextval('public.reservations_reservation_id_seq'::regclass);


--
-- TOC entry 4715 (class 2604 OID 24666)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4893 (class 0 OID 24712)
-- Dependencies: 226
-- Data for Name: conversation_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.conversation_history (id, username, query, response, "timestamp") FROM stdin;
4	uriisss	have you got wifi in the restaurant?	Yes, we provide free WiFi for all customers.	2024-12-03 16:42:58.0824
5	uriisss	also i wanted to ask if is wheelchair acessible	Yes, our restaurant is fully wheelchair accessible.	2024-12-03 16:43:29.102734
6	uriisss	parking	Yes, we have free parking for customers right outside the restaurant.	2024-12-04 18:00:30.444403
7	uriisss	allaparkingjjd	Yes, we have free parking for customers right outside the restaurant.	2024-12-04 18:00:36.412297
\.


--
-- TOC entry 4889 (class 0 OID 24688)
-- Dependencies: 222
-- Data for Name: faq; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.faq (id, question, answer) FROM stdin;
10	Do you provide WiFi?	Yes, we provide free WiFi for all customers.
11	What payment methods do you accept?	We accept cash, credit/debit cards, and contactless payments.
12	Do you offer vegan options?	Yes, we offer a variety of vegan dishes.
13	Do you have any special discounts?	We offer discounts on weekdays from 2 PM to 4 PM.
14	What are your opening hours?	We are open from 9 AM to 10 PM.
15	Is the restaurant wheelchair accessible?	Yes, our restaurant is fully wheelchair accessible.
16	How can I make a reservation?	Yes, you can reserve a table through our chatbot or by calling the restaurant.
17	Do you have a kids' menu?	Yes, we have a special menu for children.
18	Are pets allowed?	Only service animals are allowed inside the restaurant.
19	Is parking available?	Yes, we have free parking for customers right outside the restaurant.
\.


--
-- TOC entry 4891 (class 0 OID 24697)
-- Dependencies: 224
-- Data for Name: feedback; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.feedback (id, username, rating, comment) FROM stdin;
1	uriisss	3	great food and service!
2	uriisss	5	Lovely staff
\.


--
-- TOC entry 4887 (class 0 OID 24676)
-- Dependencies: 220
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reservations (reservation_id, username, reservation_date, reservation_time, guests) FROM stdin;
2	uriisss	2024-12-15	12:30:00	2
3	uriisss	2024-12-20	18:30:00	5
\.


--
-- TOC entry 4885 (class 0 OID 24663)
-- Dependencies: 218
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, full_name, username, email, password, phone_number, preferences) FROM stdin;
1	Oriol Morros Vilaseca	uriisss	oriolmorros25@gmail.com	2005	\N	\N
\.


--
-- TOC entry 4905 (class 0 OID 0)
-- Dependencies: 225
-- Name: conversation_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.conversation_history_id_seq', 7, true);


--
-- TOC entry 4906 (class 0 OID 0)
-- Dependencies: 221
-- Name: faq_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.faq_id_seq', 19, true);


--
-- TOC entry 4907 (class 0 OID 0)
-- Dependencies: 223
-- Name: feedback_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.feedback_id_seq', 2, true);


--
-- TOC entry 4908 (class 0 OID 0)
-- Dependencies: 219
-- Name: reservations_reservation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reservations_reservation_id_seq', 3, true);


--
-- TOC entry 4909 (class 0 OID 0)
-- Dependencies: 217
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- TOC entry 4735 (class 2606 OID 24720)
-- Name: conversation_history conversation_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conversation_history
    ADD CONSTRAINT conversation_history_pkey PRIMARY KEY (id);


--
-- TOC entry 4731 (class 2606 OID 24695)
-- Name: faq faq_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.faq
    ADD CONSTRAINT faq_pkey PRIMARY KEY (id);


--
-- TOC entry 4733 (class 2606 OID 24705)
-- Name: feedback feedback_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feedback
    ADD CONSTRAINT feedback_pkey PRIMARY KEY (id);


--
-- TOC entry 4729 (class 2606 OID 24681)
-- Name: reservations reservations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservations_pkey PRIMARY KEY (reservation_id);


--
-- TOC entry 4723 (class 2606 OID 24674)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4725 (class 2606 OID 24670)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4727 (class 2606 OID 24672)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4738 (class 2606 OID 24721)
-- Name: conversation_history conversation_history_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conversation_history
    ADD CONSTRAINT conversation_history_username_fkey FOREIGN KEY (username) REFERENCES public.users(username) ON DELETE CASCADE;


--
-- TOC entry 4737 (class 2606 OID 24706)
-- Name: feedback feedback_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feedback
    ADD CONSTRAINT feedback_username_fkey FOREIGN KEY (username) REFERENCES public.users(username) ON DELETE CASCADE;


--
-- TOC entry 4736 (class 2606 OID 24682)
-- Name: reservations reservations_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservations_username_fkey FOREIGN KEY (username) REFERENCES public.users(username) ON DELETE CASCADE;


-- Completed on 2024-12-07 15:45:44

--
-- PostgreSQL database dump complete
--

