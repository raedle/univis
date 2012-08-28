--
-- META DATA REFERENCE
--
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1008, 'by_continent', 'kontinent', 'dim_kontinent');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1009, 'by_sub-continent', 'region', 'dim_subkontinent');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1010, 'by_country', 'land', 'dim_land');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1011, 'gender', 'geschlecht', 'bluep_geschlecht');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1012, 'eligibility', 'hzb', 'bluep_hzb');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1007, 'nationality', NULL, 'bluep_nation');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1000, 'students', NULL, 'sos_cube');
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1001, 'term', 'NULL', 'bluep_fachsem');
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
INSERT INTO meta_data_reference (id, i18n_key, joinable, table_name) VALUES (1023, '#bluep_fachsem#', 'fachsem', 'bluep_fachsem');
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


--
-- META DIMENSION
--
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1001, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1002, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1003, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1004, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1005, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1006, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1007, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1008, true, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1009, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1010, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1011, true, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1012, true, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1013, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1014, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1015, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1016, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1017, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1018, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1019, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1020, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1021, false, true, false);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1022, true, true, false);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (1023, true, true, false);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2001, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2002, true, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2003, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2004, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2005, true, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2006, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2007, true, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2008, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2009, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2010, true, true, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2011, false, true, false);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2012, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2013, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2014, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2015, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2016, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2017, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2018, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2019, false, false, true);
INSERT INTO meta_dimension (data_reference_id, summable, parentable, visible) VALUES (2020, false, false, false);


--
-- META HIERARCHY
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
INSERT INTO meta_hierarchy (id, data_reference_id, parent_id, child_order) VALUES (1023, 1023, 1003, 0);
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


--
-- META CUBE
--
INSERT INTO meta_cube (data_reference_id, color_rgb, hierarchy_id) VALUES (1000, -65536, 1000);
INSERT INTO meta_cube (data_reference_id, color_rgb, hierarchy_id) VALUES (2000, -16776961, 2000);


--
-- META DICE BOX
--
INSERT INTO meta_dice_box (tree_fresh_id, dice_box_name) VALUES (1, 'UniVis Explorer');


--
-- META DIMENSION SUPPORTED CUBE
--
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1001, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1002, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1003, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1004, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1005, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1006, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1007, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1008, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1009, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1010, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1011, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1012, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1013, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1014, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1015, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1016, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1017, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1018, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1018, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1019, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1019, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1020, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1021, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1022, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (1023, 1000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2001, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2002, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2003, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2004, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2005, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2006, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2007, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2008, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2009, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2010, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2011, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2012, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2013, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2014, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2015, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2016, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2017, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2018, 2000);
INSERT INTO meta_dimension_supported_cube (dimension_id, cube_id) VALUES (2019, 2000);