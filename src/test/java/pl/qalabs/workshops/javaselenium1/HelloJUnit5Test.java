package pl.qalabs.workshops.javaselenium1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloJUnit5Test {

    HelloJUnit5 sut;

    @BeforeEach
    void beforeEach() {
        sut = new HelloJUnit5();
    }

    @Test
    void hello() {
        assertEquals("Hello, John Doe", sut.hello("John Doe"));
    }
}