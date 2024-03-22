package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
	public static void main(String [] args) {
		try {
			WebDriver driver = new ChromeDriver();
			boolean result;
			result = SeleniumTest.searchTest(driver);
			driver.close();//Close window
			if (!result) {
				System.out.println("Test falied due to finding a result that did not match expected");
			} else {
				System.out.println("Test passed");
			}
		} catch (Exception e) {
			System.out.println("Test failed due to an exception: " +e.getMessage());
			e.printStackTrace();
		}
	}
}
