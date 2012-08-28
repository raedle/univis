--
-- PostgreSQL database dump
--

-- Started on 2006-05-23 18:37:50 Westeuropäische Normalzeit

SET client_encoding = 'UTF8';
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 1632 (class 0 OID 113732)
-- Dependencies: 1302
-- Data for Name: meta_hierarchy; Type: TABLE DATA; Schema: public; Owner: univis
--

INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1, NULL, NULL, NULL);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1000, 1000, 1, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1001, 1001, 1000, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1004, 1004, 1000, 1);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1007, 1007, 1000, 2);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1011, 1011, 1000, 3);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1012, 1012, 1000, 4);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1013, 1013, 1000, 5);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1018, 1018, 1000, 6);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1002, 1002, 1001, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1003, 1003, 1002, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1005, 1005, 1004, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1006, 1006, 1005, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1008, 1008, 1007, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1009, 1009, 1008, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1010, 1010, 1009, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1014, 1014, 1013, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1015, 1015, 1014, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1016, 1016, 1015, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1017, 1017, 1015, 1);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1019, 1019, 1018, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1020, 1020, 1019, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1021, 1021, 1010, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1022, 1022, 1006, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2000, 2000, 1, 1);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2001, 2001, 2000, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2002, 2002, 2001, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2003, 2003, 2002, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2004, 2004, 2003, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2005, 2005, 2000, 1);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2006, 2006, 2000, 2);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2007, 1018, 2000, 3);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2008, 2007, 2007, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2015, 2012, 2000, 4);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2020, 1013, 2015, 1);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2009, 2008, 2008, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2010, 2009, 2009, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2011, 2010, 2010, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2012, 2011, 2011, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2013, 1019, 2007, 1);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2014, 1020, 2013, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2016, 2013, 2015, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2017, 2014, 2016, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2018, 2015, 2017, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2019, 2016, 2018, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2021, 1014, 2020, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2022, 2017, 2021, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2023, 1015, 2021, 1);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2024, 1016, 2023, 0);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2025, 1017, 2023, 1);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2026, 2018, 2020, 1);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2027, 2019, 2015, 2);
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (2028, 2020, 2004, 0);


-- Completed on 2006-05-23 18:37:51 Westeuropäische Normalzeit

--
-- PostgreSQL database dump complete
--

