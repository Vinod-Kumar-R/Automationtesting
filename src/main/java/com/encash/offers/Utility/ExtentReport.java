package com.encash.offers.Utility;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.encash.offers.Configuration.ConstantVariable;

public class ExtentReport {

	public ExtentHtmlReporter htmlreporter;
	public ExtentReports extent;
	public ExtentTest extenttest;

	public ExtentReport()  {
		
		this.htmlreporter = new ExtentHtmlReporter(ConstantVariable.ExtentReportsLocation);
		this.htmlreporter.loadXMLConfig(ConstantVariable.ExtentReportsPropeties);
		this.extent = new ExtentReports();
		this.extent.setSystemInfo("Organization", "Enchashes");
		this.extent.setSystemInfo("Browser", ConstantVariable.BrowserName);
		this.extent.setSystemInfo("Operation System ", System.getProperty("os.name"));
		this.extent.setSystemInfo("OS Version number", System.getProperty("os.version"));
		this.extent.attachReporter(this.htmlreporter);
		

	}

	public void CreateTest(String TestName) {
		this.extenttest = this.extent.createTest(TestName);

	}

	public void WriteLog(Status status, String details) {
		this.extenttest.log(status, details);
	}

	public void flushlog() {
		this.extent.flush();
	}

	public void AttachScreenshot(String imagePath) throws IOException {
		this.extenttest.addScreenCaptureFromPath(imagePath);
	}
	
	public void WriteInfo(String details) {
		this.extenttest.info(details);
	}



}
