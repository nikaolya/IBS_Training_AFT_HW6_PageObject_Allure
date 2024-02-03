package ru.company.framework.stepDefinitions;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.company.framework.pages.BasePage;
import ru.company.framework.pages.StartPage;

public class StartPageSteps extends BasePage {
	private StartPage startPage = pageManager.getPage(StartPage.class);

	@Step("Переходим в раздел \"{menuTitle}\"")
	public StartPageSteps openMenu(String menuTitle) {
		WebElement menu = startPage.selectMenu(menuTitle);
		Assertions.assertNotNull(menu, String.format("Меню %s не найдено на странице", menuTitle));
		wait.until(ExpectedConditions.elementToBeClickable(menu));
		menu.click();
		return this;
	}

	@Step("Выбираем подменю \"{subMenuTitle}\"")
	public SecondaryHousingMortgageSteps openSubMenu(String subMenuTitle) {
		WebElement subMenu = startPage.selectSubMenu(subMenuTitle);
		Assertions.assertNotNull(subMenu, String.format("Меню %s не найдено на странице", subMenuTitle));
		wait.until(ExpectedConditions.elementToBeClickable(subMenu));
		subMenu.click();
		return pageManager.getPage(SecondaryHousingMortgageSteps.class);
	}

//	@Step("Выбираем подменю \"{subMenuTitle}\"")
//	public <T extends BasePage> T openSubMenu(String subMenuTitle) {
//		Class<? extends BasePage> nextPage = null;
//		if ("Ипотека на вторичное жильё".contains(subMenuTitle)){
//			nextPage = pageManager.getPage(SecondaryHousingMortgageSteps.class).getClass();
//		}
//		WebElement subMenu = startPage.selectSubMenu(subMenuTitle);
//		Assertions.assertNotNull(subMenu, String.format("Меню %s не найдено на странице", subMenuTitle));
//		wait.until(ExpectedConditions.elementToBeClickable(subMenu));
//		subMenu.click();
//		return (T) pageManager.getPage(nextPage);
//	}
}
