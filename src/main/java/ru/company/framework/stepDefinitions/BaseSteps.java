package ru.company.framework.stepDefinitions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.company.framework.managers.DriverManager;
import ru.company.framework.managers.PageManager;

import java.time.Duration;

public class BaseSteps {
	protected final DriverManager driverManager = DriverManager.getDriverManager();
	protected PageManager pageManager = PageManager.getPageManager();
	protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10));
	protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();
}
