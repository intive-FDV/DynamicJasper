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

package ar.com.fdvs.dj.test;


import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.test.util.ReportAssertions;
import ar.com.fdvs.dj.test.util.ReportContentExtractor;
import ar.com.fdvs.dj.util.SortUtils;
import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDjReportTest extends TestCase {

    public Map getParams() {
        return params;
    }

    protected static final Log log = LogFactory.getLog(BaseDjReportTest.class);

    protected JasperPrint jp;
    protected JasperReport jr;
    protected final Map<String, Object> params = new HashMap<String, Object>();
    protected DynamicReport dr;

    public abstract DynamicReport buildReport() throws Exception;

    public void testReport() throws Exception {
        dr = buildReport();

			/*
              Get a JRDataSource implementation
			 */
        JRDataSource ds = getDataSource();


			/*
			  Creates the JasperReport object, we pass as a Parameter
			  the DynamicReport, a new ClassicLayoutManager instance (this
			  one does the magic) and the JRDataSource
			 */
        jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);

			/*
			  Creates the JasperPrint object, we pass as a Parameter
			  the JasperReport object, and the JRDataSource
			 */
        log.debug("Filling the report");
        if (ds != null)
            jp = JasperFillManager.fillReport(jr, params, ds);
        else
            jp = JasperFillManager.fillReport(jr, params);

        log.debug("Filling done!");
        log.debug("Exporting the report (pdf, xls, etc)");
        exportReport();

        log.debug("test finished");

    }

    protected LayoutManager getLayoutManager() {
        return new ClassicLayoutManager();
    }

    protected void exportReport() throws Exception {
        ReportExporter.exportReport(jp, System.getProperty("user.dir") + "/target/reports/" + this.getClass().getSimpleName() + ".pdf");
        exportToJRXML();
    }

    protected void exportToJRXML() throws Exception {
        if (this.jr != null) {
            DynamicJasperHelper.generateJRXML(this.jr, "UTF-8", System.getProperty("user.dir") + "/target/reports/" + this.getClass().getSimpleName() + ".jrxml");

        } else {
            DynamicJasperHelper.generateJRXML(this.dr, this.getLayoutManager(), this.params, "UTF-8", System.getProperty("user.dir") + "/target/reports/" + this.getClass().getSimpleName() + ".jrxml");
        }
    }

    protected void exportToHTML() throws Exception {
        ReportExporter.exportReportHtml(this.jp, System.getProperty("user.dir") + "/target/reports/" + this.getClass().getSimpleName() + ".html");
    }

    /**
     * @return JRDataSource
     */
    protected JRDataSource getDataSource() {
        Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
        dummyCollection = SortUtils.sortCollection(dummyCollection, dr.getColumns());

        //here contains dummy hardcoded objects...
        return new JRBeanCollectionDataSource(dummyCollection);
    }

    public Collection getDummyCollectionSorted(List columnlist) {
        Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
        return SortUtils.sortCollection(dummyCollection, columnlist);

    }

    public DynamicReport getDynamicReport() {
        return dr;
    }

    /**
     * Uses a non blocking HSQL DB. Also uses HSQL default test data
     *
     * @return a Connection
     * @throws Exception
     */
    public static Connection createSQLConnection() throws Exception {
        Class.forName("org.hsqldb.jdbcDriver");
        return DriverManager.getConnection("jdbc:hsqldb:file:target/test-classes/hsql/test_dj_db", "sa", "");
    }

    public int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    // ========================================================================
    // Assertion Helper Methods
    // Added in 5.3.10 to enable content-based test validation
    // ========================================================================

    /**
     * Extract all text content from the report.
     * Useful for custom assertions and debugging test failures.
     *
     * @return List of all text content found in the report
     * @since 5.3.10
     */
    protected List<String> extractAllText() {
        return ReportContentExtractor.extractAllText(jp);
    }

    /**
     * Assert that a specific text value appears exactly the expected number of times.
     * This is a version-agnostic assertion that validates content presence rather
     * than element positions.
     *
     * <p>Example usage:</p>
     * <pre>
     * assertTextOccurs("Total Amount", 4);  // Verify group footer appears 4 times
     * assertTextOccurs("State", 1);         // Verify column header appears once
     * </pre>
     *
     * @param text The text value to search for
     * @param count The expected number of occurrences
     * @since 5.3.10
     */
    protected void assertTextOccurs(String text, int count) {
        ReportAssertions.assertTextOccurrences(jp, text, count);
    }

    /**
     * Assert that a column header with the specified text exists in the report.
     *
     * <p>Example usage:</p>
     * <pre>
     * assertColumnHeader("State");
     * assertColumnHeader("Amount");
     * </pre>
     *
     * @param headerText The column header text to search for
     * @since 5.3.10
     */
    protected void assertColumnHeader(String headerText) {
        ReportAssertions.assertColumnHeaderExists(jp, headerText);
    }

    /**
     * Assert that column headers appear in the specified order.
     *
     * <p>Example usage:</p>
     * <pre>
     * assertColumnHeadersInOrder("State", "Branch", "Amount");
     * </pre>
     *
     * @param headers The expected column headers in order
     * @since 5.3.10
     */
    protected void assertColumnHeadersInOrder(String... headers) {
        ReportAssertions.assertColumnHeadersInOrder(jp, headers);
    }

    /**
     * Assert that a specific text value appears at least once in the report.
     *
     * <p>Example usage:</p>
     * <pre>
     * assertTextExists("Florida");  // Verify data appears
     * </pre>
     *
     * @param text The text value to search for
     * @since 5.3.10
     */
    protected void assertTextExists(String text) {
        ReportAssertions.assertTextExists(jp, text);
    }

    /**
     * Assert that at least one text element matches the specified regex pattern.
     * Useful for validating formatted values (numbers, dates, etc.).
     *
     * <p>Example usage:</p>
     * <pre>
     * // Verify currency formatted values exist
     * assertTextMatchesPattern("\\$ [0-9,]+\\.[0-9]{2}");
     *
     * // Verify dates exist
     * assertTextMatchesPattern("[0-9]{2}/[0-9]{2}/[0-9]{4}");
     * </pre>
     *
     * @param pattern The regex pattern to match
     * @since 5.3.10
     */
    protected void assertTextMatchesPattern(String pattern) {
        ReportAssertions.assertTextMatchesPattern(jp, pattern);
    }

    /**
     * Assert that at least the specified number of text elements match the pattern.
     *
     * <p>Example usage:</p>
     * <pre>
     * // Verify at least 10 currency values
     * assertTextMatchesPatternCount("\\$ [0-9,]+\\.[0-9]{2}", 10);
     * </pre>
     *
     * @param pattern The regex pattern to match
     * @param minCount The minimum expected count
     * @since 5.3.10
     */
    protected void assertTextMatchesPatternCount(String pattern, int minCount) {
        ReportAssertions.assertTextMatchesPatternCount(jp, pattern, minCount);
    }

    /**
     * Assert that the report has at least the specified number of pages.
     *
     * <p>Example usage:</p>
     * <pre>
     * assertPageCount(2);  // Verify report has at least 2 pages
     * </pre>
     *
     * @param expectedPages The minimum expected number of pages
     * @since 5.3.10
     */
    protected void assertPageCount(int expectedPages) {
        ReportAssertions.assertPageCount(jp, expectedPages);
    }

    /**
     * Assert that the report is not empty (has content).
     * Validates that the report has at least one page with text content.
     *
     * <p>Example usage:</p>
     * <pre>
     * assertReportNotEmpty();  // Basic sanity check
     * </pre>
     *
     * @since 5.3.10
     */
    protected void assertReportNotEmpty() {
        ReportAssertions.assertNotEmpty(jp);
    }
}
