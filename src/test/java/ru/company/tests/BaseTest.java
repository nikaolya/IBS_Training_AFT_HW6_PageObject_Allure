package ru.company.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.company.framework.managers.DriverManager;
import ru.company.framework.managers.PageManager;
import ru.company.framework.stepDefinitions.StartPageSteps;

public class BaseTest {
	protected PageManager pageManager = PageManager.getPageManager();
	protected DriverManager driverManager = DriverManager.getDriverManager();
	protected StartPageSteps steps = null;
	ObjectMapper mapper = new ObjectMapper();
}
