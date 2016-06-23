package com.omniwyse.selenium.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SeleniumCoreFunctions {
	// static WebDriver driver;
	public static void main(String[] args) {
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "D:\\IBaseDev\\testify\\Drivers\\chromedriver.exe"); DesiredCapabilities
		 * capabilities = new DesiredCapabilities(); driver = new
		 * ChromeDriver(capabilities); driver.manage().timeouts().implicitlyWait(90,
		 * TimeUnit.SECONDS); driver.manage().window().maximize();
		 */
		// TODO Auto-generated method stub

	}

	public static boolean clickById(String Id) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.id(Id)).click();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickById method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	
	public static boolean clickBycssSelector(String cssPath) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.cssSelector(cssPath)).click();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickBycssSelector method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public boolean getDataFromTableByXpath(String Xpath) {
		boolean flag=false;
		try {
			WebElement table = SeleniumFramework.driver.findElement(By.className("detailedsummary"));
			List<HashMap<String, Object>> orderTable = new ArrayList<HashMap<String, Object>>();
			List<WebElement> trCollectionThead = (table.findElements(By.xpath("className('detailedsummary')/thead/tr")));
			collectingData(trCollectionThead, orderTable);
			List<WebElement> trCollectionTbody = (table.findElements(By.xpath("className('detailedsummary')/tbody/tr")));
			collectingData(trCollectionTbody, orderTable);
			List<WebElement> trCollectionTfoot = (table.findElements(By.xpath("className('detailedsummary')/tfoot/tr")));
			collectingData(trCollectionTfoot, orderTable);
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getDataFromTableByXpath method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean collectingData(List<WebElement> tr_collection, List<HashMap<String, Object>> orderTable) {
		boolean flag=false;
		try {
			for (WebElement trElement : tr_collection) {
				HashMap<String, Object> orderDetails = new HashMap<String, Object>();
				List<WebElement> td_collection = trElement.findElements(By.tagName("td"));
				int col_num = 1;
				for (WebElement tdElement : td_collection) {
					if (col_num == 1)
						orderDetails = new HashMap<String, Object>();
					orderDetails.put("quantity", tdElement.getText());
					if (col_num == 2)
						orderDetails = new HashMap<String, Object>();
					orderDetails.put("item", tdElement.getText());
					if (col_num == 3)
						orderDetails = new HashMap<String, Object>();
					orderDetails.put("unit", tdElement.getText());
					if (col_num == 5)
						orderDetails = new HashMap<String, Object>();
					orderDetails.put("amount", tdElement.getText());
					orderTable.add(orderDetails);
				}
			}
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in collectingData method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	
	/**
	 * This method will wait for the element to be present
	 * @param xpath
	 * @throws InterruptedException
	 */
	public static boolean waitForElementTobePresentByCssPath(String cssPath) throws InterruptedException {
		boolean flag=false;
		try {
			int i = 0;
			boolean elementPresent = true;
			while (elementPresent) {
				i = i + 1;
				try {
					SeleniumFramework.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					SeleniumFramework.driver.findElement(By.cssSelector(cssPath));
					SeleniumFramework.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					elementPresent = false;
				} catch (Exception e) {
					Thread.sleep(1000);
				}
				if (i == 30)
					elementPresent = false;
			}
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in waitForElementTobePresentByCssPath method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}


	/**
	 * This method will wait for the element to be present
	 * @param xpath
	 * @throws InterruptedException
	 */
	public static boolean waitForElementTobePresentByXpath(String xpath) throws InterruptedException {
		boolean flag=false;
		try {
			int i = 0;
			boolean elementPresent = true;
			while (elementPresent) {
				i = i + 1;
				try {
					SeleniumFramework.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					SeleniumFramework.driver.findElement(By.xpath(xpath));
					SeleniumFramework.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					elementPresent = false;
				} catch (Exception e) {
					Thread.sleep(1000);
				}
				if (i == 30)
					elementPresent = false;
			}
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in waitForElementTobePresentByXpath method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	

	public static boolean clickByXpath(String xpath) throws InterruptedException {
		boolean flag=false;
		try {
			System.out.println("clickByXpath in SeleniumFun class");
			System.out.println("Xpath is:"+ xpath);
			System.out.println("Printed xpath");		
			waitForElementTobePresentByXpath(xpath);
			SeleniumFramework.driver.findElement(By.xpath(xpath)).click();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickByXpath method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickByClassName(String className) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.className(className)).click();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickByClassName method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickByLinkText(String linkText) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.linkText(linkText)).click();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickByLinkText method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickByName(String name) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.name(name)).click();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickByName method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickCheckBoxByName(String name) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.name(name)).click();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickCheckBoxByName method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean clickCheckBoxById(String id,String value) {
		boolean flag=false;
		try {
			if(value.equals("true")||value.equals("TRUE"))
			SeleniumFramework.driver.findElement(By.id(id)).click();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickCheckBoxById method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	
	public static boolean clickCheckBoxByCss(String css,String value) {
		boolean flag=false;
		try {
			if(value.equals("true")||value.equals("TRUE"))
			SeleniumFramework.driver.findElement(By.cssSelector(css)).click();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in clickCheckBoxByCss method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean enterValueInTextBoxById(String id, String text) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.id(id)).sendKeys(text);
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in enterValueInTextBoxById method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	
	public static boolean enterValueInTextBoxBycsspath(String cssPath, String text) throws InterruptedException {
		boolean flag=false;
		try {
			waitForElementTobePresentByCssPath(cssPath);
			SeleniumFramework.driver.findElement(By.cssSelector(cssPath)).clear();
			SeleniumFramework.driver.findElement(By.cssSelector(cssPath)).sendKeys(text);
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in enterValueInTextBoxBycsspath method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean enterValueInTextBoxByXpath(String xpath, String text) throws InterruptedException {
		boolean flag=false;
		try {
			waitForElementTobePresentByXpath(xpath);
			SeleniumFramework.driver.findElement(By.xpath(xpath)).clear();
			SeleniumFramework.driver.findElement(By.xpath(xpath)).sendKeys(text);
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in enterValueInTextBoxByXpath method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean selectValueInDropDownById(String id, String text) {
		boolean flag=false;
		try {
			Select select = new Select(SeleniumFramework.driver.findElement(By.id(id)));
			select.selectByVisibleText(text.toString());
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in selectValueInDropDownById method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	public static boolean selectValueInDropDownByCss(String id, String text) {
		boolean flag=false;
		try {
			Select select = new Select(SeleniumFramework.driver.findElement(By.cssSelector(id)));
			select.selectByVisibleText(text.toString());
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in selectValueInDropDownByCss method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	public static boolean selectValueInDropDownByXpath(String xpath, String text) {
		boolean flag=false;
		try {
			Select select = new Select(SeleniumFramework.driver.findElement(By.xpath(xpath)));
			select.selectByVisibleText(text.toString());
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in selectValueInDropDownByXpath method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean selectByIndexInDropDownById(String id, int index) {
		boolean flag=false;
		try {
			Select select = new Select(SeleniumFramework.driver.findElement(By.id(id)));
			select.selectByIndex(index);
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in selectByIndexInDropDownById method and the exception is :"+e);
			flag=true;
		}
		return flag;
	}
	public static boolean selectByIndexInDropDownByCss(String id, int index) {
		boolean flag=false;
		try {
			Select select = new Select(SeleniumFramework.driver.findElement(By.cssSelector(id)));
			select.selectByIndex(index);
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in selectByIndexInDropDownByCss method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	public static boolean selectByIndexInDropDownByXpath(String xpath, int index) {
		boolean flag=false;
		try {
			Select select = new Select(SeleniumFramework.driver.findElement(By.xpath(xpath)));
			select.selectByIndex(index);
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in selectByIndexInDropDownByXpath method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean enterValueInDateId(String id, String text) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.id(id)).sendKeys(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in enterValueInDateId method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean getDataByXpath(String xpath) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.xpath(xpath)).getText();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getDataByXpath method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
	public static boolean getDataByCss(String css) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.cssSelector(css)).getText();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getDataByCss method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean getDataByClassName(String className) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.id(className)).getText();
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getDataByClassName method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean getDataByUsingAttributeByXpath(String xpath, String attribute) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.xpath(xpath)).getAttribute(attribute);
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getDataByUsingAttributeByXpath method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}

	public static boolean getDataByUsingAttributeByClassName(String className, String attribute) {
		boolean flag=false;
		try {
			SeleniumFramework.driver.findElement(By.id(className)).getAttribute(attribute);
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception was raised in getDataByUsingAttributeByClassName method and the exception is :"+e);
			flag=false;
		}
		return flag;
	}
}

