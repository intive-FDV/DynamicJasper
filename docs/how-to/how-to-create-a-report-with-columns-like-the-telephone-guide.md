---
layout: page
title: HOW-TO Create a report with columns [like the telephone guide]
# permalink: /how-to/
---


If we need more than one column (not the columns from the DJ API) like in the telephone guide we can tell the DynamicReportBuilder the number of columns per page in the report.

```java
FastReportBuilder drb = new FastReportBuilder();
drb.addColumn("State", "state", String.class.getName(),30)
.addColumn("Branch", "branch", String.class.getName(),30)
.addColumn("Item", "item", String.class.getName(),50)
.addColumn("Amount", "amount", Float.class.getName(),60,true)
.addGroups(2)
.setTitle("November 2006 sales report")
.setSubtitle("This report was generated at " + new Date())
.setColumnsPerPage(2,10)        //number of columns and space between columns
.setUseFullPageWidth(true);

DynamicReport dr = drb.build();
```

Note the `.setColumnsPerPage(2,10)` sentence, the two parameters are

* Number of columns
* Space between columns

This code generates a report like this:

![columns-report](../images/columns-report.jpg)

Refer to [ColumnsReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/ColumnsReportTest.java) for a working example.