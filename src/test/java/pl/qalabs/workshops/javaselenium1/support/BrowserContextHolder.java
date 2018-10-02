package pl.qalabs.workshops.javaselenium1.support;

public class BrowserContextHolder {

    private static Browser BROWSER_INSTANCE;

    public static void setBrowser(Browser browser) {
        BROWSER_INSTANCE = browser;
    }

    public static Browser getBrowser() {
        return BROWSER_INSTANCE;
    }
}
