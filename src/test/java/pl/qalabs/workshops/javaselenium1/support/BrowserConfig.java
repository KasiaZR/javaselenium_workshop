package pl.qalabs.workshops.javaselenium1.support;

public class BrowserConfig {

    private String browserType;
    private int defaultWaitTimeout;
    private int implicitlyWait;
    private String screenshotsPath;

    public BrowserConfig(String browserType, int implicitlyWait, int defaultWaitTimeout, String screenshotsPath) {
        this.browserType = browserType;
        this.implicitlyWait = implicitlyWait;
        this.defaultWaitTimeout = defaultWaitTimeout;
        this.screenshotsPath = screenshotsPath;
    }

    public String getBrowserType() {
        return browserType;
    }

    public int getDefaultWaitTimeout() {
        return defaultWaitTimeout;
    }

    public int getImplicitlyWait() {
        return implicitlyWait;
    }

    public String getScreenshotsPath() {
        return screenshotsPath;
    }
}
