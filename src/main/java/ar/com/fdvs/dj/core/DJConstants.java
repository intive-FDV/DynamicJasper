/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ar.com.fdvs.dj.core;

import net.sf.jasperreports.engine.JRReport;
/**
 * See value of constants here: 
 * http://jasperreports.sourceforge.net/api/constant-values.html
 * @author mamana
 *
 */
public interface DJConstants {

	String HEADER = "header";
	String FOOTER = "footer";

	int SUBREPORT_PARAMETER_MAP_ORIGIN_PARAMETER = 0;
	int SUBREPORT_PARAMETER_MAP_ORIGIN_FIELD = 1;

	int DATA_SOURCE_ORIGIN_PARAMETER = 0;
	int DATA_SOURCE_ORIGIN_FIELD = 1;
	int DATA_SOURCE_ORIGIN_REPORT_DATASOURCE = 2;
	int DATA_SOURCE_ORIGIN_USE_REPORT_CONNECTION = 10;

	int DATA_SOURCE_TYPE_COLLECTION 			= 0;
	int DATA_SOURCE_TYPE_ARRAY 					= 1;
	int DATA_SOURCE_TYPE_JPA 					= 2;
	int DATA_SOURCE_TYPE_RESULTSET				= 3;
	int DATA_SOURCE_TYPE_XML					= 4;
	int DATA_SOURCE_TYPE_HIBERNATE_ITERATE		= 5;
	int DATA_SOURCE_TYPE_HIBERNATE_LIST			= 5;
	int DATA_SOURCE_TYPE_HIBERNATE_SCROLL		= 6;
	int DATA_SOURCE_TYPE_CSV					= 7;
	int DATA_SOURCE_TYPE_MONDRIAN				= 8;
	int DATA_SOURCE_TYPE_OGNL_VALUE_STACK		= 9;
	int DATA_SOURCE_TYPE_JRDATASOURCE			= 10;

	int DATA_SOURCE_TYPE_SQL_CONNECTION			= 100; //This is in DJ only, not in JasperReports

	int COLOR_SCHEMA_WHITE = 0;
	int COLOR_SCHEMA_VIOLET = 1;
	int COLOR_SCHEMA_PINK = 2;
	int COLOR_SCHEMA_PINK_AND_BROWN = 3;
	int COLOR_SCHEMA_LIGHT_GREEN = 4;
	int COLOR_SCHEMA_BLUE = 5;
	int COLOR_SCHEMA_GRAY = 6;

	String QUERY_LANGUAGE_SQL = "sql";
	String QUERY_LANGUAGE_EJBQL = "ejbql";
	String QUERY_LANGUAGE_HQL = "hql";
	String QUERY_LANGUAGE_XPATH = "xPath";
	String QUERY_LANGUAGE_MONDRIAN = "mdx";

	public static byte WHEN_NO_DATA_TYPE_NO_PAGES = JRReport.WHEN_NO_DATA_TYPE_NO_PAGES;
	public static byte WHEN_NO_DATA_TYPE_BLANK_PAGE = JRReport.WHEN_NO_DATA_TYPE_BLANK_PAGE;
	public static byte WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL = JRReport.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL;
	public static byte WHEN_NO_DATA_TYPE_NO_DATA_SECTION = JRReport.WHEN_NO_DATA_TYPE_NO_DATA_SECTION;

	public static byte WHEN_RESOURCE_MISSING_TYPE_EMPTY = JRReport.WHEN_RESOURCE_MISSING_TYPE_EMPTY;
	public static byte WHEN_RESOURCE_MISSING_TYPE_ERROR = JRReport.WHEN_RESOURCE_MISSING_TYPE_ERROR;
	public static byte WHEN_RESOURCE_MISSING_TYPE_KEY = JRReport.WHEN_RESOURCE_MISSING_TYPE_KEY;
	public static byte WHEN_RESOURCE_MISSING_TYPE_NULL = JRReport.WHEN_RESOURCE_MISSING_TYPE_NULL;

	int SUBREPORT_PARAM_ORIGIN_FIELD = 1;
	int SUBREPORT_PARAM_ORIGIN_PARAMETER = 2;
	int SUBREPORT_PARAM_ORIGIN_VARIABLE = 3;
	int SUBREPORT_PARAM_ORIGIN_CUSTOM = 4; //write your own expression

	String CUSTOM_EXPRESSION__PARAMETERS_MAP = "REPORT_PARAMETERS_MAP";
	
	public static String FORMAT_CSV = "CSV";
	public static String FORMAT_HTML = "HTML";
	public static String FORMAT_PDF = "PDF";
	public static String FORMAT_XLS = "XLS";
	public static String FORMAT_XML = "XML";


}
