package pl.qalabs.workshops.javaselenium1.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.qalabs.workshops.javaselenium1.support.AbstractTest;
import pl.qalabs.workshops.javaselenium1.support.data.User;
import pl.qalabs.workshops.javaselenium1.support.data.UserAggregator;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserTest extends AbstractTest {

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

    // parameterized selector as Lambda
    private final BiFunction<Integer, Integer, By> tableDataByRowAndColumnSelector = (row, col) -> By.cssSelector("tr.md-table-row:nth-child({row}) > td:nth-child({col})"
        .replace("{row}", row + "")
        .replace("{col}", col + ""));


    @Override
    protected String getUrl() {
        return "https://qalabs.gitlab.io/vuejs-material-demo/#/samples/sample2";
    }


    @Test
    @DisplayName("Creates user and verifies record is on the list")
    void createsUser() {

        clearAndType(firstNameInputSelector, "John");
        clearAndType(lastNameInputSelector, "Doe");
        clearAndType(ageInputSelector, "42");
        clearAndType(emailInputSelector, "john.doe@example.com");
        click(genderSelectSelector);
        click(maleListItemSelector);
        click(createUserButtonSelector);

        var progressBar = wait.until(ExpectedConditions.visibilityOfElementLocated(progressBarSelector));
        wait.until(ExpectedConditions.invisibilityOf(progressBar));

        assertAll(
            () -> assertEquals("John Doe", driver.findElement(tableDataByRowAndColumnSelector.apply(1, 2)).getText()),
            () -> assertEquals("john.doe@example.com", driver.findElement(tableDataByRowAndColumnSelector.apply(1, 3)).getText()),
            () -> assertEquals("M", driver.findElement(tableDataByRowAndColumnSelector.apply(1, 4)).getText()),
            () -> assertEquals("42", driver.findElement(tableDataByRowAndColumnSelector.apply(1, 5)).getText())
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/users1.csv", delimiter = ';', numLinesToSkip = 1)
    void createsUserParameterized1(String firstName, String lastName, String gender, int age, String email) {
        clearAndType(firstNameInputSelector, firstName);
        clearAndType(lastNameInputSelector, lastName);
        clearAndType(ageInputSelector, String.valueOf(age));
        clearAndType(emailInputSelector, email);
        click(genderSelectSelector);
        click(gender.equals("M") ? maleListItemSelector : femaleListItemSelector);
        click(createUserButtonSelector);

        var progressBar = wait.until(ExpectedConditions.visibilityOfElementLocated(progressBarSelector));
        wait.until(ExpectedConditions.invisibilityOf(progressBar));

        assertAll(
            () -> assertEquals(firstName + " " + lastName, driver.findElement(tableDataByRowAndColumnSelector.apply(1, 2)).getText()),
            () -> assertEquals(email, driver.findElement(tableDataByRowAndColumnSelector.apply(1, 3)).getText()),
            () -> assertEquals(gender, driver.findElement(tableDataByRowAndColumnSelector.apply(1, 4)).getText()),
            () -> assertEquals(String.valueOf(age), driver.findElement(tableDataByRowAndColumnSelector.apply(1, 5)).getText())
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/users1.csv", delimiter = ';', numLinesToSkip = 1)
    void createsUserParameterized2(@AggregateWith(UserAggregator.class) User user) {
        clearAndType(firstNameInputSelector, user.getFirstName());
        clearAndType(lastNameInputSelector, user.getLastName());
        clearAndType(ageInputSelector, user.getAgeAsString());
        clearAndType(emailInputSelector, user.getEmail());
        click(genderSelectSelector);
        click(user.isMale() ? maleListItemSelector : femaleListItemSelector);
        click(createUserButtonSelector);

        var progressBar = wait.until(ExpectedConditions.visibilityOfElementLocated(progressBarSelector));
        wait.until(ExpectedConditions.invisibilityOf(progressBar));

        assertAll(
            () -> assertEquals(user.getFullName(), driver.findElement(tableDataByRowAndColumnSelector.apply(1, 2)).getText()),
            () -> assertEquals(user.getEmail(), driver.findElement(tableDataByRowAndColumnSelector.apply(1, 3)).getText()),
            () -> assertEquals(user.getGender(), driver.findElement(tableDataByRowAndColumnSelector.apply(1, 4)).getText()),
            () -> assertEquals(user.getAgeAsString(), driver.findElement(tableDataByRowAndColumnSelector.apply(1, 5)).getText())
        );
    }

    @Test
    @DisplayName("Creating user with no data results in validation errors")
    void validatesRequiredFields() {

        String expectedErrors[] = {
            "The first name is required",
            "The last name is required",
            "The gender is required",
            "The age is required",
            "The email is required"
        };

        click(createUserButtonSelector);

        var mdErrors = driver.findElements(formErrorSelector);
        var actualErrors = mdErrors.stream().map(WebElement::getText).toArray();

        assertAll(
            () -> assertEquals(5, mdErrors.size()),
            () -> assertArrayEquals(expectedErrors, actualErrors)
        );

    }

    @Test
    @DisplayName("Creating user with invalid data results in validation errors")
    void validatesErrorMessages() {
        clearAndType(firstNameInputSelector, "J");
        clearAndType(lastNameInputSelector, "D");
        clearAndType(ageInputSelector, "11");
        clearAndType(emailInputSelector, "john.doe(at).com");
        click(createUserButtonSelector);

        String expectedErrors[] = {
            "Invalid first name",
            "Invalid last name",
            "The gender is required",
            "Invalid age",
            "Invalid email"
        };

        var mdErrors = driver.findElements(formErrorSelector);
        var actualErrors = mdErrors.stream().map(WebElement::getText).toArray();

        assertAll(
            () -> assertEquals(5, mdErrors.size()),
            () -> assertArrayEquals(expectedErrors, actualErrors)
        );
    }

    @Test
    @DisplayName("Two users with exactly the same data may be created")
    void createsDuplicateUsers() {
        for (int i = 1; i <= 10; i++) {
            clearAndType(firstNameInputSelector, "Jane");
            clearAndType(lastNameInputSelector, "Doe");
            clearAndType(ageInputSelector, "21");
            clearAndType(emailInputSelector, "jane.doe@example.com");
            click(genderSelectSelector);
            click(femaleListItemSelector);
            click(createUserButtonSelector);

            var progressBar = wait.until(ExpectedConditions.visibilityOfElementLocated(progressBarSelector));
            wait.until(ExpectedConditions.invisibilityOf(progressBar));

            int row = i;
            assertAll(
                () -> assertEquals("Jane Doe", driver.findElement(tableDataByRowAndColumnSelector.apply(row, 2)).getText()),
                () -> assertEquals("jane.doe@example.com", driver.findElement(tableDataByRowAndColumnSelector.apply(row, 3)).getText()),
                () -> assertEquals("F", driver.findElement(tableDataByRowAndColumnSelector.apply(row, 4)).getText()),
                () -> assertEquals("21", driver.findElement(tableDataByRowAndColumnSelector.apply(row, 5)).getText())
            );
        }

    }

    private void clearAndType(By selector, String value) {
        var age = driver.findElement(selector);
        age.clear();
        age.sendKeys(value);
    }

    private void click(By selector) {
        driver.findElement(selector).click();
    }
}
