package com.Luma.RegisterPages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.Luma.BaseClass.BaseClass;
import com.Project_Luma_Utilites.Log;
import com.Project_Luma_Utilites.Utilites;
public class RegisterFormFilling extends BaseClass {

	static By firstName =  By.xpath("//input[@id='firstname']");
	static By lastName=By .xpath("//input[@id='lastname']");
	static By email=By.xpath("//input[@id='email_address']");
	static By password=By.xpath("//input[@id='password']");
	static By confirmpassword=By.xpath("//input[@id='password-confirmation']");
	static By continueButton=By.xpath("//button[@title='Create an Account']");
	static By sub=By.xpath("//input[@id='is_subscribed']");
	static By success=By.xpath("//*[contains(text(),'Thank you for registering with Main Website Store.')]");
	static By logAction=By.xpath("/html/body/div[1]/header/div[1]/div/ul/li[2]");
	static By logout=By.xpath("/html/body/div[1]/header/div[1]/div/ul/li[2]/div/ul/li[3]");
	static By msg=By.xpath("//span[@class='base']");
	static By firstNameErrorMsg=By.xpath("//div[@id='firstname-error']");
	static By lastNameErrirMsg=By.xpath("//div[@id='lastname-error']");
	static By gmailErrirMsg=By.xpath("//div[@id='email_address-error']");
	static By passwordErrMsg=By.xpath("//div[@id='password-error']");
	static By confirmpasswordErrMsg=By.xpath("//div[@id='password-confirmation-error']");

	public static void register_Filling() {
		Navigate_To_RegisterPage.naviagte_To_Register();
		Log.startTestCase("register_Page");
		enterText(firstName,dataPro.getProperty("firstName"));
		Log.info("First Name Passed");
		enterText(lastName,dataPro.getProperty("lastName"));
		Log.info("Last Name Passed");
		enterText(email,Utilites.random_EmailID());
		Log.info("Email Passed");
		enterText(password,dataPro.getProperty("password"));
		Log.info("Password Passed");
		enterText(confirmpassword,dataPro.getProperty("password"));
		Log.info("confirmpassword Passed");
		click(sub);
		Log.info("subscribed Passed");
		click(continueButton);
		Log.info("continueButton Passed");
	}
	public static void validate_SuccessPage() throws Exception {
		Thread.sleep(5000);
		String actualMsg=Xpath(success).getText();
		System.out.println("Actual Login message is :"+actualMsg);
		Assert.assertEquals(actualMsg, dataPro.getProperty("successmsg"), "Successfully registerd message is not showing correct");

	}
	public static void validate_LogOutPage() throws Exception {
		Thread.sleep(1000);
		action.moveToElement(Xpath(logAction)).click().perform();
		Thread.sleep(1000);
		action.moveToElement(Xpath(logout)).click().perform();
		String actualMsg=Xpath(msg).getText();
		System.out.println("Actual logout message is :"+actualMsg);
		Assert.assertEquals(actualMsg, dataPro.getProperty("successlogoutmsg"), "Successfully Logout message is not showing correct");

	}

	public static void validate_RegisterPage() throws Exception {
		Navigate_To_RegisterPage.naviagte_To_Register();
		click(continueButton);
		Log.info("continueButton Passed");
		String actualMsg=Xpath(firstNameErrorMsg).getText();
		Assert.assertEquals(actualMsg, dataPro.getProperty("e1msg"), "This is a required field. message is not showing correct");
		String actual1Msg=Xpath(lastNameErrirMsg).getText();
		Assert.assertEquals(actual1Msg, dataPro.getProperty("e1msg"), "This is a required field. message is not showing correct");
		String actual2Msg=Xpath(gmailErrirMsg).getText();
		Assert.assertEquals(actual2Msg, dataPro.getProperty("e1msg"), "This is a required field. message is not showing correct");
		String actual3Msg=Xpath(passwordErrMsg).getText();
		Assert.assertEquals(actual3Msg, dataPro.getProperty("e1msg"), "This is a required field. message is not showing correct");
		String actual4Msg=Xpath(confirmpasswordErrMsg).getText();
		Assert.assertEquals(actual4Msg, dataPro.getProperty("e1msg"), "This is a required field. message is not showing correct");


	}
}
