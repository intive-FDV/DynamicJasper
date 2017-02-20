package ar.com.fdvs.dj.util;

import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.domain.Style;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;


public class Utils {

	private Utils(){

	}

	/**
	 * Returns true if collection is null or empty
	 * @param col
	 * @return
	 */
	public static boolean isEmpty(Collection col) {
		return col == null || col.isEmpty();

	}

	public static void addNotNull(Collection col, Object obj){
		if (col == null || obj == null)
			return;

		col.add(obj);
	}


	/**
	 * This takes into account objects that breaks the JavaBean convention
	 * and have as getter for Boolean objects an "isXXX" method.
	 * @param dest
	 * @param orig
	 */
	public static void copyProperties(Object dest, Object orig){
		try {
			if (orig != null && dest != null){
				BeanUtils.copyProperties(dest, orig);

				PropertyUtils putils = new PropertyUtils();
	            PropertyDescriptor origDescriptors[] = putils.getPropertyDescriptors(orig);

				for (PropertyDescriptor origDescriptor : origDescriptors) {
					String name = origDescriptor.getName();
					if ("class".equals(name)) {
						continue; // No point in trying to set an object's class
					}

					Class propertyType = origDescriptor.getPropertyType();
					if (!Boolean.class.equals(propertyType) && !(Boolean.class.equals(propertyType)))
						continue;

					if (!putils.isReadable(orig, name)) { //because of bad convention
						Method m = orig.getClass().getMethod("is" + name.substring(0, 1).toUpperCase() + name.substring(1), (Class<?>[]) null);
						Object value = m.invoke(orig, (Object[]) null);

						if (putils.isWriteable(dest, name)) {
							BeanUtilsBean.getInstance().copyProperty(dest, name, value);
						}
					}

				}
			}
		} catch (Exception e) {
			throw new DJException("Could not copy properties for shared object: " + orig +", message: " + e.getMessage(),e);
		}
	}


	public static void main(String[] args) {
		JRDesignStyle s1 = new Style().transform();
		JRDesignStyle s2 = new Style().transform();

		s1.setBold(Boolean.TRUE);
		s1.setItalic(Boolean.TRUE);
		s1.setUnderline(Boolean.TRUE);

		Utils.copyProperties(s2, s1);

		System.out.println(s2.isBold() + " - " + s2.isItalic() + " - " + s2.isUnderline());


	}

	/**
	 * When adding a text to an expression, we have to make sure that the String will not
	 * break JasperReports expression syntax.<br>
	 * For example: if the title text is: <b>November "2009" sales</b><br>
	 * The double quotes (") must be escaped to <b>\"</b>, and in java that would be <b>\\\\\"</b><br>
	 *
	 * single bars "\" are escaped to "\\"
	 *
	 * To understand better, the expression can be tested in iReport expression editor.
	 * @param text
	 * @return
	 */
	public static String escapeTextForExpression(String text) {
		if (text == null)
			return null;
		text = text.replaceAll("\\\\", "\\\\\\\\");
		text = text.replaceAll("\"", "\\\\\"");
		return text;
	}

	public static JRDesignStyle cloneStyle(JRDesignStyle style){
		return (JRDesignStyle) style.clone(); //only for JR 3.5.1+
//		JRDesignStyle nstyle = new JRDesignStyle();
//		copyProperties(nstyle, style);
//		return nstyle;

	}

    /**
     * Check whether <code>string</code> has been set to
     * something other than <code>""</code> or <code>null</code>.
     * @param string the <code>String</code> to check
     * @return a boolean indicating whether the string was non-empty (and non-null)
     */
	public static boolean stringSet(String string) {
        return (string != null) && !"".equals(string);
    }


	public static boolean isEmpty(String text) {
		return text == null || "".equals(text.trim());

	}
}
