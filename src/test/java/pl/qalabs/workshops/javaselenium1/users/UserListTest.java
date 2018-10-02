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
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserListTest extends AbstractTest {

    private final By selectAllUsersCheckboxSelector = By.cssSelector("table > thead > tr > th.md-table-head.md-table-cell-selection .md-checkbox");
    private final By alternateTableHeaderLocator = By.cssSelector(".md-table-alternate-header");
    private final By selectedUsersTextSelector = By.cssSelector(".md-table-alternate-header .md-toolbar-section-start");
    private final By tableRowsSelector = By.cssSelector("table tbody tr");
    private final By usersSearchInputSelector = By.cssSelector("input[placeholder^='Search by name']");
    private final By clearSearchButtonSelector = By.cssSelector("button.md-clear");

    private final BiFunction<Integer, Integer, By> tableDataByRowAndColumnSelector = (row, col) -> By.cssSelector("tr.md-table-row:nth-child({row}) > td:nth-child({col})"
        .replace("{row}", row + "")
        .replace("{col}", col + ""));

    private final Function<Integer, By> tableDataByColumnSelector = col -> By.cssSelector("td:nth-child({col})"
        .replace("{col}", col + ""));

    private final Function<Integer, By> tableHeaderByColumnSelector = col -> By.cssSelector("th:nth-child({col})"
        .replace("{col}", col + ""));

    @Override
    protected String getUrl() {
        return "https://qalabs.gitlab.io/vuejs-material-demo/#/samples/sample2";
    }

    @Test
    @DisplayName("Searches for a user in the list and clears the search")
    void searchesUser() {
        createUsersFromJsonResource("/users1.json");

        var usersCount = driver.findElements(tableRowsSelector).size();
        var searchInput = driver.findElement(usersSearchInputSelector);

        searchInput.clear();
        searchInput.sendKeys("Logan");

        var filteredUsersCount = driver.findElements(tableRowsSelector).size();
        assertEquals(1, filteredUsersCount);

        int nameColumnIndex = 2;
        var name = driver.findElement(tableDataByColumnSelector.apply(nameColumnIndex)).getText();
        assertEquals("Weeks Logan", name);

        click(clearSearchButtonSelector);
        filteredUsersCount = driver.findElements(tableRowsSelector).size();

        assertEquals(usersCount, filteredUsersCount);
    }

    @Test
    @DisplayName("Sorts by name in natural and reverse order")
    void sortsByName() {
        createUsersFromJsonResource("/users3.json");

        int nameColumnIndex = 2;
        var nameColumn = driver.findElement(tableHeaderByColumnSelector.apply(nameColumnIndex));
        nameColumn.click();

        var className = nameColumn.getAttribute("className");
        assertTrue(className.contains("md-sorted"));

        var namesSorted = driver.findElements(tableDataByColumnSelector.apply(nameColumnIndex))
                                .stream()
                                .map(WebElement::getText)
                                .toArray(String[]::new);

        assertThat(namesSorted).isSortedAccordingTo(Comparator.naturalOrder());

        nameColumn.click();
        namesSorted = driver.findElements(tableDataByColumnSelector.apply(nameColumnIndex))
                            .stream()
                            .map(WebElement::getText)
                            .toArray(String[]::new);

        assertThat(namesSorted).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    @DisplayName("Sorts by email in natural and reverse orde")
    void sortsByEmail() {
        createUsersFromJsonResource("/users3.json");

        int emailColumnIndex = 3;
        var emailColumn = driver.findElement(tableHeaderByColumnSelector.apply(emailColumnIndex));
        emailColumn.click();

        var className = emailColumn.getAttribute("className");
        assertTrue(className.contains("md-sorted"));

        var emailsSorted = driver.findElements(tableDataByColumnSelector.apply(emailColumnIndex))
                                 .stream()
                                 .map(WebElement::getText)
                                 .toArray(String[]::new);

        assertThat(emailsSorted).isSortedAccordingTo(Comparator.reverseOrder());


        emailColumn.click();
        emailsSorted = driver.findElements(tableDataByColumnSelector.apply(emailColumnIndex))
                             .stream()
                             .map(WebElement::getText)
                             .toArray(String[]::new);

        assertThat(emailsSorted).isSortedAccordingTo(Comparator.naturalOrder());
    }

    @Test
    @DisplayName("Sorts by age in natural and reverse orde")
    void sortsByAge() {
        createUsersFromJsonResource("/users3.json");
        int ageColumnIndex = 4;
        var ageColumn = driver.findElement(tableHeaderByColumnSelector.apply(ageColumnIndex));
        ageColumn.click();

        var className = ageColumn.getAttribute("className");
        assertTrue(className.contains("md-sorted"));

        var ageSorted = driver.findElements(tableDataByColumnSelector.apply(ageColumnIndex))
                              .stream()
                              .map(WebElement::getText)
                              .toArray(String[]::new);

        assertThat(ageSorted).isSortedAccordingTo(Comparator.reverseOrder());


        ageColumn.click();
        ageSorted = driver.findElements(tableDataByColumnSelector.apply(ageColumnIndex))
                          .stream()
                          .map(WebElement::getText)
                          .toArray(String[]::new);

        assertThat(ageSorted).isSortedAccordingTo(Comparator.naturalOrder());
    }

    @Test
    @DisplayName("Selected users count is updated on selection")
    void selectedUsersCountIsUpdatedOnSelection() {
        createUsersFromJsonResource("/users3.json");

        click(selectAllUsersCheckboxSelector);
        var tableAlternateHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(alternateTableHeaderLocator));

        var usersCount = driver.findElements(tableRowsSelector).size();
        assertEquals(usersCount + " users selected", driver.findElement(selectedUsersTextSelector).getText());

        click(selectAllUsersCheckboxSelector);
        wait.until(ExpectedConditions.invisibilityOf(tableAlternateHeader));

        click(tableDataByRowAndColumnSelector.apply(1, 1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(alternateTableHeaderLocator));
        assertEquals("1 user selected", driver.findElement(selectedUsersTextSelector).getText());

        click(tableDataByRowAndColumnSelector.apply(2, 1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(alternateTableHeaderLocator));
        assertEquals("2 users selected", driver.findElement(selectedUsersTextSelector).getText());
    }

    private void createUsersFromJsonResource(String resource) {
        var jsonString = readLinesFromResource(resource);
        ((JavascriptExecutor) driver).executeScript("localStorage.clear();localStorage.setItem('users', arguments[0]);", jsonString);
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
