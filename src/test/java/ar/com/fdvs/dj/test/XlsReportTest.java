package ar.com.fdvs.dj.test;

import java.util.Date;

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.core.layout.ListLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import net.sf.jasperreports.view.JasperViewer;

public class XlsReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {


		/*
		  Creates the DynamicReportBuilder and sets the basic options for
		  the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		Style columDetail = new Style();
//		columDetail.setBorder(Border.THIN());

		drb.addColumn("State"			, "state"		, String.class.getName(), 30)
			.addColumn("Branch"			, "branch"		, String.class.getName(), 30)
			.addColumn("Product Line"	, "productLine"	, String.class.getName(), 50)
//			.addColumn("Item"			, "item"		, String.class.getName(), 50)
//			.addColumn("Item Code"		, "id"			, Long.class.getName()	, 30, true)
//			.addColumn("Quantity"		, "quantity"	, Long.class.getName()	, 60, true)
			.addColumn("Amount"			, "amount"		, Float.class.getName()	, 70, true,"0.00")
			.addColumn("Date"			, "date"		, Date.class.getName()	, 70, true, "dd/MM/yyyy",null)
			.addGroups(2) //Not used by the ListLayoutManager
			.setPrintColumnNames(true)
			.setIgnorePagination(true) //for Excel, we may dont want pagination, just a plain list
			.setMargins(0, 0, 0, 0)
			.setPageSizeAndOrientation(Page.Page_Letter_Landscape())
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setReportName("My Excel Report")
			.setDefaultStyles(null, null, null, columDetail)
			.setUseFullPageWidth(false);

		((PropertyColumn)drb.getColumn(0)).getColumnProperty().addFieldProperty("net.sf.jasperreports.export.xls.column.width.ratio","3.25f");

		DynamicReport dr = drb.build();

		DJGroup group = dr.getColumnsGroups().iterator().next();
		group.setLayout(GroupLayout.EMPTY); //not used by ListLayoutManager

		return dr;
	}

	protected LayoutManager getLayoutManager() {
		return new ListLayoutManager();
	}

	protected void exportReport() throws Exception {
		ReportExporter.exportReportXls(jp, System.getProperty("user.dir")+ "/target/" + this.getClass().getName() + ".xls");
	}


	public static void main(String[] args) throws Exception {
		XlsReportTest test = new XlsReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);
	}
}
