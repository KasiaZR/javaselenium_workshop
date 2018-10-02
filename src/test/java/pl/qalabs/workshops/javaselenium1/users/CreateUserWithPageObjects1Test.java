package pl.qalabs.workshops.javaselenium1.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.qalabs.workshops.javaselenium1.support.AbstractTest;
import pl.qalabs.workshops.javaselenium1.support.UserFile;
import pl.qalabs.workshops.javaselenium1.support.UserParameterResolver;
import pl.qalabs.workshops.javaselenium1.support.data.User;
import pl.qalabs.workshops.javaselenium1.support.data.UserAggregator;
import pl.qalabs.workshops.javaselenium1.support.pages1.CreateUserForm;
import pl.qalabs.workshops.javaselenium1.support.pages1.UserList;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UserParameterResolver.class)
class CreateUserWithPageObjects1Test extends AbstractTest {


    @Override
    protected String getUrl() {
        return "https://qalabs.gitlab.io/vuejs-material-demo/#/samples/sample2";
    }

    @Test
    @DisplayName("Creates user and verifies record is on the list")
    void createsUser(@UserFile(resource = "/user1.json") User user) {

        UserList userList = new CreateUserForm(browser)
            .setFirstName(user.getFirstName())
            .setLastName(user.getLastName())
            .setAge(user.getAgeAsString())
            .setGender(user.getGender())
            .setEmail(user.getEmail())
            .submitToUsersList();

        int userCount = userList.getCount();
        String[] names = userList.getNames();
        String[] emails = userList.getEmails();

        assertAll(
            () -> assertEquals(1, userCount),
            () -> assertTrue(Arrays.asList(names).contains(user.getFullName())),
            () -> assertTrue(Arrays.asList(emails).contains(user.getEmail()))
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
        String[] actualErrors = new CreateUserForm(browser)
            .submitAndGetErrors();
        assertArrayEquals(expectedErrors, actualErrors);
    }

    @Test
    @DisplayName("Creating user with invalid data results in validation errors")
    void validatesErrorMessages() {

        String expectedErrors[] = {
            "Invalid first name",
            "Invalid last name",
            "The gender is required",
            "Invalid age",
            "Invalid email"
        };


        String[] actualErrors = new CreateUserForm(browser)
            .setFirstName("J")
            .setLastName("D")
            .setAge("12")
            .setEmail("john.doe(at).com")
            .submitAndGetErrors();

        assertArrayEquals(expectedErrors, actualErrors);
    }


}
