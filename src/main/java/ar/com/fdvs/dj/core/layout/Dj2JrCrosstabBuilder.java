/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.core.layout;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabBucket;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabCell;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabDataset;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabMeasure;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabRowGroup;
import net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.base.JRBaseBox;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignDatasetRun;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabColumn;
import ar.com.fdvs.dj.domain.DJCrosstabMeasure;
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.util.ExpressionUtils;

public class Dj2JrCrosstabBuilder {

	private static final Log log = LogFactory.getLog(Dj2JrCrosstabBuilder.class);
	private JasperDesign design;
	private JRDesignCrosstab jrcross;
	private DJCrosstab djcross;
	private DJCrosstabColumn[] cols;
	private DJCrosstabRow[] rows;
	private Color[][] colors;
	private AbstractLayoutManager layoutManager;

	public JRDesignCrosstab createCrosstab(DJCrosstab djcrosstab, AbstractLayoutManager layoutManager) {
		this.djcross = djcrosstab;
		this.layoutManager = layoutManager;
		this.design = layoutManager.getDesign();

		jrcross = new JRDesignCrosstab();

		jrcross.setPositionType(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP);

		cols = (DJCrosstabColumn[]) djcrosstab.getColumns().toArray(new DJCrosstabColumn[]{});
		rows = (DJCrosstabRow[]) djcrosstab.getRows().toArray(new DJCrosstabRow[]{});

		initColors();

		/**
		 * Set the size
		 */
		setCrosstabOptions();

		/**
		 * Register COLUMNS
		 */
		registerColumns();

		/**
		 * Register ROWS
		 */
		registerRows();

		/**
		 * Measures
		 */
		registerMeasures();


		/**
		 * Create CELLS
		 */
		createCells();


		/**
		 * Create main header cell
		 */
		createMainHeaderCell();

		/**
		 * Register DATASET
		 */
		registerDataSet(djcrosstab);

		return jrcross;
	}

	/**
	 * Sets the options contained in the DJCrosstab to the JRDesignCrosstab.
	 * Also fits the correct width
	 */
	private void setCrosstabOptions() {
		if (djcross.isUseFullWidth()){
			jrcross.setWidth(layoutManager.getReport().getOptions().getPrintableWidth());
		} else {
			jrcross.setWidth(djcross.getWidth());
		}
		jrcross.setHeight(djcross.getHeight());

		jrcross.setColumnBreakOffset(djcross.getColumnBreakOffset());

	}

	private void createMainHeaderCell() {
		JRDesignCellContents contents = new JRDesignCellContents();

		contents.setBackcolor(colors[colors.length-1][colors[0].length-1]);
		contents.setMode(new Byte(Transparency.OPAQUE.getValue()));

		jrcross.setHeaderCell(contents);

		JRDesignTextField element = new JRDesignTextField();
		String text = "";
		int auxHeight = 0;
		int auxWidth = 0;

		if (djcross.isAutomaticTitle())
			text = createAutomaticMainHeaderTitle();
		else if (djcross.getMainHeaderTitle() != null)
			text = "\"" + djcross.getMainHeaderTitle() +  "\"";

		for (Iterator iterator = djcross.getColumns().iterator(); iterator.hasNext();) {
			DJCrosstabColumn col = (DJCrosstabColumn) iterator.next();
			auxHeight += col.getHeaderHeight();
		}
		for (Iterator iterator = djcross.getRows().iterator(); iterator.hasNext();) {
			DJCrosstabRow row = (DJCrosstabRow) iterator.next();
			auxWidth += row.getHeaderWidth();
		}

		JRDesignExpression exp = ExpressionUtils.createStringExpression(text);
		element.setExpression(exp);

		element.setWidth(auxWidth);
		element.setHeight(auxHeight);
		element.setStretchWithOverflow(true);

		if (djcross.getHeaderStyle() != null)
			layoutManager.applyStyleToElement(djcross.getHeaderStyle(), element);

		applyCellBorder(contents);
		contents.addElement(element);
	}

	/**
	 * @param text
	 * @return
	 */
	private String createAutomaticMainHeaderTitle() {
		String text = "";
		for (Iterator iterator = djcross.getColumns().iterator(); iterator.hasNext();) {
			DJCrosstabColumn col = (DJCrosstabColumn) iterator.next();
//			auxHeight += col.getHeaderHeight();
			text += col.getTitle();
			if (iterator.hasNext())
				text += ", ";
		}
		text += "\\nvs.\\n";
		for (Iterator iterator = djcross.getRows().iterator(); iterator.hasNext();) {
			DJCrosstabRow row = (DJCrosstabRow) iterator.next();
//			auxWidth += row.getHeaderWidth();
			text += row.getTitle();
			if (iterator.hasNext())
				text += ", ";
		}
		text = "\"" + text + "\"";
		return text;
	}

	private void initColors() {
		colors = CrossTabColorShema.createSchema(djcross.getColorScheme(), cols.length, rows.length);

	}


	/**
	 * @param djcrosstab
	 */
	private void registerDataSet(DJCrosstab djcrosstab) {
		JRDesignCrosstabDataset dataset = new JRDesignCrosstabDataset();
		dataset.setDataPreSorted(djcrosstab.getDatasource().isPreSorted());
		JRDesignDatasetRun datasetRun = new JRDesignDatasetRun();
//		datasetRun.setDatasetName("sub1");
		JRDesignExpression exp = ExpressionUtils.getDataSourceExpression(djcrosstab.getDatasource());
		datasetRun.setDataSourceExpression(exp);



		dataset.setDatasetRun(datasetRun);


		JRDesignDataset jrDataset = new JRDesignDataset(false);
//		jrDataset.setName("sub1");

		for (int i =  rows.length-1; i >= 0; i--) {
			DJCrosstabRow crosstabRow = rows[i];
			JRDesignField field = new JRDesignField();
			field.setName(crosstabRow.getProperty().getProperty());
			field.setValueClassName(crosstabRow.getProperty().getValueClassName());
			try {
				jrDataset.addField(field);
			} catch (JRException e) {
				log.error(e.getMessage(),e);
			}
		}
		for (int i = cols.length-1; i >= 0; i--) {
			DJCrosstabColumn crosstabColumn = cols[i];
			JRDesignField field = new JRDesignField();
			field.setName(crosstabColumn.getProperty().getProperty());
			field.setValueClassName(crosstabColumn.getProperty().getValueClassName());
			try {
				jrDataset.addField(field);
			} catch (JRException e) {
				log.error(e.getMessage(),e);
			}
		}
		JRDesignField field = new JRDesignField();
		field.setName(djcrosstab.getMeasure(0).getProperty().getProperty());
		field.setValueClassName(djcrosstab.getMeasure(0).getProperty().getValueClassName());
		try {
			jrDataset.addField(field);
		} catch (JRException e) {
			log.error(e.getMessage(),e);
		}


		jrcross.setDataset(dataset);
		Random rd = new Random();
		String dsName = "crosstabDataSource_" + rd.nextLong();

		while (design.getDatasetMap().containsKey(dsName)){
			dsName = "crosstabDataSource_" + rd.nextLong();
		}

		datasetRun.setDatasetName(dsName);
		jrDataset.setName(dsName);

		log.debug("Crosstab dataset name = " + dsName);
		try {
				if ( design.getDatasetMap().containsKey(jrDataset.getName())==false)
					design.addDataset(jrDataset);
			} catch (JRException e) {
				//Will never happen
				log.error(e.getMessage(),e);
			}
	}

	/**
	 * @param djcross
	 */
	private void createCells() {
		/**
		 * The way to create the cells is like this:
		 *
		 * the result is a matrix of (cols+1)*(rows+1) cells.
		 * Each cell has 2 properties that describes wich row and column they belong (like coordinates).
		 *
		 * null/null	| col(n)/null	| ...	| col(1)/null
		 * --------------------------------------------------
		 * null/row(n)	| col(n)/row(n)	| ...	| col(1)/row(n)
		 * --------------------------------------------------
		 * null/...		| col(n)/...	| ...	| col(1)/...
		 * --------------------------------------------------
		 * null/row(1)	| col(n)/row(1)	| ...	| col(1)/row(1)
		 *
		 *
		 * you get this matrix with this two vectors
		 * cols: {null, col(n), ..., col(1)}
		 * rows: {null, row(n), ..., row(1)}
		 *
		 * where the col(n) is the outer most column, and row(n) is the outer most row in the crosstab
		 *
		 * The cell with null/null is the inner most cell in the crosstab
		 */
		DJCrosstabColumn auxCol = new DJCrosstabColumn();
		DJCrosstabRow auxRow = new DJCrosstabRow();
		try {
			BeanUtils.copyProperties(auxCol, djcross.getColumns().get(djcross.getColumns().size()-1));
			BeanUtils.copyProperties(auxRow, djcross.getRows().get(djcross.getRows().size()-1));
		} catch (Exception e) {
			log.error(e.getMessage(),e); //must not happend
		}
		auxCol.setProperty(null);
		auxRow.setProperty(null);

		List auxColsList = new ArrayList(djcross.getColumns());
		auxColsList.add(auxCol);
		List auxRowsList = new ArrayList(djcross.getRows());
		auxRowsList.add(auxRow);

		DJCrosstabColumn[] auxCols = (DJCrosstabColumn[]) auxColsList.toArray(new DJCrosstabColumn[]{});
		DJCrosstabRow[] auxRows = (DJCrosstabRow[]) auxRowsList.toArray(new DJCrosstabRow[]{});


		for (int i = auxCols.length-1; i >= 0; i--) {
			for (int j =  auxRows.length-1; j >= 0; j--) {
				DJCrosstabColumn crosstabColumn = auxCols[i];
				DJCrosstabRow crosstabRow = auxRows[j];

				JRDesignCrosstabCell cell = new JRDesignCrosstabCell();

				cell.setWidth(new Integer(crosstabColumn.getWidth()));
				cell.setHeight(new Integer(crosstabRow.getHeight()));

				if (crosstabColumn.getProperty() != null)
					cell.setColumnTotalGroup(crosstabColumn.getProperty().getProperty());

				if (crosstabRow.getProperty() != null)
					cell.setRowTotalGroup(crosstabRow.getProperty().getProperty());


				JRDesignCellContents contents = new JRDesignCellContents();

				JRDesignTextField element = new JRDesignTextField();
				element.setWidth(crosstabColumn.getWidth());
				element.setHeight(crosstabRow.getHeight());

				JRDesignExpression measureExp = new JRDesignExpression();
				DJCrosstabMeasure measure = djcross.getMeasure(0);
				measureExp.setValueClassName(measure.getProperty().getValueClassName());
				measureExp.setText("$V{"+measure.getProperty().getProperty()+"}");

				element.setExpression(measureExp);

				/**
				 * Is there any style for this object?
				 */
				if (crosstabRow.getProperty() == null && crosstabColumn.getProperty() == null && measure.getStyle() != null ){
					//this is the inner most cell
					layoutManager.applyStyleToElement(measure.getStyle() , element);
				} else if (crosstabRow.getTotalStyle() != null) {
					layoutManager.applyStyleToElement(crosstabRow.getTotalStyle(), element);
				}
				else if (crosstabColumn.getTotalStyle() != null) {
					layoutManager.applyStyleToElement(crosstabColumn.getTotalStyle(), element);
				}

//				if ((i == auxCols.length-1 &&  j != auxRows.length-1) || (i != auxCols.length-1 &&  j != auxRows.length-1)){
//					cell.setWidth(Integer.valueOf( 100));
//				}
//				if (crosstabColumn.getProperty() != null && j != auxRows.length-1 && crosstabRow.getTotalHeaderHeight() != 0){
//					cell.setWidth(Integer.valueOf( crosstabRow.getTotalHeaderHeight() ));
//				}
//				if (i != auxCols.length-1 && j != auxRows.length-1 && crosstabRow.getTotalHeaderHeight() != 0){
//					cell.setWidth(Integer.valueOf( crosstabRow.getTotalHeaderHeight() ));
//				}


				contents.setMode(new Byte(Transparency.OPAQUE.getValue()));
				contents.setBackcolor(colors[i][j]);
				contents.addElement(element);

				applyCellBorder(contents);

				cell.setContents(contents);


				try {
					jrcross.addCell(cell);
				} catch (JRException e) {
					log.error(e.getMessage(),e);
				}

			}

		}
	}

	/**
	 * @param djcross
	 */
	private void registerMeasures() {
		for (Iterator iterator = djcross.getMeasures().iterator(); iterator.hasNext();) {
			DJCrosstabMeasure djmeasure = (DJCrosstabMeasure) iterator.next();

			JRDesignCrosstabMeasure measure = new JRDesignCrosstabMeasure();
			measure.setName(djmeasure.getProperty().getProperty());
			measure.setCalculation(djmeasure.getOperation().getValue());
			measure.setValueClassName(djmeasure.getProperty().getValueClassName());
			JRDesignExpression valueExp = new JRDesignExpression();
			valueExp.setValueClassName(djmeasure.getProperty().getValueClassName());
			valueExp.setText("$F{"+djmeasure.getProperty().getProperty()+"}");
			measure.setValueExpression(valueExp);

			try {
				jrcross.addMeasure(measure);
			} catch (JRException e) {
				log.error(e.getMessage(),e);
			}
		}
	}

	/**
	 * Register the Rowgroup buckets and places the header cells for the rows
	 */
	private void registerRows() {
		for (int i =  0; i < rows.length; i++) {
			DJCrosstabRow crosstabRow = rows[i];

			JRDesignCrosstabRowGroup ctRowGroup = new JRDesignCrosstabRowGroup();
			ctRowGroup.setWidth(crosstabRow.getHeaderWidth());
			ctRowGroup.setName(crosstabRow.getProperty().getProperty());

			JRDesignCrosstabBucket rowBucket = new JRDesignCrosstabBucket();
			ctRowGroup.setBucket(rowBucket);

			JRDesignExpression bucketExp = ExpressionUtils.createExpression("$F{"+crosstabRow.getProperty().getProperty()+"}", crosstabRow.getProperty().getValueClassName());
			rowBucket.setExpression(bucketExp);


			JRDesignCellContents rowHeaderContents = new JRDesignCellContents();
			JRDesignTextField rowTitle = new JRDesignTextField();

			JRDesignExpression rowTitExp = new JRDesignExpression();
			rowTitExp.setValueClassName(crosstabRow.getProperty().getValueClassName());
			rowTitExp.setText("$V{"+crosstabRow.getProperty().getProperty()+"}");

			rowTitle.setExpression(rowTitExp);
			rowTitle.setWidth(crosstabRow.getHeaderWidth());

			//The width can be the sum of the with of all the rows starting from the current one, up to the inner most one.
			int auxHeight = getRowHeaderMaxHeight(crosstabRow);
//			int auxHeight = crosstabRow.getHeight(); //FIXME getRowHeaderMaxHeight() must be FIXED because it breaks when 1rs row sho total and 2nd doesnt
			rowTitle.setHeight(auxHeight);


			if (crosstabRow.getHeaderStyle() != null)
				layoutManager.applyStyleToElement(crosstabRow.getHeaderStyle(), rowTitle);

			rowHeaderContents.addElement(rowTitle);
			rowHeaderContents.setBackcolor(colors[cols.length-1][i]);
//			rowHeaderContents.setBackcolor(colors[0][0]);
//			rowHeaderContents.setBackcolor(Color.blue);
			rowHeaderContents.setMode(new Byte(Transparency.OPAQUE.getValue()));
			applyCellBorder(rowHeaderContents);

			ctRowGroup.setHeader(rowHeaderContents );

			if (crosstabRow.isShowTotals())
				createRowTotalHeader(ctRowGroup,crosstabRow);


			try {
				jrcross.addRowGroup(ctRowGroup);
			} catch (JRException e) {
				log.error(e.getMessage(),e);
			}

		}
	}

	/**
	 * @param crosstabRow
	 * @return
	 */
	private int getRowHeaderMaxHeight(DJCrosstabRow crosstabRow) {
		int auxHeight = crosstabRow.getHeight();
		boolean found = false;
		for (Iterator iterator = djcross.getRows().iterator(); iterator.hasNext();) {
			DJCrosstabRow row = (DJCrosstabRow) iterator.next();
			if (!row.equals(crosstabRow) && found == false){
				continue;
			} else {
				found = true;
			}

			if (row.equals(crosstabRow))
				continue;

			if (row.isShowTotals())
				auxHeight += row.getHeight();
		}
		return auxHeight;
	}

	/**
	 * Registers the Columngroup Buckets and creates the header cell for the columns
	 */
	private void registerColumns() {
		for (int i = 0; i < cols.length; i++) {
			DJCrosstabColumn crosstabColumn = cols[i];

			JRDesignCrosstabColumnGroup ctColGroup = new JRDesignCrosstabColumnGroup();
			ctColGroup.setName(crosstabColumn.getProperty().getProperty());
			ctColGroup.setHeight(crosstabColumn.getHeaderHeight());

			JRDesignCrosstabBucket bucket = new JRDesignCrosstabBucket();

			JRDesignExpression bucketExp = ExpressionUtils.createExpression("$F{"+crosstabColumn.getProperty().getProperty()+"}", crosstabColumn.getProperty().getValueClassName());
			bucket.setExpression(bucketExp);

			ctColGroup.setBucket(bucket);

			JRDesignCellContents colHeaerContent = new JRDesignCellContents();
			JRDesignTextField colTitle = new JRDesignTextField();

			JRDesignExpression colTitleExp = new JRDesignExpression();
			colTitleExp.setValueClassName(crosstabColumn.getProperty().getValueClassName());
			colTitleExp.setText("$V{"+crosstabColumn.getProperty().getProperty()+"}");


			colTitle.setExpression(colTitleExp);
			colTitle.setWidth(crosstabColumn.getWidth());
			colTitle.setHeight(crosstabColumn.getHeaderHeight());

			//The height can be the sum of the heights of all the columns starting from the current one, up to the inner most one.
			int auxWidth = calculateRowHeaderMaxWidth(crosstabColumn);
			colTitle.setWidth(auxWidth);

			if (crosstabColumn.getHeaderStyle() != null)
				layoutManager.applyStyleToElement(crosstabColumn.getHeaderStyle(),colTitle);


			colHeaerContent.addElement(colTitle);
			colHeaerContent.setBackcolor(colors[i][rows.length-1]);
			colHeaerContent.setMode(new Byte(Transparency.OPAQUE.getValue()));
			applyCellBorder(colHeaerContent);

			ctColGroup.setHeader(colHeaerContent);

			if (crosstabColumn.isShowTotals())
				createColumTotalHeader(ctColGroup,crosstabColumn);


			try {
				jrcross.addColumnGroup(ctColGroup);
			} catch (JRException e) {
				log.error(e.getMessage(),e);
			}
		}
	}

	/**
	 * The max possible width can be calculated doing the sum of of the inner cells and its totals
	 * @param crosstabColumn
	 * @return
	 */
	private int calculateRowHeaderMaxWidth(DJCrosstabColumn crosstabColumn) {
		int auxWidth = 0;
		boolean firstTime = true;
		List auxList = new ArrayList(djcross.getColumns());
		Collections.reverse(auxList);
		for (Iterator iterator = auxList.iterator(); iterator.hasNext();) {
			DJCrosstabColumn col = (DJCrosstabColumn) iterator.next();

			if (col.equals(crosstabColumn)){
				if (auxWidth == 0)
					auxWidth = col.getWidth();
				break;
			}

			if (firstTime){
				auxWidth += col.getWidth();
				firstTime = false;
			}
			if (col.isShowTotals()) {
				auxWidth += col.getWidth();
			}
		}
		return auxWidth;
	}


	private void createRowTotalHeader(JRDesignCrosstabRowGroup ctRowGroup, DJCrosstabRow crosstabRow) {
		JRDesignCellContents totalHeaderContent = new JRDesignCellContents();
		ctRowGroup.setTotalHeader(totalHeaderContent);
		ctRowGroup.setTotalPosition(BucketDefinition.TOTAL_POSITION_END); //FIXME the total can be at the end of a group or at the beginin

		totalHeaderContent.setBackcolor(colors[colors.length/2][0]);
		totalHeaderContent.setMode(new Byte(Transparency.OPAQUE.getValue()));

		JRDesignTextField element = new JRDesignTextField();
		JRDesignExpression exp = ExpressionUtils.createExpression("\"Total "+crosstabRow.getTitle()+"\"",String.class);
		element.setExpression(exp);
		element.setHeight(crosstabRow.getHeight());

		if (crosstabRow.getTotalHeaderStyle() != null)
			layoutManager.applyStyleToElement(crosstabRow.getTotalHeaderStyle(), element);

		//The width can be the sum of the with of all the rows starting from the current one, up to the inner most one.
		int auxWidth = 0;
		boolean found = false;
		for (Iterator iterator = djcross.getRows().iterator(); iterator.hasNext();) {
			DJCrosstabRow row = (DJCrosstabRow) iterator.next();
			if (!row.equals(crosstabRow) && found == false){
				continue;
			} else {
				found = true;
			}

			auxWidth += row.getHeaderWidth();
		}
		element.setWidth(auxWidth);

		applyCellBorder(totalHeaderContent);

		totalHeaderContent.addElement(element);
	}

	/**
	 * @param cellContent
	 */
	private void applyCellBorder(JRDesignCellContents cellContent) {
		if (djcross.getCellBorder() != null && !djcross.getCellBorder().equals(Border.NO_BORDER)){
			JRBaseBox box = new JRBaseBox();
//			box.setBorder(djcross.getCellBorder().getValue());
			box.setBottomBorder(djcross.getCellBorder().getValue());
			box.setRightBorder(djcross.getCellBorder().getValue());
			box.setBorderColor(Color.black);
			cellContent.setBox(box);
		}
	}

	private void createColumTotalHeader(JRDesignCrosstabColumnGroup ctColGroup, DJCrosstabColumn crosstabColumn) {
		JRDesignCellContents totalHeaderContent = new JRDesignCellContents();
		ctColGroup.setTotalHeader(totalHeaderContent);
		ctColGroup.setTotalPosition(BucketDefinition.TOTAL_POSITION_END);

		totalHeaderContent.setBackcolor(colors[colors.length/2][colors[0].length/2]);
		totalHeaderContent.setMode(new Byte(Transparency.OPAQUE.getValue()));

		JRDesignExpression exp = ExpressionUtils.createExpression("\"Total "+crosstabColumn.getTitle()+"\"",String.class);
		JRDesignTextField element = new JRDesignTextField();
		element.setExpression(exp);
		element.setWidth(crosstabColumn.getWidth());


		if (crosstabColumn.getTotalHeaderStyle() != null)
			layoutManager.applyStyleToElement(crosstabColumn.getTotalHeaderStyle(), element);

		//The height can be the sum of the heights of all the columns starting from the current one, up to the inner most one.
		int auxWidth = 0;
		boolean found = false;
		for (Iterator iterator = djcross.getColumns().iterator(); iterator.hasNext();) {
			DJCrosstabColumn col = (DJCrosstabColumn) iterator.next();
			if (!col.equals(crosstabColumn) && found == false){
				continue;
			} else {
				found = true;
			}

			auxWidth += col.getHeaderHeight();
		}
		element.setHeight(auxWidth);

		applyCellBorder(totalHeaderContent);

		totalHeaderContent.addElement(element);

	}

}
