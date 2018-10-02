package pl.qalabs.workshops.javaselenium1.users;

import org.junit.jupiter.api.Test;
import pl.qalabs.workshops.javaselenium1.support.AbstractTest;
import pl.qalabs.workshops.javaselenium1.support.Browser;
import pl.qalabs.workshops.javaselenium1.support.pages.CreateUserPage;
import pl.qalabs.workshops.javaselenium1.support.pages.UserListPage;

class CreateUserTest2 extends AbstractTest {

    CreateUserPage createUserPage;

    @Override
    protected String getUrl() {
        return "https://qalabs.gitlab.io/vuejs-material-demo/#/samples/sample2";
    }

    @Test
    void createsUser(Browser browser) {
        UserListPage userListPage = CreateUserPage
            .get(browser)
            .setFirstName("John")
            .setLastName("Doe")
            .setGender("M")
            .setAge("21")
            .setEmail("john.doe@example.com")
            .submitToUsersList();

        // TODO Finish the implementation

    }
}
