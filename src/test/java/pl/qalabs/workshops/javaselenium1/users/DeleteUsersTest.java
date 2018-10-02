package pl.qalabs.workshops.javaselenium1.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.qalabs.workshops.javaselenium1.support.AbstractTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DeleteUsersTest extends AbstractTest {
    private final By emptyStateLabelSelector = By.cssSelector(".md-empty-state .md-empty-state-label");
    private final By emptyStateDescriptionSelector = By.cssSelector(".md-empty-state .md-empty-state-description");
    private final By selectAllUsersCheckboxSelector = By.cssSelector("table > thead > tr > th.md-table-head.md-table-cell-selection .md-checkbox");
    private final By alternateTableHeaderLocator = By.cssSelector(".md-table-alternate-header");
    private final By selectedUsersTextSelector = By.cssSelector(".md-table-alternate-header .md-toolbar-section-start");
    private final By deleteUsersButtonSelector = By.cssSelector(".md-table-alternate-header .md-toolbar-section-end button");
    private final By tableRowsSelector = By.cssSelector("table tbody tr");

    private final BiFunction<Integer, Integer, By> tableDataByRowAndColumnSelector = (row, col) -> By.cssSelector("tr.md-table-row:nth-child({row}) > td:nth-child({col})"
        .replace("{row}", row + "")
        .replace("{col}", col + ""));

    private final Function<Integer, By> tableDataByColumnSelector = col -> By.cssSelector("td:nth-child({col})"
        .replace("{col}", col + ""));

    @Override
    protected String getUrl() {
        return "https://qalabs.gitlab.io/vuejs-material-demo/#/samples/sample2";
    }

    @Test
    @DisplayName("Deletes selected user")
    void deletesSelectedUser() {

        createUsersFromJsonResource("/users1.json");

        var usersCount = driver.findElements(tableRowsSelector).size();
        var userToBeDeleted = driver.findElement(tableDataByRowAndColumnSelector.apply(2, 2)).getText();

        // FIXME Application bug - first selection does not remove the element
        for (int i = 0; i < 3; i++) {
            click(tableDataByRowAndColumnSelector.apply(2, 1));
            if (i % 2 == 0) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(alternateTableHeaderLocator));
            }
        }

        click(deleteUsersButtonSelector);

        Optional<String> found = driver.findElements(tableDataByColumnSelector.apply(2))
                                       .stream()
                                       .map(WebElement::getText)
                                       .filter(name -> name.equals(userToBeDeleted))
                                       .findAny();

        var usersCountAfterDeletion = driver.findElements(tableRowsSelector).size();

        assertEquals(usersCount - 1, usersCountAfterDeletion);
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Removes all selected users")
    void deletesAllUsers() {
        createUsersFromJsonResource("/users2.json");

        click(selectAllUsersCheckboxSelector);
        var usersCount = driver.findElements(tableRowsSelector).size();
        assertEquals(usersCount + " users selected", driver.findElement(selectedUsersTextSelector).getText());

        click(deleteUsersButtonSelector);
        assertAll(
            () -> assertEquals("Create your first user", driver.findElement(emptyStateLabelSelector).getText()),
            () -> assertEquals("Fill in the form and save the user!", driver.findElement(emptyStateDescriptionSelector).getText())
        );
    }

    private void createUsersFromJsonResource(String resource) {
        var jsonString = readLinesFromResource(resource);
        ((JavascriptExecutor)driver).executeScript("localStorage.clear();localStorage.setItem('users', arguments[0]);", jsonString);
        driver.navigate().refresh();
    }

    private String readLinesFromResource(String resource) {
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resource)));
        return br.lines().collect(Collectors.joining("\n"));
    }

    private void click(By selector) {
        var element = driver.findElement(selector);
        element.click();
    }
}
