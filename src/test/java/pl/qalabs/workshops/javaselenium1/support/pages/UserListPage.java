package pl.qalabs.workshops.javaselenium1.support.pages;

import org.openqa.selenium.support.PageFactory;
import pl.qalabs.workshops.javaselenium1.support.Browser;

public class UserListPage extends Page {

    public UserListPage(Browser browser) {
        super(browser);
    }

    public static UserListPage get(Browser browser) {
        UserListPage page = new UserListPage(browser);
        PageFactory.initElements(browser.getDriver(), page);
        return page;
    }

    // TODO Finish the implementation
}
