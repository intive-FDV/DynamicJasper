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

package ar.com.fdvs.dj.test.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {

	private Long id;
	private String productLine;
	private String item;
	private String state;
	private String branch;
	private Long quantity;
	private Float amount;
	
	public static List statistics_ = new ArrayList();
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
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

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Float getAmount() {
		return amount;
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
}
