package ar.com.fdvs.dj.core.registration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.Entity;

public class DJCrosstabRegistrationManager extends AbstractEntityRegistrationManager {

	private static final Log log = LogFactory.getLog(DJCrosstabRegistrationManager.class);
	private String type;
	
	public DJCrosstabRegistrationManager(String type, DynamicJasperDesign jd,  DynamicReport dr, LayoutManager layoutManager) {
		super(jd,dr,layoutManager);
		this.type = type;
	}

	protected void registerEntity(Entity entity) {
		log.debug("registering crosstab...");
		DJCrosstab crosstab = (DJCrosstab) entity;
		if (crosstab.getMeasures() != null && !crosstab.getMeasures().isEmpty()){
			DJCrosstabMeasureRegistrationManager measuresRm = new DJCrosstabMeasureRegistrationManager(type, getDjd(),getDynamicReport(),getLayoutManager());
			measuresRm.registerEntities(crosstab.getMeasures());
		}
	}

	protected Object transformEntity(Entity entity) {
		return null;
	}

}
