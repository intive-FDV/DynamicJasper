---
layout: page
title: HOW-TO Hide a column
# permalink: /how-to/
---

Its possible to hide a column when it’s used for grouping, printing only the current value on the header of the group every time it changes.

This can be achieved through the different “group layouts”.

There are many possibilities, one example that hides the column is like this:

```java
drb.setPrintColumnNames(true); //Show the column names on the top of every page

ColumnsGroup g1 = gb1.addCriteriaColumn((PropertyColumn) columnState)
                .addFooterVariable(columnAmount, ColumnsGroupVariableOperation.SUM)
                .addFooterVariable(columnaQuantity,     ColumnsGroupVariableOperation.SUM) // idem for the columnaQuantity column
                .setGroupLayout(GroupLayout.VALUE_IN_HEADER) // tells the group how to be shown, there are many posibilities, see the GroupLayout for more.
                .build();

GroupBuilder gb2 = new GroupBuilder(); // Create another group (using another column as criteria)

ColumnsGroup g2 = gb2.addCriteriaColumn((PropertyColumn) columnBranch) // and we add the same operations for the columnAmount and
                .addFooterVariable(columnAmount,ColumnsGroupVariableOperation.SUM) // columnaQuantity columns
                .addFooterVariable(columnaQuantity,ColumnsGroupVariableOperation.SUM)
                .setGroupLayout(GroupLayout.DEFAULT) // tells the group how to be shown, there are many posibilities, see the GroupLayout for more.
                .build();
```

In this case, group 1 (g1) uses `GroupLayout.VALUE_IN_HEADER` which tells the DJ to hide the column, and show only the value every time the group change in the header.

Group 2 (g2) uses `GroupLayout.DEFAULT`, which tells the DJ to show the colum to: show the column, not to print the other column headers, not to repeat the current value, not to print the column name (of the column used for grouping).

Also note that re `DynamicReportBuilder` (drb) is configured to print the column names on the beginning of every page.

Note in this example that the column “_state_” is not printed, the other columns fills the width of the page, and finally the current value of the state (Arizona) is shown in the header of the group. In this example,

### Other example of hiding a column

In this example:

* DynamicReportBuilder uses `setPrintColumnNames(false)`
* Group 1 uses `GroupLayout.VALUE_IN_HEADER_WITH_HEADERS`
* Group 2 uses `GroupLayout.DEFAULT`
* Column _State_ has a _Style_ for the detail with a bigger font

[//]: # (### )
[//]: # ()
[//]: # (no images were found)

### Yet another example of a hidden column

Notice that the column name “State” is shown before the current value (Arizona)

In this example:

* DynamicReportBuilder uses `setPrintColumnNames(false)`
* Group 1 uses `GroupLayout.VALUE_IN_HEADER_WITH_HEADERS_AND_COLUMN_NAME`
* Group 2 uses `GroupLayout.DEFAULT`
* Column _State_ has a _Style_ for the detail with a bigger font, and a _Style_ for the header. Also, the width is 40

More information about [grouping styles](../how-to/grouping-styles.html)