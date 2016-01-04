package ar.com.fdvs.dj.core.layout;

import ar.com.fdvs.dj.util.Utils;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by mamana on 1/4/16.
 * Behaves like @ListLayoutManager but will add title and subtile row if present
 */
public class ExtendedListLayoutManager extends ListLayoutManager {

    private static final Log log = LogFactory.getLog(ListLayoutManager.class);

    @Override
    protected void startLayout() {
        super.startLayout();
        generateTitleBand();
    }

    protected void generateTitleBand() {
        log.debug("Generating title band...");
        JRDesignBand band = (JRDesignBand) getDesign().getPageHeader();
        int yOffset = 0;

        //If title is not present then subtitle will be ignored
        if (getReport().getTitle() == null)
            return;

        if (band != null && !getDesign().isTitleNewPage()){
            //Title and subtitle comes afer the page header
            yOffset = band.getHeight();

        } else {
            band = (JRDesignBand) getDesign().getTitle();
            if (band == null){
                band = new JRDesignBand();
                getDesign().setTitle(band);
            }
        }

        JRDesignTextField title = new JRDesignTextField();
        JRDesignExpression exp = new JRDesignExpression();
        if (getReport().isTitleIsJrExpression()){
            exp.setText(getReport().getTitle());
        }else {
            exp.setText("\"" + Utils.escapeTextForExpression( getReport().getTitle()) + "\"");
        }
        exp.setValueClass(String.class);
        title.setExpression(exp);
        title.setWidth(getReport().getOptions().getPrintableWidth());
        title.setHeight(getReport().getOptions().getTitleHeight());
        title.setY(yOffset);
        title.setRemoveLineWhenBlank(true);

        applyStyleToElement(getReport().getTitleStyle(), title);
        title.setStretchType( StretchTypeEnum.NO_STRETCH );
        band.addElement(title);

        JRDesignTextField subtitle = new JRDesignTextField();
        if (getReport().getSubtitle() != null) {
            JRDesignExpression exp2 = new JRDesignExpression();
            exp2.setText("\"" + getReport().getSubtitle() + "\"");
            exp2.setValueClass(String.class);
            subtitle.setExpression(exp2);
            subtitle.setWidth(getReport().getOptions().getPrintableWidth());
            subtitle.setHeight(getReport().getOptions().getSubtitleHeight());
            subtitle.setY(title.getY() + title.getHeight());
            subtitle.setRemoveLineWhenBlank(true);
            applyStyleToElement(getReport().getSubtitleStyle(), subtitle);
            title.setStretchType( StretchTypeEnum.NO_STRETCH );
            band.addElement(subtitle);
        }

    }

}
