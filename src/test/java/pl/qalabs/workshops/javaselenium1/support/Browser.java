package pl.qalabs.workshops.javaselenium1.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Browser {

    private WebDriver driver;
    private final BrowserConfig browserConfig;

    Browser(BrowserConfig browserConfig) {
        this.browserConfig = browserConfig;
    }

    public WebDriver getDriver() {
        if (driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {
            driver = createDriver(getBrowserConfig().getBrowserType());
            driver.manage().timeouts().implicitlyWait(getBrowserConfig().getImplicitlyWait(), TimeUnit.MILLISECONDS);
        }
        return driver;
    }

    private WebDriver createDriver(String type) {
        switch (type) {
            case "firefox":
                return new FirefoxDriver();
            case "chrome":
                return new ChromeDriver();
            default:
                throw new RuntimeException("No suitable driver found for type " + type);
        }
    }

    public WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), getBrowserConfig().getDefaultWaitTimeout());
    }

    public BrowserConfig getBrowserConfig() {
        return browserConfig;
    }
}
