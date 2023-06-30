---
layout: page
title: HOW-TO Avoid splitting of rows
# permalink: /how-to/
---

Sometimes, when a report is being filled at the end of a page, the value of a field is big enough to not 
fit in the space given making the line to be split and continued in the next page. 

To avoid this behavior and make the line to directly appear in the next page, use the `setAllowDetailSplit(false)` feature in the `DynamicReportBuilder`

```java
DynamicReportBuilder drb = new DynamicReportBuilder();
...
drb.setAllowDetailSplit(false);
```