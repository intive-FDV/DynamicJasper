---
layout: page
title: HOW-TO Create variables and define specific behavior
# permalink: /how-to/
---



If there is a need to decouple how a variable is calculated, and how the result must be shown, then a combination of `CustomExpresion` and `DJValueFormatter` may be used.

Suppose we have a column that must show an amount of time (a duration of something, like a phone call) in a format like _14′ 23″_, and the field for that column is an _Integer_ that represents the duration in _seconds_. Besides that, we want to have some groups, and display totals in its footer. They must also be shown as _mm’ ss”_

First, when creating the column, two different custom expression are passed, one defines how data is shown in the column (`setCustomExpression(…)`), the other how data must be passed to report engine, so it makes calculations (`setCustomExpressionForCalculation(…)`)

```java
...
AbstractColumn columnaCustomExpression = ColumnBuilder.getNew()
.setCustomExpression(getCustomExpression())
.setCustomExpressionForCalculation(getCustomExpression2())
.setTitle("Duration").setWidth(new Integer(90))
.setStyle(amountStyle).setHeaderStyle(headerStyle).build();
...
```

Also, add a group with a variable in its footer to show to total amount of time (the sum). Pay attention to the _getValueFormatter()_ call

```java
...

drb.addGlobalFooterVariable(columnaCustomExpression, DJCalculation.SUM,amountStyle, getValueFormatter());

...
```

And now the definition of the `CustomExpression` and `DJValueFormatter` calls.

```java
private DJValueFormatter getValueFormatter() {
    return new DJValueFormatter(){
        public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
                return "Total time: " + getAsMinutes((Long) value);
        }

        public String getClassName() {
                return String.class.getName();
        }
    };
}

private CustomExpression getCustomExpression() {
    return new CustomExpression() {
        public Object evaluate(Map fields, Map variables, Map parameters) {
            Long amount = (Long) fields.get("quantity");
            return getAsMinutes((Long) amount);
        }

        public String getClassName() {
            return String.class.getName();
        }
    };
}

private CustomExpression getCustomExpression2() {
    return new CustomExpression() {
        public Object evaluate(Map fields, Map variables, Map parameters) {
            return fields.get("quantity");
        }
        
        public String getClassName() {
                return Long.class.getName();
        }
    };
}

public static String getAsMinutes(Long value) {
    Long amount = (Long) value;
    int sec = amount.intValue() % 60;
    int mins = amount.intValue() / 60;
    return mins + "' " + sec + """;
}
```

Refer to [VariableValueFormatterReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/VariableValueFormatterReportTest.java) for a working example.