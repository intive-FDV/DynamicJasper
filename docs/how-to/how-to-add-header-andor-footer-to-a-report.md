---
layout: page
title: HOW-TO Add header and/or footer to a report
---

# HOW-TO Add header and/or footer to a report

You have two different ways to achieve this task:

1) Use the [AutoText feature](../how-to/how-to-create-autotext-ie-page-xy-out-of-the-box.html) (recommended)

2) Use a Custom Template as shown above, and add the info to the Report Builder, like this:

```java
DynamicReportBuilder drb = new DynamicReportBuilder();
//ReportBuilder initialization

drb.addTemplateFile("pathToTemplate/TemplateName.jrxml");

//Assuming you added $P{header} and $P{footer} as content for the textfields used as header and footer,
//you must add their content:
Map parameters = new HashMap();
params.put("header","&lt;my header message&gt;");
params.put("footer","&lt;my footer message&gt;");
//Creates the JasperPrint, using the parameters map
JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds, parameters );
```

Refer to [TemplateFileReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/TemplateFileReportTest.java) for a working example. Here you can see the used jrxml: [download](https://github.com/intive-FDV/DynamicJasper/blob/master/src/test/resources/templates/TemplateReportTest.jrxml)