package com.poet.core.test.testcases;

import org.testng.IReporter;
import org.testng.TestNG;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



public class TestClassOne {

	@Test
	@Parameters({"myname"})
	public void doTest(String myname)
	{
		System.out.println("Welcome to TestClassOne"+" "+Nramal.getName(myname));
		
		
		
	}
	
	public static void main(String[] args) {
		TestNG  tng = new TestNG();
		IReporter rep;
		
	}
}
