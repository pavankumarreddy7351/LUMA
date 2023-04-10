package com.Luma.TestRunner;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class TestRunner {
	public static void main(String[] args) {
		TestNG object=new TestNG();

		List<String> suites =new ArrayList<String>();
		suites.add("C:\\Users\\puchha.pavan\\automation.own\\Project_Luma\\testNG.xml");
		object.setTestSuites(suites);
		object.run();
	}
}
