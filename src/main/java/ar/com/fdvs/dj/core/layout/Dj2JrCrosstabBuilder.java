package ar.com.fdvs.dj.core.layout;

import java.awt.Color;

import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabBucket;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabCell;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabDataset;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabMeasure;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabRowGroup;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignDatasetRun;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabColumn;
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Transparency;

public class Dj2JrCrosstabBuilder {
	
	private static final Log log = LogFactory.getLog(Dj2JrCrosstabBuilder.class);
	private JasperDesign design;
	private JRDesignCrosstab jrcross;
	
	public JRDesignCrosstab createCrosstab(DJCrosstab djcross) {
		jrcross = new JRDesignCrosstab();
		jrcross.setWidth(djcross.getWidth());
		jrcross.setHeight(djcross.getHeight());
		
		/**
		 * Register COLUMNS
		 */
		DJCrosstabColumn[] cols = (DJCrosstabColumn[]) djcross.getColumns().toArray(new DJCrosstabColumn[]{});
		for (int i = 0; i < cols.length; i++) {
			DJCrosstabColumn crosstabColumn = cols[i];
			
			JRDesignCrosstabColumnGroup ctColGroup = new JRDesignCrosstabColumnGroup();
			ctColGroup.setName(crosstabColumn.getProperty().getProperty());
			ctColGroup.setHeight(crosstabColumn.getHeaderHeight());
			
			JRDesignCrosstabBucket bucket = new JRDesignCrosstabBucket();
			JRDesignExpression bucketExp = new JRDesignExpression();
			
			bucketExp.setValueClassName(crosstabColumn.getProperty().getValueClassName());
			bucketExp.setText("$F{"+crosstabColumn.getProperty().getProperty()+"}");
			
			bucket.setExpression(bucketExp);
			ctColGroup.setBucket(bucket);
			
			JRDesignCellContents colHeaer = new JRDesignCellContents();
			JRDesignTextField colTitle = new JRDesignTextField();
//			JRDesignExpression colTitleExp = ExpressionUtils.createStringExpression("$V{"+crosstabColumn.getProperty().getValueClassName()+"}");
			
			JRDesignExpression colTitleExp = new JRDesignExpression();
			colTitleExp.setValueClassName(crosstabColumn.getProperty().getValueClassName());
			colTitleExp.setText("$V{"+crosstabColumn.getProperty().getProperty()+"}");			
						
			
			colTitle.setExpression(colTitleExp);
			colTitle.setWidth(crosstabColumn.getWidth());
			colTitle.setHeight(crosstabColumn.getHeaderHeight());
			
			colTitle.setBorder(Border.THIN.getValue());
			colTitle.setBackcolor(Color.green);
			colTitle.setMode(Transparency.OPAQUE.getValue());	
			
			colHeaer.addElement(colTitle);
			ctColGroup.setHeader(colHeaer);	
			
			try {
				jrcross.addColumnGroup(ctColGroup);
			} catch (JRException e) {
				log.error(e.getMessage(),e);
			}
		}
		
		/**
		 * Register ROWS
		 */
		DJCrosstabRow[] rows = (DJCrosstabRow[]) djcross.getRows().toArray(new DJCrosstabRow[]{});
		for (int i =  0; i < rows.length; i++) {
			DJCrosstabRow crosstabRow = rows[i];
			
			JRDesignCrosstabRowGroup ctRowGroup = new JRDesignCrosstabRowGroup();
			ctRowGroup.setWidth(crosstabRow.getHeaderWidth());
			ctRowGroup.setName(crosstabRow.getProperty().getProperty());
			
			JRDesignCrosstabBucket rowBucket = new JRDesignCrosstabBucket();
			ctRowGroup.setBucket(rowBucket);		
			JRDesignExpression bucketExp = new JRDesignExpression();
			bucketExp.setValueClass(String.class);
			bucketExp.setText("$F{"+crosstabRow.getProperty().getProperty()+"}");
			rowBucket.setExpression(bucketExp);
			
			
			JRDesignCellContents rowHeader = new JRDesignCellContents();
			JRDesignTextField rowTitle = new JRDesignTextField();
			
			JRDesignExpression rowTitExp = new JRDesignExpression();
			rowTitExp.setValueClassName(crosstabRow.getProperty().getValueClassName());
			rowTitExp.setText("$V{"+crosstabRow.getProperty().getProperty()+"}");			
			
			rowTitle.setExpression(rowTitExp);
			rowTitle.setHeight(crosstabRow.getHeight());
			rowTitle.setWidth(crosstabRow.getHeaderWidth());
			
			rowTitle.setBorder(Border.THIN.getValue());
			rowTitle.setBackcolor(Color.pink);
			rowTitle.setMode(Transparency.OPAQUE.getValue());		
			rowHeader.addElement(rowTitle);
			ctRowGroup.setHeader(rowHeader );
			
			try {
				jrcross.addRowGroup(ctRowGroup);
			} catch (JRException e) {
				log.error(e.getMessage(),e);
			}			
			
		}
		
		/**
		 * Measure
		 * //FIXME Measures can be more than one!!!
		 */
		JRDesignCrosstabMeasure measure = new JRDesignCrosstabMeasure();
		measure.setName(djcross.getMeasure().getProperty());
		measure.setCalculation(JRDesignVariable.CALCULATION_SUM);
		measure.setValueClassName(Float.class.getName());
		JRDesignExpression valueExp = new JRDesignExpression();
		valueExp.setValueClassName(djcross.getMeasure().getValueClassName());
		valueExp.setText("$F{"+djcross.getMeasure().getProperty()+"}");
		measure.setValueExpression(valueExp);	
		
		try {
			jrcross.addMeasure(measure);
		} catch (JRException e) {
			log.error(e.getMessage(),e);
		}		
		
		
		/**
		 * Create CELLS
		 */
		for (int i = cols.length; i >= 0; i--) {
			for (int j =  rows.length; j >= 0; j--) {
				DJCrosstabColumn crosstabColumn = null;
				DJCrosstabRow crosstabRow = null;
				if (i != cols.length)
					crosstabColumn = cols[i];
				
				if (j != rows.length)
					crosstabRow = rows[j];
				
				JRDesignCrosstabCell cell = new JRDesignCrosstabCell();
				
				if (i == cols.length)
					cell.setWidth(Integer.valueOf(cols[i-1].getWidth()));
				else
					cell.setWidth(Integer.valueOf(crosstabColumn.getWidth()));
				
				if (j == rows.length)
					cell.setHeight(Integer.valueOf(rows[j-1].getHeight()));
				else
					cell.setHeight(Integer.valueOf(crosstabRow.getHeight()));
				
				
				
				JRDesignCellContents contents = new JRDesignCellContents();
				JRDesignRectangle designElem = new JRDesignRectangle();
				designElem.setWidth(20);
				designElem.setHeight(20);
				designElem.setMode(JRDesignElement.MODE_OPAQUE);
				designElem.setBackcolor(Color.blue);
				contents.addElement(designElem);
				
				JRDesignTextField element = new JRDesignTextField();
				element.setWidth(50);
				element.setHeight(30);
				
				JRDesignExpression measureExp = new JRDesignExpression();
				measureExp.setValueClassName(djcross.getMeasure().getValueClassName());
				measureExp.setText("$V{"+djcross.getMeasure().getProperty()+"}");
				
				element.setExpression(measureExp);
				
				
				element.setBackcolor(Color.cyan);
				element.setMode(Transparency.OPAQUE.getValue());
				contents.addElement(element);		
				
				cell.setContents(contents);		
				
				if (crosstabColumn != null)
					cell.setColumnTotalGroup(crosstabColumn.getProperty().getProperty());
				
				if (crosstabRow != null)
					cell.setRowTotalGroup(crosstabRow.getProperty().getProperty());
				
				try {
					jrcross.addCell(cell);
				} catch (JRException e) {
					log.error(e.getMessage(),e);
				}
				
			}

		}
		
		/**
		 * Register DATASET
		 */
		JRDesignCrosstabDataset dataset = new JRDesignCrosstabDataset();
		dataset.setDataPreSorted(false); 		
		JRDesignDatasetRun datasetRun = new JRDesignDatasetRun();
		datasetRun.setDatasetName("sub1");
		JRDesignExpression exp = new JRDesignExpression();
		exp.setValueClass(JRDataSource.class);	
		exp.setText("new "+JRBeanCollectionDataSource.class.getName()+"((java.util.Collection)$P{REPORT_PARAMETERS_MAP}.get( \"sr\" ))");
		datasetRun.setDataSourceExpression(exp);
		
		
		dataset.setDatasetRun(datasetRun);
		
		
		JRDesignDataset jrDataset = new JRDesignDataset(false);
		jrDataset.setName("sub1");

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
		field.setName(djcross.getMeasure().getProperty());
		field.setValueClassName(djcross.getMeasure().getValueClassName());
		try {
			jrDataset.addField(field);
		} catch (JRException e) {			
			log.error(e.getMessage(),e);
		}
		
		
		jrcross.setDataset(dataset);
		
		try {
				if ( design.getDatasetMap().containsKey(jrDataset.getName())==false)
					design.addDataset(jrDataset);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		
		return jrcross;
	}

	public void setDesign(JasperDesign design) {
		this.design = design;
	}

}
