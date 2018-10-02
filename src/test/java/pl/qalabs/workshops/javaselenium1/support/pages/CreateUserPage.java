package pl.qalabs.workshops.javaselenium1.support.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.qalabs.workshops.javaselenium1.support.Browser;

import java.util.List;

public class CreateUserPage extends Page {

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(xpath = "//select[@id='gender']/..")
    private WebElement genderSelect;

    @FindBy(css = "ul.md-list li:nth-child(1)")
    private WebElement maleOption;

    @FindBy(css = "ul.md-list li:nth-child(2)")
    private WebElement femaleOption;

    @FindBy(id = "age")
    private WebElement ageInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "button[type=submit]")
    private WebElement submitButton;

    private final By progressBarSelector = By.cssSelector(".md-progress-bar");

    private CreateUserPage(Browser browser) {
        super(browser);
    }

    public static CreateUserPage get(Browser browser) {
        CreateUserPage page = new CreateUserPage(browser);
        PageFactory.initElements(browser.getDriver(), page);
        return page;
    }

    public CreateUserPage setFirstName(String firstName) {
        clearAndType(firstNameInput, firstName);
        return this;
    }

    public CreateUserPage setLastName(String lastName) {
        clearAndType(lastNameInput, lastName);
        return this;
    }

    public CreateUserPage setGender(String gender) {
        genderSelect.click();
        if (gender.equals("M")) {
            maleOption.click();
        } else {
            femaleOption.click();
        }
        return this;
    }

    public CreateUserPage setAge(String age) {
        clearAndType(ageInput, age);
        return this;
    }

    public CreateUserPage setEmail(String email) {
        clearAndType(emailInput, email);
        return this;
    }

    public CreateUserPage submit() {
        submitButton.click();
        return this;
    }

    public boolean hasError(String error) {
        return true;
    }

    public List<String> getErrors() {
        return null;
    }

    public UserListPage submitToUsersList() {
        submit();
        var progressBar = webDriverWait().until(ExpectedConditions.visibilityOfElementLocated(progressBarSelector));
        webDriverWait().until(ExpectedConditions.invisibilityOf(progressBar));

        UserListPage userListPage = new UserListPage(browser());
        PageFactory.initElements(driver(), userListPage);
        return userListPage;
    }

    private void clearAndType(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }
}
