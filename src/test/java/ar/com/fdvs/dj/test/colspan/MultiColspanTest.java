package ar.com.fdvs.dj.test.colspan;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Calendar;
import java.util.Date;

public class MultiColspanTest extends BaseDjReportTest {

    @Override
    public DynamicReport buildReport() throws Exception {

        FastReportBuilder frb = new FastReportBuilder();

        frb.addColumn("State", "state", String.class.getName(), 30)
                .addColumn("Branch", "branch", String.class.getName(), 30)
                .addColumn("Item", "item", String.class.getName(), 50)
                .addColumn("Amount", "amount", Float.class.getName(), 60, true)
                .addColumn("Amount 1", "amount", Float.class.getName(), 60, true)
                .addColumn("Amount 2", "amount", Float.class.getName(), 60, true)
                .addColumn("Amount 3", "amount", Float.class.getName(), 60, true)
                .addGroups(2)

                .setTitle("November " + Calendar.getInstance().get(Calendar.YEAR) + " sales report")
                .setSubtitle("This report was generated at " + new Date())
                .setColumnsPerPage(1, 10)
                .setUseFullPageWidth(true);

        frb.setColspan(1, 2, "Estimated");
        frb.setColspan(4,2,"Other");

        DynamicReport dynamicReport = frb.build();
        dynamicReport.getOptions().getDefaultHeaderStyle().setBorder(Border.PEN_1_POINT());

        return dynamicReport;
    }

    public static void main(String[] args) throws Exception {
        MultiColspanTest test = new MultiColspanTest();
        test.testReport();
        JasperViewer.viewReport(test.jp);
    }
}
