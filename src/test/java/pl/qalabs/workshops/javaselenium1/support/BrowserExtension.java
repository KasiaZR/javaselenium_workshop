package pl.qalabs.workshops.javaselenium1.support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class BrowserExtension implements ParameterResolver {

    private Browser browser = null;

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Browser.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (browser == null) {
            var browserType = extensionContext.getConfigurationParameter("browser-type").orElse("firefox");
            var implicitlyWait = extensionContext.getConfigurationParameter("implicitly-wait").orElse("500");
            var defaultWaitTimeout = extensionContext.getConfigurationParameter("default-wait-timeout").orElse("5");
            var screenshotsPath = extensionContext.getConfigurationParameter("screenshots-path").orElse("c:/tmp/");
            var browserConfig = new BrowserConfig(browserType, Integer.valueOf(implicitlyWait), Integer.valueOf(defaultWaitTimeout), screenshotsPath);
            browser = new Browser(browserConfig);
        }
        // extensionContext.getStore(ExtensionContext.Namespace.create(getClass())).put("browser", browser);
        BrowserContextHolder.setBrowser(browser);
        return browser;
    }
}
