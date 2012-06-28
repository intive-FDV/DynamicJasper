/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ar.com.fdvs.dj.test.domain;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Product {

	private Long id;
	private String productLine;
	private String item;
	private String state;
	private String branch;
	private Long quantity;
	private Float amount;
	private Code code = new Code();

    private Boolean showAsPrices;

	public static List statistics_ = new ArrayList();
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	static String[] images = {"confused.gif","cool.gif","happy.gif","puaj.gif","ungry.gif","what.gif"};
	static int counter = 0;
	static {

		try {
			statistics_.add(new Statistic(formatter.parse("01/01/2003"),"West",14.3f,50.4f,43.1f));
			statistics_.add(new Statistic(formatter.parse("01/01/2004"),"West",40.0f,49.4f,44.5f));
			statistics_.add(new Statistic(formatter.parse("01/01/2005"),"North",33.3f,63.4f,45f));
			statistics_.add(new Statistic(formatter.parse("01/01/2006"),"East",91.1f,34.4f,46f));
			statistics_.add(new Statistic(formatter.parse("01/01/2007"),"South",99.3f,52.4f,47f));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public List getStatistics(){
		return statistics_;
	}
	public List getEmptyStatistics(){
		return Collections.EMPTY_LIST;
	}

    public Product(){}

	public Product(Long id, String productLine, String item, String state, String branch, Long quantity, Float amount) {
		this.id = id;
		this.productLine = productLine;
		this.item = item;
		this.state = state;
		this.branch = branch;
		this.quantity = quantity;
		this.amount = amount;
    }

    public Product(Long id, String productLine, String item, String state, String branch, Long quantity, Float amount, boolean showAsPrices) {
        this(id, productLine, item, state, branch, quantity, amount);
        this.showAsPrices = showAsPrices;
    }

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Float getAmount() {
		return amount;
//		return new Float(quantity.floatValue());
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

    public Date getDate() {
        return new Date();
    }
    
    public Boolean getIsAvailable(){
    	return Boolean.valueOf(random.nextBoolean());
    }

    public Boolean getShowAsPrices() {
        return showAsPrices;
    }

    public void setShowAsPrices(Boolean showAsPrices) {
        this.showAsPrices = showAsPrices;
    }

    static Random random = new Random();

	public InputStream getImage() {
		InputStream ret = this.getClass().getClassLoader().getResourceAsStream("images/emoticons/" + images[counter++]);
		if (counter >= images.length)
			counter = 0;
		return ret;
	}
	
	public Code getCode() {
		return code;
	}	
	

	public class Code {
		
		public String getCode() {
			return "001-123ABC-HRC";
		}
	}
}
