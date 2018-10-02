package pl.qalabs.workshops.javaselenium1.input_and_dialog;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.qalabs.workshops.javaselenium1.support.AbstractTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@DisplayName("My first Selenium Test 😀")
class InputAndDialogSampleTest extends AbstractTest {

    private final By cardSelector = By.cssSelector(".md-card");
    private final By titleSelector = By.cssSelector(".md-title");
    private final By inputSelector = By.cssSelector(".md-field input[type=text]");
    private final By checkboxSelector = By.cssSelector(".md-checkbox input[type=checkbox]");
    private final By checkboxLabelSelector = By.cssSelector(".md-checkbox-label");
    private final By buttonSelector = By.cssSelector(".md-button");
    private final By dialogSelector = By.cssSelector(".md-dialog");

    @Override
    protected String getUrl() {
        return "https://qalabs.gitlab.io/vuejs-material-demo/#/samples/sample1";
    }

    @Test
    void elementsExistOnAPage() {

        var card = driver.findElement(cardSelector);
        var cardTitle = card.findElement(titleSelector);
        var textInput = card.findElement(inputSelector);
        var checkbox = card.findElement(checkboxSelector);
        var checkboxLabel = card.findElement(checkboxLabelSelector);
        var button = card.findElement(buttonSelector);

        assertAll(
            () -> assertEquals("Input and Dialog", cardTitle.getText()),
            () -> assertTrue(textInput.isDisplayed() && textInput.isEnabled()),
            () -> assertTrue(checkbox.isEnabled()),
            () -> assertEquals("Show using window.alert", checkboxLabel.getText()),
            () -> assertTrue(button.isEnabled() && button.isDisplayed())
        );
    }

    @Test
    @DisplayName("Shows entered input value in dialog, closes the dialog")
    void showsInputValueInDialog() {

        var card = driver.findElement(cardSelector);
        var textInput = card.findElement(inputSelector);
        var button = card.findElement(buttonSelector);

        clearAndType(textInput, "Jane Doe");
        click(button);

        var dialog = wait.until(visibilityOfElementLocated(dialogSelector));

        assertAll(
            () -> assertEquals("Alert!", dialog.findElement(By.cssSelector("span.md-title")).getText()),
            () -> assertEquals("Hello, Jane Doe", dialog.findElement(By.cssSelector("div.md-dialog-content")).getText())
        );

        dialog.findElement(buttonSelector).click();
        wait.until(invisibilityOf(dialog));
    }

    @Test
    @DisplayName("Shows entered input value in alert, closes the alert")
    void showsInputValueInAlert() {
        var card = driver.findElement(cardSelector);
        var textInput = card.findElement(inputSelector);
        var button = card.findElement(buttonSelector);
        var checkboxLabel = card.findElement(checkboxLabelSelector);

        clearAndType(textInput, "Jane Doe");
        click(checkboxLabel);
        click(button);

        var alert = wait.until(alertIsPresent());

        // alternatively
        // var alert = driver.switchTo().alert();

        assertEquals("Hello, Jane Doe", alert.getText());
    }

    @Test
    @DisplayName("Shows entered input value in dialog, closes the dialog with ESC key")
    void dialogClosesWithEscKey() {
        var card = driver.findElement(cardSelector);
        var textInput = card.findElement(inputSelector);
        var button = card.findElement(buttonSelector);

        clearAndType(textInput, "Jane Doe");
        click(button);

        var dialog = wait.until(visibilityOfElementLocated(dialogSelector));

        new Actions(driver)
            .sendKeys(Keys.ESCAPE)
            .perform();

        wait.until(invisibilityOf(dialog));
    }

    private void clearAndType(WebElement input, String text) {
        input.clear();
        input.sendKeys(text);
    }

    private void click(WebElement element) {
        element.click();
    }
}

