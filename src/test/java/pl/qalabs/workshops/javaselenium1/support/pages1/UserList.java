package pl.qalabs.workshops.javaselenium1.support.pages1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.qalabs.workshops.javaselenium1.support.Browser;

import java.util.function.BiFunction;
import java.util.function.Function;

public class UserList extends Page {

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

    public UserList(Browser browser) {
        super(browser);
    }

    public int getCount() {
        return browser.getDriver().findElements(tableRowsSelector).size();
    }

    public String[] getColumnData(int column) {
        return browser.getDriver().findElements(tableDataByColumnSelector.apply(column))
                      .stream()
                      .map(WebElement::getText)
                      .toArray(String[]::new);
    }

    public String[] getNames() {
        return getColumnData(2);
    }

    public String[] getEmails() {
        return getColumnData(3);
    }

    // TODO More methods to be added

}
