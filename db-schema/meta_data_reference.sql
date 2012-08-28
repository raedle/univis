--
-- PostgreSQL database dump
--

-- Started on 2006-05-23 18:35:17 Westeuropäische Normalzeit

SET client_encoding = 'UTF8';
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 1630 (class 0 OID 113713)
-- Dependencies: 1298
-- Data for Name: meta_data_reference; Type: TABLE DATA; Schema: public; Owner: univis
--

INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1008, 'by_continent', 'kontinent', 'dim_kontinent');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1009, 'by_sub-continent', 'region', 'dim_subkontinent');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1010, 'by_country', 'land', 'dim_land');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1011, 'gender', 'geschlecht', 'bluep_geschlecht');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1012, 'eligibility', 'hzb', 'bluep_hzb');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1007, 'nationality', NULL, 'bluep_nation');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1000, 'students', NULL, 'sos_cube');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1001, 'term', 'fachsem', 'bluep_fachsem');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1002, 'by_term_group', 'fs', 'dim_fachsemestergruppen');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1003, 'by_term', 'gruppe', 'dim_fachsemester');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1004, 'degree', NULL, 'bluep_abschluss');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1005, 'by_degree_type', 'abschlussart', 'dim_abschlussarten');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1006, 'by_degree', 'abschluss', 'dim_abschluss');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1013, 'teaching_unit', 'org_einheit', 'bluep_lehreinheiten');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1014, 'by_faculty', NULL, 'dim_fakultaet');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1015, 'by_department', NULL, 'dim_abteilung');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1016, 'by_institute', NULL, 'dim_institut');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1017, 'by_chair', NULL, 'dim_lehrstuhl');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1018, 'period', 'semester', 'bluep_semester');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1019, 'by_year_academic', 'jahr', 'dim_academ_jahr');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1020, 'by_semester', 'semester', 'dim_semester');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1021, '#bluep_nation#', 'nation', 'bluep_nation');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1022, '#bluep_abschluss#', 'abschluss', 'bluep_abschluss');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2000, 'orders', NULL, 'cob_busa_cube');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2001, 'cost_categories', 'kostenart', 'bluep_kostenart');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2002, 'category', 'kategorie', 'dim_kostenkategorien');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2003, 'sub-category', 'unterkategorie', 'dim_kostenunterkategorien');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2004, 'class', 'kostenart', 'dim_kostenarten');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2005, 'funds', 'mittelherk', 'bluep_mittelherk');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2006, 'project', 'projnr', 'bluep_proj_inst');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2007, 'by_year', 'jahr', 'bluep_jahr');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2008, 'by_semi-annual', 'halbjahr', 'dim_halbjahre');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2009, 'by_quarter', 'quartal', 'dim_quartale');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2010, 'by_month', 'monat', 'dim_monate');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2011, '#bluep_zeit#', 'zeit', 'bluep_zeit');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2012, 'institution', 'institution', 'bluep_institution');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2013, 'administrative_unit', NULL, 'Adminstrative unit');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2014, 'unit', NULL, 'Unit');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2015, 'post', NULL, 'Post');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2016, 'service', NULL, 'dim_dienst');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2017, 'departments_only', NULL, 'Departments only');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2018, 'faculties_only', NULL, 'Faculties only');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2019, 'staff', NULL, 'Staff');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (2020, '#bluep_kostenart#', 'kostenart', 'bluep_kostenart');


-- Completed on 2006-05-23 18:35:17 Westeuropäische Normalzeit

--
-- PostgreSQL database dump complete
--

