package eu.drone.drones;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

public class CustomAssertions {

    private CustomAssertions() {
    }

    public static void assertExceptionThrownWithMessage(
            Class<? extends Throwable> expectedException, Executable codeForRun, String expectedMessage) {

        var ex = Assertions.assertThrows(expectedException, codeForRun, expectedMessage);
        Assertions.assertEquals(expectedMessage, ex.getMessage());
    }

}
