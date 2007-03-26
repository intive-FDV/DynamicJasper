package ar.com.fdvs.dj.domain.builders;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Date;

/**
 * Builder created to give users an easy way of creating a DynamicReport based on a collection.</br>
 * </br>
 * Usage example: </br>
 * DynamicReport report =
 *      new ReflectiveReportBuilder(data, new String[]{"productLine", "item", "state", "id", "branch", "quantity", "amount"})
 *      .addGroups(3).build();
 * </br>
 * Like with all DJ's builders, it's usage must end with a call to build() mehtod.
 * </br>
 */
public class ReflectiveReportBuilder extends FastReportBuilder {

    private static final Log LOGGER = LogFactory.getLog(ReflectiveReportBuilder.class);

    /**
     * Takes the first item in the collection and adds a column for every property in that item.
     * @param _data the data collection. A not null and nor empty collection should be passed.
     */
    public ReflectiveReportBuilder(final Collection _data) {
        if (_data == null || _data.isEmpty()) {
            throw new IllegalArgumentException("Parameter _data is null or empty");
        }
        final Object item = _data.iterator().next();
        try {
            addProperties(PropertyUtils.getPropertyDescriptors(item));
        } catch (Exception ex) {
            LOGGER.error("", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Adds a column for every property specified in the array.
     * @param _data the data collection. A not null and nor empty collection should be passed.
     * @param _propertiesNames and array with the names of the desired properties.
     */
    public ReflectiveReportBuilder(final Collection _data, final String[] _propertiesNames) {
        if (_data == null || _data.isEmpty()) {
            throw new IllegalArgumentException("Parameter _data is null or empty");
        }
        if (_propertiesNames == null || _propertiesNames.length == 0) {
            throw new IllegalArgumentException("Parameter _propertiesNames is null or empty");
        }
        final Object item = _data.iterator().next();
        final PropertyDescriptor[] properties = new PropertyDescriptor[_propertiesNames.length];
        try {
            for (int i = 0; i < _propertiesNames.length; i++) {
                final String propertiesName = _propertiesNames[i];
                properties[i] = PropertyUtils.getPropertyDescriptor(item, propertiesName);
            }
            addProperties(properties);
        } catch (Exception ex) {
            LOGGER.error("", ex);
        }
    }

    /**
     * Adds columns for the specified properties.
     * @param _properties the array of <code>PropertyDescriptor</code>s to be added.
     * @throws ColumnBuilderException if an error occurs.
     * @throws ClassNotFoundException if an error occurs.
     */
    private void addProperties(final PropertyDescriptor[] _properties) throws ColumnBuilderException, ClassNotFoundException {
        for (int i = 0; i < _properties.length; i++) {
            final PropertyDescriptor property = _properties[i];
            if (isValidProperty(property)) {
                addColumn(getColumnTitle(property), property.getName(), property.getPropertyType().getName(), getColumnWidth(property));
            }
        }
        addUseFullPageWidth(true);
    }

    /**
     * Calculates a column title using camel humps to separate words.
     * @param _property the property descriptor.
     * @return the column title for the given property.
     */
    private static String getColumnTitle(final PropertyDescriptor _property) {
        final StringBuffer buffer = new StringBuffer();
        final String name = _property.getName();
        buffer.append(Character.toUpperCase(name.charAt(0)));
        for (int i = 1; i < name.length(); i++) {
            final char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                buffer.append(' ');
            }
            buffer.append(c);
        }
        return buffer.toString();
    }

    /**
     * Checks if a property is valid to be included in the report.
     * @param _property the property.
     * @return true if the property is not class, and it is of a valid type.
     */
    private static boolean isValidProperty(final PropertyDescriptor _property) {
        return !"class".equals(_property.getName()) && isValidPropertyClass(_property);
    }

    /**
     * Checks if a property's type is valid to be included in the report.
     * @param _property the property.
     * @return true if the property is is of a valid type.
     */
    private static boolean isValidPropertyClass(final PropertyDescriptor _property) {
        final Class type = _property.getPropertyType();
        return Number.class.isAssignableFrom(type) || type == String.class || Date.class.isAssignableFrom(type) || type == Boolean.class;
    }

    /**
     * Calculates the column width according to its type.
     * @param _property the property.
     * @return the column width.
     */
    private static int getColumnWidth(final PropertyDescriptor _property) {
        final Class type = _property.getPropertyType();
        if (Float.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type)) {
            return 70;
        } else if (type == Boolean.class) {
            return 10;
        } else if (Number.class.isAssignableFrom(type)) {
            return 60;
        } else if (type == String.class) {
            return 100;
        } else if (Date.class.isAssignableFrom(type)) {
            return 50;
        } else {
            return 50;
        }
    }
}
