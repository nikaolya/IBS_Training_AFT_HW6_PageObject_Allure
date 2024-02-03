package ru.company.framework.stepDefinitions;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import ru.company.framework.pages.BasePage;
import ru.company.framework.pages.SecondaryHousingMortgagePage;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SecondaryHousingMortgageSteps extends BasePage {
	SecondaryHousingMortgagePage mortgagePage = pageManager.getPage(SecondaryHousingMortgagePage.class);

	@Step("Прокручиваем страницу до раздела \"{section}\"")
	public SecondaryHousingMortgageSteps scrollToSection(String section) {
		Assertions.assertNotNull(mortgagePage.getH2Header(section),
				String.format("Заголовка %s нет на странице", section));
		mortgagePage.scrollToElementJs(mortgagePage.getH2Header(section));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

//	public void waitPageLoadingComplete(int maxWaitMillis, int pollDelimiter) {
//		double startTime = System.currentTimeMillis();
//		while (System.currentTimeMillis() < startTime + maxWaitMillis) {
//			String prevState = webDriver.getPageSource();
//			Thread.sleep(pollDelimiter); // <-- would need to wrap in a try catch
//			if (prevState.equals(webDriver.getPageSource())) {
//				return;
//			}
//		}
//	}

	@Step("Заполняем поля значениями")
	public SecondaryHousingMortgageSteps fillInputFields(Map<String, Integer> input) {
		input.entrySet().forEach(i -> {
			WebElement inputField = mortgagePage.getInputField(i.getKey());
			Assertions.assertNotNull(inputField, "Поле для заполнения не найдено");
			mortgagePage.scrollToElementJsInFrame(mortgagePage.getFrameInput(), inputField);
			mortgagePage.fillInputField(inputField, String.valueOf(i.getValue()));
			assertThat("Введенное значение неверно",
					mortgagePage.getInputFieldValue(inputField), is(i.getValue()));
		});
		return this;
	}

	@Step("Убираем галочку \"{option}\"")
	public SecondaryHousingMortgageSteps setCheckbox(String option) {
		WebElement checkbox = mortgagePage.getCheckbox(option);
		mortgagePage.scrollToElementJsInFrame(mortgagePage.getFrameInput(), checkbox);
		Assertions.assertNotNull(checkbox, String.format("На странице нет чекбокса %s", option));
		mortgagePage.setCheckbox(checkbox);
		return this;
	}

	@Step("Проверяем правильность расчета условий по ипотеке")
	public SecondaryHousingMortgageSteps checkCalculationCorrectness(Map<String, String> result) {
		result.entrySet().forEach(i -> {
			WebElement resultField = mortgagePage.getResult(i.getKey());
			Assertions.assertNotNull(resultField, String.format("Поле %s не найдено", i.getKey()));
			mortgagePage.scrollToElementJsInFrame(mortgagePage.getFrameInput(), resultField);
			assertThat(String.format("Введенное значение %s неверно", i.getKey()),
					mortgagePage.getMainResultValue(resultField), is(i.getValue()));
		});
		return this;
	}

	@Step("Проверяем, что необходимый доход составляет {expectedResult}")
	public SecondaryHousingMortgageSteps checkRequiredIncome(String expectedResult) {
		assertThat("Значение необходимого дохода посчитано не верно",
				mortgagePage.getRequiredIncomeValue(), is(expectedResult));
		return this;
	}
}
