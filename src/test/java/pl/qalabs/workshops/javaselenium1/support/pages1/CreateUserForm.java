package pl.qalabs.workshops.javaselenium1.support.pages1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.qalabs.workshops.javaselenium1.support.Browser;

import java.util.function.BiFunction;

public class CreateUserForm extends Page {

    private final By ageInputSelector = By.id("age");
    private final By emailInputSelector = By.id("email");
    private final By createUserButtonSelector = By.cssSelector("button[type=submit]");
    private final By progressBarSelector = By.cssSelector(".md-progress-bar");

    private final By firstNameInputSelector = By.id("first-name");
    private final By lastNameInputSelector = By.id("last-name");
    private final By genderSelectSelector = By.xpath("//select[@id='gender']/..");
    private final By femaleListItemSelector = By.cssSelector("ul.md-list li:nth-child(2)");
    private final By maleListItemSelector = By.cssSelector("ul.md-list li:nth-child(1)");

    private final By formErrorSelector = By.cssSelector("form .md-error");

    public CreateUserForm(Browser browser) {
        super(browser);
    }

    public CreateUserForm setFirstName(String firstName) {
        clearAndType(firstNameInputSelector, firstName);
        return this;
    }

    public CreateUserForm setLastName(String lastName) {
        clearAndType(lastNameInputSelector, lastName);
        return this;
    }

    public CreateUserForm setGender(String gender) {
        click(genderSelectSelector);
        if (gender.equals("M")) {
            click(maleListItemSelector);
        } else {
            click(femaleListItemSelector);
        }
        return this;
    }

    public CreateUserForm setAge(String age) {
        clearAndType(ageInputSelector, age);
        return this;
    }

    public CreateUserForm setEmail(String email) {
        clearAndType(emailInputSelector, email);
        return this;
    }

    public CreateUserForm submit() {
        click(createUserButtonSelector);
        return this;
    }

    public boolean hasError(String error) {
        return browser.getDriver().findElements(formErrorSelector).size() > 0;
    }

    public String[] getErrors() {
        return browser.getDriver().findElements(formErrorSelector)
                      .stream()
                      .map(WebElement::getText)
                      .toArray(String[]::new);
    }

    public String[] submitAndGetErrors() {
        submit();
        return getErrors();
    }

    public UserList submitToUsersList() {
        submit();
        var progressBar = browser.getWait().until(ExpectedConditions.visibilityOfElementLocated(progressBarSelector));
        browser.getWait().until(ExpectedConditions.invisibilityOf(progressBar));
        return new UserList(browser);
    }
}
