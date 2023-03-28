package com.Ninja.RegistrationPages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.Ninja.BaseClass.BaseClass;

import Telus.Project_Ninja_Utilites.Utilites;

public class RegisterWIthKeyBoradKeys extends BaseClass{
	By fn = By.xpath("//*[@id=\"input-firstname\"]");
	By ln = By.xpath("//*[@id=\"input-lastname\"]");
	By email=By.xpath("//*[@id=\"input-email\"]");
	By phone=By.xpath("//*[@id=\"input-telephone\"]");
	By pass=By.xpath("//*[@id=\"input-password\"]");
	By cpass=By.xpath("//*[@id=\"input-confirm\"]");
	By sub=By.xpath("//*[@id=\"content\"]/form/fieldset[3]/div/div/label[1]/input");
	By check=By.xpath("//*[@id=\"content\"]/form/div/div/input[1]");
	By con=By.xpath("//*[@id=\"content\"]/form/div/div/input[2]");
	By success=By.xpath("//*[@id=\"content\"]/p[1]");
	public void register_WithKeyBoard() throws Throwable {
		enterText(fn,dataPro.getProperty("firstName"));
		action.sendKeys(Keys.TAB).build().perform();
		enterText(ln,dataPro.getProperty("lastName"));
		action.sendKeys(Keys.TAB).build().perform();
		enterText(email,Utilites.random_EmailID());
		action.sendKeys(Keys.TAB).build().perform();
		enterText(phone,dataPro.getProperty("phoneNo"));
		action.sendKeys(Keys.TAB).build().perform();
		enterText(pass,dataPro.getProperty("password"));
		action.sendKeys(Keys.TAB).build().perform();
		enterText(cpass,dataPro.getProperty("password"));
		action.sendKeys(Keys.TAB).build().perform();
		click(sub);
		action.sendKeys(Keys.TAB).build().perform();
		click(check);
		action.sendKeys(Keys.TAB).build().perform();
		click(con);

		
	}

}
