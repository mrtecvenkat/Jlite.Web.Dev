package org.jlite.core.testmodel;

import java.util.Hashtable;

public class TestData {
	
	private String RECORD_ID;
	private String RUN_FLAG;
	private String ITERATION_ID;
	private String RUN_RESULTS;
	private String RUN_DESCRIPTION;
	private String RUN_START_TIME;
	private String RUN_END_TIME;
	private String RUN_TOTAL_TIME;
	private TestGlobalData globalData;
	private Hashtable<String, String> testdata;
	public String getRECORD_ID() {
		return RECORD_ID;
	}
	public void setRECORD_ID(String rECORD_ID) {
		RECORD_ID = rECORD_ID;
	}
	public String getRUN_FLAG() {
		return RUN_FLAG;
	}
	public void setRUN_FLAG(String rUN_FLAG) {
		RUN_FLAG = rUN_FLAG;
	}
	public String getITERATION_ID() {
		return ITERATION_ID;
	}
	public void setITERATION_ID(String iTERATION_ID) {
		ITERATION_ID = iTERATION_ID;
	}
	public String getRUN_RESULTS() {
		return RUN_RESULTS;
	}
	public void setRUN_RESULTS(String rUN_RESULTS) {
		RUN_RESULTS = rUN_RESULTS;
	}
	public String getRUN_DESCRIPTION() {
		return RUN_DESCRIPTION;
	}
	public void setRUN_DESCRIPTION(String rUN_DESCRIPTION) {
		RUN_DESCRIPTION = rUN_DESCRIPTION;
	}
	public String getRUN_START_TIME() {
		return RUN_START_TIME;
	}
	public void setRUN_START_TIME(String rUN_START_TIME) {
		RUN_START_TIME = rUN_START_TIME;
	}
	public String getRUN_END_TIME() {
		return RUN_END_TIME;
	}
	public void setRUN_END_TIME(String rUN_END_TIME) {
		RUN_END_TIME = rUN_END_TIME;
	}
	public String getRUN_TOTAL_TIME() {
		return RUN_TOTAL_TIME;
	}
	public void setRUN_TOTAL_TIME(String rUN_TOTAL_TIME) {
		RUN_TOTAL_TIME = rUN_TOTAL_TIME;
	}
	public TestGlobalData getGlobalData() {
		return globalData;
	}
	public void setGlobalData(TestGlobalData globalData) {
		this.globalData = globalData;
	}
	public Hashtable<String, String> getTestdata() {
		return testdata;
	}
	public void setTestdata(Hashtable<String, String> testdata) {
		this.testdata = testdata;
	}
	


}
