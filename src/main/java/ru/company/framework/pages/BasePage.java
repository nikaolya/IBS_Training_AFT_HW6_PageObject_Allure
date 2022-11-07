package ru.company.framework.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.company.framework.managers.DriverManager;
import ru.company.framework.managers.PageManager;
import ru.company.framework.managers.PropManager;

import java.time.Duration;

public class BasePage {
	protected final DriverManager driverManager = DriverManager.getDriverManager();
	protected PageManager pageManager = PageManager.getPageManager();
	private final PropManager props = PropManager.getPropManager();

	protected Actions actions = new Actions(driverManager.getDriver());
	protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();
	protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10));

	public BasePage() {
		PageFactory.initElements(driverManager.getDriver(), this);
	}

	public WebElement scrollToElementJsInFrame(WebElement frame, WebElement element) {
		switchToFrame(frame);
		js.executeScript("arguments[0].scrollIntoView({behavior: \"smooth\", block: \"center\", inline: \"nearest\"});", element);
		driverManager.getDriver().switchTo().defaultContent();
		return element;
	}

	public int convertStringToInteger(String text){
		return Integer.parseInt(text.replaceAll("\\D", ""));
	}

	public void switchToFrame(WebElement frame){
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
	}

	public WebElement scrollToElementJs(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView({behavior: \"smooth\", block: \"center\", inline: \"nearest\"});", element);
		return element;
	}
}
