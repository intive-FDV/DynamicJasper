---
layout: page
title: HOW-TO Create Autotext out of the box
# permalink: /how-to/
---


To add common auto text expressions in header and footer such as "_Page X of Y_", "_Created on 07/23/2007_", etc. 
Just use the `DynamicReportBuilder` like this:

For page counter

```java 
drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y,
AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT);
```
For creation time

```java
drb.addAutoText(AutoText.AUTOTEXT_CREATED_ON,
AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT,
AutoText.PATTERN_DATE_DATE_TIME);
```

For custom text

```java
drb.addAutoText("Autotext at top-center",
AutoText.POSITION_HEADER, AutoText.ALIGNMENT_CENTER);
```

### Parameters

All the parameters are constants of the **AutoText** class.

By the time this how-to was written, the possible autotexts are:

#### Autotext type

* **Page counter**
    * `AUTOTEXT_PAGE_X_OF_Y` Adds "_Page 1 of 4_"
    * `AUTOTEXT_PAGE_X_SLASH_Y` Adds "_Page 1/4_"
    * `AUTOTEXT_PAGE_X` Adds "_Page 1_"
* **Creation time**
    * `AUTOTEXT_CREATED_ON` Adds "**Created on &lt;date&gt;**" The date will be formatted depending on the _pattern_ parameter (see bellow)
* **Fixed message** Adds the message passed in the first parameter

#### Position

The 2nd parameter is the position, it determines if the autotext will be shown in the header or the footer of the report.

* `POSITION_HEADER`
* `POSITION_FOOTER`

#### Alignment

The 3rd parameter is the Alignment, it determines the horizontal alignment (left, right, center).

* `ALIGNMENT_LEFT`
* `ALIGNMENT_RIGHT`
* `ALIGNMENT_CENTER`

#### Pattern

The 4th parameter is optional, is only useful when using AUTOTEXT_CREATED_ON. It determines how to format the creation date/time

* `PATTERN_DATE_DATE_ONLY` Depending on the locale the date will be shown like this: _03/24/2007_
* `PATTERN_DATE_TIME_ONLY` Depending on the locale the time will be shown like this: _21:43:47_
* `PATTERN_DATE_DATE_TIME` Depending on the locale the date will be shown like this: _03/24/2007 21:43:47_

**Note:** The Autotext feature uses i18N, so the text can be internationalized changing the report locale like this: `drb.addReportLocale(new Locale("es","AR"));`

To localize your messages, you have to create a file named "dj-messages_**&lt;locale&gt;**.properties" where &lt;locale&gt; must be

the desired language-country pair (ie: en_US, es_ES, fr_FR, etc). This file must be in the classpath.

Another possibility is to tell the DJ wich resource bundle to use like this:

```java
drb.addResourceBundle("other_messages");
```

The key needed for translation are:

```
autotext.page Page  
autotext.of of  
autotext.created_on Created on_
```

For an usage example, see the [AutotextReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/AutotextReportTest.java) class