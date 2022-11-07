package ru.company.framework.listeners;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.company.framework.managers.DriverManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MyTestWatcher implements AfterTestExecutionCallback {

	public InputStream takeScreenshot() {
		byte[] screenshot = ((TakesScreenshot) DriverManager.getDriverManager().getDriver()).getScreenshotAs(OutputType.BYTES);
		return new ByteArrayInputStream(screenshot);
	}

	@Override
	public void afterTestExecution(ExtensionContext extensionContext){
		if (extensionContext.getExecutionException().isPresent()) {
			Allure.addAttachment("screenshot", "image/png", takeScreenshot(), "png");
		}
	}
}
