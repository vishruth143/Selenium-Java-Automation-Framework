package com.pta.pages;

import org.openqa.selenium.By;

import com.base.TestBase;

public class PracticePage extends TestBase {
	
	By practice_txt = By.xpath("//h1[normalize-space()='Practice']");
	
	//Actions
	public String validate_practicepage_title() {
		return driver.getTitle();
	}
	
	public boolean validate_practice_txt() {
		return driver.findElement(practice_txt).isDisplayed();
	}
}
