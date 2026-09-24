---
layout: page
title: Changes
permalink: /changes/
---

{% include nav.html %}

# Changes

Full version history of DynamicJasper. Source: [`src/changes/changes.xml`](https://github.com/intive-FDV/DynamicJasper/blob/master/src/changes/changes.xml).

## 7.0.0 (2026-09-19)
*JDK 21 build with Java 8 bytecode compatibility*

- **Change**: Build with JDK 21; published JAR targets Java 8 bytecode (--release 8) for runtime compatibility with JDK 8+. Animal Sniffer verifies no post-Java-8 APIs are used in main sources. Internal code modernized incrementally (generics, lambdas, streams, try-with-resources). (Juan Manuel Alvarez)
- **Add**: Support for JasperReports 7.0.0-7.0.8 (Juan Manuel Alvarez)

## 5.3.9 (2023-08-16)
*More Pull request*

- **Add**: Support for JasperReports 6.20.1+ (to 6.20.4 as of time of writing). At the time of writing, JR 6.20.5 is available, but it has this bug: https://community.jaspersoft.com/jasperreports-library/issues/3127-0 which affects the layout of sub-reports heavily. If you don't use sub-reports, it is safe to use JR 6.20.5 (Juan Manuel Alvarez)
- **Change**: Removed support to WebWork and Struts2 as part of the main core. Maybe adding it as a separated artifact (Juan Manuel Alvarez)

## 5.3.8 (2023-03-04)
*More Pull request*

- **Add**: fix for https://github.com/intive-FDV/DynamicJasper/issues/118 Subreport styled headers / not styled correctly (when exported to docx) (Juan Manuel Alvarez)

## 5.3.7 (2023-03-04)
*More Pull request*

- **Add**: Update deprecated code in latest JasperReports #135 https://github.com/intive-FDV/DynamicJasper/pull/135 , JasperReport min and max version are now: 6.11.0 and 6.20.0 (oyarzun)
- **Add**: bumped some dependencies versions (Juan Manuel Alvarez)

## 5.3.6 (2022-08-16)
*Pull request season*

- **Add**: add OSGI headers to MANIFEST.MF https://github.com/intive-FDV/DynamicJasper/pull/132 (jherkel - Jakub Herkel)
- **Add**: remove apache common collections https://github.com/intive-FDV/DynamicJasper/pull/130 (jherkel - Jakub Herkel)
- **Add**: fix compatibility with jasperreport 6.19.1 https://github.com/intive-FDV/DynamicJasper/pull/129 (jherkel - Jakub Herkel)
- **Add**: allow the ability to specify incrementerFactoryClass on groupings: https://github.com/intive-FDV/DynamicJasper/pull/83 (gschrader - Glen Schrader)
- **Add**: allow for crosstabs in summary band: https://github.com/intive-FDV/DynamicJasper/pull/70 (gschrader - Glen Schrader)
- **Add**: Use main datasource directly for crosstabs that ask for it: https://github.com/intive-FDV/DynamicJasper/pull/69/ (gschrader - Glen Schrader)

## 5.3.5 (2022-08-01)

- **Add**: Supports JasperReports 6.8.0 to 6.19.1 (Juan Manuel Alvarez)
- **Add**: #124 - fix compatibility with jasperreport 6.19.1 #129 (Juan Manuel Alvarez)

## 5.3.4 (2022-02-21)

- **Add**: Fix on autotext JR expression, not compiling in certain scenarios (Juan Manuel Alvarez)

## 5.3.3 (2022-02-21)

- **Add**: Supports JasperReports 6.8.0 to 6.18.1 (Juan Manuel Alvarez)
- **Add**: Version bump on dependencies due to vulnerabilities (Juan Manuel Alvarez)

## 5.3.2 (2021-05-31)

- **Add**: Supports JasperReports 6.8.0 to 6.17.0 (Juan Manuel Alvarez)

## 5.3.1 (2020-07-23)

- **Add**: Supports JasperReports 6.8.0 to 6.13.0 (Juan Manuel Alvarez)

## 5.3.0 (2020-03-23)

- **Add**: Compiled with Java 1.8 (Juan Manuel Alvarez)
- **Add**: Supports JasperReports 6.8.0 to 6.11.0 (Juan Manuel Alvarez)

## 5.2.0 (2020-03-23)

- **Add**: Compiled with Java 1.8 (Juan Manuel Alvarez)
- **Add**: Supports JasperReports 6.6.0 to 6.7.1 (Juan Manuel Alvarez)

## 5.1.3 (2020-03-23)

- **Add**: Compiled with Java 1.7 (Juan Manuel Alvarez)
- **Add**: Supports JasperReports 6.3.0 to 6.5.1 (Juan Manuel Alvarez)

## 5.1.2 (2019-05-20)

- **Add**: Crosttabs supports a comparator in both column and row (Juan Manuel Alvarez)
- **Fix**: Dependencies to core-fonts 2.0 which supports JasperReports 6.3.1+ (Juan Manuel Alvarez)

## 5.1.1 (2018-05-12)

- **Add**: Supports JasperReport 6.4.1 (Juan Manuel Alvarez)
- **Fix**: Fix for https://github.com/intive-FDV/DynamicJasper/issues/59 Crosstab: useMainReportDatasource only works when datasource is passed as a parameter (Geoff Gibbs)
- **Add**: Simple support for parameter defaults. (Sam Hough)
- **Add**: Allow for ignoreWidth to be specified for crosstabs. (Geoff Gibbs)

## 5.1.0 (2017-02-20)

- **Add**: Supports JasperReport 6.3.0 (Juan Manuel Alvarez)
- **Add**: Code Cleanup (adding generics, lint inspections) (Juan Manuel Alvarez)

## 5.0.11 (2016-09-28)

- **Add**: Introducing JasperDesignDecorator. This enables you to tweak the JasperDesign object before and after applying the layout. Check for JasperDesignDecoratorReportTest for an example (Juan Manuel Alvarez)
- **Add**: DynamicReportBuilder.setDefaultEncoding(String) to use as default encoding in the report. Useful when exporting to PDF. Checkout DefaultEncodingReportTest for an example. (Juan Manuel Alvarez)

## 5.0.10 (2016-08-15)

- **Add**: AutoText Page X, and Page X / Y supports passing an offset for the starting page. Many Thanks to NerdKnight. (Juan Manuel Alvarez)

## 5.0.9 (2016-07-09)

- **Add**: Support JasperReports from 4.6.0 yo 6.3.0 (Juan Manuel Alvarez)

## 5.0.8 (2016-07-09)

- **Fix**: Group Labels Custom Expression shows wrong field values (Juan Manuel Alvarez)
- **Add**: Added Reset Page Number flag in groups. https://github.com/FDVSolutions/DynamicJasper/issues/27 (Juan Manuel Alvarez)

## 5.0.7 (2016-04-16)

- **Fix**: Fixes "Unable to create more than one subreport with charts" https://github.com/FDVSolutions/DynamicJasper/issues/16 (mguimaraes)

## 5.0.6 (2016-04-16)

- **Fix**: POM updated for Maven Central (nexus OSS) (Juan Manuel Alvarez)

## 5.0.5 (2016-01-04)

- **Add**: Introducing ExtendedListLayoutManager for xls export with title and subtitle. (Juan Manuel Alvarez)

## 5.0.4 (2015-12-04)

- **Fix**: Load from template honors page orientation (Juan Manuel Alvarez)
- **Add**: Ability to add watermark text on reports (Juan Manuel Alvarez)

## 5.0.3 (2015-07-07)

- **Fix**: Added support for RTF. It was missing from the very beginning (Leonid Rozenblyum)
- **Fix**: Avoid warn message when duplicate report param if loading a tempalte file (Juan Manuel Alvarez)

## 5.0.2 (2014-07-02)

- **Update**: Some performance fixes on subreport handling, thanks to inanutshellus (inanutshellus)
- **Update**: Changing dependency to POI 3.10-FINAL, thanks to inanutshellus (inanutshellus)
- **Add**: Added Portuguese translation. Thanks to pauloremoli (pauloremoli)
- **Fix**: DynamicReportBuilder throws Exception if build() methid is invoked more than once. (Juan Manuel Alvarez)

## 5.0.1 (2013-11-13)
*Better support for HTML export*

- **Add**: Better support for HTML export when using frameworks such as GWT or Vaadin when writing to the response is not an option. (Juan Manuel Alvarez)

## 5.0.0 (2013-10-23)
*Compatibility release*

- **Add**: From this version DJ needs Java 1.6+ and jasper resports 4.6.0+. Tested with JasperRepoerts 4.6.0, 4.7.0, 4.8.0, 5.0.0, 5.1.0 and 5.2.0 (Juan Manuel Alvarez)

## 4.0.3 (2012-09-30)
*bug fixing*

- **Fix**: custom variables and parameters cannot be reached inside custom expression due to new naming strategy. (Juan Manuel Alvarez)

## 4.0.2 (2012-08-11)
*bug fixing and some new features*

- **Fix**: Applied this fix to group footer height calculation: https://github.com/FDVSolutions/DynamicJasper/pull/1 (Martin D'Aloia (from github))
- **Fix**: fix duplicated parameter name when: Two Subreports with Hyperlink columns. See SubReportRecursive2Test (Juan Manuel Alvarez)
- **Add**: you can now set report languaje to java, groovy or javascript (Patel, Dipesh)
- **Add**: We can now specify columns data and header markup. See ColumnBuilder.setMarkup(...) and ColumnBuilder.setHeaderMarkup(...). Also see ColumnMarkupFastReportTest (Patel, Dipesh)
- **Fix**: Expression Columns in charts. Now adding a test: ExpressionColumnInChartTest (Patel, Dipesh)

## 4.0.1 (2012-06-25)
*minor bug fix in charts with custom expression columns*

- **Fix**: Due to this post http://stackoverflow.com/questions/5667658/using-a-customexpression-in-a-dynamicjasper-chart/11191944#11191944 the proposed solution is used for both: new and old charts (Juan Manuel Alvarez)

## 4.0.0 (2012-06-15)
*Final 4.0 Release*

- **Fix**: Fixed problem with border colors https://sourceforge.net/tracker/?func=detail&atid=923805&aid=3532995&group_id=188060 ,The test StylesReportTest demostrates how it works properly (Juan Manuel Alvarez)

## 4.0.0_beta3 (2012-05-12)
*Major Release*

- **Fix**: Fixed problem with custom expression in subreport: http://dynamicjasper.com/forum/viewtopic.php?f=8&t=15116 ,The test SubReportBuilder3Test demostrates how it works properly (Juan Manuel Alvarez)

## 4.0.0_beta2 (2012-04-01)
*Major Release*

- **Add**: Added test dependency to DynamicJasper-core-fonts-1.0 in order to prevent missing fonts. Fonts added are: Arial, Times New Roman, Courier New, Comic Sans MS, Georgia, Verdana, Monospaced (Juan Manuel Alvarez)
- **Add**: Added Monospaced font (Juan Manuel Alvarez)

## 4.0.0-beta1 (2012-04-01)
*Major Release*

- **Change**: Style borders break change. They now have their own class. So, no more Border.THIN, but Border.THIN() as a factory method (Juan Manuel Alvarez)
- **Change**: DJ now requires JasperReport 4.1.1+ (Juan Manuel Alvarez)
- **Change**: Removed support to JDK 1.5. Now compiled against JDK 1.6 (Juan Manuel Alvarez)
- **Change**: Removed dependency with POI-3.5-FINAL. We now use POI from JasperReports dependency. (Juan Manuel Alvarez)

## 3.2.2 (2012-03-12)
*typo fix release*

- **Fix**: Just fixed a type in a property name in the DJVariable.java file. (Juan Manuel Alvarez)

## 3.2.1 (2011-12-23)
*improved DJValueFormatter capabilities*

- **Add**: Introducing the DjBaseMMValueFormatter which adds better support to access to measures in crosstabs. See CrosstabReportWithMMValueFormatterTest and CrosstabReportWithInvisibleMeasure (Juan Lagostena)

## 3.2.0 (2011-12-21)
*introducing column spanning*

- **Add**: Introducing column span in regular column reports. See tests located at src/test/java/ar/com/fdvs/dj/test/colspan (Mariano Vicente)

## 3.1.9 (2011-06-23)
*small fixes on crosstabs*

- **Add**: Detuche messages, thanks to Philip Helger (Juan Manuel Alvarez)
- **Fix**: Removed unneeded direct dependency to opensymphony:oscore:jar:2.2.4 (Juan Manuel Alvarez)
- **Fix**: Totals in groups footers did not stretch when content overflows (due to wrong evaluation time) (Juan Manuel Alvarez)
- **Fix**: Group label was miss-aligned. It was 2 (two) pixels higher than other columns (Juan Manuel Alvarez)

## 3.1.8 (2011-01-07)
*small fixes on crosstabs*

- **Fix**: borders for totals in crosstab were missing. (Juan Manuel Alvarez)
- **Fix**: A bug on the internal styles naming was preventing totals on crosstabs to use their own style. (Juan Manuel Alvarez)

## 3.1.7 (2010-12-27)
*small inprovements on crosstabs*

- **Add**: * Full borders on crosstabs are now possible. * replaced some deprecated from JR to its new version (Juan Manuel Alvarez)

## 3.1.6 (2010-11-30)
*fixes and inprovements*

- **Add**: added method setHeaderVariablesHeight() to DynamicReportBuilder (Juan Manuel Alvarez)
- **Fix**: ImageColumn and BarcodeColumn stretch type was not correctly applied. See BarcodeColumnReportTest2 and see how the large row gets the bottom border properly applied. (Juan Manuel Alvarez)
- **Fix**: ColumnBuilder failed to pass image scale mode properly to the column being built. (Juan Manuel Alvarez)

## 3.1.5 (2010-06-16)
*small improvements in builders*

- **Add**: DynamicReport now has "addParameter()" method (Juan Manuel Alvarez)
- **Add**: overloaded version of the method "DynamicReportBuilder.addGlobalColumnVariable" to add a "position" parameter to specify footer or header (Juan Manuel Alvarez)
- **Add**: overloaded version of the method "DynamicReportBuilder.addSubreportInGroup" to add a "position" parameter to specify footer or header (Juan Manuel Alvarez)
- **Add**: overloaded version of the method "FastReportBuilder.addGroupVariable" to add a "position" parameter to specify footer or header (Juan Manuel Alvarez)

## 3.1.4 (2010-06-11)
*fixes in subreports*

- **Change**: fixed the internal subreport naming to ensure a clear understanding of how they got included. (Juan Manuel Alvarez)
- **Add**: added some debug log information when generating subreports (Juan Manuel Alvarez)

## 3.1.3 (2010-05-18)
*quick fix in styles*

- **Add**: changed the internal style naming to avoid problem reported in JasperReports tracker: http://jasperforge.org/plugins/mantis/view.php?id=4721 (Juan Manuel Alvarez)
- **Add**: We can now register variables. useful for "balance" columns. See ReportWithVariablesTest for an example (Juan Manuel Alvarez)

## 3.1.2 (2010-05-04)
*Percentage in crosstabs*

- **Add**: Ability to show percentage in crosstab cells. See CrosstabReportWithPercentageMeasure as an example (Juan Manuel Alvarez)
- **Fix**: Removed duplicated dependencies bcmail-jdk14 and bcprov-jdk14 (Juan Manuel Alvarez)
- **Update**: Image columns can have custom expression. See ImageExpressionColumnReportTest as an example. (Juan Manuel Alvarez)

## 3.1.1 (2010-03-23)
*bug fix for issue 2975227*

- **Update**: fixes the bug 2975227: InstantiationException when JRDesignImage instantiation (Juan Manuel Alvarez)

## 3.1.0 (2010-03-13)
*catch up with JR 3.7.1*

- **Update**: DJ is now compiled in JAVA 5. No more support to java 1.4 (Juan Manuel Alvarez)
- **Add**: DJ Supports JasperReport 3.5.2+ (up to 3.7.1) (Juan Manuel Alvarez)
- **Add**: Added documentation about new Chart API, see the web (Juan Manuel Alvarez)

## 3.0.15-beta1 (2009-12-10)
*fixes*

- **Update**: Hability to use the same property/field in crosstab measures more than once (i.e: sum, average, count) (Juan Manuel Alvarez)

## 3.0.14 (2009-12-08)
*fixes*

- **Update**: Change in method signature for customized totals legend in crosstabs. (Juan Manuel Alvarez)

## 3.0.14-beta6 (2009-12-03)
*fixes*

- **Update**: Crosstab totals legend can be customized (see CrosstabReportTest10 for an example). (Juan Manuel Alvarez)

## 3.0.14-beta5 (2009-11-25)
*fixes*

- **Fix**: Custom Expression can have a property definition. (Juan Manuel Alvarez)

## 3.0.14-beta4 (2009-11-25)
*fixes*

- **Fix**: Value formatters are not working in crosstab totals. (Juan Manuel Alvarez)

## 3.0.14-beta3 (2009-11-23)
*fixes*

- **Fix**: NullPointerException while creating group layout (DEFAULT, DEFAULT_WITH_HEADER,VALUE_IN_HEADER_WITH_HEADERS_AND_COLUMN_NAME) (Ricardo Mariaca)
- **Fix**: Custom Expression where not working since 3.0.14-beta2 (Juan Manuel Alvarez)

## 3.0.14-beta2 (2009-11-19)
*Fixes in variable naming*

- **Fix**: Custom expression columns internal property name now is: "customExpression_for_" + columnName ; where if columnName was not defined, then "COLUMN_XXX" will be used (XXX ix the column index in the report starting from 0) (Juan Manuel Alvarez)

## 3.0.14-beta1 (2009-11-19)
*Bugfixing on crosstabs value formatters*

- **Fix**: Value formatters fails when many concatenated subreports. (Juan Manuel Alvarez)

## 3.0.13 (2009-11-17)
*New stuff*

- **Add**: Possibility to register field variable in group (see GroupLabelTest5) (Ricardo Mariaca)
- **Fix**: Some custom expression classes where not serializable, they are now (Ricardo Mariaca)
- **Fix**: Fixed problem related to percentage column when no group is defined (Ricardo Mariaca)
- **Fix**: Problem with columns TextFormatter (Juan Manuel Alvarez)
- **Fix**: ClassCastException in class MultiPropertyComparator (Juan Manuel Alvarez)
- **Fix**: When applying a style to using AUTOTEXT_PAGE_X_SLASH_Y, the reference style is used for the current page, but not the total page count. (Juan Manuel Alvarez)
- **Add**: Autotext supports print when expression (see AutotextReportTest2) (Ricardo Mariaca)
- **Add**: Imagebanner can have imageScaleMode (see ImageBannerReportTest) (Ricardo Mariaca)
- **Add**: Enabling custom label expression and position in group footer label (see GroupLabelTest4) Enabling custom label expression in group header/footer variable label (see GroupLabelTest4) (Ricardo Mariaca)
- **Fix**: Setting style to "blank when null" as true in certain scenarios where an element does not have a style and a new one must be provided (Juan Manuel Alvarez)
- **Add**: Crosstab can have precalculated values: See DJCRosstabMeasurePrecalculatedTotalProvider class and example test: CrosstabReportWithPrecalculatedTotalsAndValueFormatterTest (Juan Manuel Alvarez)
- **Fix**: NullPointerException while creating crosstabs due to uninitialized style. (Juan Manuel Alvarez)
- **Add**: crosstabs measures can have value formatters. See CrosstabReportWithNullValuesTest for an example (Juan Manuel Alvarez)

## 3.0.13-beta5 (2009-11-09)
*Autotext enhancements*

- **Fix**: Problem with columns TextFormatter (Juan Manuel Alvarez)
- **Fix**: ClassCastException in class MultiPropertyComparator (Juan Manuel Alvarez)
- **Fix**: When applying a style to using AUTOTEXT_PAGE_X_SLASH_Y, the reference style is used for the current page, but not the total page count. (Juan Manuel Alvarez)
- **Add**: Autotext supports print when expression (see AutotextReportTest2) (Ricardo Mariaca)
- **Add**: Imagebanner can have imageScaleMode (see ImageBannerReportTest) (Ricardo Mariaca)
- **Add**: Enabling custom label expression and position in group footer label (see GroupLabelTest4) Enabling custom label expression in group header/footer variable label (see GroupLabelTest4) (Ricardo Mariaca)

## 3.0.13-beta4 (2009-11-06)
*Crosstabs enhancements*

- **Fix**: Setting style to "blank when null" as true in certain scenarios where an element does not have a style and a new one must be provided (Juan Manuel Alvarez)

## 3.0.13-beta3 (2009-11-05)
*Crosstabs enhancements*

- **Add**: Crosstab can have precalculated values: See DJCRosstabMeasurePrecalculatedTotalProvider class and example test: CrosstabReportWithPrecalculatedTotalsAndValueFormatterTest (Juan Manuel Alvarez)

## 3.0.13-beta2 (2009-11-05)
*Crosstabs enhancements*

- **Fix**: NullPointerException while creating crosstabs due to uninitialized style. (Juan Manuel Alvarez)

## 3.0.13-beta1 (2009-11-04)
*Crosstabs enhancements*

- **Add**: crosstabs measures can have value formatters. See CrosstabReportWithNullValuesTest for an example (Juan Manuel Alvarez)

## 3.0.12 (2009-11-02)
*Urgent Fix*

- **Fix**: Fixed bug introduced in 3.0.11 which prevented global variables to be shown. (Juan Manuel Alvarez)

## 3.0.11 (2009-10-30)
*Percentage Column*

- **Fix**: DJ Exceptions thrown are properly initialized with real the cause (exception). This gives log information for troubleshooting. (Juan Manuel Alvarez)
- **Add**: Percentage column capability. See example class: PercentageColumnReportTest (Ricardo Mariaca)
- **Add**: Group/Global header and footer variable supports custom value expression (Ricardo Mariaca)
- **Add**: Group/Global header and footer variable supports print when expression (Ricardo Mariaca)
- **Add**: Crosstab supports conditional styles (Ricardo Mariaca)
- **Add**: "Summary start in new page" can be configured through the report options (Juan Manuel Alvarez)
- **Fix**: Some report options where not being taking into account when a template was used. (Juan Manuel Alvarez)

## 3.0.11-beta3 (2009-10-17)
*New stuff*

- **Fix**: Some classes related to charting where not serializable, they are now (ar.com.fdvs.dj.domain.StringExpression, ar.com.fdvs.dj.domain.chart.plot.DJAxisFormat and ar.com.fdvs.dj.domain.hyperlink.LiteralExpression) (Juan Manuel Alvarez)

## 3.0.11-beta2 (2009-09-20)
*New stuff*

- **Add**: New charts! area chart, stackedarea chart, bar chart, bar3d chart, stackedbar chart, stackedbar3d chart, line chart, pie chart, pie3d chart, timeseries chart, xyarea chart, xybar chart, xyline chart, scatter chart. (Ricardo Mariaca)
- **Add**: Report definition classes are now serializable. (Juan Manuel Alvarez)

## 3.0.10 (2009-08-25)
*Urgent Fix II*

- **Fix**: JasperCompilation error due to DJValueFormatters (Juan Manuel Alvarez)

## 3.0.9 (2009-08-15)
*Urgent Fix*

- **Fix**: Struts 2 integration classes are now in a separate jar (look for DynamicJasper-Struts2 at ) (Juan Manuel Alvarez)
- **Fix**: Fixed problem related to hyperlinks and crosstabs (parameter REPORT_SCRLIPTLET does not exists) (Juan Manuel Alvarez)

## 3.0.8 (2009-07-30)
*Urgent Fix*

- **Fix**: Urgent fix on conditional style expression introduced in 3.0.7 (Juan Manuel Alvarez)

## 3.0.7 (2009-07-30)
*at last!*

- **Add**: Fix at DJDefaultScriptlet, which was messing parameter, fields and variables in CustomExpression when subreports or crosstabs where used (Juan Manuel Alvarez)
- **Add**: DJLabel supports custom expression (Juan Manuel Alvarez)

## 3.0.7-b10 (2009-07-20)
*fixes*

- **Add**: Enabling crosstabs in group header and footers (Juan Manuel Alvarez)
- **Add**: Crosstabs can have a caption (DJLabel) (Juan Manuel Alvarez)
- **Fix**: Crosstab :: "null" appear in main header when automatic title is set to false. (Juan Manuel Alvarez)

## 3.0.7-b9 (2009-07-20)
*fixes*

- **Add**: Random crashes with 2 or more expressionColumns, see issue detail at the bug tracker. (Juan Manuel Alvarez)
- **Add**: Report title can be a jasper expression like "this is the " + $P{title_param} + " report"; see (Juan Manuel Alvarez)

## 3.0.7-b8 (2009-07-20)
*fixes*

- **Add**: Hyper links in crosstabs. See CrosstabHyperLinkReportTest (Juan Manuel Alvarez)

## 3.0.7-b7 (2009-07-10)
*fixes*

- **Add**: Hyper links in column values. (still needs work). See HyperLinkReportTest (Juan Manuel Alvarez)

## 3.0.7-b6 (2009-07-10)
*fixes*

- **Add**: Styles are clonable (Juan Manuel Alvarez)
- **Fix**: Footer height and header height can be independent from its content (taller). (Juan Manuel Alvarez)
- **Fix**: NPE when using template jrmxl with no detail band. (Juan Manuel Alvarez)
- **Update**: Report Templates referenced in jrxml where not taken into account. (Juan Manuel Alvarez)
- **Fix**: Group header variable is not shown is special scenario when using VALUE_IN_HEADER_WITH_HEADERS_AND_COLUMN_NAME (Juan Manuel Alvarez)
- **Fix**: Title and subtitle text is escaped properly. Titles with double quotes (") could cause JRException (Juan Manuel Alvarez)
- **Add**: Added SafeReportBuilder in order to prevent building the report twice. (Juan Manuel Alvarez)
- **Fix**: Fixed NPE related with group footers (Juan Manuel Alvarez)
- **Fix**: Groups and expression to group by: It was not being taken into account. See https://sourceforge.net/support/tracker.php?aid=2768177 Also see ExpressionToGroupByReportTest (Juan Manuel Alvarez)
- **Fix**: Crosstab builder: Default values for cell dimensions, row header width and column header height. (Juan Manuel Alvarez)
- **Fix**: Crosstab builder: methods for setting cell width and height had to be invoked after all columns and rows where added. Now it can be called anytime. (Juan Manuel Alvarez)
- **Fix**: Fixed issue related to copying JRStyle objects. Now using JRStyle.clobe(), which forces to use JasperReport 3.0+ (Juan Manuel Alvarez)
- **Add**: Crosstabs: Configurable color schema for automatic coloring (now it is nice!). See CrosstabReportTest6 which uses TwoSeedCrossTabColorShema class (Juan Manuel Alvarez)
- **Add**: Crosstabs: Can specify colors and styles for each element: row headers, column headers, totals, etc. Also default values are provided See CrosstabReportTest7.java (Juan Manuel Alvarez)
- **Add**: Crosstabs can use the main report datasource. Use "CrosstabBuilder.useMainReportDatasource(true)". See CrosstabReportTest6.java (Juan Manuel Alvarez)
- **Fix**: Fixed problem with Font.XXXXX static shared objects. Now style.setFont( aFont ) gets a copy of the font passed. (Juan Manuel Alvarez)
- **Fix**: Fixed problem with conditional styles: conditional styles (for columns) don't work, unless you specify odd row styles, too. It also lets a conditional style override odd row style's background. See https://sourceforge.net/tracker/index.php?func=detail&aid=2809714&group_id=188060&atid=923805 for more information (Juan Manuel Alvarez)
- **Add**: Ability to hide detail band. Useful (but not efficient with large reports) to show groups & totals. See TotalingReportTest for an example (Juan Manuel Alvarez)
- **Update**: forum moved to http://dj.fdvsolutions.com/forum/ (Juan Manuel Alvarez)
- **Fix**: fixed bug related to inherited styles and the order in which they where registered. It may cause a JasperException. (Juan Manuel Alvarez)
- **Add**: Autotext accepts styles. Thanks Andrew M ( bucklane ) (Juan Manuel Alvarez)
- **Fix**: Header text and variable got sloppy when the first is too long. See https://sourceforge.net/tracker/index.php?func=detail&aid=2782948&group_id=188060&atid=923805 for more information (Juan Manuel Alvarez)
- **Update**: Default footer variables height is "20"; Defaul header variables height is "20" (Juan Manuel Alvarez)
- **Update**: Bar charts series and categories can be switched. See https://sourceforge.net/forum/message.php?msg_id=7396861 (Juan Manuel Alvarez)
- **Add**: Group Header on Every Page. Use GroupBuilder.setReprintHeaderOnEachPage(bool) See https://sourceforge.net/forum/message.php?msg_id=7427326 (Juan Manuel Alvarez)

## 3.0.6 (2009-04-23)
*crosstab fixes*

- **Add**: ability to have multiple measures on crosstabs. (Juan Manuel Alvarez)
- **Add**: columns can have a text formatter (java.text.Format) as an alternative for formatting values (Juan Manuel Alvarez)
- **Fix**: Removed unneeded imports in many classes (Juan Manuel Alvarez)

## 3.0.5 (2009-02-09)
*fixes*

- **Fix**: Fixed typo in method name: Style.setPaddingBotton should end with an 'm' not an 'n'. See https://sourceforge.net/tracker2/?func=detail&aid=2634753&group_id=188060&atid=923805 (Juan Manuel Alvarez)
- **Fix**: Fixed bug related to conditional styles, now working as expected. (Juan Manuel Alvarez)
- **Add**: Added ability to truncate text defining a suffix like "..." (Juan Manuel Alvarez)

## 3.0.4 (2009-02-09)
*fixes*

- **Fix**: Struts 2 (version 2.1.6) was not working due to change in method signature of xwork library. see tracker: https://sourceforge.net/tracker2/?func=detail&aid=2562199&group_id=188060&atid=923808 (Alejandro Gomez)
- **Add**: DynamicJasper Grails plugin :: see http://dynamicjasper.sourceforge.net/docs/grails dj plugin.html (Alejandro Gomez)
- **Add**: CustomExpression for Calculation in groups :: When grouping a column, you can specify a CustomExpression to be used for variable calacularion. see http://dynamicjasper.sourceforge.net/docs/HOWTO Create variables and define specific behavior.html (Juan Manuel Alvarez)
- **Add**: Ability to format variables in groups :: When grouping a column, you can specify a DJValueFormatter to be used for variable formatting in group header and/or footer. see http://dynamicjasper.sourceforge.net/docs/HOWTO Create variables and define specific behavior.html (Juan Manuel Alvarez)
- **Fix**: Exception on certain cross-tab scenario :: see https://sourceforge.net/forum/message.php?msg_id=6013486 (Juan Manuel Alvarez)
- **Fix**: Ensure order in crosstabs :: see https://sourceforge.net/forum/message.php?msg_id=5950390 (Juan Manuel Alvarez)

## 3.0.3 (2008-12-23)
*Enhancements and fixes*

- **Fix**: removed unnecessary static dependency to webwork. (Juan Manuel Alvarez)
- **Fix**: Error in charts logic when only 1 group. Now showing the chart once. (Juan Manuel Alvarez)
- **Add**: Groups variables height can be defined independently on each group. This fix includes footer and header grand total height. (Juan Manuel Alvarez)
- **Fix**: Bug in the logic for choosing default style in group headers and footer (Juan Manuel Alvarez)

## 3.0.2 (2008-12-09)
*Enhancements and fixes*

- **Add**: Performance fix for CustomExpression, now using Jasper's ScriptLet to reuse field, variables and parameters map (Juan Manuel Alvarez)
- **Fix**: In CustomExpression's, non visible fields where not being populated (Juan Manuel Alvarez)
- **Fix**: Fixed multi line title and subtitle :: Certain style attributes in the title made subtitle not to stretch vertically. See MultiLineTitleReportTest https://sourceforge.net/forum/forum.php?thread_id=2591976&forum_id=659589 (Juan Manuel Alvarez)

## 3.0.1 (2008-11-21)
*Enhancements and fixes*

- **Add**: Added DJCalculation.DISTINCT_COUNT (for use in groups header and footer totals) (Juan Manuel Alvarez)
- **Update**: Refactor (BREAKING CHANGE):: The following classes where renamed for the sake of the human race: ColumnsGroup -- DJGroup ColumnsGroupVariable -- DJGroupVariable ColumnsGroupVariableOperation -- DJCalculation and some other classes you should not care... (Juan Manuel Alvarez)
- **Add**: Subreports allow split option :: We can tell the band which holds the subreport to not allow splitting of the content (Juan Manuel Alvarez)
- **Add**: Added missing method to the SubreportBuilder set up the subreport parameter origin. See SubReportBuilder.setParameterMapPath(...) for more info. (Juan Manuel Alvarez)
- **Add**: Group header and footer bands splitting configuration. (Juan Manuel Alvarez)
- **Add**: DynamicJasper result type for Struts 2 integration (Alejandro Gomez)
- **Fix**: Report locale was not not working. https://sourceforge.net/tracker/?func=detail&atid=923805&aid=2317770&group_id=188060 (Juan Manuel Alvarez)
- **Fix**: Border thickness is wrong. https://sourceforge.net/forum/message.php?msg_id=5689553 (Juan Manuel Alvarez)

## 3.0.0 (2008-11-05)
*New Mayor version - Breaking changes*

- **Fix**: Images border were shown all the time (Juan Manuel Alvarez)
- **Update**: Subreport white space when empty :: Now removing white space when a subreport is empty (Juan Manuel Alvarez)
- **Update**: Custom Expressions :: Cleaner interface, they receive 3 maps (fields, variables and parameters). They also have now a className attribute, so many types other than String can be returned (for formatting using patterns, etc) (Juan Manuel Alvarez)
- **Update**: Conditional Styles :: Changes needed due to changes in CustomExpression. Custom implementations of ConditionStyles will need fixing and recompiling. (Juan Manuel Alvarez)
- **Update**: Group Header Style :: Now each group can define it's own header when showing column names. (Juan Manuel Alvarez)
- **Add**: StyleBuilder :: Convenient methods added for constructing (Juan Manuel Alvarez)
- **Remove**: Deprecated methods in builders and DynamicJasperHelper were removed. (Juan Manuel Alvarez)
- **Add**: Web Export to HTML :: DynamicJasperHelper.exportToHtml(...) generates and sets up everything for a clean web html response. (Juan Manuel Alvarez)

## 2.1.0 (2008-09-06)
*fixes*

- **Add**: Tests enhancements :: To ensure product quality, the following technologies had been added to the automated tests: * HSQL DB: for real JDBC and SQL report tests * Hibernate: HQL report test * HTTP Unit: to test Webwork result type. Also enabling future testing to new http facilities. (Juan Manuel Alvarez)
- **Fix**: WebWork integration :: Fixed bug that had this feature broken. Also added new options to result type. Updated documentation (see "Integrate DynamicJasper with Webwork"). (Juan Manuel Alvarez)
- **Add**: Custom Expressions :: Now variables also available in Custom Expression. Instead of implementing CustomExpression, use DJCustomExpression which already provides as parameter one map per family (fields, variables and parameters) (Juan Manuel Alvarez)
- **Add**: Report Name :: Report name can now be specified (dynamicReport.setReportName(...) ). When exporting to Excel, this name will be used as the sheet name (Juan Manuel Alvarez)
- **Add**: Report Properties :: Now the DynamicReport has a setProperty(String, String) method which passes the properties to the JasperReportDesign. (Juan Manuel Alvarez)
- **Fix**: Page Orientation :: Orientation is working now (Juan Manuel Alvarez)
- **Add**: JRXML Templates :: Now bringing existing parameters, variables and fields and datasets from jrxml templates :: thanks yaro! (Juan Manuel Alvarez)
- **Update**: Field Properties Refactor :: Only PropertyColumn and its children are worthless to have field properties (Juan Manuel Alvarez)

## 2.0.10 (2008-07-24)
*Autotext improvements, many fixes*

- **Add**: Autotext :: More control over the width of Autotext components. No more chucked total page numbers. (Juan Manuel Alvarez)
- **Fix**: odd rows was also painting groups header and footers variables :: This bug was introduced in 2.0.9 (Juan Manuel Alvarez)
- **Add**: Parameter Map available in Custom Expression :: It is stored in the Map under the key DJConstants.CUSTOM_EXPRESSION__PARAMETERS_MAP (Juan Manuel Alvarez)
- **Fix**: NPE when cloning styles :: this bug was introduced in version 2.0.9 (Juan Manuel Alvarez)

## 2.0.9 (2008-07-07)
*Many improvements*

- **Add**: Dynamic Subreport fits automatically to parent report printable area :: Use Subreport.fitToParentPrintableArea flag. (Juan Manuel Alvarez)
- **Add**: New Exception ar.com.fdvs.dj.core.DJExpection for generics problems inside the API (Juan Manuel Alvarez)
- **Fix**: Tests: path to output file was not being created (Juan Manuel Alvarez)
- **Fix**: Report Locale: made some fixes to ensure that at least the default locale is loaded. (Juan Manuel Alvarez)
- **Add**: Field properties: JRField properties can be passed to the column builder or the column object itself. Thanks Eric Bartley! (Juan Manuel Alvarez)
- **Fix**: Odd rows on HTML: Coloring was not working properly. Thanks Eric Bartley! (Juan Manuel Alvarez)
- **Add**: Charts with many categories: more than one column can be added to charts (one column per category) (Juan Manuel Alvarez)
- **Fix**: Many charts for a given group may exceed band allowed height. (Juan Manuel Alvarez)

## 2.0.8 (2008-04-14)
*Subreport parameters release*

- **Add**: Subreport parameters :: You can pass parameters to subreports using values from current row. Thanks Marcos Reyes! (Juan Manuel Alvarez)
- **Fix**: WebWork integration :: Added "exportParams" feature, so you can pass custom parameters to the exporter used. (Juan Manuel Alvarez)
- **Fix**: Crosstabs NPE :: 1xX and Xx1 crosstabs thorws NPE, thanks Marcos Reyes (Juan Manuel Alvarez)
- **Add**: Allow control over detail band split :: thanks balazsborbely. (Juan Manuel Alvarez)
- **Fix**: Crosstab measure type peroblem :: Only java.lang.Float was working. Thanks my_aro! (Juan Manuel Alvarez)
- **Fix**: Removed static dependency Eclipse JST compiler :: Thanks Tito (enjuto). (Juan Manuel Alvarez)
- **Remove**: Java 1.2 is no longer supported :: This is from jasperReports 2.0.5 (they removed the class JRJdk12Compiler. Thanks mikea59. (Juan Manuel Alvarez)
- **Fix**: Header Charts :: they where leaving a blank space before the column titles. (Juan Manuel Alvarez)
- **Add**: Sub reports can start in new page :: thanks jacklty! (Juan Manuel Alvarez)

## 2.0.7 (2008-03-04)
*when no data section release*

- **Add**: When no data section feature :: you can set up what to show when the data source is empty. See WhenNoDataTest for a working example (Juan Manuel Alvarez)
- **Fix**: javadoc jar generated always :: javadoc jar is generated when a new release is out (pom.xml configuration) (Juan Manuel Alvarez)

## 2.0.6 (2008-02-22)
*Minor bug fixes and features*

- **Fix**: Problem with dynamically added queries when using template files :: the query added dinamically got lost when using templates files, thanks gustav_b (Juan Manuel Alvarez)
- **Fix**: More than one chart in a group :: You can now create more than one chart in the same group, thanks Sagara! (Juan Manuel Alvarez)
- **Add**: Define behaviour when datasource is empty :: just a passthough to the JasperReports options. Use DJConstants.WHEN_NO_DATA_TYPE_NO_PAGES, DJConstants.WHEN_NO_DATA_TYPE_BLANK_PAGE, DJConstants.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL, DJConstants.WHEN_NO_DATA_TYPE_NO_DATA_SECTION. Thanks Dorin Scutarasu!!! (Juan Manuel Alvarez)
- **Add**: Define behaviour when missing resource is found :: just a passthough to the JasperReports options. Use DJConstants.WHEN_RESOURCE_MISSING_TYPE_EMPTY, DJConstants.WHEN_RESOURCE_MISSING_TYPE_ERROR, DJConstants.WHEN_RESOURCE_MISSING_TYPE_KEY, DJConstants.WHEN_RESOURCE_MISSING_TYPE_NULL. Thanks Dorin Scutarasu!!! (Juan Manuel Alvarez)

## 2.0.5 (2008-02-04)
*Minor users requests*

- **Add**: Field desctiption for XML datasources :: XML Data sources need that the fields had the property "description" defined. Though ColumBuilder and FastReport builder "description" can be defined, thanks gustav_b (Juan Manuel Alvarez)
- **Add**: Parameter registration :: Whatever is in the "params map" will be registered as a parameter, thanks Charles Abreu (Juan Manuel Alvarez)
- **Add**: Fonts handling :: The fonts now have the properties pdfFontName, pdfFontEmbedded and pdfFontEncodding. You can now export to PDF with any encoding. See FontReportTest. Thanks Sagara for asking! (Juan Manuel Alvarez)

## 2.0.4 (2008-01-10)
*Report query release*

- **Add**: Report query :: Added report query to work the same as JasperReports (Juan Manuel Alvarez)
- **Add**: Export to jrxml file :: You can export your design to jrxml (for retouching in iReport) from the DynamicJasperHelper (Juan Manuel Alvarez)

## 2.0.3 (2008-01-04)
*urgent fix*

- **Fix**: Error on 2.0.2 build :: was compiled in java 5, it is java 1.4 now (Juan Manuel Alvarez)

## 2.0.2 (2007-12-28)
*Image column release*

- **Add**: pom.xml cleanup :: Unnecesary depenencies are optional now. Thanks danttran!!! (Juan Manuel Alvarez)
- **Add**: BarCode Column :: Hability to show bar codes in the detail band (Juan Manuel Alvarez)
- **Add**: Image Column :: Hability to show images in the detail band (Juan Manuel Alvarez)
- **Fix**: Bug with group names: https://sourceforge.net/forum/message.php?msg_id=4684608 Thanks danidacila! (Juan Manuel Alvarez)

## 2.0.1 (2007-12-19)
*Crosstab release*

- **Add**: FastReportBuilder: setGroupLayout(groupNumber, GroupLayout) (Juan Manuel Alvarez)
- **Add**: CrosstabRowBuilder: for easy crosstab row building (Juan Manuel Alvarez)
- **Add**: CrosstabcolumnBuilder: for easy crosstab column building (Juan Manuel Alvarez)
- **Add**: DynamicReportBuilder: addFooterCrosstab(DJCrosstab cross) (Juan Manuel Alvarez)
- **Add**: DynamicReportBuilder: addHeaderCrosstab(DJCrosstab cross) (Juan Manuel Alvarez)
- **Add**: FastReportBuilder: addFooterVariable(int groupNum, int colNumber, ColumnsGroupVariableOperation op, Style style) (Juan Manuel Alvarez)
- **Add**: FastReportBuilder: addHeaderVariable(int groupNum, int colNumber, ColumnsGroupVariableOperation op, Style style) (Juan Manuel Alvarez)

## 2.0.0 (2007-12-13)
*Crosstab release*

- **Add**: Crosstabs support (still in developement) (Juan Manuel Alvarez)
- **Add**: StyleBuilder class: convinient when creating styles (Juan Manuel Alvarez)
- **Fix**: MissingResourceException when locale is not EN or US. Thanks danidacila. https://sourceforge.net/forum/message.php?msg_id=4645638 (Juan Manuel Alvarez)
- **Fix**: Problem with Chart and grouping. Thanks danidacila. https://sourceforge.net/forum/message.php?msg_id=4645263 (Juan Manuel Alvarez)

## 1.4.2 (2007-11-05)
*Minor release*

- **Fix**: Grand Total Legends positions calculation fixed (Mariano Simone)
- **Add**: Convinient methods in FastReportBuilder to add columns with more options (Juan Manuel Alvarez)
- **Fix**: FastReportBuilder no guess in styles is the column already has a style (Juan Manuel Alvarez)
- **Fix**: odd rows background border and foreground color was not working (Juan Manuel Alvarez)

## 1.4.0 (2007-10-31)
*Styles release*

- **Add**: Groups can start in new page or in new column. (Juan Manuel Alvarez)
- **Add**: Existing styles in .jrxml files can be referenced from a DynamicReport (Juan Manuel Alvarez)
- **Fix**: Anoying loggin information when using repeated styles (Juan Manuel Alvarez)
- **Add**: Support for inherited styles (Juan Manuel Alvarez)
- **Add**: Convinient method in the FastReportBuilder to pass a style whe adding a new column. (Mariano Simone)
- **Fix**: Bug with autotext "page x / y" was showing "page 1null" (Juan Manuel Alvarez)
- **Update**: webwork result type: now you can specify a layout manager (Juan Manuel Alvarez)
- **Update**: refactor in DynamicReportBuilder: grand total legend is now: drb.setGrandTotalLegend("...") (Juan Manuel Alvarez)
- **Fix**: small bug with "ignore pagination" (Juan Manuel Alvarez)
- **Add**: concatenated reports can be also DynamicReports (Juan Manuel Alvarez)

## 1.3.3 (2007-10-18)
*minor styles fix*

- **Fix**: DynamicReportBuilder supports subreports in and elegant way (Juan Manuel Alvarez)
- **Fix**: You could have many subreports, but not recursive subreports (Juan Manuel Alvarez)
- **Fix**: Deprecated methods in builders (DynamicReportBuilder, etc) and create new ones to give meaninfull names (Juan Manuel Alvarez)
- **Update**: DynamicJasperHelper methods now throws exceptions as they should always do (Juan Manuel Alvarez)
- **Update**: DynamicJasperHelper methods are not "final" anymore. (Juan Manuel Alvarez)
- **Update**: DynamicJasperHelper convinience methods changed from "private" to "protected" (Juan Manuel Alvarez)
- **Fix**: Issue with file encoding is fixed for every compiler, not just eclipse jdt compiler. (Alejandro Gomez)

## 1.3.2 (2007-09-24)
*minor styles fix*

- **Fix**: The thin border is back, now the available border are: NO_BORDER, THIN, PEN_1_POINT, PEN_2_POINT, PEN_4_POINT and DOTTED (Juan Manuel Alvarez)
- **Fix**: The Group Layouts behaviour changed, see HOW-TO hide a column (Juan Manuel Alvarez)

## 1.3.1 (2007-09-17)
*minor XLS improvements*

- **Fix**: Fixed some flags for XLS exporting that where lost during refactorings: ignore pagination and print column names (Juan Manuel Alvarez)
- **Add**: Example test for exporting to XLS (Juan Manuel Alvarez)

## 1.3.0 (2007-08-21)
*Subreports beta 2*

- **Add**: Subreports support: Added SubReportBuilder class (Juan Manuel Alvarez)
- **Add**: Subreports support: Hability to the DynamicReportBuilder to concatenate subreports in a single report (Juan Manuel Alvarez)
- **Add**: Subreports support: Added 2 tests to be used as examples (SubReportBuilderTest and ConcatenatedReportTest) (Juan Manuel Alvarez)
- **Add**: Subreports support: HOW-TO documentation in the site (Juan Manuel Alvarez)

## 1.3.0-beta1 (2007-07-20)
*Subreports early release*

- **Add**: Subreports support: see SubReportTest test case. (Juan Manuel Alvarez)

## 1.2.2 (2007-08-15)
*JasperReports 2.0 (internal)*

- **Add**: Support for JasperReports 2.0 (Juan Manuel Alvarez)

## 1.2.1 (2007-08-15)
*bugfix release*

- **Add**: Minor issue with files not encoded in the system default encoding. (Alejandro Gomez)

## 1.2.0 (2007-07-20)
*Autotext release*

- **Add**: Autotext feature: Out of the box "Page x/y", "Created on {date}" and common autotexts in header and footer. (Mariano Simone)
- **Add**: i18N (Internationalization) - Autotext uses i18N (dj-messages.properties), you can also specify a custom resource bundle and a Locale to the report. (Juan Manuel Alvarez)
- **Fix**: Title in new Page: Some how we lost this feature in a refactor, its back. (Juan Manuel Alvarez)
- **Add**: added 2 more convenient methods on DynamicJasperHelper class. DynamicJasperHelper.generateJasperPrint(...) can now receive as a datasource a Collection or a ResultSet besides the JRDataSource (Juan Manuel Alvarez)
- **Fix**: Minor cosmetic issue with Image Banners, when usign different images for the first page and the rest of the report, there was a blank space betwen the image in the first page and the rest of the report. (Juan Manuel Alvarez)
- **Add**: Tons of documentation in the project website (how-to and getting started) (Juan Manuel Alvarez)

## 1.1.3 (2007-06-29)
*minor release*

- **Add**: Font has missing attribute for italic and underline, now there are setters ang getter for every property. (Juan Manuel Alvarez)
- **Add**: Padding: Now you can configure different padding for every side (Juan Manuel Alvarez)

## 1.1.2 (2007-05-13)
*minor release*

- **Add**: Now you can define styles for header and footer variables. (see StylesReport2Test) (Juan Manuel Alvarez)

## 1.1.1 (2007-04-21)
*minor release*

- **Add**: User-Defined colors can be used in charts usign Lists of colors (Mariano Simone)
- **Add**: Text elements can now be rotated left or right (through the Style class) (Juan Manuel Alvarez)

## 1.1.0 (2007-03-22)
*Charting release*

- **Add**: Charting capabilities. Added builder to easily add charts to DynamicJasper. (See the examples) (Mariano Simone)
- **Fix**: Template file. When using template file, first looks in the file system, then in the classpath (Juan Manuel Alvarez)
- **Add**: Example Report that uses a tempalte file (Juan Manuel Alvarez)
- **Add**: Fixed column width for columns that need to keep it´s original witdh (Juan Manuel Alvarez)
- **Update**: Documentation on the API and How-to guides (Mariano Simone)
- **Add**: Reflection Report Builder (Alejandro Gomez)
- **Add**: DynamicJasper result type for webwork integration (Alejandro Gomez)

## 1.0.2 (2007-02-27)
*Minor Release*

- **Add**: Playground. Example webapp made in GWT that shows the DynamicJasper potential (Juan Manuel Alvarez)
- **Add**: Added utility class to sort the datasource using the column of the report as criteria (Juan Manuel Alvarez)
- **Add**: Added FastReportBuilder that simplifies even more the report creation process (Juan Manuel Alvarez)
- **Update**: Documentation and examples (see the test sources) (Juan Manuel Alvarez)
- **Update**: More control over the styles (borders) (Juan Manuel Alvarez)

## 1.0.1 (2007-02-08)
*Minor Release*

- **Add**: Added the capability to place images on the header of the first page and the rest of the report (Juan Manuel Alvarez)
- **Update**: The DynamicReportBuilder can set page size and orientation (Juan Manuel Alvarez)
- **Add**: Added Sreenshots and pdf examples of the reports (Juan Manuel Alvarez)
- **Update**: Documentation is updated with screen shots (Juan Manuel Alvarez)
- **Remove**: Removed Unneded Cglib dependency. (Juan Manuel Alvarez)

## 1.0 (2007-02-01)
*Initial Release*

- **Add**: Initial version of DynamicJasper (Juan Manuel Alvarez)
- **Add**: Documentation with examples (Juan Manuel Alvarez)
