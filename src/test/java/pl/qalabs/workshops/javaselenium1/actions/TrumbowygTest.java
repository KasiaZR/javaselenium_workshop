package pl.qalabs.workshops.javaselenium1.actions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pl.qalabs.workshops.javaselenium1.support.AbstractTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrumbowygTest extends AbstractTest {

    @Override
    protected String getUrl() {
        return "https://alex-d.github.io/Trumbowyg/";
    }

    @Test
    @DisplayName("Clears editor, creates text and marks it as bold")
    @DisabledOnOs(OS.MAC)
    void createsBoldText() {
        WebElement input = driver.findElement(By.cssSelector(".trumbowyg-editor"));

        new Actions(driver)
            .click(input)
            .keyDown(Keys.CONTROL)
            .sendKeys("A")
            .keyUp(Keys.CONTROL)
            .sendKeys(Keys.BACK_SPACE)
            .perform();

        new Actions(driver)
            .click(input)
            .sendKeys("Bold demo!")
            .keyDown(Keys.CONTROL)
            .sendKeys("A")
            .keyUp(Keys.CONTROL)
            .keyDown(Keys.CONTROL)
            .sendKeys("B")
            .keyUp(Keys.CONTROL)
            .perform();

        WebElement textArea = driver.findElement(By.tagName("textarea"));
        var editorValue = textArea.getAttribute("value");

        assertEquals("<p><strong>Bold demo!<br></strong></p>", editorValue);
    }

    @Test
    @DisplayName("Clears editor, creates text and marks it as bold")
    @EnabledOnOs(OS.MAC)
    void createsBoldTextOnMac() {
        WebElement input = driver.findElement(By.cssSelector(".trumbowyg-editor"));

        new Actions(driver)
            .click(input)
            .keyDown(Keys.COMMAND)
            .sendKeys("A")
            .keyUp(Keys.COMMAND)
            .sendKeys(Keys.BACK_SPACE)
            .perform();

        new Actions(driver)
            .click(input)
            .sendKeys("Bold demo!")
            .keyDown(Keys.COMMAND)
            .sendKeys("A")
            .keyUp(Keys.COMMAND)
            .keyDown(Keys.COMMAND)
            .sendKeys("B")
            .keyUp(Keys.COMMAND)
            .perform();

        WebElement textArea = driver.findElement(By.tagName("textarea"));
        var editorValue = textArea.getAttribute("value");

        assertEquals("<p><strong>Bold demo!<br></strong></p>", editorValue);
    }
}
