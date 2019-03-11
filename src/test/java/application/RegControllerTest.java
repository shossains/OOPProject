package application;

import org.junit.Assert;
import org.junit.Test;

public class RegControllerTest {

    @Test
    public void isLettersTrue() {
        boolean letterOnly = RegController.isLetters("test");
        boolean empty = RegController.isLetters("");

        Assert.assertTrue(letterOnly);
        Assert.assertTrue(empty);
    }

    @Test
    public void isLettersFalse() {
        boolean numbers = RegController.isLetters("test1323");
        boolean chars = RegController.isLetters("12/12/");

        Assert.assertFalse(numbers);
        Assert.assertFalse(chars);
    }

    @Test
    public void isIntTrue() {
        boolean numbersOnly = RegController.isInt("0636547093");

        Assert.assertTrue(numbersOnly);
    }

    @Test
    public void isIntFalse() {
        boolean letters = RegController.isLetters("numbers");
        boolean empty = RegController.isLetters("");

        Assert.assertTrue(letters);
        Assert.assertTrue(empty);
    }
}
