package ua.cinemabooking.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.cinemabooking.exeptions.DefaultExceptionAttributes;
import ua.cinemabooking.exeptions.ExceptionAttributes;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by macbookair on 08.02.17.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception, HttpServletRequest servletRequest){

        logger.error("> handleException");
        logger.error("Exception > ",exception);

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.
                getExceptionsAttributes(exception, servletRequest, HttpStatus.INTERNAL_SERVER_ERROR);

        logger.error("< handleException ");
        return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Map<String, Object>> hadleNoResultException(NoResultException noResultEx,
                                                                      HttpServletRequest servletRequest){
        logger.error("> hadleNoResultException");
        logger.error("NoResultException > ", noResultEx);

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.
                getExceptionsAttributes(noResultEx, servletRequest, HttpStatus.NOT_FOUND);

        logger.error("< hadleNoResultException");

        return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity handleValidationException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getBindingResult(), HttpStatus.BAD_REQUEST);
    }
}
