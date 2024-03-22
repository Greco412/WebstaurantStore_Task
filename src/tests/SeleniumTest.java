package tests;

import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.*;

import objects.CSSIdentifiers;
import objects.TestVariables;


public class SeleniumTest {
	public static boolean searchTest (WebDriver driver) throws NoSuchElementException {
		driver.get(TestVariables.URL);//Go to url
		boolean result = true;
		WebElement searchBox = driver.findElement(By.id(CSSIdentifiers.SEARCH_BOX_ID));
		WebElement searchButton = driver.findElement(By.cssSelector(CSSIdentifiers.SEARCH_BUTTON_SELECTOR));
		searchBox.sendKeys(TestVariables.SEARCH_QUERY);// Search for query
		searchButton.click();
		//TODO: Go through each page
		List<WebElement> productListingNames = null;
		boolean morePages = true;
		while (morePages) {
			productListingNames = driver.findElement(By.id(CSSIdentifiers.RESULTS_LIST_ID)).findElements(By.xpath(CSSIdentifiers.ITEM_LISTING_NAME_XPATH));
			for (int i=0; i<productListingNames.size(); i++) {//Cycle through results and confirm match
				String productListingName = productListingNames.get(i).getText();
				if (!Pattern.compile(TestVariables.RESULT_MATCH, Pattern.CASE_INSENSITIVE).matcher(productListingName).matches()) {//Check if product name matches
					System.out.println("Text: '" + productListingName + "', did not contain expected result: '" + TestVariables.RESULT_MATCH + "'");
					result = false;
				}
			}
			try {
				WebElement nextPageButton = driver.findElement(By.cssSelector(CSSIdentifiers.NEXT_PAGE_BUTTON_SELECTOR));
				if (nextPageButton.getAttribute(CSSIdentifiers.ARIA_LABEL).contains(TestVariables.CURRENT_PAGE)) {
					morePages = false;
				} else {
					nextPageButton.click();//Go to next page
				}
			} catch (NoSuchElementException e) {
				morePages = false;
			}
		}
		WebElement lastItemAddToCart = driver.findElement(By.xpath(CSSIdentifiers.ITEM_LAST_ENTRY_XPATH_PT1+productListingNames.size()+CSSIdentifiers.ITEM_LAST_ENTRY_XPATH_PT2));
		lastItemAddToCart.click();//Add last item found to cart
		try {Thread.sleep(15000);} catch (InterruptedException e) {e.printStackTrace();}//Wait for cart pop-up to disappear
		WebElement cartButton = driver.findElement(By.cssSelector(CSSIdentifiers.CART_BUTTON_SELECTOR));
		cartButton.click();//Navigate to cart page
		WebElement emptyCartButton = driver.findElement(By.cssSelector(CSSIdentifiers.EMPTY_CART_BUTTON_SELECTOR));
		emptyCartButton.click();//Empty Cart
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}//Wait empty cart confirmation to appear
		WebElement confirmEmptyCartButton = driver.findElement(By.xpath(CSSIdentifiers.CONFIRM_EMPTY_CART_BUTTON_XPATH));
		confirmEmptyCartButton.click();//Confirm Empty Cart
		return result;
	}
}
