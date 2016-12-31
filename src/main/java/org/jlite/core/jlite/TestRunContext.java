package org.jlite.core.jlite;

import java.util.Hashtable;
import java.util.List;

import org.jlite.core.datasource.ExcelFileHandler2;
import org.jlite.core.reports.LogReporter;
import org.jlite.core.testmodel.TestData;
import org.jlite.core.testmodel.TestIteration;
import org.jlite.core.testmodel.TestStep;
import org.jlite.core.util.DateUtil;

public class TestRunContext {
	
	private TestData data;
	private TestIteration iteration;
	private ExcelFileHandler2 excelinputfile;
	private ExcelFileHandler2 excelreport;
	private LogReporter logreport;
	private ActionManager antman;
	private String inputfile;
	private String inputpath;
	private String baseoutputpath;
	private String testexceloutputpath;
	private String testresoucepath;
	private Hashtable<String, String> contextdata;
	private DateUtil dateutil;
	private List<TestStep> teststeps;
	
	
	public synchronized List<TestStep> getTeststeps() {
		return teststeps;
	}
	public synchronized void setTeststeps(List<TestStep> teststeps) {
		this.teststeps = teststeps;
	}
	public synchronized ExcelFileHandler2 getExcelinputfile() {
		return excelinputfile;
	}
	public synchronized void setExcelinputfile(ExcelFileHandler2 excelinputfile) {
		this.excelinputfile = excelinputfile;
	}
	public synchronized String getInputfile() {
		return inputfile;
	}
	public synchronized void setInputfile(String inputfile) {
		this.inputfile = inputfile;
	}
	public synchronized DateUtil getDateutil() {
		return dateutil;
	}
	public synchronized void setDateutil(DateUtil dateutil) {
		this.dateutil = dateutil;
	}
	public synchronized String getTestresoucepath() {
		return testresoucepath;
	}
	public synchronized void setTestresoucepath(String testresoucepath) {
		this.testresoucepath = testresoucepath;
	}
	public synchronized TestData getData() {
		return data;
	}
	public synchronized void setData(TestData data) {
		this.data = data;
	}
	public synchronized TestIteration getIteration() {
		return iteration;
	}
	public synchronized void setIteration(TestIteration iteration) {
		this.iteration = iteration;
	}
	
	public synchronized ExcelFileHandler2 getExcelreport() {
		return excelreport;
	}
	public synchronized void setExcelreport(ExcelFileHandler2 excelreport) {
		this.excelreport = excelreport;
	}
	public synchronized LogReporter getLogreport() {
		return logreport;
	}
	public synchronized void setLogreport(LogReporter logreport) {
		this.logreport = logreport;
	}
	public synchronized ActionManager getAntman() {
		return antman;
	}
	public synchronized void setAntman(ActionManager antman) {
		this.antman = antman;
	}
	public synchronized String getInputpath() {
		return inputpath;
	}
	public synchronized void setInputpath(String inputpath) {
		this.inputpath = inputpath;
	}
	public synchronized String getBaseoutputpath() {
		return baseoutputpath;
	}
	public synchronized void setBaseoutputpath(String baseoutputpath) {
		this.baseoutputpath = baseoutputpath;
	}
	
	public synchronized String getTestexceloutputpath() {
		return testexceloutputpath;
	}
	public synchronized void setTestexceloutputpath(String testexceloutputpath) {
		this.testexceloutputpath = testexceloutputpath;
	}
	public synchronized Hashtable<String, String> getContextdata() {
		return contextdata;
	}
	public synchronized void setContextdata(Hashtable<String, String> contextdata) {
		this.contextdata = contextdata;
	}
	
	

}
