package ar.com.fdvs.dj.core.registration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJCrosstabMeasure;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.Entity;

public class DJCrosstabMeasureRegistrationManager extends AbstractEntityRegistrationManager {

	private static final Log log = LogFactory.getLog(DJCrosstabMeasureRegistrationManager.class);
	private String type;

	public DJCrosstabMeasureRegistrationManager(String type, DynamicJasperDesign jd,  DynamicReport dr, LayoutManager layoutManager) {
		super(jd,dr,layoutManager);
		this.type = type;
	}

	protected void registerEntity(Entity entity) {
		log.debug("registering measure...");
		DJCrosstabMeasure measure = (DJCrosstabMeasure) entity;
		if (measure.getConditionalStyles() != null && !measure.getConditionalStyles().isEmpty()){
			ConditionalStylesRegistrationManager conditionalStylesRm = new ConditionalStylesRegistrationManager(getDjd(),getDynamicReport(),measure.getProperty().getProperty() + "_" + type,getLayoutManager());
			conditionalStylesRm.registerEntities(measure.getConditionalStyles());
		}
	}

	protected Object transformEntity(Entity entity) {
		return null;
	}

}
