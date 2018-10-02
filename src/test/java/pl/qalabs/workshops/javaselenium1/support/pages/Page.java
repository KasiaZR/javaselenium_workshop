package pl.qalabs.workshops.javaselenium1.support.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.qalabs.workshops.javaselenium1.support.Browser;

public abstract class Page {

    private Browser browser;

    Page(Browser browser) {
        this.browser = browser;
    }

    public Browser browser() {
        return browser;
    }

    public WebDriver driver() {
        return browser.getDriver();
    }

    public WebDriverWait webDriverWait() {
        return browser.getWait();
    }
}
