/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2007  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.core.layout;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.registration.ColumnsGroupVariablesRegistrationManager;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.ColumnsGroupVariable;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.GlobalGroupColumn;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;

/**
 * Main Layout Manager recommended for mostly cases.</br>
 * </br>
 * It provides DJ full features (styles, groups, conditional styles, </br>
 * expressions, group and total variables, etc)
 */
public class ClassicLayoutManager extends AbstractLayoutManager {

	private static final Log log = LogFactory.getLog(ClassicLayoutManager.class);

	public ClassicLayoutManager() {
		super();
	}

	protected void startLayout() {
		super.startLayout();
		generateTitleBand();
		generateHeaderBand();
		if ( getReport().getColumnsGroups()!=null)
			layoutGroups();
	}

	/**
	 * Adds title and subtitle to the TitleBand when it applies.
	 */
	private void generateTitleBand() {
		log.debug("Generating title band...");
		// If title is not present then subtitle will be ignored
		if (getReport().getTitle() == null)
			return;

		JRDesignBand titleBand = (JRDesignBand) getDesign().getTitle();
		if (titleBand == null)
			titleBand = new JRDesignBand();

		JRDesignStaticText title = new JRDesignStaticText();
		title.setText(getReport().getTitle());
		title.setWidth(getReport().getOptions().getPrintableWidth());
		title.setHeight(getReport().getOptions().getTitleHeight().intValue());
		applyStyleToTextElement(getReport().getTitleStyle(), title);
		titleBand.addElement(title);

		JRDesignStaticText subtitle = new JRDesignStaticText();
		if (getReport().getSubtitle() != null){
			subtitle.setText(getReport().getSubtitle());
			subtitle.setWidth(getReport().getOptions().getPrintableWidth());
			subtitle.setHeight(getReport().getOptions().getSubtitleHeight().intValue());
			subtitle.setY(title.getY() + title.getHeight());
			applyStyleToTextElement(getReport().getSubtitleStyle(), subtitle);
			titleBand.addElement(subtitle);
		}

		titleBand.setHeight(title.getHeight() + subtitle.getHeight());
		getDesign().setTitle(titleBand);
	}

	/**
	 * Layout columns in groups by reading the corresponding report options.
	 * @throws LayoutException
	 */
	private void layoutGroups() {
		log.debug("Starting groups layout...");
		int i = 0;
		for (Iterator iter = getReport().getColumnsGroups().iterator(); iter.hasNext();) {
			ColumnsGroup columnsGroup = (ColumnsGroup) iter.next();

			JRDesignGroup jgroup = (JRDesignGroup) getDesign().getGroupsList().get(i++);
			JRDesignBand header = (JRDesignBand) jgroup.getGroupHeader();
			JRDesignBand footer = (JRDesignBand) jgroup.getGroupFooter();
			header.setHeight(columnsGroup.getHeaderHeight().intValue());
			footer.setHeight(columnsGroup.getFooterHeight().intValue());
			if (columnsGroup.getLayout().isShowColumnNames()) {
				for (Iterator iterator =  getReport().getColumns().iterator(); iterator.hasNext();) {
					AbstractColumn col = (AbstractColumn) iterator.next();

					JRDesignTextField designStaticText = new JRDesignTextField();
					JRDesignExpression exp = new JRDesignExpression();
					exp.setText("\"" + col.getTitle() + "\"");
					exp.setValueClass(String.class);
					designStaticText.setExpression(exp);
					designStaticText.setHeight(columnsGroup.getHeaderHeight().intValue());
					designStaticText.setWidth(col.getWidth().intValue());
					designStaticText.setX(col.getPosX().intValue());
					designStaticText.setY(col.getPosY().intValue());

					applyStyleToTextElement(col.getHeaderStyle(), designStaticText);

					header.addElement(designStaticText);
				}
			}
			layoutGroupVariables(columnsGroup, jgroup);
		}
	}

	/**
	 * If variables are present for a given group, they are placed in it's
	 * header/footer band.
	 * @param ColumnsGroup group
	 * @param JRDesignGroup jgroup
	 * @throws LayoutException
	 */
	private void layoutGroupVariables(ColumnsGroup group, JRDesignGroup jgroup) {
		log.debug("Starting groups variables layout...");
		JRDesignBand headerBand = (JRDesignBand) jgroup.getGroupHeader();
		JRDesignBand footerBand = (JRDesignBand) jgroup.getGroupFooter();

		int headerOffset = changeHeaderBandHeightForVariables(headerBand, group);

		if (group.getLayout().isShowValueInHeader())
			insertValueInHeader(headerBand, group, headerOffset);

		placeVariableInBand(group.getHeaderVariables(), group, jgroup, ColumnsGroupVariablesRegistrationManager.HEADER, headerBand, headerOffset);
		placeVariableInBand(group.getFooterVariables(), group, jgroup, ColumnsGroupVariablesRegistrationManager.FOOTER, footerBand, 0);
	}

	private void placeVariableInBand(Collection variables, ColumnsGroup columnsGroup, JRDesignGroup jgroup, String type, JRDesignBand band, int yOffset) {
		log.debug("Placing variables in "+type+" band...");
		if ((variables != null)&&(variables.size()>0)) {
			Iterator it = variables.iterator();
			while (it.hasNext()) {
				ColumnsGroupVariable var = (ColumnsGroupVariable) it.next();
				AbstractColumn col = (AbstractColumn) var.getColumnToApplyOperation();

				String variableName = col.getGroupVariableName(type, columnsGroup.getColumnToGroupBy().getColumnProperty().getProperty());

				JRDesignExpression expression = new JRDesignExpression();
				JRDesignTextField textField = new JRDesignTextField();
				expression.setText("$V{" + variableName + "}");
				expression.setValueClassName(col.getVariableClassName(var.getOperation()));
				if (var.getOperation() != ColumnsGroupVariableOperation.COUNT)
					textField.setPattern(col.getPattern());

				textField.setKey(variableName);
				textField.setExpression(expression);

				textField.setX(col.getPosX().intValue());
				if (yOffset!=0)
					textField.setY(yOffset);

				textField.setHeight(columnsGroup.getHeaderHeight().intValue());
				textField.setWidth(col.getWidth().intValue());

				textField.setEvaluationTime(JRExpression.EVALUATION_TIME_GROUP);

				textField.setEvaluationGroup(jgroup);

				applyStyleToTextElement(col.getStyle(), textField);

				band.addElement(textField);

			}

			if (columnsGroup.getColumnToGroupBy() instanceof GlobalGroupColumn) {
				int totalWidth = 0;

				totalWidth = ((ColumnsGroupVariable)variables.iterator().next()).getColumnToApplyOperation().getPosX().intValue();

				GlobalGroupColumn globalCol = (GlobalGroupColumn) columnsGroup.getColumnToGroupBy();

				JRDesignTextField globalTextField = new JRDesignTextField();
				JRDesignExpression globalExp = new JRDesignExpression();
				globalExp.setText(globalCol.getTextForExpression());
				globalExp.setValueClassName(globalCol.getValueClassNameForExpression());
				globalTextField.setExpression(globalExp);

				globalTextField.setHeight(band.getHeight());
				globalTextField.setWidth(totalWidth);
				globalTextField.setX(((AbstractColumn)getReport().getColumns().get(0)).getPosX().intValue());
				if (type.equals(ColumnsGroupVariablesRegistrationManager.HEADER))
					globalTextField.setY(yOffset);
				globalTextField.setKey("global_legend_"+type);

				applyStyleToTextElement(globalCol.getStyle(), globalTextField);

				band.addElement(globalTextField);
			}
		}
	}

	private void insertValueInHeader(JRDesignBand headerBand, ColumnsGroup columnsGroup, int headerOffset) {
		JRDesignTextField textField = generateTextFieldFromColumn(columnsGroup.getColumnToGroupBy(), columnsGroup.getHeaderHeight().intValue(), columnsGroup);
		textField.setHorizontalAlignment(columnsGroup.getColumnToGroupBy().getStyle().getHorizontalAlign().getValue());
		textField.setY(textField.getY() + headerOffset);
		headerBand.addElement(textField);
	}

	private int changeHeaderBandHeightForVariables(JRDesignBand headerBand, ColumnsGroup columnsGroup) {
		int result = 0;
		if (!headerBand.getChildren().isEmpty()) {
			int acu = headerBand.getHeight();
			headerBand.setHeight(headerBand.getHeight() + columnsGroup.getHeaderHeight().intValue());
			result = acu;
		} else
			headerBand.setHeight(getReport().getOptions().getFooterHeight().intValue());
		return result;
	}

	private void generateHeaderBand() {
		log.debug("generating header band...");
		JRDesignBand header = (JRDesignBand) getDesign().getColumnHeader();
		if (header == null) {
			header = new JRDesignBand();
			getDesign().setColumnHeader(header);
		}
		if (!DynamicJasperHelper.existsGroupWithColumnNames(getReport().getColumnsGroups()))
			generateHeaderBand(header);
	}

	protected void transformDetailBandTextField(AbstractColumn column, JRDesignTextField textField) {
		//TODO: Set default characters when null values are found.
		log.debug("transforming detail band text field...");
		ColumnsGroup group = DynamicJasperHelper.getColumnGroup(column, getReport().getColumnsGroups());
		if (group!=null&&!group.getLayout().isShowValueForEach()) {
			textField.getStyle().setBorder((byte)0);
			JRDesignExpression exp = new JRDesignExpression();
			exp.setText("\" \"");
			exp.setValueClassName(String.class.getName());
			textField.setExpression(exp);
		}
	}

}
