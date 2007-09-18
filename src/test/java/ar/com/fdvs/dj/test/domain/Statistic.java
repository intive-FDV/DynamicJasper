package ar.com.fdvs.dj.test.domain;

import java.util.Date;

public class Statistic {
	
	private Date date;
	private String name;
	private float percentage;
	private float average;
	private float amount;
	
	public Statistic() {
		super();
	}
	public Statistic(Date date, String name, float percentage, float average, float amount) {
		super();
		this.date = date;
		this.name = name;
		this.percentage = percentage;
		this.average = average;
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	public float getAverage() {
		return average;
	}
	public void setAverage(float average) {
		this.average = average;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}
