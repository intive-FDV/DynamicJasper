package ar.com.fdvs.dj.test;

import junit.framework.TestCase;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ReflectiveReportBuilder;
import ar.com.fdvs.dj.util.SortUtils;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReflectiveReportTest extends TestCase {

	private DynamicReport buildReport(final Collection _data) {
		return new ReflectiveReportBuilder(_data).build();
	}

	private DynamicReport buildOrderedReport(final Collection _data, final String[] _properties) {
		return new ReflectiveReportBuilder(_data, _properties).addGroups(3).build();
	}

	public void testReport() {
        final Collection data = TestRepositoryProducts.getDummyCollection();
        doReport(buildReport(data), data);
    }

	public void testOrderedReport() {
        final Collection data = TestRepositoryProducts.getDummyCollection();
        final List items = SortUtils.sortCollection(data, Arrays.asList(new String[]{"productLine", "item", "state"}));
        doReport(buildOrderedReport(items, new String[]{"productLine", "item", "state", "id", "branch", "quantity", "amount"}), items);
    }

	public void doReport(final DynamicReport _report, final Collection _data) {
        final JRDataSource dataSource = new JRBeanCollectionDataSource(_data);
        final JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(_report, new ClassicLayoutManager(), dataSource);
        JasperViewer.viewReport(jasperPrint);
        try {
			ReportExporter.exportReport(jasperPrint, System.getProperty("user.dir")+ "/target/ReflectiveReportTest.pdf");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] _args) {
        final ReflectiveReportTest reportTest = new ReflectiveReportTest();
        reportTest.testReport();
        reportTest.testOrderedReport();
	}
}
