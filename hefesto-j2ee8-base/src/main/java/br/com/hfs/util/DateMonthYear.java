package br.com.hfs.util;

import java.io.Serializable;
import java.util.Date;

public class DateMonthYear implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String monthYear;
	private int month;
	private int year;
	private Date date;

	public DateMonthYear(String monthYear, int month, int year, Date date) {
		super();
		
		String sid = month > 9 ? String.valueOf(month) : "0" + month;
		sid = String.valueOf(year) + sid;
		
		this.id = Long.parseLong(sid);
		this.monthYear = monthYear;
		this.month = month;
		this.year = year;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


}
