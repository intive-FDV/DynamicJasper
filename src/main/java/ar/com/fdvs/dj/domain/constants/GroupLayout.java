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

package ar.com.fdvs.dj.domain.constants;

public class GroupLayout extends BaseDomainConstant {

	private static final long serialVersionUID = 1L;

	/**
	 * When true, the column headers will be printed
	 */
	private boolean printHeaders;

	/**
	 * If true, the value will be printed in an isolated line.
	 * If printHeaders is true, the headers will be shown below the current value
	 */
	private boolean showValueInHeader;

	/**
	 * If true, the value of column used as group criteria will be shown
	 * for each row.
	 */
	private boolean showValueForEachRow;

	/**
	 * used with showValueInHeader, if both are true, the column name will
	 * be printed before the value, i.e: columnX: Value y
	 */
	private boolean showColumnName;

	/**
	 * If true, the column of this group will not be printed
	 */
	private boolean hideColumn;

	public GroupLayout(boolean showValueInHeader,
			boolean showValueForEach,
			boolean showColumnName,
			boolean hideColumn,
			boolean printHeaders)
	{
		this.showValueInHeader = showValueInHeader;
		this.showValueForEachRow = showValueForEach;
		this.showColumnName = showColumnName;
		this.hideColumn = hideColumn;
		this.printHeaders = printHeaders;
	}

	public boolean isShowColumnName() {
		return showColumnName;
	}

	public boolean isShowValueForEachRow() {
		return showValueForEachRow;
	}

	public boolean isShowValueInHeader() {
		return showValueInHeader;
	}

	public static GroupLayout VALUE_IN_HEADER_AND_FOR_EACH 				= new GroupLayout(true,true,false,false,false);
	public static GroupLayout VALUE_IN_HEADER_AND_FOR_EACH_WITH_HEADERS = new GroupLayout(true,true,false,false,true);

	public static GroupLayout VALUE_IN_HEADER 								= new GroupLayout(true,false,false,true,false);
	public static GroupLayout VALUE_IN_HEADER_WITH_HEADERS 					= new GroupLayout(true,false,false,true,true);
	public static GroupLayout VALUE_IN_HEADER_WITH_HEADERS_AND_COLUMN_NAME 	= new GroupLayout(true,false,true,true,true);

	public static GroupLayout VALUE_FOR_EACH 				= new GroupLayout(false,true,false,false,false);
	public static GroupLayout VALUE_FOR_EACH_WITH_HEADERS   = new GroupLayout(false,true,false,false,true);

	public static GroupLayout EMPTY = new GroupLayout(false,false,false,true,false);

	public static GroupLayout DEFAULT = new GroupLayout(true,false,false,false,false);
	public static GroupLayout DEFAULT_WITH_HEADER = new GroupLayout(true,false,false,false,true);

	public boolean isHideColumn() {
		return hideColumn;
	}

	public void setHideColumn(boolean hideColumn) {
		this.hideColumn = hideColumn;
	}

	public boolean isPrintHeaders() {
		return printHeaders;
	}

}
