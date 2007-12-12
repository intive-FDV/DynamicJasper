package ar.com.fdvs.dj.domain.builders;

import java.awt.Color;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Stretching;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

public class StyleBuilder {
	
	Style style = null;

	public StyleBuilder(boolean blank) {
		super();
		
		if (blank)
			style = Style.createBlankStyle(null);
		else
			style = new Style();
		
	}
	
	public Style build(){
		return style;
	}
	
	
	public StyleBuilder setName(String name){
		style.setName(name);
		return this;
	}
	
	public StyleBuilder setPattern(String pattern){
		style.setPattern(pattern);
		return this;
	}

	public StyleBuilder setFont(Font font){
		style.setFont(font);
		return this;
	}

	public StyleBuilder setHorizontalAlign(HorizontalAlign horizontalAlign){
		style.setHorizontalAlign(horizontalAlign);
		return this;
	}

	public StyleBuilder setVerticalAlign(VerticalAlign verticalAlign){
		style.setVerticalAlign(verticalAlign);
		return this;
	}

	public StyleBuilder setStretching(Stretching streching){
		style.setStreching(streching);
		return this;
	}
	
	public StyleBuilder setTextColor(Color textColor){
		style.setTextColor(textColor);
		return this;
	}


}
