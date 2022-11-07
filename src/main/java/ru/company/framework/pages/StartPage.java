package ru.company.framework.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class StartPage extends BasePage {

	@FindBy(xpath = "//ul[contains(@class, 'kitt-top-menu')]/li/a[@role='button' and @aria-label]")
	private List<WebElement> menu;

	@FindBy(xpath = "//li[contains(@class, 'kitt-top-menu__item_opened')]//li/a")
	private List<WebElement> subMenu;

	public WebElement selectMenu(String menuTitle) {
		wait.until(ExpectedConditions.visibilityOfAllElements(menu));
		return menu.stream().filter(i -> i.getAttribute("aria-label").equalsIgnoreCase(menuTitle))
				.findFirst().orElse(null);
	}

	public WebElement selectSubMenu(String subMenuTitle) {
		wait.until(ExpectedConditions.visibilityOfAllElements(subMenu));
		return subMenu.stream().filter(i -> i.getText().equalsIgnoreCase(subMenuTitle))
				.findFirst().orElse(null);
	}
}
