package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.domain.entities.Entity;

public class DJQuery extends DJBaseElement{

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
		
	private String text; 
	private String language;
	
	/**
	 * 
	 * @param text is the query
	 * @param language is the type (sql, hql, etc.), use constants from DJConstants
	 */
	public DJQuery(String text, String language) {
		super();
		this.text = text;
		this.language = language;
	}
	public String getText() {
		return text;
	}
	public String getLanguage() {
		return language;
	}

}