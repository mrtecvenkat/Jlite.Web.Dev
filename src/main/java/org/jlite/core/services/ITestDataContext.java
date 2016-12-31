package org.jlite.core.services;

import java.util.Hashtable;

public interface ITestDataContext {

	public Hashtable<String, String> getGloablData();
	public void addGloablData(Hashtable<String, String> gdata);
	public Hashtable<String, String> getRecordData();
	public void getGloablData(Hashtable<String, String> rdata);

}
