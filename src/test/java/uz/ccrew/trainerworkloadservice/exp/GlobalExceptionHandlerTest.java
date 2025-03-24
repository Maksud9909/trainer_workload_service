package uz.ccrew.trainerworkloadservice.exp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.ccrew.trainerworkloadservice.dto.error.ErrorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleObjectNotProvidedException_ShouldReturnBadRequest() {
        ObjectNotProvidedException exception = new ObjectNotProvidedException("Object is missing");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleObjectNotProvided(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad request", response.getBody().getMessage());
    }

    @Test
    void handleGeneralException_ShouldReturnInternalServerError() {
        Exception exception = new Exception("Something went wrong");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", response.getBody().getMessage());
    }
}
