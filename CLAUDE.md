# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

DynamicJasper (DJ) is a Java library that provides a fluent API for creating JasperReports dynamically at runtime without using graphical design tools. It abstracts JasperReports complexity, automatically handling layout, column positioning, and report element generation.

**Version:** 5.3.9
**Java:** 8+
**Build Tool:** Maven 3.x
**Core Dependency:** JasperReports 7.0.0-7.0.8
**License:** LGPL v2.1

## Build & Test Commands

```bash
# Full build with tests
mvn clean install

# Run all tests
mvn test

# Run single test class
mvn test -Dtest=FastReportTest

# Run single test method
mvn test -Dtest=FastReportTest#testReport

# Build JAR without tests
mvn clean package -DskipTests

# Generate Javadoc
mvn javadoc:javadoc

# Build with source and javadoc JARs
mvn clean install -Psources

# Generate site documentation
mvn site -Psite
```

## Core Architecture

### Data Flow

```
Builder (Fast/Dynamic/ReflectiveReportBuilder)
    ↓
DynamicReport (domain model)
    ↓
DynamicJasperHelper.generateJasperPrint()
    ├→ Create DynamicJasperDesign
    ├→ LayoutManager positions elements
    ├→ Registration Managers convert DJ to Jasper design
    └→ JRCompiler compiles expressions
    ↓
JasperPrint (executable report)
    ↓
Export via ReportWriter (PDF, Excel, HTML, CSV, etc.)
```

### Package Structure

**`ar.com.fdvs.dj.domain`** - Domain model and entities
- `domain.builders` - Builder pattern implementations
  - `FastReportBuilder` - Quick reports with defaults
  - `DynamicReportBuilder` - Full control
  - `ReflectiveReportBuilder` - Auto-generated from beans
  - `ColumnBuilder`, `GroupBuilder`, `StyleBuilder` - Component builders
- `domain.entities` - Core entities (DJGroup, DJVariable, Parameter, Subreport)
- `domain.entities.columns` - Column type hierarchy
  - `AbstractColumn` - Base class
  - `PropertyColumn` - Bean property mapping
  - `ExpressionColumn` - Calculated columns
  - `BarCodeColumn`, `ImageColumn` - Special content
- `domain` - DynamicReport, Style, AutoText, DJChart, DJCrosstab

**`ar.com.fdvs.dj.core`** - Report generation engine
- `DynamicJasperHelper` - Main API facade
  - `generateJasperPrint()` - Primary method
  - `generateJasperReport()` - Get compiled report
- `core.layout` - Layout management
  - `LayoutManager` - Abstract layout handler
  - `ClassicLayoutManager` - Standard implementation
- `core.registration` - Entity registration to Jasper
  - `ColumnRegistrationManager` - Register columns
  - `DJGroupRegistrationManager` - Register groups
  - `VariableRegistrationManager` - Register variables
  - `ConditionalStylesRegistrationManager` - Register styles

**`ar.com.fdvs.dj.util`** - Utilities
- `ExpressionUtils` - Expression handling
- `LayoutUtils` - Layout calculations
- `DJCompilerFactory` - Compiler selection (Javac, JDT, Jdk13)

**`ar.com.fdvs.dj.output`** - Export handling
- `ReportWriter` interface and implementations

### Design Patterns

**Builder Pattern**: Fluent API for report construction
- `FastReportBuilder` - Simple, default styles
- `DynamicReportBuilder` - Complex, custom everything
- `ReflectiveReportBuilder` - Auto-detect from data

**Registration Pattern**: Separate managers convert DJ domain objects to JasperReports design elements by entity type

**Adapter Pattern**: DJ wraps JasperReports API for simplified usage

**Factory Pattern**: `DJCompilerFactory`, `ReportWriterFactory` for strategy selection

## Key Concepts

### Builder Selection

**FastReportBuilder** - Use for quick reports with default styling:
```java
FastReportBuilder drb = new FastReportBuilder();
drb.addColumn("State", "state", String.class.getName(), 30)
   .addColumn("Amount", "amount", Float.class.getName(), 70)
   .setTitle("Sales Report")
   .build();
```

**DynamicReportBuilder** - Use for full control over styles, layouts, formats:
```java
DynamicReportBuilder drb = new DynamicReportBuilder();
drb.setTitle("Report")
   .setDetailHeight(15)
   .setMargins(30, 20, 30, 15)
   .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
   .addColumn(columnBuilder.build())
   .build();
```

**ReflectiveReportBuilder** - Use for instant reports from bean collections (auto-detects property types)

### Layout Management

- `ClassicLayoutManager` automatically positions report elements
- DJ calculates column widths, element placement, band heights
- Use `setUseFullPageWidth(true)` for proportional column resizing to fit page
- Manual column widths must fit within page margins (calculated as pageWidth - leftMargin - rightMargin)

### Data Sources

**Primary**: `JRBeanCollectionDataSource` - Pass Java bean collections

**Also supported**:
- JDBC `Connection` or `ResultSet`
- Custom `JRDataSource` implementations

Test classes typically use `JRBeanCollectionDataSource(TestRepositoryProducts.getDummyCollection())`

### Expression Languages

DJ supports multiple expression languages via `DJConstants`:
- `DJConstants.LANGUAGE_JAVA` (default)
- `DJConstants.LANGUAGE_GROOVY`
- `DJConstants.LANGUAGE_JAVASCRIPT`

Set via `DynamicReportBuilder.setReportLanguage()`

### Extension Points

**Custom columns**: Extend `AbstractColumn` and implement required methods

**Custom expressions**: Implement `CustomExpression` interface with `evaluate()` method

**Custom formatters**: Implement `DJValueFormatter` for custom value display logic

**Custom layouts**: Extend `LayoutManager` for alternative element positioning

## Testing

### Test Structure

All tests extend `BaseDjReportTest`:

1. Override `buildReport()` to return a `DynamicReport`
2. Test framework calls `testReport()` which:
   - Generates the report via `DynamicJasperHelper.generateJasperReport()`
   - Fills the report via `JasperFillManager.fillReport()`
   - Exports to PDF and other formats
3. Generated reports saved in `target/` directory

### Running Tests

**Via JUnit runner**: Window auto-closes after test completes

**Via main() method**: JasperViewer window stays open for manual inspection
```java
public static void main(String[] args) throws Exception {
    FastReportTest test = new FastReportTest();
    test.testReport();
}
```

**Common test base classes**:
- `BaseDjReportTest` - Standard test infrastructure
- Tests in `src/test/java/ar/com/fdvs/dj/test/` provide extensive examples

### Writing Test Assertions

**IMPORTANT**: Always add assertions to validate report content. Tests without assertions only catch exceptions, not bugs in calculations, data, or formatting.

**Use content-based validation** (version-agnostic):
```java
@Override
public void testReport() throws Exception {
    super.testReport();

    // Validate column headers
    assertColumnHeader("State");
    assertColumnHeader("Amount");

    // Validate title
    assertTextExists("November 2024 sales report");

    // Validate data appears
    assertTextExists("Florida");
    assertTextExists("New York");

    // Validate formatted values (currency, numbers)
    assertTextMatchesPatternCount("\\$ [0-9,]+\\.[0-9]{2}", 5);

    // Validate report structure
    assertPageCount(1);
    assertReportNotEmpty();
}
```

**Available assertion helpers** (in `BaseDjReportTest`):
- `assertColumnHeader(String)` - Verify column header exists
- `assertColumnHeadersInOrder(String...)` - Verify headers in order
- `assertTextExists(String)` - Verify text appears at least once
- `assertTextOccurs(String, int)` - Verify exact occurrence count
- `assertTextMatchesPattern(String)` - Verify regex pattern matches
- `assertTextMatchesPatternCount(String, int)` - Verify pattern match count
- `assertPageCount(int)` - Verify minimum page count
- `assertReportNotEmpty()` - Basic sanity check

**DO**:
- Validate column headers, titles, labels
- Validate data values appear (state names, product names)
- Validate formatted numbers (currency, percentages, dates) using regex patterns
- Validate group calculations appear (SUM, COUNT values)
- Keep assertions lightweight (avoid heavy computation)

**DON'T**:
- Use hardcoded element positions/indices (brittle across JasperReports versions)
- Validate exact pixel coordinates or element counts
- Validate subtitle text (often dynamic dates, may not render consistently)
- Validate visual attributes (colors, fonts) - can't extract from JasperPrint text
- Over-assert (focus on critical content, not every detail)

**Regex patterns for formatted values**:
```java
// Currency: $ 1,234.56
assertTextMatchesPattern("\\$ [0-9,]+\\.[0-9]{2}");

// Percentage: 45.6%
assertTextMatchesPattern("[0-9]+\\.[0-9]+%");

// Date: 12/31/2024
assertTextMatchesPattern("[0-9]{2}/[0-9]{2}/[0-9]{4}");

// Integer: 1,234
assertTextMatchesPattern("[0-9,]+");
```

**Group layout considerations**:
- `GroupLayout.VALUE_IN_HEADER` - Column headers may not render, validate data instead
- `GroupLayout.DEFAULT_WITH_HEADER` - Column headers and group headers both appear
- Always validate group data (state names, branch names) regardless of layout

**Utility classes**:
- `ReportContentExtractor` - Extract text from JasperPrint (advanced usage)
- `ReportAssertions` - Low-level assertions (used by BaseDjReportTest helpers)

## Common Patterns & Gotchas

### JasperReports Version Compatibility
DJ 5.3.9 supports JasperReports 7.0.0-7.0.8. Incompatible versions may cause compilation or runtime errors.

### Column Width Management
If not using `setUseFullPageWidth(true)`, ensure total column widths fit within:
```
availableWidth = pageWidth - leftMargin - rightMargin
```

### Font Handling
DJ provides font extensions via `DynamicJasper-core-fonts` and `DynamicJasper-test-fonts` artifacts. Custom fonts require JasperReports font extension mechanism.

### Group Variables
Variables in groups require:
1. Define column: `ColumnBuilder.build()`
2. Create variable: `DJGroupVariable` with operation (SUM, COUNT, etc.)
3. Add to group: `GroupBuilder.addFooterVariable()`

### Conditional Styles
Apply styles conditionally via `ConditionalStyle`:
```java
ConditionalStyle conditionalStyle = new ConditionalStyle();
conditionalStyle.setCondition(new ConditionStyleExpression() {
    public Object evaluate(...) {
        return ((Number)value).doubleValue() > 1000;
    }
});
conditionalStyle.setStyle(style);
column.addConditionalStyle(conditionalStyle);
```

### Crosstabs and Charts
Both require specific builders:
- `CrosstabBuilder` for pivot tables
- `DJChartBuilder` for charts (bar, pie, line, etc.)

See test classes like `CrosstabReportTest` and `ChartReportTest` for examples.

### OSGi Bundles
Project generates OSGi manifest via maven-bundle-plugin. Bundle symbolic name: `DynamicJasper`

## Documentation

**Online docs**: http://dynamicjasper.com/
**Getting started**: `docs/getting-started.md`
**Sample PDFs**: `docs/sample-reports/`
**GitHub**: https://github.com/FDVSolutions/DynamicJasper
**Issues**: https://github.com/FDVSolutions/DynamicJasper/issues
