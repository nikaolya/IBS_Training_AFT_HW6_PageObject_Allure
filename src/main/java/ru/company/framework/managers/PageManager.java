package ru.company.framework.managers;

import ru.company.framework.pages.BasePage;

import java.util.HashMap;
import java.util.Map;

public class PageManager {
	private static PageManager pageManager = null;
	private Map<String, BasePage> mapPages = new HashMap<>();

	private PageManager() {
	}

	public static PageManager getPageManager() {
		if (pageManager == null) {
			pageManager = new PageManager();
		}
		return pageManager;
	}

	public <T extends BasePage> T getPage(Class<T> ex) {
		if (mapPages.isEmpty() || mapPages.get(ex.getName()) == null) {
			try {
				mapPages.put(ex.getName(), ex.newInstance());
			} catch (IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}
		}
		return (T) mapPages.get(ex.getName());
	}

	public void clearMapPages() {
		mapPages.clear();
	}
}
