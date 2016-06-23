package com.omniwyse.selenium.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.xml.XmlTest;

import com.omniwyse.selenium.data.DataManager;

/**
 * Driver Web Driver instance for specific browser.
 *
 * @throws MalformedURLException
 *
 * @author manohark
 */

public class Driver {

	public static String browserName;
	public static String browserVersion;
	public static boolean writeBrowserStringToFile = false;
	public static String browserVersionString = " ";

	public static RemoteWebDriver getDriver(XmlTest Config, String product) throws MalformedURLException {

		String remoteServerIP = null;
		String remoteServerPort;
		int driverTimeout = 10;

		int height = Integer.parseInt(Config.getParameter("browser_window_height"));
		int width = Integer.parseInt(Config.getParameter("browser_window_width"));
		DesiredCapabilities capability = null;

		remoteServerIP = DataManager.configInfo.get(product).get("NodeIP");//Config.getParameter("grid_client");
		browserName = DataManager.configInfo.get(product).get("Browser");
		remoteServerPort = DataManager.configInfo.get(product).get("Port").split("\\.")[0];

		System.out.println("Remote server IP :" + remoteServerIP);
		System.out.println("Client browser name:" + browserName);

		if (browserName.equalsIgnoreCase("ff") || browserName.equalsIgnoreCase("firefox")) {
			System.out.println("Configuring settings for Firefox");
			capability = DesiredCapabilities.firefox();
			capability.setPlatform(Platform.ANY);
		} else if (browserName.equalsIgnoreCase("chrome")) {
			System.out.println("Configuring settings for Chrome");
			capability = DesiredCapabilities.chrome();
		}else if (browserName.equalsIgnoreCase("ie")||browserName.equalsIgnoreCase("internetexplorer")||browserName.contains("internet")) {
			System.out.println("Configuring settings for IE");
			capability = DesiredCapabilities.internetExplorer();
		}

		// URL of the remote device to run the test scripts

		String url = "http://" + remoteServerIP + ":" + remoteServerPort + "/wd/hub";
		URL clientURL = new URL(url);

		RemoteWebDriver driver = new RemoteWebDriver(clientURL, capability);
		// Code to Maximize the browser
		System.out.println("Maximizing the browser");
		driver.manage().window().setPosition(new Point(0, 0));
		Dimension dim = new Dimension(height, width);
		driver.manage().window().setSize(dim);

		// Allow timeout to be set by a config parameter - used by Performance
		// Team

		driver.manage().timeouts().implicitlyWait(driverTimeout, TimeUnit.SECONDS);
		return driver;
	}
}
