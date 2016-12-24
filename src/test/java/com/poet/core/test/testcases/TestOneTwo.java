package com.poet.core.test.testcases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestOneTwo {
	
	@Test
	@Parameters({"myname"})
	public void doTest(String myname)
	{
		System.out.println("Welcome to TestOneTwo"+myname+" "+Nramal.getName(myname));
	} 

}
