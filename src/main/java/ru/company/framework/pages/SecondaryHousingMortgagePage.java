package ru.company.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SecondaryHousingMortgagePage extends BasePage {

	@FindBy(xpath = "//h2[@class = 't-header-big']")
	private List<WebElement> h2Header;

	@FindBy(xpath = "//iframe[@id='iFrameResizer0']")
	private WebElement frameInput;

	@FindBy(xpath = "//input[contains(@class, 'dc-input__input')]")
	private List<WebElement> inputFieldList;

	@FindBy(xpath = "//div[@data-e2e-id='mland-calculator/collapsible-discount-true']")
	private List<WebElement> checkboxList;

	@FindBy(xpath = "//span[contains(text(), 'Необходимый доход')]")
	private WebElement requiredIncome;

	private final String INPUT_FIELD_LABEL_NESTED_XPATH = "./../label";
	private final String CHECKBOX_TITLE_NESTED_XPATH = "./div/div/span";
	private final String CHECKBOX_NESTED_XPATH = ".//input[@type = 'checkbox']";
	private final String MAIN_RESULTS_TITLE_XPATH = "//div[@data-test-id='main-results-block']//li//span[contains(text(), '%s')]";
	private final String MAIN_RESULTS_VALUE_NESTED_XPATH = "./../div/span/span";

	public WebElement getH2Header(String title) {
		wait.until(ExpectedConditions.visibilityOfAllElements(h2Header));
		return h2Header.stream().filter(i -> i.getText().contains(title)).findFirst().orElse(null);
	}

	public WebElement getFrameInput() {
		return frameInput;
	}

	public WebElement getInputField(String title) {
		switchToFrame(frameInput);
		wait.until(ExpectedConditions.visibilityOfAllElements(inputFieldList));
		WebElement element = inputFieldList.stream().filter(i -> i.findElement(By.xpath(INPUT_FIELD_LABEL_NESTED_XPATH)).getText()
				.equalsIgnoreCase(title)).findFirst().orElse(null);
		driverManager.getDriver().switchTo().defaultContent();
		return element;
	}

	public void fillInputField(WebElement inputField, String value) {
		switchToFrame(frameInput);
		wait.until(ExpectedConditions.elementToBeClickable(inputField));
		js.executeScript("arguments[0].click();", inputField);
		inputField.sendKeys(Keys.CONTROL + "A");
		inputField.sendKeys(Keys.BACK_SPACE);
		for (String s : value.split("")) {
			inputField.sendKeys(s);
		}
		inputField.sendKeys(Keys.ENTER);
		driverManager.getDriver().switchTo().defaultContent();
	}

	public int getInputFieldValue(WebElement inputField) {
		switchToFrame(frameInput);
		wait.until(ExpectedConditions.visibilityOfAllElements(inputFieldList));
		int value = convertStringToInteger(inputField.getAttribute("value"));
		driverManager.getDriver().switchTo().defaultContent();
		return value;
	}

	public WebElement getCheckbox(String option) {
		switchToFrame(frameInput);
		WebElement element = checkboxList.stream().filter(i -> i.findElement(By.xpath(CHECKBOX_TITLE_NESTED_XPATH)).getText()
				.contains(option)).map(i -> i.findElement(By.xpath(CHECKBOX_NESTED_XPATH))).findFirst().orElse(null);
		driverManager.getDriver().switchTo().defaultContent();
		return element;
	}

	public void setCheckbox(WebElement element) {
		switchToFrame(frameInput);
		boolean state = Boolean.parseBoolean(element.getAttribute("aria-checked"));
		js.executeScript("arguments[0].click();", element);
		wait.until(ExpectedConditions
				.attributeToBe(element, "aria-checked", String.valueOf(!state)));
		driverManager.getDriver().switchTo().defaultContent();
	}

	public String getMainResultValue(WebElement result) {
		scrollToElementJsInFrame(frameInput, result);
		switchToFrame(frameInput);
		String value = result.findElement(By.xpath(MAIN_RESULTS_VALUE_NESTED_XPATH))
				.getText().replaceAll("[^\\d.,]", "");
		driverManager.getDriver().switchTo().defaultContent();
		return value;
	}

	public WebElement getResult(String title) {
		switchToFrame(frameInput);
		List<WebElement> list = driverManager.getDriver().findElements(By.xpath(String.format(MAIN_RESULTS_TITLE_XPATH, title)));
		WebElement element = list.stream().filter(WebElement::isDisplayed).findFirst().orElse(null);
		driverManager.getDriver().switchTo().defaultContent();
		return element;
	}

	public String getRequiredIncomeValue() {
		switchToFrame(frameInput);
		wait.until(ExpectedConditions.visibilityOf(requiredIncome));
		String value = requiredIncome.findElement(By.xpath(MAIN_RESULTS_VALUE_NESTED_XPATH))
				.getText().replaceAll("[^\\d.,]", "");
		driverManager.getDriver().switchTo().defaultContent();
		return value;
	}
}
