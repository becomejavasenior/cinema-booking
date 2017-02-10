package ua.cinemabooking.exeptions;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by macbookair on 07.12.16.
 */
public interface ExceptionAttributes {

    Map<String, Object> getExceptionsAttributes(Exception exception, HttpServletRequest request, HttpStatus status);
}
