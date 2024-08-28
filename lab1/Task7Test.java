package lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Task7Test {
    @Test
    void Should_ReturnEmptyString_When_WordsLengthIsOne() {
        assertEquals("dd", Task7.deleteSinglesAndSpaces("      dd     i    m a     "));
    }

    @Test
    void Should_ReturnSymbols_When_NoLetters() {
        assertEquals(". : ^ [ ] { } ( ) * & ? ^ % $ # @ ! | / \\", Task7.deleteSinglesAndSpaces(" . : a ^ [ ] { } ( ) * & ? ^ % $ # @ ! | / \\ "));
    }

    @Test
    void Should_ReturnSameString_When_WordsLengthMoreThanTwo() {
        assertEquals("Standing by my window, breathing summer breeze",
                Task7.deleteSinglesAndSpaces(" Standing by my window, breathing summer breeze"));
        assertEquals("Took us by the hands and up we go",
                Task7.deleteSinglesAndSpaces(" Took us by the hands and up we go "));
    }

    @Test
    void Should_ThrowEmptyStringException_When_StringIsEmpty() throws EmptyStringException {
        EmptyStringException exception = assertThrows(EmptyStringException.class, () -> {
            Task7.deleteSinglesAndSpaces("");
        });
        assertTrue(exception.getMessage().contains("The source line should not be empty"));
    }
    @Test
    void Should_ThrowEmptyStringException_When_StringOfSpaces() throws EmptyStringException {
        EmptyStringException exception = assertThrows(EmptyStringException.class, () -> {
            Task7.deleteSinglesAndSpaces("       ");
        });
        assertTrue(exception.getMessage().contains("The source line should not be empty"));
    }

}