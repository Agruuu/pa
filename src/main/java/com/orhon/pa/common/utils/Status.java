package com.orhon.pa.common.utils;

public class Status {
	private String value;
	private String label;
	private boolean busy;
	private String createBy;

	public Status() {

	}

	public Status(String value, String label,String createBy) {
		this.value = value;
		this.label = label;
		this.createBy = createBy;
	}
	

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}
}
