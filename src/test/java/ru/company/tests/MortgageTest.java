package ru.company.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.company.framework.listeners.MyTestWatcher;
import ru.company.framework.managers.DriverManager;
import ru.company.framework.managers.PropManager;
import ru.company.framework.utils.ConstProp;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@ExtendWith(MyTestWatcher.class)
@Epic("Ипотека")
@Feature("Ипотечный калькулятор")
@DisplayName("Ипотечный калькулятор")
public class MortgageTest extends BaseTest {

	@BeforeEach
	public void setup() {
		driverManager.getDriver().get(PropManager.getPropManager().getProperty(ConstProp.BASE_URL));
		steps = pageManager.getSteps();
	}

	@AfterEach
	public void tearDown(){
		DriverManager.getDriverManager().quitDriver();
	}

	@Test
	@Story("Ипотека на вторичное жилье")
	@DisplayName("Проверка правильности расчета условий по ипотеке")
	@Severity(SeverityLevel.NORMAL)
	public void test() {
		Map<String, Integer> inputData = null;
		Map<String, String> expectedResult = null;
		try {
			inputData = mapper.readValue(new File("src/test/resources/inputData.json"), Map.class);
			expectedResult = mapper.readValue(new File("src/test/resources/expectedResult.json"), Map.class);
		} catch (IOException e) {
			e.printStackTrace();
			Assertions.assertNotNull(inputData, "Файл \"inputData.json\" не считан");
			Assertions.assertNotNull(expectedResult, "Файл \"expectedResult.json\" не считан");
		}

		steps.openMenu("Ипотека")
				.openSubMenu("Ипотека на вторичное жильё")
				.scrollToSection("Рассчитайте ипотеку")
				.fillInputFields(inputData)
				.setCheckbox("Страхование жизни и здоровья")
				.scrollToSection("Рассчитайте ипотеку")
				.checkCalculationCorrectness(expectedResult)
				.checkRequiredIncome("35449");
	}
}
