package ar.com.fdvs.dj.core;

public interface DJConstants {
	
	public static final String HEADER = "header";
	public static final String FOOTER = "footer";
	
	int SUBREPORT_PARAMETER_MAP_ORIGIN_PARAMETER = 0;
	int SUBREPORT_PARAMETER_MAP_ORIGIN_FIELD = 1;

	/**
	 * @deprecated use {@link #DATA_SOURCE_ORIGIN_PARAMETER}
	 */
	int SUBREPORT_DATA_SOURCE_ORIGIN_PARAMETER = 0;
	
	/**
	 * @deprecated use {@link #DATA_SOURCE_ORIGIN_FIELD}
	 */
	int SUBREPORT_DATA_SOURCE_ORIGIN_FIELD = 1;
	
	/**
	 * @deprecated use {@link #DATA_SOURCE_ORIGIN_INTERNAL}
	 */
	int SUBREPORT_DATA_SOURCE_ORIGIN_INTERNAL = 2;

	int DATA_SOURCE_ORIGIN_PARAMETER = 0;
	int DATA_SOURCE_ORIGIN_FIELD = 1;
	int DATA_SOURCE_ORIGIN_INTERNAL = 2;

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

}
