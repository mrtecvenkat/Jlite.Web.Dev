package com.poet.core.test.views;

import org.jlite.core.jlite.ActionManager;
import org.jlite.core.jlite.TestKeywords;
import org.jlite.core.testmodel.TestStep;

public class AppHeader {

	private ActionManager am;
	private UI_AppHeader ui;

	public UI_AppHeader getUi() {
		return ui;
	}

	public void setUi(UI_AppHeader ui) {
		this.ui = ui;
	}

	public void init(ActionManager vam) {
		this.am = vam;
		this.setUi(new UI_AppHeader());
	}

	public TestStep goToHome(ActionManager vam) {
		this.init(vam);
		TestStep cmp = new TestStep();
		cmp.setCOMMAND(TestKeywords.ACTION_OPEN);
		cmp.setTARGET(ui.getUi_link_home());
		cmp.setVALUE("");
		String res = am.doAction(TestKeywords.ACTION_CLICK, ui.getUi_link_home(), TestKeywords.EMPTY);

		if (res.equalsIgnoreCase("")) {
			cmp.setRUN_RESULTS(TestKeywords.PASS);

		} else {
			cmp.setRUN_RESULTS(TestKeywords.FAIL);
			cmp.setRUN_DESCRIPTION(res);

		}

		return cmp;
	}

	public TestStep goToOfferHistory(ActionManager vam) {
		this.init(vam);
		TestStep cmp = new TestStep();
		cmp.setCOMMAND(TestKeywords.ACTION_OPEN);
		cmp.setTARGET(ui.getUi_link_offerhistory());
		cmp.setVALUE(TestKeywords.EMPTY);
		String res = am.doAction(TestKeywords.ACTION_CLICK, ui.getUi_link_offerhistory(), TestKeywords.EMPTY);

		if (res.equalsIgnoreCase("")) {
			cmp.setRUN_RESULTS(TestKeywords.PASS);

		} else {
			cmp.setRUN_RESULTS(TestKeywords.FAIL);
			cmp.setRUN_DESCRIPTION(res);

		}

		return cmp;
	}

}

class UI_AppHeader {

	private String ui_link_home = "@jq::T(Home)";
	private String ui_link_offerhistory = "@jq::T(Offer History)";
	// private String ui_link_favorites;
	// private String ui_link_awaitingactions;
	private String ui_link_reporting = "@jq::T(Reporting)";
	private String ui_link_help = "@jq::T(Help)";
	private String ui_link_myprofile = "@jq::T(My Profile)";
	private String ui_link_myorg = "@jq::T(My Organization)";
	private String ui_link_accountmanagement = "@jq::T(Account Management)";
	private String ui_link_logout = "@jq::T(Logout)";

	// private String ui_link_hiusertext;
	public String getUi_link_home() {
		return ui_link_home;
	}

	public void setUi_link_home(String ui_link_home) {
		this.ui_link_home = ui_link_home;
	}

	public String getUi_link_offerhistory() {
		return ui_link_offerhistory;
	}

	public void setUi_link_offerhistory(String ui_link_offerhistory) {
		this.ui_link_offerhistory = ui_link_offerhistory;
	}

	// public String getUi_link_favorites() {
	// return ui_link_favorites;
	// }
	// public void setUi_link_favorites(String ui_link_favorites) {
	// this.ui_link_favorites = ui_link_favorites;
	// }
	// public String getUi_link_awaitingactions() {
	// return ui_link_awaitingactions;
	// }
	// public void setUi_link_awaitingactions(String ui_link_awaitingactions) {
	// this.ui_link_awaitingactions = ui_link_awaitingactions;
	// }
	public String getUi_link_reporting() {
		return ui_link_reporting;
	}

	public void setUi_link_reporting(String ui_link_reporting) {
		this.ui_link_reporting = ui_link_reporting;
	}

	public String getUi_link_help() {
		return ui_link_help;
	}

	public void setUi_link_help(String ui_link_help) {
		this.ui_link_help = ui_link_help;
	}

	public String getUi_link_myprofile() {
		return ui_link_myprofile;
	}

	public void setUi_link_myprofile(String ui_link_myprofile) {
		this.ui_link_myprofile = ui_link_myprofile;
	}

	public String getUi_link_myorg() {
		return ui_link_myorg;
	}

	public void setUi_link_myorg(String ui_link_myorg) {
		this.ui_link_myorg = ui_link_myorg;
	}

	public String getUi_link_accountmanagement() {
		return ui_link_accountmanagement;
	}

	public void setUi_link_accountmanagement(String ui_link_accountmanagement) {
		this.ui_link_accountmanagement = ui_link_accountmanagement;
	}

	public String getUi_link_logout() {
		return ui_link_logout;
	}

	public void setUi_link_logout(String ui_link_logout) {
		this.ui_link_logout = ui_link_logout;
	}
	// public String getUi_link_hiusertext() {
	// return ui_link_hiusertext;
	// }
	// public void setUi_link_hiusertext(String ui_link_hiusertext) {
	// this.ui_link_hiusertext = ui_link_hiusertext;
	// }

}