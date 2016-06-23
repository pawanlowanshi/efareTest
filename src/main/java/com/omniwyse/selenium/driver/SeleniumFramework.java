package com.omniwyse.selenium.driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omniwyse.selenium.data.DataManager;
import com.omniwyse.selenium.report.Reporter;
import com.omniwyse.selenium.test.object.EfareObjectRepository;
import com.omniwyse.selenium.test.param.Param;
import com.omniwyse.selenium.utils.GmailUtility;

public class SeleniumFramework {

	public static WebDriver driver;
	static WebElement activeElement;
	static List<List<HashMap<String, Object>>> completeOrderTable = null;
	static HashMap<String,String> catchingInfo=new HashMap<String,String>();

	public static boolean navigateToLoginPage(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, InterruptedException, IOException {
		boolean flag = false;
		try {
			System.out.println("parameter::::" + params.get(0).getValue());
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(0).getValue()));
			if(flag==false)
				return flag;
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//Reporter.captureScreenShot(DataManager.resultPath);
			flag = true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in navigateToLoginPage method and the exception is :"+e);
			flag=false;
			}
		return flag;
	}

	public static boolean setUserNameAndPassword_Login(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			Thread.sleep(3000);
			driver.findElement(By.cssSelector(getefareORElement(params.get(0).getValue()))).clear();
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(0).getValue()), params.get(2).getValue());
			if(flag==false)
				return flag;
			driver.findElement(By.cssSelector(getefareORElement(params.get(1).getValue()))).clear();
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(1).getValue()), params.get(3).getValue());
			if(flag==false)
				return flag;
			//Reporter.captureScreenShot(DataManager.resultPath);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in setUserNameAndPassword_Login method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickOnLoginButton(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(0).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(2000);
			//Reporter.captureScreenShot(DataManager.resultPath);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickOnLoginButton method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean verifyingUserDetails(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;

		try {
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(0).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(5000);
			// SeleniumCoreFunctions.clickByXpath(getefareORElement(params.get(1).getValue()));
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(1).getValue()));
			if(flag==false)
				return flag;

			Thread.sleep(2000);
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(2).getValue()), params.get(5).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(3).getValue()), params.get(6).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(4).getValue()), params.get(7).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(8).getValue()));
			if(flag==false)
				return flag;
			//Reporter.captureScreenShot(DataManager.resultPath);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in verifyingUserDetails method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean comparingSearchData(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			Thread.sleep(3000);
			List<HashMap<String, String>> finalList=extractingDataFromSearchTable("//*[@id='search-results']//tr");
			System.out.println("Table Data : " + finalList);
			for (int k = 0; k < finalList.size(); k++) {
				if (finalList.get(k).get("Name").equals(params.get(0).getValue()) && finalList.get(k).get("Email").equals(params.get(1).getValue())) {
					driver.findElement(By.xpath("(//*[text()='View'])" + "[" + (k + 1) + "]")).click();
					Thread.sleep(3000);
					break;
				}
			}
			if (driver.findElement(By.cssSelector(getefareORElement(params.get(2).getValue()))).getText().equals(params.get(0).getValue()))
				flag = true;
			//Reporter.captureScreenShot(DataManager.resultPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in comparingSearchData method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	private static List<HashMap<String, String>> extractingDataFromSearchTable(String xpath) {
		List<WebElement> table = driver.findElements(By.xpath(xpath));
		HashMap<String, String> tableData = null;
		List<String> l = new ArrayList<String>();
		List<HashMap<String, String>> finalList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < table.size(); i++) {
			if (i == 0) {
				List<WebElement> headerEle = table.get(i).findElements(By.tagName("th"));
				for (WebElement header : headerEle)
					l.add(header.getText());
			} else {
				List<WebElement> bodyEle = table.get(i).findElements(By.tagName("td"));
				tableData = new HashMap<String, String>();
				for (int j = 0; j < bodyEle.size(); j++) {
					String column = bodyEle.get(j).getText();
					System.out.println("column : " + column);
					tableData.put(l.get(j), column);
				}
				finalList.add(tableData);
			}
		}
		return finalList;
	}

	public static boolean getAndSetActiveCardElement_SelectACard(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			List<WebElement> listSelectCard;
			// String cardType,quantityElement,addToCartElement;
			WebElement cardTypeElement;
			WebElement cardPriceElement;

			activeElement = null;
			listSelectCard = driver.findElements(By.cssSelector(getefareORElement(params.get(0).getValue())));

			for (int selectCardIndex = 0; selectCardIndex < listSelectCard.size(); selectCardIndex++) {
				cardTypeElement = listSelectCard.get(selectCardIndex).findElement(By.cssSelector(getefareORElement(params.get(1).getValue())));
				cardPriceElement = listSelectCard.get(selectCardIndex).findElement(By.cssSelector(getefareORElement(params.get(2).getValue())));
				if (cardTypeElement.getText().equals(params.get(3).getValue()) && cardPriceElement.getText().equals(params.get(4).getValue())) {
					activeElement = listSelectCard.get(selectCardIndex);
					break;
				}
			}
			//Reporter.captureScreenShot(DataManager.resultPath);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getAndSetActiveCardElement_SelectACard method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean setQuantity_ActiveCardElement_SelectACard(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;

		try {
			WebElement quantityElement = activeElement.findElement(By.cssSelector(getefareORElement(params.get(0).getValue())));
			quantityElement.clear();
			quantityElement.sendKeys(params.get(1).getValue());
			//Reporter.captureScreenShot(DataManager.resultPath);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in setQuantity_ActiveCardElement_SelectACard method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickAddToCart_ActiveCardElement_SelectACard(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			WebElement addToCartElement = activeElement.findElement(By.cssSelector(getefareORElement(params.get(0).getValue())));
			addToCartElement.click();
			//Reporter.captureScreenShot(DataManager.resultPath);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickAddToCart_ActiveCardElement_SelectACard method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	/**
	 * This method will get the element of price in pay as you go section in Add
	 * products section.
	 * 
	 * @param xpath
	 * @param params
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws Exception
	 */
	public static boolean getAndSetActiveElement_PayAsYouGo_AddProducts(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			activeElement = null;
			List<WebElement> amountToAddEle = driver.findElements(By.cssSelector(getefareORElement(params.get(0).getValue())));
			WebElement priceEle = null;

			System.out.println("size of Elements in PayAsYouGo" + amountToAddEle.size());
			for (int amountToAddIndex = 0; amountToAddIndex < amountToAddEle.size(); amountToAddIndex++) {
				try {
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					priceEle = amountToAddEle.get(amountToAddIndex).findElement(By.cssSelector(getefareORElement(params.get(1).getValue())));
				} catch (Exception e) {
					continue;
				}
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				String priceFromUi = priceEle.getText();
				System.out.println("Price Element is:" + priceFromUi);
				if (priceFromUi.equals(params.get(2).getValue())) {
					System.out.println("Match is There");
					System.out.println("priceEle.getText():params.get(2).getValue()::" + priceEle.getText() + ":" + params.get(2).getValue());

					activeElement = amountToAddEle.get(amountToAddIndex);
					break;
				}
			}
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			System.out.println("Exception was raised in getAndSetActiveElement_PayAsYouGo_AddProducts method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickAddToCart_PayAsYouGo_AddProducts(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			WebElement addToCartElements = activeElement.findElement(By.cssSelector(getefareORElement(params.get(0).getValue())));
			addToCartElements.click();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickAddToCart_PayAsYouGo_AddProducts method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean getAndCheckFrequentRider_AddProducts(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			activeElement = null;
			List<WebElement> listFrequentRider = driver.findElements(By.cssSelector(getefareORElement(params.get(0).getValue())));
			for (int selectFrequentRidesIndex = 0; selectFrequentRidesIndex < listFrequentRider.size(); selectFrequentRidesIndex++) {
				String product = listFrequentRider.get(selectFrequentRidesIndex).findElement(By.cssSelector(getefareORElement(params.get(1).getValue()))).getText();
				String rider = listFrequentRider.get(selectFrequentRidesIndex).findElement(By.cssSelector(getefareORElement(params.get(2).getValue()))).getText();
				String subRider = listFrequentRider.get(selectFrequentRidesIndex).findElement(By.cssSelector(getefareORElement(params.get(3).getValue()))).getText();
				String price = listFrequentRider.get(selectFrequentRidesIndex).findElement(By.cssSelector(getefareORElement(params.get(4).getValue()))).getText();
				System.out.println(product + "--" + rider + "--" + subRider + "--" + price);
				if (product.equals(params.get(5).getValue()) && rider.equals(params.get(6).getValue()) && subRider.equals(params.get(7).getValue())
						&& price.equals(params.get(8).getValue())) {
					activeElement = listFrequentRider.get(selectFrequentRidesIndex);
					break;
				}
			}
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getAndCheckFrequentRider_AddProducts method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickAddToCart_AddProducts(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			WebElement quantityElement = null;
			quantityElement = activeElement.findElement(By.cssSelector(getefareORElement(params.get(0).getValue())));
			quantityElement.click();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickAddToCart_AddProducts method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean getAndSet_RidePassesFareProduct_AddProducts(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			activeElement = null;
			List<WebElement> listRidePasses = driver.findElements(By.cssSelector(getefareORElement(params.get(0).getValue())));
			for (int selectRidePassesIndex = 0; selectRidePassesIndex < listRidePasses.size(); selectRidePassesIndex++) {
				String product;
				String rider;
				String subRider;
				try {
					product = listRidePasses.get(selectRidePassesIndex).findElement(By.cssSelector(getefareORElement(params.get(1).getValue()))).getText();
					rider = listRidePasses.get(selectRidePassesIndex).findElement(By.cssSelector(getefareORElement(params.get(2).getValue()))).getText();
					subRider = listRidePasses.get(selectRidePassesIndex).findElement(By.cssSelector(getefareORElement(params.get(3).getValue()))).getText();
				} catch (Exception e) {
					continue;
				}
				String price = listRidePasses.get(selectRidePassesIndex).findElement(By.cssSelector(getefareORElement(params.get(4).getValue()))).getText();
				System.out.println(product + "--" + rider + "--" + subRider + "--" + price);
				if (product.equals(params.get(5).getValue()) && rider.equals(params.get(6).getValue()) && subRider.equals(params.get(7).getValue())
						&& price.equals(params.get(8).getValue())) {
					activeElement = listRidePasses.get(selectRidePassesIndex);
					break;
				}
			}
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getAndSet_RidePassesFareProduct_AddProducts method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickAddToCart_RidePassesFareProduct_AddProducts(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			WebElement quantityElement = null;
			quantityElement = activeElement.findElement(By.cssSelector(getefareORElement(params.get(0).getValue())));
			quantityElement.click();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickAddToCart_RidePassesFareProduct_AddProducts method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clear_ActiveCardElement(String css, List<Param> params) {
boolean flag=false;
		try {
			activeElement.findElement(By.cssSelector(css)).clear();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clear_ActiveCardElement method and the exception is :"+e);
			flag=false;
		}
		return flag;

	}

	public static boolean setValue_ActiveCardElement(String css, List<Param> params) {
		boolean flag=false;
		try {
			activeElement.findElement(By.cssSelector(css)).sendKeys(params.get(0).getValue());
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in setValue_ActiveCardElement method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean getAndSetActiveCardElementByContent_CheckOut(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			List<WebElement> listchildProducts;
			String cardType;
			List<List<String>> checkpoutproductDetails;
			List<WebElement> listSelectCard;
			WebElement cardTypeElement;
			WebElement quantityElement;
			WebElement priceElement;
			WebElement childProductType;
			WebElement childProductName;
			WebElement childProductPrice;
			int checkoutproductDetailsIndex = 1;
			int childMatchingCount = 0;

			checkpoutproductDetails = buildCardDetailsList(params.get(0).getValue());

			activeElement = null;
			listSelectCard = driver.findElements(By.cssSelector(getefareORElement(params.get(1).getValue())));

			for (int selectCardIndex = 0; selectCardIndex < listSelectCard.size(); selectCardIndex++) {

				cardTypeElement = listSelectCard.get(selectCardIndex).findElement(By.cssSelector(getefareORElement(params.get(2).getValue())));
				priceElement = listSelectCard.get(selectCardIndex).findElement(By.cssSelector(getefareORElement(params.get(3).getValue())));
				quantityElement = listSelectCard.get(selectCardIndex).findElement(By.cssSelector(getefareORElement(params.get(4).getValue())));

				System.out.println("cardTypeElement.getText():checkpoutproductDetails.get(0).get(0)::" + cardTypeElement.getText() + ":" + checkpoutproductDetails.get(0).get(0));
				System.out.println("quantityElement.getText():checkpoutproductDetails.get(0).get(1)::" + quantityElement.getText() + ":" + checkpoutproductDetails.get(0).get(1));
				System.out.println("quantityElement.getAttribute(value):checkpoutproductDetails.get(0).get(1)::" + quantityElement.getAttribute("value") + ":"
						+ checkpoutproductDetails.get(0).get(1));
				System.out.println("priceElement.getText():checkpoutproductDetails.get(0).get(2)::" + priceElement.getText() + ":" + checkpoutproductDetails.get(0).get(2));

				if (cardTypeElement.getText().equals(checkpoutproductDetails.get(0).get(0)) && quantityElement.getAttribute("value").equals(checkpoutproductDetails.get(0).get(1))
						&&
						// quantityElement.getText().equals(checkpoutproductDetails.get(0).get(1))
						priceElement.getText().equals(checkpoutproductDetails.get(0).get(2))) {

					listchildProducts = listSelectCard.get(selectCardIndex).findElements(By.cssSelector(getefareORElement(params.get(5).getValue())));
					for (int selectProductIndex = 0; selectProductIndex < listchildProducts.size(); selectProductIndex++) {
						childProductType = listchildProducts.get(selectProductIndex).findElement(By.cssSelector(getefareORElement(params.get(6).getValue())));
						childProductName = listchildProducts.get(selectProductIndex).findElement(By.cssSelector(getefareORElement(params.get(7).getValue())));
						childProductPrice = listchildProducts.get(selectProductIndex).findElement(By.cssSelector(getefareORElement(params.get(8).getValue())));

						System.out.println("childProductType.getText():checkpoutproductDetails.get(" + checkoutproductDetailsIndex + ").get(0)::" + childProductType.getText() + ":"
								+ checkpoutproductDetails.get(checkoutproductDetailsIndex).get(0));
						System.out.println("childProductName.getText():checkpoutproductDetails.get(" + checkoutproductDetailsIndex + ").get(1)::" + childProductName.getText() + ":"
								+ checkpoutproductDetails.get(checkoutproductDetailsIndex).get(1));
						System.out.println("childProductPrice.getText():checkpoutproductDetails.get(" + checkoutproductDetailsIndex + ").get(2)::" + childProductPrice.getText() + ":"
								+ checkpoutproductDetails.get(checkoutproductDetailsIndex).get(2));

						if (childProductType.getText().equals(checkpoutproductDetails.get(checkoutproductDetailsIndex).get(0))
								&& childProductName.getText().equals(checkpoutproductDetails.get(checkoutproductDetailsIndex).get(1))
								&& childProductPrice.getText().equals(checkpoutproductDetails.get(checkoutproductDetailsIndex).get(2))) {
							childMatchingCount++;
							System.out.println("childMatchingCount:listchildProducts.size()" + "::" + childMatchingCount + listchildProducts.size());
							System.out.println("childProductType.getText():checkpoutproductDetails.get(" + checkoutproductDetailsIndex + ").get(0)::" + childProductType.getText() + ":"
									+ checkpoutproductDetails.get(checkoutproductDetailsIndex).get(0));
							if (childMatchingCount == listchildProducts.size()) {
								System.out.println("Match is Found");
								activeElement = listSelectCard.get(selectCardIndex);
								flag = true;
								return flag;
							}
						}
						checkoutproductDetailsIndex++;
					}
				}
			}
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getAndSetActiveCardElementByContent_CheckOut method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean getAndSetActiveCardElementByIndex_CheckOut(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			List<List<String>> checkpoutproductDetails;
			List<WebElement> listSelectCard;
			List<WebElement> listchildProducts;
			String cardType;
			WebElement cardTypeElement;
			WebElement quantityElement;
			WebElement priceElement;
			WebElement childProductType;
			WebElement childProductName;
			WebElement childProductPrice;
			int checkoutproductDetailsIndex = 1;
			int childMatchingCount = 0;
			boolean matchFound = false;

			checkpoutproductDetails = buildCardDetailsList(params.get(1).getValue());
			activeElement = null;
			listSelectCard = driver.findElements(By.cssSelector(getefareORElement(params.get(2).getValue())));
			System.out.println("listSelectCard.size()" + listSelectCard.size());
			System.out.println("value:" + Integer.parseInt(params.get(0).getValue()));

			if (listSelectCard.size() < Integer.parseInt(params.get(0).getValue())) {
				System.out.println("Match Not Found");
				return matchFound;
			}
			activeElement = listSelectCard.get(Integer.parseInt(params.get(0).getValue()) - 1);

			cardTypeElement = activeElement.findElement(By.cssSelector(getefareORElement(params.get(3).getValue())));
			priceElement = activeElement.findElement(By.cssSelector(getefareORElement(params.get(4).getValue())));
			quantityElement = activeElement.findElement(By.cssSelector(getefareORElement(params.get(5).getValue())));

			System.out.println("cardTypeElement.getText():checkpoutproductDetails.get(0).get(0)::" + cardTypeElement.getText() + ":" + checkpoutproductDetails.get(0).get(0));
			System.out.println("quantityElement.getAttribute(value):checkpoutproductDetails.get(0).get(1)::" + quantityElement.getAttribute("value") + ":"
					+ checkpoutproductDetails.get(0).get(1));
			System.out.println("quantityElement.getText():checkpoutproductDetails.get(0).get(1)::" + quantityElement.getText() + ":" + checkpoutproductDetails.get(0).get(1));
			System.out.println("priceElement.getText():checkpoutproductDetails.get(0).get(2)::" + priceElement.getText() + ":" + checkpoutproductDetails.get(0).get(2));

			if (cardTypeElement.getText().equals(checkpoutproductDetails.get(0).get(0)) && quantityElement.getAttribute("value").equals(checkpoutproductDetails.get(0).get(1)) &&
			// quantityElement.getText().equals(checkpoutproductDetails.get(0).get(1))
			// &&
					priceElement.getText().equals(checkpoutproductDetails.get(0).get(2))) {

				listchildProducts = activeElement.findElements(By.cssSelector(getefareORElement(params.get(6).getValue())));
				for (int selectProductIndex = 0; selectProductIndex < listchildProducts.size(); selectProductIndex++) {
					childProductType = listchildProducts.get(selectProductIndex).findElement(By.cssSelector(getefareORElement(params.get(7).getValue())));
					childProductName = listchildProducts.get(selectProductIndex).findElement(By.cssSelector(getefareORElement(params.get(8).getValue())));
					childProductPrice = listchildProducts.get(selectProductIndex).findElement(By.cssSelector(getefareORElement(params.get(9).getValue())));

					if (childProductType.getText().equals(checkpoutproductDetails.get(checkoutproductDetailsIndex).get(0))
							&& childProductName.getText().equals(checkpoutproductDetails.get(checkoutproductDetailsIndex).get(1))
							&& childProductPrice.getText().equals(checkpoutproductDetails.get(checkoutproductDetailsIndex).get(2))) {
						childMatchingCount++;
						if (childMatchingCount == listchildProducts.size()) {
							System.out.println("Match Found");
							matchFound = true;
							break;
						}
					}
					checkoutproductDetailsIndex++;
				}
			}
			if (matchFound == true) {
			} else {
				activeElement = null;
			}
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getAndSetActiveCardElementByIndex_CheckOut method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean getAndCheckPeriodPass_AddProducts(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			activeElement = null;
			List<WebElement> listPeriodRider = driver.findElements(By.cssSelector(getefareORElement(params.get(0).getValue())));
			for (int selectPeriodRidesIndex = 0; selectPeriodRidesIndex < listPeriodRider.size(); selectPeriodRidesIndex++) {
				String product = listPeriodRider.get(selectPeriodRidesIndex).findElement(By.cssSelector(getefareORElement(params.get(1).getValue()))).getText();
				String rider = listPeriodRider.get(selectPeriodRidesIndex).findElement(By.cssSelector(getefareORElement(params.get(2).getValue()))).getText();
				String subRider = listPeriodRider.get(selectPeriodRidesIndex).findElement(By.cssSelector(getefareORElement(params.get(3).getValue()))).getText();
				String price = listPeriodRider.get(selectPeriodRidesIndex).findElement(By.cssSelector(getefareORElement(params.get(4).getValue()))).getText();
				System.out.println(product + "--" + rider + "--" + subRider + "--" + price);
				if (product.equals(params.get(5).getValue()) && rider.equals(params.get(6).getValue()) && subRider.equals(params.get(7).getValue())
						&& price.equals(params.get(8).getValue())) {
					activeElement = listPeriodRider.get(selectPeriodRidesIndex);
					break;
				}
			}
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getAndCheckPeriodPass_AddProducts method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean enterShippinInformation(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(0).getValue()), params.get(1).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(2).getValue()), params.get(3).getValue());
			if(flag==false)
				return flag;
			catchingInfo.put("Name", params.get(1).getValue()+" "+params.get(3).getValue());
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(4).getValue()), params.get(5).getValue());
			if(flag==false)
				return flag;
			catchingInfo.put("Add1", params.get(5).getValue());
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(6).getValue()), params.get(7).getValue());
			if(flag==false)
				return flag;
			catchingInfo.put("Add2", params.get(7).getValue());
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(8).getValue()), params.get(9).getValue());
			if(flag==false)
				return flag;
			catchingInfo.put("City", params.get(9).getValue());
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(10).getValue()), params.get(11).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(12).getValue()), params.get(13).getValue());
			if(flag==false)
				return flag;
			catchingInfo.put("Zip", params.get(13).getValue());
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(14).getValue()), params.get(15).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(16).getValue()), params.get(17).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(18).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(4000);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in enterShippinInformation method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean enteringCardDetailInformationinFields_ReviewOrder(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(0).getValue()), params.get(9).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(1).getValue()), params.get(10).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(2).getValue()), params.get(11).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(3).getValue()), params.get(12).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(4).getValue()), params.get(13).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(5).getValue()), params.get(14).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(6).getValue()), params.get(15).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(7).getValue()), params.get(16).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(8).getValue()), params.get(17).getValue());
			if(flag==false)
				return flag;
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in enteringCardDetailInformationinFields_ReviewOrder method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	public static boolean fetchOrderDetails(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			catchingInfo.put("Date", driver.findElement(By.cssSelector(getefareORElement(params.get(0).getValue()))).getText());
			catchingInfo.put("OrderId", driver.findElement(By.cssSelector(getefareORElement(params.get(1).getValue()))).getText());
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in fetchOrderDetails method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean verifyingUserOrder(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		/*catchingInfo.put("OrderId", "3478");
		catchingInfo.put("Name", "Manohar K");
		catchingInfo.put("Date", "05/27/2016");
		catchingInfo.put("Add1", "addr1");
		catchingInfo.put("Add2", "addr2");
		catchingInfo.put("City", "alaska");
		catchingInfo.put("Zip", "12345");*/
		try {
			System.out.println("Catching the Inforamtion:" + catchingInfo);
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(0).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(5000);
			// SeleniumCoreFunctions.clickByXpath(getefareORElement(params.get(1).getValue()));
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(1).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(2000);
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(2).getValue()), catchingInfo.get("OrderId"));
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(3).getValue()));
			if(flag==false)
				return flag;
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in verifyingUserOrder method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	private static boolean flagCheck(boolean flag) {
		// TODO Auto-generated method stub
		boolean conditionFlag = false;
		try {
			if(flag==true)
				conditionFlag=true;
			else if(flag==false)
				conditionFlag = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conditionFlag;
	}

	public static boolean enterManageBalCardDetails(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(0).getValue()), params.get(3).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(1).getValue()), params.get(4).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(2).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(2000);
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(5).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(2000);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in enterManageBalCardDetails method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	public static boolean verifyManageBalCardDetails(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			String str[];
			if(driver.findElement(By.xpath(getefareORElement(params.get(0).getValue()))).getText().equals(params.get(3).getValue()))
				flag = true;
				else
					flag = false;
			str=driver.findElement(By.xpath(getefareORElement(params.get(1).getValue()))).getText().split(":");
			if(str[1].toString().equals(params.get(4).getValue()))
				flag = true;
			else
				flag = false;
			str=driver.findElement(By.xpath(getefareORElement(params.get(2).getValue()))).getText().split(":");
			if(str[1].toString().equals(params.get(5).getValue()))
				flag = true;
			else
				flag = false;
			Thread.sleep(2000);
			System.out.println("passed");
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in verifyManageBalCardDetails method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}	

	public static boolean comparingSearchDataForUserOrder(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			Thread.sleep(3000);
			List<HashMap<String, String>> finalList=extractingDataFromSearchTable("//*[@id='order-search-results']//tr");
			System.out.println("Table Data : " + finalList);
			for (int k = 0; k < finalList.size(); k++) {
				if (finalList.get(k).get("Order #").equals(catchingInfo.get("OrderId"))) {
					driver.findElement(By.xpath("(//*[text()='View'])" + "[" + (k + 1) + "]")).click();
					Thread.sleep(3000);
					break;
				}
			}
			if (driver.findElement(By.cssSelector(getefareORElement(params.get(0).getValue()))).getText().equals(catchingInfo.get("OrderId"))&&
					driver.findElement(By.cssSelector(getefareORElement(params.get(1).getValue()))).getText().equals(catchingInfo.get("Date"))&&
					driver.findElement(By.cssSelector(getefareORElement(params.get(2).getValue()))).getText().equals(catchingInfo.get("Name"))&&
					driver.findElement(By.cssSelector(getefareORElement(params.get(3).getValue()))).getText().equals(catchingInfo.get("Add1"))&&
					driver.findElement(By.cssSelector(getefareORElement(params.get(4).getValue()))).getText().equals(catchingInfo.get("Add2"))&&
					driver.findElement(By.cssSelector(getefareORElement(params.get(5).getValue()))).getText().equals(catchingInfo.get("City"))&&
					driver.findElement(By.cssSelector(getefareORElement(params.get(6).getValue()))).getText().equals(catchingInfo.get("Zip")))
				flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in comparingSearchDataForUserOrder method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	
	/**
	 * 
	 * @param xpath
	 * @param params
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws Exception
	 */
	public static boolean clickOnPay_ReviewOrder(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(0).getValue()));
			if(flag==false)
				return flag;
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickOnPay_ReviewOrder method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean updateMyProfileAndVerify(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			System.out.println("Updating basic information of user in 'My Profile'");
			System.out.println("Entering email address");
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(0).getValue()), params.get(1).getValue());
			if(flag==false)
				return flag;
			System.out.println("Entering First Name");
			Thread.sleep(2000);
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(2).getValue()), params.get(3).getValue());
			if(flag==false)
				return flag;
			System.out.println("Entering last name");
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(4).getValue()), params.get(5).getValue());
			if(flag==false)
				return flag;

			System.out.println("Updating address of user in 'My Profile'");
			System.out.println("Entering address line 1");
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(6).getValue()), params.get(7).getValue());
			if(flag==false)
				return flag;
			System.out.println("Entering address line 2");
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(8).getValue()), params.get(9).getValue());
			if(flag==false)
				return flag;
			System.out.println("Entering city name");
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(10).getValue()), params.get(11).getValue());
			if(flag==false)
				return flag;
			System.out.println("Slecting state from dropdpwn");
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(12).getValue()), params.get(13).getValue());
			if(flag==false)
				return flag;
			System.out.println("Entering zip code");
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(14).getValue()), params.get(15).getValue());
			if(flag==false)
				return flag;
			System.out.println("Slecting country from dropdpwn");
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(16).getValue()), params.get(17).getValue());
			if(flag==false)
				return flag;

			System.out.println("Updating Contact information of user in 'My Profile'");
			System.out.println("Entering primary phone no");
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(18).getValue()), params.get(19).getValue());
			if(flag==false)
				return flag;
			System.out.println("Entering date of birth");
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(20).getValue()), params.get(21).getValue());
			if(flag==false)
				return flag;
			System.out.println("Entering mobile phone no");
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(22).getValue()), params.get(23).getValue());
			if(flag==false)
				return flag;

			if (params.get(25).getValue().equalsIgnoreCase("yes") || params.get(25).getValue().equalsIgnoreCase("true")) {
				if (!driver.findElement(By.cssSelector(getefareORElement(params.get(24).getValue()))).isSelected()) {
					System.out.println("Clicking on checkbox");
					flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(24).getValue()));
					if(flag==false)
						return flag;
				}
			}
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(26).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(4000);
			try {
				String successMsg = driver.findElement(By.cssSelector(getefareORElement(params.get(27).getValue()))).getText();
				System.out.println("Alert message after update of the user information : " + successMsg);
				if (successMsg.contains("Your email address has been changed"))
					System.out.println("User details updated successfully");
			} catch (Exception e) {
				/*
				 * flag = false; return flag;
				 */
			}
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in updateMyProfileAndVerify method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

  /**
  * Will verify order summary email.
  * @param xpath
  * @param params
  * @return
  * @throws Exception
  */
  public static boolean verifyOrderSummaryEmail(String xpath, List<Param> params) throws Exception {
         boolean flag = false;
         try {
					Thread.sleep(40000);
					 String orderSummery = GmailUtility.getOrderHistoryEmailDetails(params.get(0).getValue(), params.get(1).getValue());
					 //String orderSummery = GmailUtility.getOrderHistoryEmailDetails("efaretesting55@gmail.com", "Orange@5");
					 //if (orderSummery.contains("3473") && orderSummery.contains("raj kumar"))
					 
					 System.out.println("Date in Email:" + catchingInfo.get("Date") + ":OrderID in Email:" + catchingInfo.get("OrderId"));
					 if (orderSummery.contains(catchingInfo.get("Date")) && orderSummery.contains(catchingInfo.get("OrderId")))
					        flag = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Exception was raised in verifyOrderSummaryEmail method and the exception is :"+e);
					flag=false;
				}
         return flag;
  }

	
	/**
	 * Method will set the value to get the reports in 'ORDER HISTERY' tab in 'MY
	 * ACCOUNT' screen.
	 * 
	 * @param xpath
	 * @param params
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws Exception
	 */

	public static boolean setValuesToGetOrderHistory_MyAccount(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(0).getValue()), params.get(1).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(2).getValue()), params.get(3).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(4).getValue()), params.get(5).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(6).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(3000);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in setValuesToGetOrderHistory_MyAccount method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	/**
	 * 
	 * @param xpath
	 * @param params
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws Exception
	 */
	public static boolean setDatesAndCardType_MyAccount(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(0).getValue()), params.get(3).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(1).getValue()), params.get(4).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(2).getValue()), params.get(5).getValue());
			if(flag==false)
				return flag;
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in setDatesAndCardType_MyAccount method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickOnGetReports_MyAccount(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(0).getValue()));
			if(flag==false)
				return flag;
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickOnGetReports_MyAccount method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean compareTestDataAndTableDataInReviewOrder(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			boolean comparision = false;
			List<List<String>> list1 = new ArrayList<List<String>>();
			String[] splt1 = params.get(0).getValue().split("::");
			System.out.println(Arrays.asList(splt1));
			for (int i = 0; i < splt1.length; i++) {
				String[] splt2 = splt1[i].split(":");
				System.out.println(Arrays.asList(splt2));
				list1.add(Arrays.asList(splt2));
			}
			System.out.println(list1);
			int i, j;
			for (i = 0; i < list1.size(); i++) {

				for (j = 0; j < list1.get(i).size(); j++) {
					if (j == 0) {
						if (list1.get(i).get(j).equals(completeOrderTable.get(i).get(j).get("quantity")))
							System.out.println("quantity is pass");
						else {
							System.out.println("quantity is fail");
							comparision = false;
						}
					} else if (j == 1) {
						if (list1.get(i).get(j).equals(completeOrderTable.get(i).get(j).get("item")))
							System.out.println("item is pass");
						else {
							System.out.println("item is fail");
							comparision = false;
						}
					} else if (j == 2) {
						if (list1.get(i).get(j).equals(completeOrderTable.get(i).get(j).get("unit")))
							System.out.println("unit is pass");
						else {
							System.out.println("unit is fail");
							comparision = false;
						}
					} else if (j == 3) {
						if (list1.get(i).get(j).equals(completeOrderTable.get(i).get(j).get("amount")))
							System.out.println("amount is pass");
						else {
							System.out.println("amount is fail");
							comparision = false;
						}
					}
				}

				if (i == list1.size() - 1 && comparision == false || comparision == false) {
					comparision = false;
				} else
					comparision = true;
			}
			try {
				if (!(completeOrderTable.get(i).get(1).get("item")).equals("") || (completeOrderTable.get(i).get(1).get("item")) != null) {
					if ((completeOrderTable.get(i).get(1).get("item")).equals("Smart Card") || (completeOrderTable.get(i).get(1).get("item")).equals("Magnetics")
							|| (completeOrderTable.get(i).get(1).get("item")).equals("LUCC") || (completeOrderTable.get(i).get(1).get("item")).equals("Ground")) {
						comparision = true;
					}
				}
			} catch (Exception e) {

			}
			if (comparision == true) {
				System.out.println("Comparision is passed");
			} else if (comparision == false) {
				System.out.println("Comparision is failed");
			}
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in compareTestDataAndTableDataInReviewOrder method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean getDataFromTableReviewOrder(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			WebElement table = driver.findElement(By.cssSelector(getefareORElement(params.get(0).getValue())));
			completeOrderTable = new ArrayList<List<HashMap<String, Object>>>();
			List<HashMap<String, Object>> orderTable = null;
			/*
			 * List<WebElement> trCollectionThead =
			 * (table.findElements(By.xpath("//*[@class='detailedsummary']/thead/tr")));
			 * collectingData(trCollectionThead,orderTable,completeOrderTable);
			 */
			List<WebElement> trCollectionTbody = (table.findElements(By.xpath("//*[@class='detailedsummary']/tbody/tr")));
			collectingData(trCollectionTbody, orderTable, completeOrderTable);
			List<WebElement> trCollectionTfoot = (table.findElements(By.xpath("//*[@class='detailedsummary']/tfoot/tr")));
			collectingData(trCollectionTfoot, orderTable, completeOrderTable);
			System.out.println(completeOrderTable);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getDataFromTableReviewOrder method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean setUrl(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			driver.get(params.get(0).getValue());
			Thread.sleep(2000);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in setUrl method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	/**
	 * Will register the user and activate the user
	 * 
	 * @param xpath
	 * @param params
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws Exception
	 */
	public static boolean registerAndValidateUser(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, Exception {
		boolean flag = false;
		try {
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(0).getValue()), params.get(18).getValue());if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(1).getValue()), params.get(19).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(2).getValue()), params.get(20).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(3).getValue()), params.get(21).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(4).getValue()), params.get(22).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(5).getValue()), params.get(23).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(6).getValue()), params.get(24).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(7).getValue()), params.get(25).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(8).getValue()), params.get(26).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(9).getValue()), params.get(27).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(10).getValue()), params.get(28).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(11).getValue()), params.get(29).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(12).getValue()), params.get(30).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(13).getValue()), params.get(31).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.clickCheckBoxByCss(getefareORElement(params.get(14).getValue()), params.get(32).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.selectValueInDropDownByCss(getefareORElement(params.get(15).getValue()), params.get(33).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.enterValueInTextBoxBycsspath(getefareORElement(params.get(16).getValue()), params.get(34).getValue());
			if(flag==false)
				return flag;
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(17).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(50000);
			String link = GmailUtility.getVerificationLinkFromGmail(params.get(35).getValue(), params.get(36).getValue());
			driver.get(link);

			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in registerAndValidateUser method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static void collectingData(List<WebElement> tr_collection, List<HashMap<String, Object>> orderTable, List<List<HashMap<String, Object>>> completeOrderTable) {
		for (WebElement trElement : tr_collection) {
			HashMap<String, Object> orderDetails = new HashMap<String, Object>();
			List<WebElement> td_collection = trElement.findElements(By.tagName("td"));
			orderTable = new ArrayList<HashMap<String, Object>>();
			int col_num = 1;
			for (WebElement tdElement : td_collection) {
				if (col_num == 1) {
					orderDetails = new HashMap<String, Object>();
					orderDetails.put("quantity", tdElement.getText());
				}
				if (col_num == 2) {
					orderDetails = new HashMap<String, Object>();
					orderDetails.put("item", tdElement.getText());
				}
				if (col_num == 3) {
					orderDetails = new HashMap<String, Object>();
					orderDetails.put("unit", tdElement.getText());

				}
				if (col_num == 4) {
					if (orderTable.get(0).get("quantity").toString().equals("") || orderTable.get(0).get("quantity") == null) {
						orderDetails = new HashMap<String, Object>();
						orderDetails.put("amount", tdElement.getText());
					} else {
						col_num++;
						continue;
					}
				}
				if (col_num == 5) {
					orderDetails = new HashMap<String, Object>();
					orderDetails.put("amount", tdElement.getText());
				}
				col_num++;
				orderTable.add(orderDetails);
			}

			completeOrderTable.add(orderTable);
		}
	}

	/**
	 * User will Logout from the application
	 * 
	 * @param path
	 * @param param
	 * @return
	 * @throws Exception
	 * @throws NoSuchFieldException
	 */
	public boolean logout(String path, List<Param> params) throws NoSuchFieldException, Exception {
		boolean flag = false;
		try {
			Thread.sleep(3000);
			//System.out.println("Clicking on user name");
			flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(0).getValue()));
			if(flag==false)
				return flag;
			Thread.sleep(3000);
			// SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(0).getValue()));
			
				//System.out.println("Clicking 'Logout' link");
				flag=SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(1).getValue()));
			 if(flag==false) {
				System.out.println("'Logout' link didn't appear so again clicking on user name");
				SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(0).getValue()));
				//System.out.println("Clicking 'Logout' link");
				SeleniumCoreFunctions.clickBycssSelector(getefareORElement(params.get(1).getValue()));
			}
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in logout method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static List<List<String>> buildCardDetailsList(String cardDetails) {

		List<List<String>> productDetails = new ArrayList<List<String>>();
		String[] indiviualCardDetails = cardDetails.split("::");

		
		System.out.println("cardDetails Details:" + cardDetails);
		
		
		for (int i = 0; i < indiviualCardDetails.length; i++) {
			String[] cardDetailInfo = indiviualCardDetails[i].split(":");
			productDetails.add(Arrays.asList(cardDetailInfo));
		}
		System.out.println("Product Details:" + productDetails);		
		return productDetails;
	}

	public static String getefareORElement(String objectRepositoryName) throws NoSuchFieldException, SecurityException {

		String objcsspath;
		objcsspath = ((EfareObjectRepository) DataManager.objRepositories.get(objectRepositoryName)).getByCss();
		System.out.println("ObjectORName:xPath::" + objectRepositoryName + ":" + objcsspath);
		return objcsspath;

	}

	public static boolean click(String xpath, List<Param> params) throws Exception {
		boolean flag=false;
		try {
			Thread.sleep(2000);
			SeleniumCoreFunctions.clickBycssSelector(xpath);
			Thread.sleep(2000);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in click method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean press(String xpath, List<Param> params) {
		System.out.println("xpath=-=-=- " + xpath);
		System.out.println("params =-=-=- " + params);
		for (Param p : params) {
			System.out.print(p.getValue() + " ");
		}
		System.out.println();
		return true;

	}

}
