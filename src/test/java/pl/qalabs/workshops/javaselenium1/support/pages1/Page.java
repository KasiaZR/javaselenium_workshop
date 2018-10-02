package pl.qalabs.workshops.javaselenium1.support.pages1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.qalabs.workshops.javaselenium1.support.Browser;

public abstract class Page {

    protected final Browser browser;

    protected Page(Browser browser) {
        this.browser = browser;
    }

    protected void clearAndType(By by, String value) {
        WebElement element = browser.getDriver().findElement(by);
        element.clear();
        element.sendKeys(value);
    }

    protected void click(By by) {
        browser.getDriver().findElement(by).click();
    }
}
