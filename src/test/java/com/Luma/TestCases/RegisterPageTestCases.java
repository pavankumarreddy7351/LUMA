package com.Luma.TestCases;

import org.testng.annotations.Test;

import com.Luma.ObjectManager.ObjectManager;
import com.Luma.RegisterPages.Navigate_To_RegisterPage;
import com.Luma.RegisterPages.RegisterFormFilling;

public class RegisterPageTestCases extends ObjectManager{
  @Test(priority = 1)
  public void navigate_To_Register() {
	  Navigate_To_RegisterPage.naviagte_To_Register();
  }
  @Test(priority = 2)
  public void register_Filling() throws Exception {
//	  RegisterFormFilling.register_Filling();
//	  RegisterFormFilling.validate_SuccessPage();
//	  RegisterFormFilling.validate_LogOutPage();
	  RegisterFormFilling.validate_RegisterPage();
  }
}
