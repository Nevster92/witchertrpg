--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

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

SET default_table_access_method = heap;

--
-- Name: armor; Type: TABLE; Schema: public; Owner: witcher_admin
--

CREATE TABLE public.armor (
    id integer NOT NULL,
    name character varying NOT NULL,
    category character varying,
    stopping_power integer,
    armor_enhancement integer,
    availability character varying,
    effect character varying,
    encumbrance_value integer,
    weight double precision,
    price integer
);


ALTER TABLE public.armor OWNER TO witcher_admin;

--
-- Name: armor_id_seq; Type: SEQUENCE; Schema: public; Owner: witcher_admin
--

CREATE SEQUENCE public.armor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.armor_id_seq OWNER TO witcher_admin;

--
-- Name: armor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: witcher_admin
--

ALTER SEQUENCE public.armor_id_seq OWNED BY public.armor.id;


--
-- Name: armor_inventory; Type: TABLE; Schema: public; Owner: witcher_admin
--

CREATE TABLE public.armor_inventory (
    id integer NOT NULL,
    name character varying NOT NULL,
    category character varying,
    stopping_power integer,
    armor_enhancement integer,
    availability character varying,
    effect character varying,
    encumbrance_value integer,
    weight double precision,
    price integer,
    character_id integer
);


ALTER TABLE public.armor_inventory OWNER TO witcher_admin;

--
-- Name: armor_inventory_id_seq; Type: SEQUENCE; Schema: public; Owner: witcher_admin
--

CREATE SEQUENCE public.armor_inventory_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.armor_inventory_id_seq OWNER TO witcher_admin;

--
-- Name: armor_inventory_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: witcher_admin
--

ALTER SEQUENCE public.armor_inventory_id_seq OWNED BY public.armor_inventory.id;


--
-- Name: campaign; Type: TABLE; Schema: public; Owner: witcher_admin
--

CREATE TABLE public.campaign (
    id integer NOT NULL,
    user_id integer NOT NULL,
    title character varying NOT NULL,
    description character varying,
    invitation_link character varying
);


ALTER TABLE public.campaign OWNER TO witcher_admin;

--
-- Name: campaign_id_seq; Type: SEQUENCE; Schema: public; Owner: witcher_admin
--

CREATE SEQUENCE public.campaign_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.campaign_id_seq OWNER TO witcher_admin;

--
-- Name: campaign_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: witcher_admin
--

ALTER SEQUENCE public.campaign_id_seq OWNED BY public.campaign.id;


--
-- Name: character; Type: TABLE; Schema: public; Owner: witcher_admin
--

CREATE TABLE public."character" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    profession character varying(255),
    race character varying(255),
    gender character varying(255),
    age integer,
    intelligence integer DEFAULT 0,
    ref integer DEFAULT 0,
    dex integer DEFAULT 0,
    body integer DEFAULT 0,
    spd integer DEFAULT 0,
    emp integer DEFAULT 0,
    cra integer DEFAULT 0,
    will integer DEFAULT 0,
    luck integer DEFAULT 0,
    stun integer DEFAULT 0,
    run integer DEFAULT 0,
    leap integer DEFAULT 0,
    hp integer DEFAULT 0,
    sta integer DEFAULT 0,
    enc integer DEFAULT 0,
    rec integer DEFAULT 0,
    punch integer DEFAULT 0,
    kick integer DEFAULT 0,
    awareness integer DEFAULT 0,
    business integer DEFAULT 0,
    deduction integer DEFAULT 0,
    education integer DEFAULT 0,
    common_speech integer DEFAULT 0,
    elder_speech integer DEFAULT 0,
    dwarven_speech integer DEFAULT 0,
    monster_lore integer DEFAULT 0,
    social_etiquette integer DEFAULT 0,
    streetwise integer DEFAULT 0,
    tactics integer DEFAULT 0,
    teaching integer DEFAULT 0,
    wilderness_survival integer DEFAULT 0,
    brawling integer DEFAULT 0,
    dodge integer DEFAULT 0,
    meele integer DEFAULT 0,
    riding integer DEFAULT 0,
    sailing integer DEFAULT 0,
    small_blades integer DEFAULT 0,
    staff integer DEFAULT 0,
    swordsmanship integer DEFAULT 0,
    archery integer DEFAULT 0,
    athletic integer DEFAULT 0,
    crossbow integer DEFAULT 0,
    sleight_of_hand integer DEFAULT 0,
    stealth integer DEFAULT 0,
    physique integer DEFAULT 0,
    endurance integer DEFAULT 0,
    charisma integer DEFAULT 0,
    deceit integer DEFAULT 0,
    fine_art integer DEFAULT 0,
    gambling integer DEFAULT 0,
    style integer DEFAULT 0,
    human_perception integer DEFAULT 0,
    leadership integer DEFAULT 0,
    persuasion integer DEFAULT 0,
    performance integer DEFAULT 0,
    seduction integer DEFAULT 0,
    alchemy integer DEFAULT 0,
    crafting integer DEFAULT 0,
    disguise integer DEFAULT 0,
    first_aid integer DEFAULT 0,
    forgery integer DEFAULT 0,
    pick_lock integer DEFAULT 0,
    trap_crafting integer DEFAULT 0,
    courage integer DEFAULT 0,
    hex_weaving integer DEFAULT 0,
    intimidation integer DEFAULT 0,
    spell_casting integer DEFAULT 0,
    resist_magic integer DEFAULT 0,
    resist_coercion integer DEFAULT 0,
    ritual_crafting integer DEFAULT 0,
    melee integer DEFAULT 0,
    max_hp integer DEFAULT 0,
    melee_bonus integer DEFAULT 0
);


ALTER TABLE public."character" OWNER TO witcher_admin;

--
-- Name: character_id_seq; Type: SEQUENCE; Schema: public; Owner: witcher_admin
--

CREATE SEQUENCE public.character_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.character_id_seq OWNER TO witcher_admin;

--
-- Name: character_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: witcher_admin
--

ALTER SEQUENCE public.character_id_seq OWNED BY public."character".id;


--
-- Name: character_wearing; Type: TABLE; Schema: public; Owner: witcher_admin
--

CREATE TABLE public.character_wearing (
    character_id integer,
    head integer,
    torso integer,
    r_arm integer,
    l_arm integer,
    legs integer
);


ALTER TABLE public.character_wearing OWNER TO witcher_admin;

--
-- Name: user_characters; Type: TABLE; Schema: public; Owner: witcher_admin
--

CREATE TABLE public.user_characters (
    user_id integer,
    character_id integer,
    campaign_id integer
);


ALTER TABLE public.user_characters OWNER TO witcher_admin;

--
-- Name: user_data; Type: TABLE; Schema: public; Owner: witcher_admin
--

CREATE TABLE public.user_data (
    id integer NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    email character varying NOT NULL
);


ALTER TABLE public.user_data OWNER TO witcher_admin;

--
-- Name: user_data_id_seq; Type: SEQUENCE; Schema: public; Owner: witcher_admin
--

CREATE SEQUENCE public.user_data_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_data_id_seq OWNER TO witcher_admin;

--
-- Name: user_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: witcher_admin
--

ALTER SEQUENCE public.user_data_id_seq OWNED BY public.user_data.id;


--
-- Name: weapon; Type: TABLE; Schema: public; Owner: witcher_admin
--

CREATE TABLE public.weapon (
    id integer NOT NULL,
    name character varying NOT NULL,
    type character varying,
    weapon_accuracy integer,
    availability character varying,
    damage character varying,
    reliability integer,
    hands_required integer,
    range character varying,
    concealment character varying,
    enhancements integer,
    weight double precision,
    cost integer,
    category character varying,
    effect character varying
);


ALTER TABLE public.weapon OWNER TO witcher_admin;

--
-- Name: weapon_id_seq; Type: SEQUENCE; Schema: public; Owner: witcher_admin
--

CREATE SEQUENCE public.weapon_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.weapon_id_seq OWNER TO witcher_admin;

--
-- Name: weapon_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: witcher_admin
--

ALTER SEQUENCE public.weapon_id_seq OWNED BY public.weapon.id;


--
-- Name: weapon_inventory; Type: TABLE; Schema: public; Owner: witcher_admin
--

CREATE TABLE public.weapon_inventory (
    id integer NOT NULL,
    name character varying NOT NULL,
    type character varying,
    weapon_accuracy integer,
    availability character varying,
    damage character varying,
    reliability integer,
    hands_required integer,
    range character varying,
    concealment character varying,
    enhancements integer,
    weight double precision,
    cost integer,
    category character varying,
    character_id integer,
    effect character varying
);


ALTER TABLE public.weapon_inventory OWNER TO witcher_admin;

--
-- Name: weapon_inventory_id_seq; Type: SEQUENCE; Schema: public; Owner: witcher_admin
--

CREATE SEQUENCE public.weapon_inventory_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.weapon_inventory_id_seq OWNER TO witcher_admin;

--
-- Name: weapon_inventory_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: witcher_admin
--

ALTER SEQUENCE public.weapon_inventory_id_seq OWNED BY public.weapon_inventory.id;


--
-- Name: armor id; Type: DEFAULT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.armor ALTER COLUMN id SET DEFAULT nextval('public.armor_id_seq'::regclass);


--
-- Name: armor_inventory id; Type: DEFAULT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.armor_inventory ALTER COLUMN id SET DEFAULT nextval('public.armor_inventory_id_seq'::regclass);


--
-- Name: campaign id; Type: DEFAULT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.campaign ALTER COLUMN id SET DEFAULT nextval('public.campaign_id_seq'::regclass);


--
-- Name: character id; Type: DEFAULT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public."character" ALTER COLUMN id SET DEFAULT nextval('public.character_id_seq'::regclass);


--
-- Name: user_data id; Type: DEFAULT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.user_data ALTER COLUMN id SET DEFAULT nextval('public.user_data_id_seq'::regclass);


--
-- Name: weapon id; Type: DEFAULT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.weapon ALTER COLUMN id SET DEFAULT nextval('public.weapon_id_seq'::regclass);


--
-- Name: weapon_inventory id; Type: DEFAULT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.weapon_inventory ALTER COLUMN id SET DEFAULT nextval('public.weapon_inventory_id_seq'::regclass);


--
-- Data for Name: armor; Type: TABLE DATA; Schema: public; Owner: witcher_admin
--

COPY public.armor (id, name, category, stopping_power, armor_enhancement, availability, effect, encumbrance_value, weight, price) FROM stdin;
3	Armored Hood	head	14	0	c	\N	0	2	350
4	Verden Archer's Hood	head	3	1	c	\N	0	0.5	100
5	Gamberson	torso	3	0	e	\N	0	1	100
6	Brigandine	torso	12	0	c	\N	1	7	300
7	Padded Trousers	legs	5	1	c	\N	0	1	125
8	Plate Graves	legs	20	1	r	\N	1	7.5	625
\.


--
-- Data for Name: armor_inventory; Type: TABLE DATA; Schema: public; Owner: witcher_admin
--

COPY public.armor_inventory (id, name, category, stopping_power, armor_enhancement, availability, effect, encumbrance_value, weight, price, character_id) FROM stdin;
4	kambelláber	torso	10	0	p	Rombol	3	1	150	18
2	ténylegchest	torso	56	2	\N	\N	\N	\N	\N	1
1	chest	head	3	\N	\N	\N	\N	\N	\N	1
5	Great Helm	head	2	1	r	Restricted Vision	1	3.22	576	18
7	Padded Trousers	legs	5	1	c	\N	0	1	125	18
8	Gamberson	torso	3	0	e	\N	0	1	100	22
\.


--
-- Data for Name: campaign; Type: TABLE DATA; Schema: public; Owner: witcher_admin
--

COPY public.campaign (id, user_id, title, description, invitation_link) FROM stdin;
4	8	teszt1 kampánya	teszt 1 nek a kamoány	1rd86qw
8	6	routerTesztes	NALÁSAUK	1f5d8s6
\.


--
-- Data for Name: character; Type: TABLE DATA; Schema: public; Owner: witcher_admin
--

COPY public."character" (id, name, profession, race, gender, age, intelligence, ref, dex, body, spd, emp, cra, will, luck, stun, run, leap, hp, sta, enc, rec, punch, kick, awareness, business, deduction, education, common_speech, elder_speech, dwarven_speech, monster_lore, social_etiquette, streetwise, tactics, teaching, wilderness_survival, brawling, dodge, meele, riding, sailing, small_blades, staff, swordsmanship, archery, athletic, crossbow, sleight_of_hand, stealth, physique, endurance, charisma, deceit, fine_art, gambling, style, human_perception, leadership, persuasion, performance, seduction, alchemy, crafting, disguise, first_aid, forgery, pick_lock, trap_crafting, courage, hex_weaving, intimidation, spell_casting, resist_magic, resist_coercion, ritual_crafting, melee, max_hp, melee_bonus) FROM stdin;
2	Test Név	csöves	törpe	nő	23	62	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
9	ujkarakter	witcher	toddd	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
3	Második Test	faszta	elfiii	férfi	800	3	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
1	Dandelion	dalnok	human	female	32	7	4	7	4	8	11	4	8	10	7	18	3	30	30	40	6	\N	\N	\N	7	5	7	\N	8	8	\N	7	9	\N	\N	\N	\N	7	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	5	\N	\N	10	9	10	6	8	9	\N	8	10	10	\N	\N	\N	\N	\N	5	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
17	ujteszt	faszagyerek	törpe	faszi	\N	15	4	7	3	5	\N	\N	8	\N	5	15	3	25	25	30	5	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	25	-2
19	test1Kari	priest	dwarf	male	\N	16	1	34	1	2	1	3	1	1	2	6	1	10	10	10	2	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	10	-4
20	test2karakter	man_at_arms	dwarf	male	\N	4	34	4	3	3	3	3	3	3	3	9	1	15	15	30	3	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	15	-2
21	hecatchiiKari	criminal	elf	male	\N	4	4	6	6	16	6	6	6	6	6	48	9	30	30	60	6	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	30	0
18	fronted trest	priest	elf	female	\N	12	6	6	6	6	8	6	10	6	6	18	3	30	30	60	6	\N	\N	6	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	30	0
22	Dendibar	man_at_arms	dwarf	male	\N	12	10	5	3	8	11	5	2	4	2	24	4	10	10	30	2	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	10	-2
\.


--
-- Data for Name: character_wearing; Type: TABLE DATA; Schema: public; Owner: witcher_admin
--

COPY public.character_wearing (character_id, head, torso, r_arm, l_arm, legs) FROM stdin;
19	\N	\N	\N	\N	\N
20	\N	\N	\N	\N	\N
21	\N	\N	\N	\N	\N
2	\N	\N	\N	\N	\N
3	\N	\N	\N	\N	\N
9	\N	\N	\N	\N	\N
18	\N	4	12	14	7
17	\N	\N	\N	\N	\N
22	\N	8	\N	\N	\N
1	1	\N	9	\N	\N
\.


--
-- Data for Name: user_characters; Type: TABLE DATA; Schema: public; Owner: witcher_admin
--

COPY public.user_characters (user_id, character_id, campaign_id) FROM stdin;
1	1	\N
2	2	\N
1	3	\N
1	9	\N
1	17	\N
8	19	\N
9	20	\N
6	18	\N
6	21	\N
6	22	8
\.


--
-- Data for Name: user_data; Type: TABLE DATA; Schema: public; Owner: witcher_admin
--

COPY public.user_data (id, username, password, email) FROM stdin;
1	Balint	{noop}12345	balint@gmail.com
2	Test	{noop}12345	testee@gmail.com
6	hecatchi	$2a$10$e0OcDJyp7t5a8C6uipSOO.27L14Exh3kNGwk38d6NI9InGynfWnI6	koteles.eni@freemail.hu
7	regiszt	$2a$10$A9n.7C2Amz5WLxEqBfCF3O6MBnFM9xbBqIJHSWSnmYBfhRvc19tme	regisztmail
8	test1	$2a$10$fx7y4fqnxYagZlsFX9wGkuHhC60kZQuAyjeh1AV0I9M6A9usL7ITi	test1@gmail.cj
9	testMod	$2a$10$cMCE3MMkpb8Tb4uhEWRhr.kqTUHUVo21dl7OJf/GhGDttn1Um9oES	testMod@gmail.com
\.


--
-- Data for Name: weapon; Type: TABLE DATA; Schema: public; Owner: witcher_admin
--

COPY public.weapon (id, name, type, weapon_accuracy, availability, damage, reliability, hands_required, range, concealment, enhancements, weight, cost, category, effect) FROM stdin;
3	Iron Long Sword	s/p	0	e	2d6+2	10	2	n/a	l	0	1.5	160	sword	\N
4	Dagger	s/p	0	e	1d6+2	10	1	n/a	s	0	0.5	50	small blade	\N
6	Arming Sword	s/p	0	c	2d6+4	15	1	n/a	l	0	2.5	270	sword	\N
7	Gleddyf	s/p	0	c	3d6+2	5	2	n/a	l	0	3	285	sword	\N
8	Kord	s/p	0	r	5d6	15	1	n/a	n/a	1	1.5	725	sword	Bleeding (25%)
9	Hand Axe	s	0	e	2d6+1	10	1	n/a	s	0	1	205	axe	\N
10	Battle Axe	s	0	c	5d6	15	1	n/a	l	0	2	525	axe	\N
11	Berserker's Axe	s	0	p	6d6	15	2	n/a	n/a	1	3	690	axe	Ablating, Bleeding (25%)
5	Gnomish Buckler	b	0	r	0	15	1	n/a	n/a	0	1	450	shield	\N
\.


--
-- Data for Name: weapon_inventory; Type: TABLE DATA; Schema: public; Owner: witcher_admin
--

COPY public.weapon_inventory (id, name, type, weapon_accuracy, availability, damage, reliability, hands_required, range, concealment, enhancements, weight, cost, category, character_id, effect) FROM stdin;
1	kardii	kardus	8	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	9	\N
2	kiskard	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	9	\N
3	jard	esdif	3	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	2	\N
9	ütősfegyver	a	3	r	2d6	10	2	\N	\N	\N	\N	\N	\N	1	\N
12	Hand Axe	s	0	e	2d6+1	10	1	n/a	s	0	1	205	axe	18	\N
14	Dagger	s/p	0	e	1d6+2	10	1	n/a	s	0	0.5	50	small blade	18	\N
15	Kord	s/p	0	r	5d6	15	1	n/a	n/a	1	1.5	725	sword	22	Bleeding (25%)
\.


--
-- Name: armor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: witcher_admin
--

SELECT pg_catalog.setval('public.armor_id_seq', 8, true);


--
-- Name: armor_inventory_id_seq; Type: SEQUENCE SET; Schema: public; Owner: witcher_admin
--

SELECT pg_catalog.setval('public.armor_inventory_id_seq', 8, true);


--
-- Name: campaign_id_seq; Type: SEQUENCE SET; Schema: public; Owner: witcher_admin
--

SELECT pg_catalog.setval('public.campaign_id_seq', 10, true);


--
-- Name: character_id_seq; Type: SEQUENCE SET; Schema: public; Owner: witcher_admin
--

SELECT pg_catalog.setval('public.character_id_seq', 22, true);


--
-- Name: user_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: witcher_admin
--

SELECT pg_catalog.setval('public.user_data_id_seq', 9, true);


--
-- Name: weapon_id_seq; Type: SEQUENCE SET; Schema: public; Owner: witcher_admin
--

SELECT pg_catalog.setval('public.weapon_id_seq', 11, true);


--
-- Name: weapon_inventory_id_seq; Type: SEQUENCE SET; Schema: public; Owner: witcher_admin
--

SELECT pg_catalog.setval('public.weapon_inventory_id_seq', 15, true);


--
-- Name: armor armor_id; Type: CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.armor
    ADD CONSTRAINT armor_id PRIMARY KEY (id);


--
-- Name: armor_inventory armor_inventory_id; Type: CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.armor_inventory
    ADD CONSTRAINT armor_inventory_id PRIMARY KEY (id);


--
-- Name: character character_id; Type: CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public."character"
    ADD CONSTRAINT character_id PRIMARY KEY (id);


--
-- Name: campaign id; Type: CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.campaign
    ADD CONSTRAINT id PRIMARY KEY (id);


--
-- Name: user_data user_data_id; Type: CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT user_data_id PRIMARY KEY (id);


--
-- Name: weapon weapon_id; Type: CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.weapon
    ADD CONSTRAINT weapon_id PRIMARY KEY (id);


--
-- Name: weapon_inventory weapon_inventory_id; Type: CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.weapon_inventory
    ADD CONSTRAINT weapon_inventory_id PRIMARY KEY (id);


--
-- Name: user_characters campaign_id; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.user_characters
    ADD CONSTRAINT campaign_id FOREIGN KEY (campaign_id) REFERENCES public.campaign(id);


--
-- Name: user_characters character_id; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.user_characters
    ADD CONSTRAINT character_id FOREIGN KEY (character_id) REFERENCES public."character"(id);


--
-- Name: weapon_inventory character_id; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.weapon_inventory
    ADD CONSTRAINT character_id FOREIGN KEY (character_id) REFERENCES public."character"(id);


--
-- Name: character_wearing character_id; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.character_wearing
    ADD CONSTRAINT character_id FOREIGN KEY (character_id) REFERENCES public."character"(id);


--
-- Name: armor_inventory chracter_id; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.armor_inventory
    ADD CONSTRAINT chracter_id FOREIGN KEY (character_id) REFERENCES public."character"(id);


--
-- Name: character_wearing head_fk; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.character_wearing
    ADD CONSTRAINT head_fk FOREIGN KEY (head) REFERENCES public.armor_inventory(id);


--
-- Name: character_wearing l_arm_fk; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.character_wearing
    ADD CONSTRAINT l_arm_fk FOREIGN KEY (l_arm) REFERENCES public.weapon_inventory(id);


--
-- Name: character_wearing legs_fk; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.character_wearing
    ADD CONSTRAINT legs_fk FOREIGN KEY (legs) REFERENCES public.armor_inventory(id);


--
-- Name: character_wearing r_arm_fk; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.character_wearing
    ADD CONSTRAINT r_arm_fk FOREIGN KEY (r_arm) REFERENCES public.weapon_inventory(id);


--
-- Name: character_wearing torso_fk; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.character_wearing
    ADD CONSTRAINT torso_fk FOREIGN KEY (torso) REFERENCES public.armor_inventory(id);


--
-- Name: user_characters user_id; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.user_characters
    ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- Name: campaign user_id; Type: FK CONSTRAINT; Schema: public; Owner: witcher_admin
--

ALTER TABLE ONLY public.campaign
    ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- PostgreSQL database dump complete
--

