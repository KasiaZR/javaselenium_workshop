package pl.qalabs.workshops.javaselenium1.file_upload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.qalabs.workshops.javaselenium1.support.AbstractTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("File upload")
class FileUploadSampleTest extends AbstractTest {

    private final By fileInputSelector = By.cssSelector("input[type=file]");

    @Override
    protected String getUrl() {
        return "https://qalabs.gitlab.io/vuejs-material-demo/#/samples/sample3";
    }

    @Test
    void uploadsFile() {

        var fileInput = driver.findElement(fileInputSelector);
        var file = new File(getClass().getResource("/images/cat.png").getFile());

        type(fileInput, file.getAbsolutePath());

        var preview = driver.findElement(By.id("preview"));
        var image = preview.findElement(By.tagName("img"));
        var span = preview.findElement(By.tagName("span"));

        assertAll(
            () -> assertNotNull(image.getAttribute("src")),
            () -> assertTrue(span.getText().contains("cat.png"))
        );
    }

    private void type(WebElement input, String text) {
        input.sendKeys(text);
    }
}

