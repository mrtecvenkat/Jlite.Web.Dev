package org.jlite.core.jlite;

import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.jlite.core.testmodel.TestData;
import org.jlite.core.testmodel.TestStep;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;



import java.util.Hashtable;





public class ActionManager {
	Logger log = Logger.getLogger(ActionManager.class);
	public static Hashtable<String, String> amresuts = new Hashtable<String, String>();

	private WebDriver wd;
	int gbltimout = 60;
	long timelaps = 500;
	int actionCounter = 0;
	String parentwindow = "";
	//LogReporter log = null;
	private TestData data;
	
	
	public WebDriver getWd() {
		return wd;
	}

	public void setWd(WebDriver wd) {
		this.wd = wd;
	}

	public TestData getData() {
		return data;
	}

	public void setData(TestData data) {
		this.data = data;
	}

	public WebDriver getWebDriver() {
		return wd;
	}

	public ActionManager(WebDriver iwd) {
		// rdd = vrdd;Lo
		this.setWd(iwd);
		this.doMaximize();
		parentwindow = wd.getWindowHandle();
	}

	private ArrayList<Object> actionObj;

	public ArrayList<Object> getActionObj() {
		return actionObj;
	}

	public void setActionObj(ArrayList<Object> actionObj) {
		this.actionObj = actionObj;
	}

	// @org.testng.annotations.Test
	public String doAction(TestStep tstep) {
		// System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"
		// );

		actionCounter = 0;
		String cmd = tstep.getCOMMAND().trim();
		String tar = tstep.getTARGET().trim();
		String val = tstep.getVALUE().trim();

		return this.doAction(cmd, tar, val);
	}

	public String doAction(String vcmd, String vtar, String vval) {
//		log.log(log.INFO, AKW.LOG_KEY_TESTSTEP + " Command :" + vcmd);
//		log.log(log.INFO, AKW.LOG_KEY_TESTSTEP + " Tareget :" + vtar);
//		log.log(log.INFO, AKW.LOG_KEY_TESTSTEP + " Value :" + vval);
		try {
			Thread.currentThread().sleep(250);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"
		// );
		String retval = "";
		actionCounter = 0;
		String cmd = vcmd.trim();
		String tar = vtar.trim();
		String val = vval.trim();

		// log.log(log.INFO, AKW.LOG_KEY_TESTSTEP +" Command :"+cmd);
		// log.log(log.INFO, AKW.LOG_KEY_TESTSTEP +" Tareget :"+tar);
		// log.log(log.INFO, AKW.LOG_KEY_TESTSTEP +" Value :"+val);
		//
		if (cmd.equalsIgnoreCase(TestKeywords.ACTION_OPEN)) {
			retval = this.doOpen(tar);
		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_MAXIMIZE)) {
			retval = this.doMaximize();
		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_CLOSE)) {
			retval = this.doClose();
		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_EXIT)) {
			retval = this.doExit();
		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_REOPEN)) {
			retval = this.doReOpen(tar, val);
		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_TCLICK)) {
			retval = this.doToolClick(tar);
		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_TSET)) {
			retval = this.doToolSet(tar, val);
		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_TSELECT)) {
			retval = this.doToolSelect(tar, val);
		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_SWITCHBACKTOWINDOW)) {
			retval = this.doSwitchBack();
		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_SWITCHTOWINDOW)) {
			if (tar.equalsIgnoreCase(TestKeywords.ACTION_SWITCHTOWINDOW)) {
				retval = this.doSwitchNewWindow();
			} else {
				retval = this.doSwitchNewWindow(tar);
			}

		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_SWITCHTOFRAME)) {
			if (tar.equalsIgnoreCase(TestKeywords.ACTION_SWITCHTOFRAME)) {
				retval = this.doSwitchToFrame();
			} else {
				retval = this.doSwitchToFrame(tar);
			}

		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_HANDLEALERT)) {
			if (tar.equalsIgnoreCase(TestKeywords.ACTION_GETALERTTEXT)) {
				retval = this.doGetAlertTest();
			} else {
				retval = this.doHandleAlert(tar);
			}

		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_RUNSCRIPT)) {
			retval = this.doRunScript(tar);
		} else if (cmd.equalsIgnoreCase(TestKeywords.ACTION_EVAL)) {
			retval = this.doEval(tar);
		} else {

			retval = this.dorunjlite(cmd, tar, val);
		}

		return retval;
	}

	public WebElement getWebElement(String tar) {
		WebElement rval = null;
		try {
			rval = wd.findElement(By.id(tar));
		} catch (Exception e1) {
			try {
				rval = wd.findElement(By.name(tar));
			} catch (Exception e2) {
				try {
					rval = wd.findElement(By.cssSelector(tar));
				} catch (Exception e3) {
					try {
						rval = wd.findElement(By.className(tar));
					} catch (Exception e4) {
						try {
							rval = wd.findElement(By.linkText(tar));
						} catch (Exception e5) {
							try {
								rval = wd.findElement(By.partialLinkText(tar));
							} catch (Exception e6) {
								try {
									rval = wd.findElement(By.tagName(tar));
								} catch (Exception e7) {
									try {
										rval = wd.findElement(By.xpath(tar));
									} catch (Exception e8) {

									}
								}
							}
						}
					}
				}
			}
		}
		if (rval == null && actionCounter < gbltimout) {
			try {
				actionCounter++;
				Thread.currentThread().sleep(timelaps);
				rval = this.getWebElement(tar);
			} catch (Exception er) {

			}
		}
		return rval;
	}

	public List<WebElement> getWebElements(String tar) {
		List<WebElement> rval = null;
		try {
			rval = wd.findElements(By.id(tar));
		} catch (Exception e1) {
			try {
				rval = wd.findElements(By.name(tar));
			} catch (Exception e2) {
				try {
					rval = wd.findElements(By.cssSelector(tar));
				} catch (Exception e3) {
					try {
						rval = wd.findElements(By.className(tar));
					} catch (Exception e4) {
						try {
							rval = wd.findElements(By.linkText(tar));
						} catch (Exception e5) {
							try {
								rval = wd.findElements(By.partialLinkText(tar));
							} catch (Exception e6) {
								try {
									rval = wd.findElements(By.tagName(tar));
								} catch (Exception e7) {
									try {
										rval = wd.findElements(By.xpath(tar));
									} catch (Exception e8) {

									}
								}
							}
						}
					}
				}
			}
		}
		if (rval == null && actionCounter < gbltimout) {
			try {
				actionCounter++;
				Thread.currentThread().sleep(timelaps);
				rval = this.getWebElements(tar);
			} catch (Exception er) {

			}
		}
		return rval;
	}

	private String dorunjlite(String cmd, String tar, String val) {
		String retval = "";
		JavascriptExecutor js = (JavascriptExecutor) wd;
		try {

			retval = (String) js.executeScript(this.getCommand(cmd, tar, val));
			if (retval.toLowerCase().contains("fail")
					|| retval.toLowerCase().contains("wait") && actionCounter < gbltimout) {
				if (actionCounter < gbltimout) {
					try {
						actionCounter++;
						Thread.currentThread().sleep(timelaps);
						retval = this.dorunjlite(cmd, tar, val);
					} catch (Exception er) {

					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			retval = "fail " + e.getMessage();

		}
		return retval;
	}

	private String doRunScript(String tar) {
		JavascriptExecutor js = (JavascriptExecutor) wd;

		try {

			String retval = (String) js.executeScript(tar);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	private String doEval(String tar) {
		return "";
	}

	private boolean isSwitchWindow(String windowid) {
		boolean retval = false;
		Set<String> allWindows = wd.getWindowHandles();
		Iterator<String> itr = allWindows.iterator();
		while (itr.hasNext()) {
			String cwindowid = itr.next();
			if (!cwindowid.equalsIgnoreCase(parentwindow)) {
				if (windowid.equalsIgnoreCase("")) {
					retval = true;
					wd.switchTo().window(cwindowid);

					break;
				} else {
					if (windowid.equalsIgnoreCase(cwindowid)) {
						retval = true;
						wd.switchTo().window(cwindowid);
						break;
					}
				}

			}
		}

		if (!retval && actionCounter < gbltimout) {
			try {
				actionCounter++;
				Thread.currentThread().sleep(timelaps);
				retval = this.isSwitchWindow(windowid);
			} catch (Exception er) {

			}
		}

		return retval;
	}

	private String doSwitchNewWindow() {
		if (this.isSwitchWindow("")) {
			return "";
		} else {
			return "fail : unable to switch new window";
		}

	}

	private String doSwitchNewWindow(String windowname) {
		if (this.isSwitchWindow(windowname)) {
			return "";
		} else {
			return "fail : unable to switch new window (" + windowname + ")";
		}
	}

	private String doSwitchToFrame() {
		return "";
	}

	private String doSwitchToFrame(String framedetails) {
		wd.switchTo().frame(framedetails);
		return "";
	}

	private String doSwitchBack() {
		wd.switchTo().window(parentwindow);
		return "";
	}

	private String doHandleAlert(String option) {
		Alert ael = wd.switchTo().alert();
		if (option.equalsIgnoreCase("Ok")) {
			ael.accept();
		} else {
			ael.dismiss();
		}
		return "";
	}

	private String doGetAlertTest() {
		return "";
	}

	private String doOpen(String url) {
		String retval = "";
		try {
			wd.get(url);
		} catch (Exception er) {
			retval = "fail : Exception occoured while opening url (" + url + ") system error message:"
					+ er.getMessage();
		}
		return retval;
	}

	private String doClose() {
		String retval = "";
		try {
			wd.close();
		} catch (Exception er) {
			retval = "fail : Exception occoured while closing browser system error message:" + er.getMessage();
		}
		return retval;
	}

	private String doMaximize() {
		String retval = "";
		try {
			wd.manage().window().maximize();
		} catch (Exception er) {
			retval = "fail : Exception occoured while maximizing browser system error message:" + er.getMessage();
		}
		return retval;
	}

	private String doToolClick(String tar) {
		String retval = "";
		try {
			WebElement wel = this.getWebElement(tar);
			if (wel != null) {
				Actions ac = new Actions(wd);
				ac.click(wel).perform();
				// wel.click();
			} else {
				retval = "fail : unable to find element (" + tar + ")";
			}
		} catch (Exception er) {
			retval = "fail : Exception occoured while clik on  (" + tar + ") system error message:" + er.getMessage();
		}
		return retval;
	}

	private String doToolSet(String tar, String val) {
		String retval = "";
		try {
			WebElement wel = this.getWebElement(tar);
			if (wel != null) {
				wel.sendKeys(val);
			} else {
				retval = "fail : unable to find element (" + tar + ")";
			}
		} catch (Exception er) {
			retval = "fail : Exception occoured while entring value (" + tar + ") system error message:"
					+ er.getMessage();
		}
		return retval;
	}

	private String doToolSelect(String tar, String val) {
		String retval = "";
		try {
			WebElement wel = this.getWebElement(tar);
			if (wel != null) {
				Select sel = new Select(wel);
				try {
					sel.selectByVisibleText(val);
				} catch (Exception er1) {
					try {
						sel.selectByValue(val);
					} catch (Exception er2) {
						try {
							sel.selectByIndex(Integer.parseInt(val));
						} catch (Exception er3) {
							retval = "fail : Unable to find option to select for elememt(" + tar + ") option (" + val
									+ ")";
						}
					}
				}
				wel.sendKeys(val);
			} else {
				retval = "fail : unable to find element (" + tar + ")";
			}
		} catch (Exception er) {
			retval = "fail : Exception occoured while selecting (" + tar + ") system error message:" + er.getMessage();
		}
		return retval;
	}

	private String doReOpen(String url, String browser) {
		return "";
	}

	private String doExit() {
		String retval = "";
		try {
			wd.quit();
		} catch (Exception er) {
			retval = "fail : Exception occoured while quiting Driver system error message:" + er.getMessage();
		}
		return retval;
	}

	private String getCommand(String cmd, String tar, String val) {
		String retval = " return doAction('" + cmd + "','" + tar + "','" + val + "');" + "var isPageLoading = 0;\n"
				+ "    var elementdetails='';\n" + "    var isSettingsAppllyed=false;\n"
				+ "    var isAjaxSettingsAppllyed=false;\n" + "    var gblTimeOutLimit=600;\n"
				+ "    var gblTimeOut=0;\n" + "    var plocation='';\n" + "    var clocation='';\n"
				+ "    var eventStatus=0;\n" + "    var icolor;\n" + "    var iwidth;\n" + "    var ibgcolor;\n"
				+ "    var isElementChange = 0;\n" + "    function doSetEventStatus(istatus)\n" + "    {\n"
				+ "        eventStatus = istatus;\n" + "        getPLocation();\n" + "    }\n"
				+ "    function pageisReady(area,istate)\n" + "    {   \n" + "        plocation = location.href;\n"
				+ "        isPageLoading = istate;\n" + "    }\n" + "    \n" + "    function isLocationChanged()\n"
				+ "    {\n" + "        clocation = location.href;\n"
				+ "        if(getFStr(plocation)!==getFStr(clocation))\n" + "            {\n"
				+ "                return true;\n" + "            }else{return false;}\n" + "    }\n"
				+ "    function getPLocation()\n" + "    {\n" + "        plocation = location.href;\n" + "    }\n"
				+ "\n" + "    function elementGettingLoad()\n" + "    {   \n" + "        try{\n"
				+ "        var e = window.event;\n" + "        var target = e.target || e.srcElement;\n"
				+ "        console.log(target);\n" + "        }catch(err){}\n" + "        isElementChange++;\n"
				+ "    }\n" + "     function elementLoaded()\n" + "    {\n" + "        isElementChange = 0;\n"
				+ "    }\n" + "\n" + "        try{\n" + "        if (window.addEventListener)\n" + "        {\n"
				+ "        window.addEventListener('DOMContentLoaded', elementLoaded, false);\n"
				+ "        window.addEventListener('DOMSubtreeModified', elementGettingLoad, false);\n"
				+ "        window.addEventListener('readystatechange', pageisReady('rstate change',1), false);\n"
				+ "        window.addEventListener('beforeunload', pageisReady('beforeload',1), false);\n"
				+ "        document.addEventListener('readystatechange', pageisReady('document rstatee change',1), false);\n"
				+ "        window.addEventListener('unload', pageisReady('unload',1), false);\n"
				+ "        window.addEventListener('load', pageisReady('load',0), false);\n"
				+ "        isSettingsAppllyed = true;\n" + "        \n" + "        }\n"
				+ "        else if (window.attachEvent)\n" + "        {\n"
				+ "        window.attachEvent('onreadystatechange', pageisReady('rstate change',1));\n"
				+ "        window.attachEvent('onbeforeunload', pageisReady('beforload',1));\n"
				+ "        document.attachEvent('onreadystatechange', pageisReady('document rstate change',1));\n"
				+ "        window.attachEvent('onunload', pageisReady('unload',1));\n"
				+ "        window.attachEvent('onload', pageisReady('load',0));\n"
				+ "        isSettingsAppllyed = true;\n" + "        }\n" + "        else{}\n"
				+ "        }catch(err1){isSettingsAppllyed=false;pageisReady('error level',0);\n" + "          \n"
				+ "        }\n" + "    \n" + "    function eScroll(iele){\n" + "\n" + "        try{\n"
				+ "        window.scrollTo(iele.offsetLeft,iele.offsetTop);\n"
				+ "        highlight(iele);}catch(err2){\n" + "      \n" + "        }\n" + "    }\n"
				+ "    function highlight(iele) {\n" + "    \n" + "       try{\n" + "        \n"
				+ "        if((iele.type==='button'||'submit')||iele.nodeName==='BUTTON')\n" + "        {\n"
				+ "                ibgcolor = iele.style.border;\n"
				+ "                iele.style.border='3px solid #04DFFC';\n" + "                \n"
				+ "                var t = setTimeout(function(){\n"
				+ "                iele.style.border = ibgcolor;    \n" + "              \n"
				+ "                },800);\n" + "\n" + "        }\n" + "        else{\n"
				+ "            ibgcolor = iele.style.backgroundColor;\n"
				+ "            iele.style.backgroundColor='#04DFFC';\n" + "            var t = setTimeout(function(){\n"
				+ "            iele.style.backgroundColor = ibgcolor;    \n" + "              \n"
				+ "            },800);\n" + "        \n" + "        }\n" + "       }catch(err4){\n" + "          \n"
				+ "       }\n" + "        \n" + "	  \n" + "    }\n" + "    \n" + "    function doNormal(iele)\n"
				+ "    { \n" + "        \n" + "\n" + "        \n" + "    \n" + "    }\n"
				+ "    function doWaitforNextPage()\n" + "    {\n" + "        gblTimeOut++;\n" + "    }\n"
				+ "    function doAction(icommand,itarget,ivalue)\n" + "    {\n" + "        var retVal;\n"
				+ "        try{\n" + "        if(isAjaxSettingsAppllyed!==true)\n" + "        {\n"
				+ "            doSetAjax();\n" + "        }\n" + "        try{\n"
				+ "            if(itarget.indexOf(',,')>-1)\n"
				+ "            {       var itargetSplit = itarget.split(',,');\n" + "                    \n"
				+ "                    for(var t=0;itargetSplit.length;t++)\n" + "                    {   \n"
				+ "                        gblTimeOut=0;\n" + "                    \n"
				+ "                      retVal = retVal+  doActionX(icommand,itargetSplit[t],ivalue);\n"
				+ "                    }\n" + "                return retVal;\n" + "                    \n"
				+ "            }else{\n" + "                \n" + "                gblTimeOut=0;\n"
				+ "                return  doActionX(icommand,itarget,ivalue);   \n" + "            }\n"
				+ "            \n" + "        \n" + "        }catch(err5)\n" + "        {\n" + "            \n"
				+ "            return 'wait error occoured' + err5.message;\n" + "        }\n" + "        }\n"
				+ "        catch(err115)\n" + "        {\n" + "            return err115;\n" + "        }\n"
				+ "        \n" + "    }\n" + "    function doActionX(icommand,itarget,ivalue)\n" + "    {\n"
				+ "       var iretVal;\n" + "        try{\n" + "        if(document.readyState==='complete')\n"
				+ "        {\n" + "            iretVal=  doAction2(icommand,itarget,ivalue); \n" + "        }\n"
				+ "        else\n" + "        {\n" + "             iretVal= 'wait';\n" + "            \n"
				+ "        }\n" + "        }catch(err6)\n" + "        {\n" + "            \n"
				+ "            iretVal= 'wait error occoured' +err6.message ;\n" + "        }\n" + "        \n"
				+ "        return iretVal;\n" + "    }\n" + "    function doSetAjax()\n" + "    {\n" + "        try{\n"
				+ "            $( document ).ajaxStart(function() {isPageLoading=1;});\n"
				+ "            $( document ).ajaxStop(function() {isPageLoading=0;});\n"
				+ "            $( document ).ajaxComplete(function() {isPageLoading=0;});\n"
				+ "            isAjaxSettingsAppllyed= true;\n" + "\n"
				+ "        }catch(err7){isAjaxSettingsAppllyed=false;\n" + "         isPageLoading=0; \n"
				+ "         \n" + "        }\n" + "    }\n" + "    \n"
				+ "    function doAction2(icommand,itarget,ivalue)\n" + "    {\n" + "    if(ivalue.indexOf('=>')>-1)\n"
				+ "    {\n" + "    ivalue = iValueByQuery(ivalue);\n" + "    }\n" + "        \n"
				+ "       var aTarget = itarget.split('::');\n" + "        var retval;\n"
				+ "        if(getFStr(itarget).indexOf('@id')>-1)\n" + "        {\n"
				+ "            elementdetails = 'Find <'+aTarget[1]+'> Element As <ID>';\n"
				+ "            retval= actionById(icommand,aTarget[1],ivalue);           \n"
				+ "                           \n" + "        }else if(getFStr(itarget).indexOf('@name')>-1)\n"
				+ "        {\n" + "            if(aTarget.length>2)\n" + "            {\n"
				+ "                elementdetails = 'Find <'+aTarget[1]+'> Element As <Name> and Index <'+aTarget[2]+'>';\n"
				+ "               retval = actionByName(icommand,aTarget[1],ivalue,parseInt(aTarget[2]));\n"
				+ "            }else\n" + "            {\n"
				+ "                 elementdetails = 'Find <'+aTarget[1]+'> Element As <Name> and Index <0>';\n"
				+ "                retval = actionByName(icommand,aTarget[1],ivalue,0);\n" + "            }\n"
				+ "            \n" + "        }else if(getFStr(itarget).indexOf('@class')>-1)\n" + "        {\n"
				+ "            if(aTarget.length>2)\n" + "            {\n"
				+ "                elementdetails = 'Find <'+aTarget[1]+'> Element As <Class Name> and Index <'+aTarget[2]+'>';\n"
				+ "               retval = actionByClassName(icommand,aTarget[1],ivalue,parseInt(aTarget[2]));\n"
				+ "            }else\n" + "            {\n"
				+ "                elementdetails = 'Find <'+aTarget[1]+'> Element As <Class Name> and Index <0>';\n"
				+ "                retval = actionByClassName(icommand,aTarget[1],ivalue,0);\n"
				+ "            }       \n" + "        }else if(getFStr(itarget).indexOf('@linktext')>-1)\n"
				+ "        {\n" + "            if(aTarget.length>2)\n" + "            {\n"
				+ "                elementdetails = 'Find <'+aTarget[1]+'> Element As <Link Text> and Index <'+aTarget[2]+'>';\n"
				+ "               retval = actionByLinkText(icommand,aTarget[1],ivalue,parseInt(aTarget[2]));\n"
				+ "            }else\n" + "            {\n"
				+ "                elementdetails = 'Find <'+aTarget[1]+'> Element As <Link Text> and Index <0>';\n"
				+ "                retval = actionByLinkText(icommand,aTarget[1],ivalue,0);\n"
				+ "            }       \n" + "        }else if(getFStr(itarget).indexOf('@text')>-1)\n" + "        {\n"
				+ "            if(aTarget.length>2)\n" + "            {\n"
				+ "                elementdetails = 'Find <'+aTarget[1]+'> Element As <Text> and Index <'+aTarget[2]+'>';\n"
				+ "               retval = actionByText(icommand,aTarget[1],ivalue,parseInt(aTarget[2]));\n"
				+ "            }else\n" + "            {\n"
				+ "                elementdetails = 'Find <'+aTarget[1]+'> Element As <Name> and Index <0>';\n"
				+ "                retval = actionByText(icommand,aTarget[1],ivalue,0);\n" + "            }\n"
				+ "        }else if(getFStr(itarget).indexOf('@xpath')>-1)\n" + "        {\n"
				+ "                elementdetails = 'Find <'+aTarget[1]+'> Element As <XPATH>';\n"
				+ "                retval =actionByXpath(icommand,aTarget[1],ivalue);\n"
				+ "        }else if(getFStr(itarget).indexOf('@attr')>-1)\n" + "        {\n"
				+ "                retval ='attr';\n" + "        }else if(getFStr(itarget).indexOf('@jq')>-1)\n"
				+ "        {\n" + "           elementdetails = 'Find <'+aTarget[1]+'> Element As <JQueryString>';\n"
				+ "           retval = actionByQuery(icommand,aTarget[1],ivalue);\n" + "                \n"
				+ "        }else {\n" + "            \n" + "            if(getFStr(icommand)==='refresh')\n"
				+ "            {\n" + "                location.reload(); \n" + "            }\n"
				+ "            else if(getFStr(icommand)==='refresh')\n" + "            {\n"
				+ "                location.reload(); \n" + "            }\n"
				+ "            else if(getFStr(icommand)==='verify')\n" + "            {\n"
				+ "                retval = doVerify(itarget,ivalue);\n" + "            }\n" + "            else{\n"
				+ "             retval = 'FAIL_-_Unable to Target Type in framework list'+ itarget;\n"
				+ "            }\n" + "        }\n" + "        return retval;\n" + "    }\n"
				+ "    function doVerify(eleValue,ivalue)\n" + "    {\n" + "         try{\n"
				+ "               if(getFStr(eleValue)===getFStr(ivalue))\n" + "                   {\n"
				+ "                       return '';\n" + "                   }else{\n"
				+ "                       return 'Difference Between Actual<'+eleValue+'> and Expected<'+ivalue+'> ';\n"
				+ "                   }\n" + "            }catch(err8)\n" + "            {\n" + "                \n"
				+ "                return 'FAIL_-_Unable to perform <Verify> operation for target '+elementdetails;\n"
				+ "            }\n" + "    }\n" + "    function doGetText(ivalue,eText)\n" + "    {\n" + "       \n"
				+ "        if(ivalue.indexOf(':=')>-1)\n" + "        {\n"
				+ "            var psplit = ivalue.split(':=');\n" + "            var splitCat = psplit[1];\n"
				+ "            var hasToSplitPattern = psplit[2];\n" + "            var iIndex;\n"
				+ "            if(splitCat.indexOf('before')>-1)\n" + "            {\n"
				+ "                iIndex = 0;\n" + "            }else if(splitCat.indexOf('after')>-1)\n"
				+ "            {\n" + "                iIndex = 1;\n" + "            }else {\n"
				+ "                try{iIndex= parseInt(psplit[1]);}\n" + "                catch(excep){iIndex = 0;}\n"
				+ "            }\n" + "            return doGetTextByText(eText,hasToSplitPattern,iIndex);\n"
				+ "        }else{\n" + "            \n" + "             return  eText;      \n" + "        }\n"
				+ "    }\n" + "    function doGetTextByText(eText,hasToSplitPattern,iIndex)\n" + "    {\n"
				+ "        \n" + "        var ieText = getFStr(eText);\n"
				+ "        var ihasToSplitPattern = getFStr(hasToSplitPattern);\n"
				+ "        if(getFStr(ieText).indexOf(getFStr(ihasToSplitPattern))>-1)\n" + "        {\n"
				+ "           try{\n" + "               \n"
				+ "            return  getFStr(ieText.split(ihasToSplitPattern)[iIndex]);\n"
				+ "           }catch(err9){\n" + "               \n"
				+ "               return 'Unable to Perform operation <'+hasToSplitPattern+'> in <'+eText+'> index <'+iIndex+'>';}\n"
				+ "        }else{\n"
				+ "            return 'Unable to Find Text <'+hasToSplitPattern+'> in actual text <'+eText+'>';\n"
				+ "        }\n" + "    }\n" + "    function doGet(actionObj,ivalue)\n" + "    {\n" + "         try{\n"
				+ "             \n" + "      \n" + "                if(ivalue.indexOf('on')>-1)\n"
				+ "                {\n" + "                   var f = getElementByQuery(actionObj,'P(form)');\n"
				+ "                   iceSubmit(f,actionObj,'onkeyup');\n" + "                   return '';\n"
				+ "                }else if(ivalue.indexOf('text')>-1)\n" + "                {\n"
				+ "                    \n" + "                     var eText ='';\n" + "                    try{\n"
				+ "                        eText= $(actionObj).text();\n" + "                    }catch(ierr)\n"
				+ "                    {\n" + "                        eText= getVTxt(actionObj);\n"
				+ "                    }\n" + "                    if(actionObj.nodeName==='SELECT')\n"
				+ "                    {\n"
				+ "                    return actionObj.options[actionObj.selectedIndex].text;\n"
				+ "                    }else{\n" + "        \n"
				+ "                    return doGetText(ivalue,eText);\n" + "                    }\n"
				+ "                    \n" + "                }else if(ivalue.indexOf('xpath')>-1)\n"
				+ "                {\n" + "                    try{\n"
				+ "                    return getXPath(actionObj);\n"
				+ "                    }catch(errrv01){return '';}\n" + "                    \n"
				+ "                    \n" + "                }else if(ivalue.indexOf('links')>-1)\n"
				+ "                {\n" + "                    var aLinks = doGetAllByLinks();\n"
				+ "                    try{\n" + "                        return aLinks;\n"
				+ "                    }catch(err10)\n" + "                    {\n" + "                       \n"
				+ "                        return 'FAIL_-_while getting Links details';\n" + "                    }\n"
				+ "                }\n" + "                else \n" + "                {   var rval =null; \n"
				+ "                    \n" + "                    ivalue = getFStr(ivalue);\n"
				+ "                    try{  \n" + "                      rval = actionObj.attr(ivalue).toString();\n"
				+ "                    }catch(erro){\n"
				+ "                        rval =actionObj.getAttribute(ivalue).toString();          \n"
				+ "                    }\n" + "                    if(rval!==null)\n" + "                    {\n"
				+ "                        return rval;\n" + "                    }else{\n"
				+ "                        try{\n"
				+ "                          return getValueByAttrbute(actionObj,ivalue);\n"
				+ "                        }catch(err11){\n" + "                             \n"
				+ "                          return '';\n" + "                        }   \n"
				+ "                    }\n" + "                        \n" + "                }\n"
				+ "                \n" + "            }catch(err12)\n" + "            {\n" + "                 \n"
				+ "                return 'FAIL_-_Unable to perform <GET> operation for target '+actionObj;\n"
				+ "            }\n" + "    }\n" + "    function getValueByAttrbute(actionObj,ivalue)\n" + "    {\n"
				+ "        try{\n" + "        if(ivalue ==='value')\n" + "        {\n"
				+ "             return actionObj.value;\n" + "        }else if(ivalue ==='id')\n" + "        {\n"
				+ "             return actionObj.id;\n" + "        }else if(ivalue ==='name')\n" + "        {\n"
				+ "             return actionObj.name;\n" + "        }else if(ivalue ==='href')\n" + "        {\n"
				+ "             return actionObj.href;\n" + "        }else if(ivalue ==='src')\n" + "        {\n"
				+ "             return actionObj.src;\n" + "        }else if(ivalue ==='style')\n" + "        {\n"
				+ "            return actionObj.style;\n" + "        }else {\n" + "            return '';\n"
				+ "        }}catch(err13){\n" + "          \n" + "        return '';\n" + "        }\n" + "    }\n"
				+ "    \n" + "    function doClick(actionObj)\n" + "    {\n" + "         try{\n"
				+ "                if(actionObj!==null)\n" + "                {\n" + "\n" + "                    \n"
				+ "                    eScroll(actionObj);\n" + "                    \n"
				+ "                    actionObj.click();\n" + "                    \n" + "                    try{\n"
				+ "                      actionObj.onclick();  \n"
				+ "                    }catch(err120){fireEvent(actionObj,'click');}\n" + "\n"
				+ "                    try{\n" + "                      actionObj.onchange();  \n"
				+ "                    }catch(err120){fireEvent(actionObj,'change');}\n" + "                    \n"
				+ "                    \n" + "                    doNormal(actionObj);\n" + "                   \n"
				+ "                    return '';\n" + "\n" + "                           \n" + "                }\n"
				+ "                else{\n"
				+ "                    return 'FAIL_-_Unable to find element'+elementdetails +' --- '+isPageLoading;\n"
				+ "                }\n" + "                 \n" + "            }catch(err14)\n" + "            {\n"
				+ "                 \n"
				+ "                return 'FAIL_-_Unable to perform <CLICK> operation for target '+elementdetails;\n"
				+ "            }\n" + "    }\n" + "    function doSet(actionObj,ivalue)\n" + "    {\n"
				+ "         try{\n" + "             if(actionObj!==null)\n" + "                {\n"
				+ "                    \n" + "\n" + "                    \n"
				+ "                    eScroll(actionObj);\n" + "                    \n"
				+ "                    actionObj.value =ivalue;\n" + "                    try{\n"
				+ "                      actionObj.onkeydown();  \n"
				+ "                    }catch(err120){fireEvent(actionObj,'keydown');}\n" + "                    try{\n"
				+ "                      actionObj.onkeyup();  \n"
				+ "                    }catch(err120){fireEvent(actionObj,'keyup');}\n" + "                    try{\n"
				+ "                      actionObj.onchange();  \n"
				+ "                    }catch(err120){fireEvent(actionObj,'change');}\n" + "                    \n"
				+ "                    \n" + "                    \n" + "                   \n"
				+ "                    doNormal(actionObj);\n" + "                \n"
				+ "                    return '';\n" + "\n" + "                }\n" + "                else{\n"
				+ "                    return 'FAIL_-_Unable to find element'+elementdetails +' --- '+isPageLoading;\n"
				+ "                }\n" + "            }catch(err15)\n" + "            {\n" + "                \n"
				+ "                return 'FAIL_-_Unable to perform <SET> operation for target '+elementdetails;\n"
				+ "            }\n" + "    }\n" + "    function doSelect(actionObj,ivalue)\n" + "    {\n"
				+ "        var retval;\n" + "            try{\n" + "                \n" + "\n"
				+ "                    \n" + "                    eScroll(actionObj);\n"
				+ "                    doNormal(actionObj);\n" + "                    var aobj = actionObj;\n"
				+ "                    var allSel = aobj.getElementsByTagName('option');\n"
				+ "                    var res = 'FAIL_-_Unable to find option<'+ivalue+'> for object'+actionObj;\n"
				+ "                    for(var i=0;i<allSel.length;i++)\n" + "                    {\n"
				+ "                      if(getFStr(allSel[i].value) === getFStr(ivalue) || getVTxt(allSel[i])=== getFStr(ivalue))\n"
				+ "                      {\n" + "                          aobj.selectedIndex =i; \n"
				+ "                          try{\n" + "                            aobj.onchange();  \n"
				+ "                          }catch(err120){fireEvent(aobj,'change');}\n"
				+ "                          \n" + "                        \n"
				+ "                          res = '';\n" + "                          break;\n"
				+ "                      }\n" + "                    }\n" + "                     retval = res;\n"
				+ "\n" + "                }catch(err16)\n" + "                {\n" + "                    \n"
				+ "                    retval = 'FAIL_-_Unable to perform <SELECT> operation for target '+elementdetails;\n"
				+ "                }\n" + "                return retval;\n" + "    }\n" + "    \n"
				+ "    function actionById(actionType,actionTarget,ivalue)\n" + "    {\n" + "       var retval;\n"
				+ "        try{\n" + "            var actionObj = document.getElementById(actionTarget);\n"
				+ "             retval = doActionByType(actionType,actionObj,ivalue);    \n" + "        }catch(err17)\n"
				+ "        {\n" + "            \n"
				+ "            retval = 'FAIL_-_Error Occoured while performing <'+actionType+'> operation for '+elementdetails;\n"
				+ "        }\n" + "        \n" + "        return retval;\n" + "    }\n"
				+ "    function actionByName(actionType,actionTarget,ivalue,objIndex)\n" + "    {\n"
				+ "       var retval;\n" + "        try{\n"
				+ "            var actionObj = document.getElementsByName(actionTarget)[objIndex];\n"
				+ "             retval = doActionByType(actionType,actionObj,ivalue);    \n" + "        }catch(err18)\n"
				+ "        {\n" + "           \n"
				+ "            retval = 'FAIL_-_Error Occoured while performing <'+actionType+'> operation for '+elementdetails;\n"
				+ "        }\n" + "        \n" + "        return retval;\n" + "    }\n"
				+ "    function actionByClassName(actionType,actionTarget,ivalue,objIndex)\n" + "    {\n"
				+ "       var retval;\n" + "        try{\n"
				+ "            var actionObj = document.getElementsByClassName(actionTarget)[objIndex];\n"
				+ "             retval = doActionByType(actionType,actionObj,ivalue);    \n" + "        }catch(err19)\n"
				+ "        {\n" + "            \n"
				+ "            retval = 'FAIL_-_Error Occoured while performing <'+actionType+'> operation for '+elementdetails;\n"
				+ "        }\n" + "        \n" + "        return retval;\n" + "    }\n"
				+ "    function actionByLinkText(actionType,actionTarget,ivalue,objIndex)\n" + "    {\n"
				+ "       var retval = 'FAIL_-_Unable to find element'+actionTarget +' --- '+isPageLoading;\n"
				+ "       var objIndexCounter =-1;\n" + "        try{\n"
				+ "            var allElements = document.getElementsByTagName('a');\n"
				+ "            for(var i=0; i<allElements.length;i++)\n" + "            {\n"
				+ "                if(getVTxt(allElements[i])===getFStr(actionTarget))\n" + "                {\n"
				+ "                    objIndexCounter ++;\n" + "                    if(objIndexCounter===objIndex)\n"
				+ "                    {\n"
				+ "                    retval = doActionByType(actionType,allElements[i],ivalue);\n"
				+ "                    break;\n" + "                    }\n" + "                }\n" + "            }\n"
				+ "                 \n" + "        }catch(err20)\n" + "        {\n" + "            \n"
				+ "            retval = 'FAIL_-_Error Occoured while performing <'+actionType+'> operation for '+elementdetails;\n"
				+ "        }\n" + "        \n" + "        return retval;\n" + "    }\n"
				+ "    function doGetAll(myaction,actionType)\n" + "    {\n"
				+ "       var retval = 'FAIL_-_Unable to find element'+actionTarget +' --- '+isPageLoading;\n"
				+ "       try{\n" + "            if(actionType.indexOf('links')>-1)\n" + "            {\n"
				+ "                \n" + "            }\n" + "            else if(actionType.indexOf('prop')>-1)\n"
				+ "            {\n" + "\n" + "            }else\n" + "            {\n" + "                \n"
				+ "            }\n" + "                 \n" + "        }catch(err21)\n" + "        {\n"
				+ "            \n"
				+ "            retval = 'FAIL_-_Error Occoured while performing <'+actionType+'> operation for '+elementdetails;\n"
				+ "        }\n" + "        \n" + "        return retval;\n" + "    }\n"
				+ "    function doGetAllByLinks()\n" + "    {\n" + "       var retval=':-:';\n" + "       try{\n"
				+ "           \n" + "       \n" + "            var allElements = document.getElementsByTagName('a');\n"
				+ "            for(var i=0; i<allElements.length;i++)\n" + "            {\n" + "                try{\n"
				+ "                    retval = retval +':-:'+getFStr(allElements[i].text())+'--'+getFStr(allElements[i].attr('href'));\n"
				+ "                }catch(err22){\n" + "                    \n"
				+ "                 retval = retval +':-:'+getVTxt(allElements[i])+'--'+getFStr(allElements[i].getAttribute('href'));   \n"
				+ "                }\n" + "                \n" + "            }\n" + "           return   retval;    \n"
				+ "        }catch(err23)\n" + "        {\n" + "           \n"
				+ "            return 'FAIL_-_Error Occoured while performing <'+actionType+'> operation for '+elementdetails;\n"
				+ "        }\n" + "        \n" + "         \n" + "    }\n"
				+ "    function doGetAllByTagName(actionType)\n" + "    {\n" + "       var retval=[];\n"
				+ "       try{\n" + "            var allElements = document.getElementsByTagName(actionType);\n"
				+ "            \n" + "            for(var i=0; i<allElements.length;i++)\n" + "            {\n"
				+ "                          retval.push(allElements[i]);\n" + "            }\n"
				+ "           return  retval;     \n" + "        }catch(err24)\n" + "        {\n" + "             \n"
				+ "            return 'FAIL_-_Error Occoured while performing <'+actionType+'> operation for '+elementdetails;\n"
				+ "        }\n" + "        \n" + "        \n" + "    }\n" + "    function doGetAllByProps(actionType)\n"
				+ "    {\n" + "       var retval=[];\n" + "       try{\n"
				+ "            var allElements = document.getElementsByTagName('*');\n" + "            \n"
				+ "            for(var i=0; i<allElements.length;i++)\n" + "            {\n"
				+ "                if(actionType.indexOf(':='))\n" + "                {\n"
				+ "                   if(allElements[i].getAttribute(actionType.split(':=')[0])===actionType.split(':=')[1])\n"
				+ "                       {\n" + "                           retval.push(allElements[i]);\n"
				+ "                       }\n" + "                }    \n" + "                \n" + "            }\n"
				+ "           return  retval;     \n" + "        }catch(err25)\n" + "        {\n" + "             \n"
				+ "            return 'FAIL_-_Error Occoured while performing <'+actionType+'> operation for '+elementdetails;\n"
				+ "        }\n" + "        \n" + "        \n" + "    }\n"
				+ "    function actionByText(actionType,actionTarget,ivalue,objIndex)\n" + "    {\n"
				+ "       var retval = 'FAIL_-_Unable to find element'+actionTarget +' --- '+isPageLoading;\n"
				+ "       var objIndexCounter =-1;\n" + "        try{\n"
				+ "            var allElements = document.getElementsByTagName('*');\n"
				+ "            for(var i=0; i<allElements.length;i++)\n" + "            {\n"
				+ "                if(getVTxt(allElements[i]).indexOf(getFStr(actionTarget))===0)\n"
				+ "                {\n" + "                    objIndexCounter ++;\n"
				+ "                    if(objIndexCounter===objIndex)\n" + "                    {\n"
				+ "                        retval = doActionByType(actionType,allElements[i],ivalue);\n"
				+ "                        break;\n" + "                    }\n" + "                    \n"
				+ "                }\n" + "            }\n" + "                 \n" + "        }catch(err26)\n"
				+ "        {\n" + "            \n"
				+ "            retval = 'FAIL_-_Error Occoured while performing <'+actionType+'> operation for '+elementdetails;\n"
				+ "        }\n" + "        \n" + "        return retval;\n" + "    }\n"
				+ "    function actionByXpath(actionType,actionTarget,ivalue)\n" + "    {\n" + "       var retval;\n"
				+ "        try{\n" + "            var actionObj = getElementByXpath(actionTarget);\n"
				+ "             retval = doActionByType(actionType,actionObj,ivalue);    \n" + "        }catch(err27)\n"
				+ "        {\n" + "             \n"
				+ "            retval = 'FAIL_-_Error Occoured while performing <'+actionType+'> operation for '+elementdetails;\n"
				+ "        }\n" + "        \n" + "        return retval;\n" + "    }\n" + "    \n"
				+ "    function getElementByXpath (path) {\n" + "        try{\n"
				+ "        return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\n"
				+ "        }catch(err28){\n" + "           \n" + "        }\n" + "    }\n" + "    \n"
				+ "    function findElementByQuery(actionTarget)\n" + "    {\n" + "       var iobj=null;\n"
				+ "        if(actionTarget.indexOf('=>')>-1)\n" + "        {\n"
				+ "            var isplit = actionTarget.split('=>');\n" + "            var iobj=null;\n"
				+ "            for(var i=0;i<isplit.length;i++)\n" + "            {\n"
				+ "                iobj = getElementByQuery(iobj,isplit[i]);\n" + "\n" + "            }\n"
				+ "            return iobj;\n" + "        }else\n" + "        {\n"
				+ "            return getElementByQuery(iobj,actionTarget);\n" + "        }\n" + "    }\n"
				+ "    function actionByQuery(actionType,actionTarget,ivalue)\n" + "    {\n" + "       var iobj=null;\n"
				+ "        if(actionTarget.indexOf('=>')>-1)\n" + "        {\n" + "            \n" + "        \n"
				+ "            var isplit = actionTarget.split('=>');\n" + "            var iobj=null;\n"
				+ "            for(var i=0;i<isplit.length;i++)\n" + "            {\n" + "                \n"
				+ "                iobj = getElementByQuery(iobj,isplit[i]);\n" + "\n" + "                \n"
				+ "            }\n" + "            \n" + "            return doActionByType(actionType,iobj,ivalue);\n"
				+ "        }else\n" + "        {\n" + "            iobj = getElementByQuery(iobj,actionTarget);\n"
				+ "            return doActionByType(actionType,iobj,ivalue);    \n" + "        }\n"
				+ "                     \n" + "    }\n" + "    function iValueByQuery(ivalue)\n" + "    {\n"
				+ "       var iobj=null;\n" + "        if(ivalue.indexOf('=>')>-1)\n" + "        {\n" + "            \n"
				+ "        \n" + "            var isplit = ivalue.split('=>');\n" + "            var iobj=null;\n"
				+ "            for(var i=0;i<isplit.length;i++)\n" + "            {\n" + "                \n"
				+ "                iobj = getElementByQuery(iobj,isplit[i]);\n" + "\n" + "                \n"
				+ "            }\n" + "            \n" + "            return doActionByType('store',iobj,'');\n"
				+ "        }else\n" + "        {\n" + "              return ivalue;  \n" + "        }\n"
				+ "                     \n" + "    }\n" + "    function getElementByQuery(iobj,queryString)\n"
				+ "    {\n" + "        var isplit = queryString.split('(');\n"
				+ "        var isplitOne = isplit[1].split(')')[0];\n" + "        var retval=null;\n"
				+ "        if(getFStr(isplit[0])==='get')\n" + "        {\n" + "            \n"
				+ "            retval= doGet(iobj,isplitOne);\n" + "            \n"
				+ "        }else if(getFStr(isplit[0])==='p')\n" + "        {\n"
				+ "           retval = getParentX(iobj,isplitOne);\n" + "        }else if(getFStr(isplit[0])==='t')\n"
				+ "        {\n" + "            if(isplitOne.indexOf(':=')>-1)\n" + "            {\n"
				+ "                var isplitTwo = isplitOne.split(':=');\n" + "                \n"
				+ "                retval= getElementByText(iobj,isplitTwo[0],parseInt(isplitTwo[1]));\n"
				+ "            }else {\n" + "                \n"
				+ "                retval= getElementByText(iobj,isplitOne,0);\n" + "            }\n"
				+ "        }else if(getFStr(isplit[0])==='e')\n" + "        {\n"
				+ "            retval = getElementX(iobj,isplitOne);\n" + "        }else if(getFStr(isplit[0])==='c')\n"
				+ "        {\n" + "            retval = getChildNodeX(iobj,isplitOne);\n" + "        }\n" + "        \n"
				+ "        \n" + "        return retval;\n" + "    }\n" + "    \n"
				+ "    function doActionByType(actionType,actionObj,ivalue)\n" + "    {\n"
				+ "        if(document.readyState==='complete')\n" + "        {\n" + "        var retval;\n"
				+ "        if(getFStr(actionType)==='click')\n" + "        {   \n"
				+ "            retval = doClick(actionObj);\n" + "        }\n"
				+ "        else if(getFStr(actionType)==='set')\n" + "        {\n"
				+ "            retval = doSet(actionObj,ivalue);\n" + "        }else if(getFStr(actionType)==='sel')\n"
				+ "        {\n" + "            retval = doSelect(actionObj,ivalue);\n"
				+ "        }else if(getFStr(actionType)==='store')\n" + "        {\n"
				+ "            retval = actionObj;\n" + "        }\n"
				+ "        else if(getFStr(actionType)==='verify')\n" + "        {\n"
				+ "           retval = doVerify(actionObj,ivalue);\n"
				+ "        }else if(getFStr(actionType)==='getobj')\n" + "        {\n"
				+ "           retval = actionObj;\n" + "        }else if(getFStr(actionType)==='waitforelement')\n"
				+ "        {\n" + "           retval = doWaitForElement(actionObj,ivalue);\n" + "        }\n"
				+ "        else if(getFStr(actionType)==='waitforelementnot')\n" + "        {\n"
				+ "           retval = doWaitForElementnot(actionObj,ivalue);\n" + "        }\n" + "        else{\n"
				+ "            retval = 'FAIL_-_Keywod <'+actionType+'> not registerd as Action Type';\n"
				+ "        }\n" + "        return retval;\n" + "        }else{\n"
				+ "            return 'wait element found';\n" + "        }\n" + "    }\n" + "    \n"
				+ "    function doWaitForElement(actionObj,ivalue)\n" + "    {\n" + "        if(ivalue!=='')\n"
				+ "        {\n" + "            if(getFStr(actionObj)===getFStr(ivalue))\n" + "            {\n"
				+ "                return '';\n" + "            }else{\n" + "                return 'wait';\n"
				+ "            }\n" + "        }else{\n" + "            if(actionObj!==null)\n" + "            {\n"
				+ "                return '';\n" + "            }\n" + "            else{\n"
				+ "                return 'wait';\n" + "            }\n" + "        }\n" + "    }\n"
				+ "    function doWaitForElementnot(actionObj,ivalue)\n" + "    {\n" + "        if(ivalue!=='')\n"
				+ "        {\n" + "            if(getFStr(actionObj)!==getFStr(ivalue))\n" + "            {\n"
				+ "                return '';\n" + "            }else{\n" + "                return 'wait';\n"
				+ "            }\n" + "        }else{\n" + "            if(actionObj===null)\n" + "            {\n"
				+ "                return '';\n" + "            }\n" + "            else{\n"
				+ "                return 'wait';\n" + "            }\n" + "        }\n" + "    }\n"
				+ "    function fireEvent(element,event){\n" + "        try{\n"
				+ "        if (document.createEventObject){\n" + "        var evt = document.createEventObject();\n"
				+ "        return element.fireEvent('on'+event,evt);\n" + "        }\n" + "        else{\n"
				+ "        var evt = document.createEvent('HTMLEvents');\n"
				+ "        evt.initEvent(event, true, true ); \n" + "        return !element.dispatchEvent(evt);\n"
				+ "        }\n" + "        }catch(err100){}\n" + "    }\n"
				+ "    function getElementByText(iobj,itext,objIndex)\n" + "    {\n" + "       var retval = null;\n"
				+ "       var objIndexCounter =-1;\n" + "        try{\n" + "            var allElements;\n"
				+ "            if(iobj===null)\n" + "            {\n"
				+ "                allElements= document.getElementsByTagName('*');\n" + "            }else{\n"
				+ "                \n" + "                allElements= iobj.getElementsByTagName('*');\n"
				+ "            }\n" + "            for(var i=0; i<allElements.length;i++)\n" + "            {\n"
				+ "                try{\n" + "                if(getVTxt(allElements[i]).indexOf(getFStr(itext))===0)\n"
				+ "                {\n" + "                    objIndexCounter ++;\n"
				+ "                    if(objIndexCounter===objIndex)\n" + "                    {\n"
				+ "                        retval = allElements[i];\n" + "                        break;\n"
				+ "                    }\n" + "                    \n" + "                }}catch(err29){\n"
				+ "             \n" + "                }\n" + "            }\n" + "                 \n"
				+ "        }catch(err30)\n" + "        {\n" + "            \n" + "        }\n" + "       \n"
				+ "       return retval;\n" + "    }\n" + "    function getElementX(iobj,eDetails)\n" + "    {\n"
				+ "        try{\n" + "        if(eDetails.indexOf(','))\n" + "        {\n"
				+ "            var idetailsSplit = eDetails.split(',');\n" + "            \n"
				+ "            for(var o=0;o<idetailsSplit.length;o++)\n" + "            {\n" + "                \n"
				+ "                if(idetailsSplit[o].indexOf(':=')>-1)\n" + "                {\n"
				+ "                    if(idetailsSplit[o].split(':=').length>2)\n" + "                    {    \n"
				+ "                    iobj=  getElement(iobj,idetailsSplit[o].split(':=')[0],idetailsSplit[o].split(':=')[1],parseInt(idetailsSplit[o].split(':=')[2]));\n"
				+ "                    }else\n" + "                    {\n"
				+ "                    iobj=  getElement(iobj,idetailsSplit[o].split(':=')[0],idetailsSplit[o].split(':=')[1],0);\n"
				+ "                    }\n" + "                }\n" + "            }\n" + "           return iobj;\n"
				+ "        }\n" + "        else{\n" + "             \n" + "            if(eDetails.indexOf(':='))\n"
				+ "            {\n" + "                if(eDetails.indexOf(':=')>-1)\n" + "                {\n"
				+ "                    if(eDetails.split(':=').length>2)\n" + "                    {    \n"
				+ "                    return getElement(iobj,eDetails.split(':=')[0],eDetails.split(':=')[1],parseInt(eDetails.split(':=')[2]));\n"
				+ "                    }else\n" + "                    {\n"
				+ "                    return  getElement(iobj,eDetails.split(':=')[0],eDetails.split(':=')[1],0);\n"
				+ "                    }\n" + "                }\n" + "            }\n" + "            \n"
				+ "        }\n" + "        }catch(err31){\n" + "          \n" + "        return null;\n" + "       \n"
				+ "        }\n" + "        \n" + "    }\n" + "     function getElement(iobj,attr,attrvalue,objIndex)\n"
				+ "    {\n" + "        \n" + "       var retval = null;\n" + "       var objIndexCounter =-1;\n"
				+ "        try{\n" + "            var allElements;\n" + "            if(iobj===null)\n"
				+ "            {\n" + "                allElements= document.getElementsByTagName('*');\n"
				+ "                iobj = document;\n" + "            }else{\n" + "                \n"
				+ "                allElements= iobj.getElementsByTagName('*');\n" + "            }\n"
				+ "            if(getFStr(attr)==='tagname')\n" + "                {\n" + "                 \n"
				+ "                  retval =  iobj.getElementsByTagName(attrvalue)[objIndex]; \n"
				+ "                }   \n" + "                else\n" + "                {\n"
				+ "                    for(var i=0; i<allElements.length;i++)\n" + "                    {\n"
				+ "                       try{\n"
				+ "                       if(getFStr(allElements[i].getAttribute(attr))===getFStr(attrvalue))\n"
				+ "                        {\n" + "                            objIndexCounter++;\n"
				+ "                            if(objIndexCounter===objIndex)\n" + "                            {\n"
				+ "                                retval=allElements[i];\n"
				+ "                                break;\n" + "                            }\n"
				+ "                        }\n" + "                       }catch(err32){\n"
				+ "                           \n" + "                       }\n" + "                    }\n"
				+ "                }\n" + "                 \n" + "        }catch(err33)\n" + "        {\n"
				+ "            \n" + "        }\n" + "       return retval;\n" + "    }\n"
				+ "    function getParentX(iobj,strDetails)\n" + "    {\n" + "       \n"
				+ "        if(strDetails.indexOf(','))\n" + "        {\n"
				+ "            var idetailsSplit = strDetails.split(',');\n" + "            \n"
				+ "            for(var o=0;o<idetailsSplit.length;o++)\n" + "            {\n" + "                \n"
				+ "                if(idetailsSplit[o].indexOf(':=')>-1)\n" + "                {\n"
				+ "                    iobj= getParentByAttr(iobj,idetailsSplit[o].split(':=')[0],idetailsSplit[o].split(':=')[1]);\n"
				+ "                }\n" + "                else{\n" + "                    \n"
				+ "                    iobj=  getParent(iobj,idetailsSplit[o]); \n" + "                }\n"
				+ "            }\n" + "           return iobj;\n" + "        }\n" + "        else{\n"
				+ "            if(strDetails.indexOf(':='))\n" + "            {\n"
				+ "                return  getParentByAttr(iobj,strDetails.split(':=')[0],strDetails.split(':=')[1]);\n"
				+ "            }\n" + "            else{\n" + "                return  getParent(iobj,strDetails); \n"
				+ "            }\n" + "        }\n" + "        \n" + "        \n" + "    }\n"
				+ "    function getParent(iobj,itagname)\n" + "    {\n" + "        \n" + "        try{\n"
				+ "        var pNode = iobj.parentNode;\n" + "        if(getFStr(pNode.tagName)===getFStr(itagname))\n"
				+ "        {\n" + "            return pNode;\n" + "        }\n" + "        else\n" + "        {\n"
				+ "            return getParent(pNode,itagname);    \n" + "        }}catch(err34)\n" + "        {\n"
				+ "            \n" + "            return null;\n" + "        }\n" + "    }\n" + "    \n"
				+ "    function getParentByAttr(iobj,att,attvalue)\n" + "    {\n" + "        try{\n"
				+ "        var pNode = iobj.parentNode;\n"
				+ "        if(getFStr(pNode.getAttribute(att))===getFStr(attvalue))\n" + "        {\n"
				+ "            return pNode;\n" + "        }\n" + "        else\n" + "        {\n"
				+ "            return getParent(pNode,att,attvalue);    \n" + "        }}catch(err35)\n" + "        {\n"
				+ "            \n" + "            return null;\n" + "        }\n" + "    }\n"
				+ "    function getChildNodeX(iobj,cDetails)\n" + "    {\n" + "        try{\n"
				+ "        if(cDetails.indexOf(',')>-1)\n" + "        {\n" + "            \n" + "        \n"
				+ "            var idetailsSplit = cDetails.split(',');\n" + "            \n"
				+ "            for(var o=0;o<idetailsSplit.length;o++)\n" + "            {\n" + "                \n"
				+ "                if(idetailsSplit[o].indexOf(':=')>-1)\n" + "                {\n"
				+ "                    if(idetailsSplit[o].split(':=').length>2)\n" + "                    {\n"
				+ "                        iobj = getChildNodeByAttr(iobj,idetailsSplit[o].split(':=')[0],idetailsSplit[o].split(':=')[1],parseInt(idetailsSplit[o].split(':=')[2]));\n"
				+ "                    }else\n" + "                    {\n"
				+ "                        iobj  = getChildNodeByAttr(iobj,idetailsSplit[o].split(':=')[0],idetailsSplit[o].split(':=')[1],0);\n"
				+ "                    }\n" + "                }\n" + "                else{\n"
				+ "                    \n" + "                    iobj=  getChildNode(iobj,idetailsSplit[o]); \n"
				+ "                }\n" + "            }\n" + "           return iobj;\n" + "        }\n"
				+ "        else{\n" + "           \n" + "            if(cDetails.indexOf(':=')>-1)\n"
				+ "            {\n" + "                if(cDetails.split(':=').length>2)\n" + "                {\n"
				+ "                    return  getChildNodeByAttr(iobj,cDetails.split(':=')[0],cDetails.split(':=')[1],parseInt(cDetails.split(':=')[2]));\n"
				+ "                }else\n" + "                {\n" + "                    \n"
				+ "                    return  getChildNodeByAttr(iobj,cDetails.split(':=')[0],cDetails.split(':=')[1],0);\n"
				+ "                }\n" + "           }\n" + "            else{\n" + "                \n"
				+ "                return  getChildNode(iobj,cDetails,0); \n" + "            }\n" + "        }\n"
				+ "        }catch(err36)\n" + "        {\n" + "            \n" + "            return null;\n"
				+ "        }\n" + "    }\n" + "    function getChildNode(iobj,itagname,iindex)\n" + "    {\n"
				+ "       \n" + "        var retval = null;\n" + "    \n" + "        try{\n"
				+ "        var cnodes = iobj.childNodes;\n" + "        var iindexCounter = -1;\n" + "        \n"
				+ "        for(var i=0;i<cnodes.length;i++)\n" + "        {\n" + "            try{\n"
				+ "            if(getFStr(cnodes[i].tagName)===getFStr(itagname))\n" + "            {\n"
				+ "                iindexCounter++;\n" + "                if(iindexCounter===iindex)\n"
				+ "                {\n" + "                   retval = cnodes[i];\n" + "                   break;\n"
				+ "                }\n" + "            }}catch(err37){\n" + "            \n" + "            }\n"
				+ "        }\n" + "         return retval;    \n" + "        }catch(err38)\n" + "        {\n"
				+ "            \n" + "        }\n" + "        return retval;\n" + "    }\n"
				+ "    function getChildNodeByAttr(iobj,attr,attrvalue,iindex)\n" + "    {\n"
				+ "        var retval = null;\n" + "    \n" + "        try{\n"
				+ "        var cnodes = iobj.childNodes;\n" + "        var iindexCounter = -1;\n" + "        \n"
				+ "        for(var i=0;i<cnodes.length;i++)\n" + "        {\n" + "            try{\n" + "            \n"
				+ "            if(getFStr(attr)==='tagname')\n" + "            {\n" + "                try{\n"
				+ "                if(getFStr(cnodes[i].tagName)===getFStr(attrvalue))\n" + "                {\n"
				+ "                    iindexCounter++;\n" + "                    if(iindexCounter===iindex)\n"
				+ "                    {\n" + "                       retval = cnodes[i];\n"
				+ "                       break;\n" + "                    }\n" + "                    \n"
				+ "                }\n" + "                }catch(err39){\n" + "                    \n"
				+ "                }\n" + "            }\n" + "            else \n" + "            {   try{\n"
				+ "                if(getFStr(cnodes[i].getAttribute(attr))===getFStr(attrvalue))\n"
				+ "                {\n" + "                    iindexCounter++;\n"
				+ "                    if(iindexCounter===iindex)\n" + "                    {\n"
				+ "                       retval = cnodes[i];\n" + "                       break;\n"
				+ "                    }\n" + "                }\n" + "                }catch(err40){\n"
				+ "                     \n" + "                }\n" + "            }\n" + "            }catch(err41){\n"
				+ "                 \n" + "            }\n" + "        }\n" + "         return retval;    \n"
				+ "        }catch(err42)\n" + "        {\n" + "           \n" + "        }\n"
				+ "        return retval;\n" + "    }\n" + "    \n" + "    function isTextPresent(itext)\n" + "    {\n"
				+ "        if(getElementByText(null,itext,0)!==null)\n" + "        {\n" + "            return true;\n"
				+ "        }\n" + "        else{\n" + "            return false;\n" + "        }\n" + "    }\n"
				+ "    function isTextNotPresent(itext)\n" + "    {\n"
				+ "        if(getElementByText(null,itext,0)===null)\n" + "        {\n" + "            return true;\n"
				+ "        }\n" + "        else{\n" + "            return false;\n" + "        }\n" + "    }\n"
				+ "    function getFStr(istr)\n" + "    {\n" + "        var retval = istr;\n" + "        try{\n"
				+ "            istr= istr.replace(/\\&nbsp;/g,'');\n"
				+ "            istr= istr.replace(/\\&lt;/g,'');\n" + "            istr= istr.replace(/\\&gt;/g,'');\n"
				+ "            istr= istr.replace(/\\amp;/g,'');\n"
				+ "            istr= istr.replace(/\\&cent;/g,'');\n"
				+ "            istr= istr.replace(/\\&pound;/g,'');\n"
				+ "            istr= istr.replace(/\\&yen;/g,'');\n"
				+ "            istr= istr.replace(/\\&euro;/g,'');\n"
				+ "            istr= istr.replace(/\\&copy;/g,'');\n"
				+ "            istr= istr.replace(/\\&reg;/g,'');\n" + "             \n" + "        }catch(erra){\n"
				+ "           \n" + "        }\n" + "        try{\n"
				+ "           retval = istr.toString().toLowerCase().replace(/^\\s+|\\s+$/g, '');\n" + "           \n"
				+ "        }catch(errx){\n" + "            try{\n"
				+ "                retval = istr.toString().toLowerCase(); \n" + "               \n"
				+ "            }catch(erry)\n" + "            {\n" + "                try{\n"
				+ "                retval = istr.toString(); \n" + "               \n"
				+ "                }catch(errz)\n" + "                {\n" + "                   \n"
				+ "                }\n" + "            }\n" + "        }\n" + "        \n" + "        return retval;\n"
				+ "    }\n" + "    function getVTxt(ele)\n" + "    {\n" + "        var retval='';\n" + "        try{\n"
				+ "            if(ele.nodeName==='SELECT')\n" + "            {\n"
				+ "                retval = ele.options[ele.selectedIndex].text||ele.options[ele.selectedIndex].innerText||ele.options[ele.selectedIndex].innerHTML;\n"
				+ "            }else{\n" + "            try{\n" + "                \n"
				+ "                retval = getFStr(ele.innerHTML)||getFStr(ele.innerText);\n"
				+ "            }catch(errx){\n" + "                \n"
				+ "                retval= getFStr(ele.innerText);\n" + "            }\n" + "            }\n"
				+ "        }catch(erry){\n" + "            \n" + "        }\n" + "        return retval;\n" + "    }\n"
				+ "\n" + "function getXPath(elt) {\n" + "    var path = '';\n"
				+ "     for (; elt && elt.nodeType == 1; elt = elt.parentNode)\n" + "     {\n"
				+ "   	var idx = getElementIdx(elt);\n" + "	var xname = elt.tagName;\n"
				+ "	if (idx > 1) xname += '[' + idx + ']';\n" + "	  path = '/' + xname + path;\n" + "     }\n"
				+ "     return path;\n" + "}\n" + "function getElementIdx(elt)\n" + "{\n" + "    var count = 1;\n"
				+ "    for (var sib = elt.previousSibling; sib ; sib = sib.previousSibling)\n" + "    {\n"
				+ "        if(sib.nodeType == 1 && sib.tagName == elt.tagName){\n" + "        count++\n" + "        }\n"
				+ "    }\n" + "    \n" + "    return count;\n" + "}";

		return retval;
	}
}