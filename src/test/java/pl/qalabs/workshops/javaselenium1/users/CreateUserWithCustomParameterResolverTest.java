package pl.qalabs.workshops.javaselenium1.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.qalabs.workshops.javaselenium1.support.AbstractTest;
import pl.qalabs.workshops.javaselenium1.support.UserFile;
import pl.qalabs.workshops.javaselenium1.support.UserParameterResolver;
import pl.qalabs.workshops.javaselenium1.support.data.User;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(UserParameterResolver.class)
class CreateUserWithCustomParameterResolverTest extends AbstractTest {

    private final By ageInputSelector = By.id("age");
    private final By emailInputSelector = By.id("email");
    private final By createUserButtonSelector = By.cssSelector("button[type=submit]");
    private final By progressBarSelector = By.cssSelector(".md-progress-bar");

    private final By firstNameInputSelector = By.id("first-name");
    private final By lastNameInputSelector = By.id("last-name");
    private final By genderSelectSelector = By.xpath("//select[@id='gender']/..");
    private final By femaleListItemSelector = By.cssSelector("ul.md-list li:nth-child(2)");
    private final By maleListItemSelector = By.cssSelector("ul.md-list li:nth-child(1)");

    // parameterized selector as Lambda
    private final BiFunction<Integer, Integer, By> tableDataByRowAndColumnSelector = (row, col) -> By.cssSelector("tr.md-table-row:nth-child({row}) > td:nth-child({col})"
        .replace("{row}", row + "")
        .replace("{col}", col + ""));

    @Override
    protected String getUrl() {
        return "https://qalabs.gitlab.io/vuejs-material-demo/#/samples/sample2";
    }


    @Test
    void createsUser(@UserFile(resource = "/user1.json") User user) {
        createUser(user, 1);
    }

    @Test
    void createsTwoUsers(@UserFile(resource = "/user1.json") User user1, @UserFile(resource = "/user2.json") User user2) {
        AtomicInteger counter = new AtomicInteger(1);
        Stream.of(user1, user2).forEach(user -> {
            createUser(user, counter.getAndIncrement());
        });
    }

    private void createUser(User user, int num) {
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
            () -> assertEquals(user.getFullName(), driver.findElement(tableDataByRowAndColumnSelector.apply(num, 2)).getText()),
            () -> assertEquals(user.getEmail(), driver.findElement(tableDataByRowAndColumnSelector.apply(num, 3)).getText()),
            () -> assertEquals(user.getGender(), driver.findElement(tableDataByRowAndColumnSelector.apply(num, 4)).getText()),
            () -> assertEquals(user.getAgeAsString(), driver.findElement(tableDataByRowAndColumnSelector.apply(num, 5)).getText())
        );
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
