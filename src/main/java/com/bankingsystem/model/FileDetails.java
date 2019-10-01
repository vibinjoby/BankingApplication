package com.bankingsystem.model;

public class FileDetails {
	private String readFileFrom;
	private String writeFileTo;

	public FileDetails(String readFileFrom, String writeFileTo) {
		super();
		this.readFileFrom = readFileFrom;
		this.writeFileTo = writeFileTo;
	}

	public String getReadFileFrom() {
		return readFileFrom;
	}

	public void setReadFileFrom(String readFileFrom) {
		this.readFileFrom = readFileFrom;
	}

	public String getWriteFileTo() {
		return writeFileTo;
	}

	public void setWriteFileTo(String writeFileTo) {
		this.writeFileTo = writeFileTo;
	}

	@Override
	public String toString() {
		return "FileDetails [readFileFrom=" + readFileFrom + ", writeFileTo=" + writeFileTo + "]";
	}

}
