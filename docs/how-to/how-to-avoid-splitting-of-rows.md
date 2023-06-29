---
layout: page
title: HOW-TO Avoid splitting of rows
# permalink: /how-to/
---

Posted in [Grouping and Layout](http://web.archive.org/web/20211021165217/http://dynamicjasper.com/category/docs/howto/grouping-and-layout/)

HOW-TO Avoid splitting of rows
==============================

*  [![](http://web.archive.org/web/20211021165217im_/http://1.gravatar.com/avatar/4995df79ed07d869e3481f29e5768cd6?s=30&d=mm&r=g) djmamana](http://web.archive.org/web/20211021165217/http://dynamicjasper.com/author/djmamana/)
* posted on [October 6, 2010](http://web.archive.org/web/20211021165217/http://dynamicjasper.com/2010/10/06/how-to-avoid-splitting-of-rows/)
* [No Comment](http://web.archive.org/web/20211021165217/http://dynamicjasper.com/2010/10/06/how-to-avoid-splitting-of-rows/#respond)

Sometimes, when a report is being filled at the end of a page, the value of a field is big enough to not fit in the space given making the line to be split and continued in the next page. To avoid this behavior and make the line to directly appear in the next page, use the \*setAllowDetailSplit(false)\* feature in the DynamicReportBuilder
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

DynamicReportBuilder drb = new DynamicReportBuilder();
...
drb.setAllowDetailSplit(false);