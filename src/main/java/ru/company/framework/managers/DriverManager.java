package ru.company.framework.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.company.framework.utils.ConstProp;

public class DriverManager {
	private WebDriver driver;
	private static DriverManager driverManager = null;
	private final PropManager props = PropManager.getPropManager();

	private DriverManager() {
	}

	public static DriverManager getDriverManager() {
		if (driverManager == null) {
			driverManager = new DriverManager();
		}
		return driverManager;
	}

	public WebDriver getDriver() {
		if (driver == null) {
			initDriver();
		}
		return driver;
	}

	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	private void initDriver() {
		switch (props.getProperty(ConstProp.BROWSER)) {
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				break;
			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				driver = new ChromeDriver(options);
				break;
			default:
				Assertions.fail(String.format("Типа браузера %s не существует во фреймворке", props.getProperty(ConstProp.BROWSER)));
		}
	}
}
