package ua.cinemabooking.exeptions;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by macbookair on 07.12.16.
 */
public class DefaultExceptionAttributes implements ExceptionAttributes {

    private final String TIMESTAMP = "timestamp";
    private final String STATUS = "status";
    private final String ERROR = "error";
    private final String MESSAGE = "message";
    private final String EXCEPTION = "exception";
    private final String PATH = "path";


    @Override
    public Map<String, Object> getExceptionsAttributes(Exception exception,
                                                       HttpServletRequest request, HttpStatus status) {
        Map<String, Object> exceptionAttributes = new LinkedHashMap<>();

        exceptionAttributes.put(TIMESTAMP, new Date());

        addHttpStatus(exceptionAttributes, status);
        addExceptionDetails(exceptionAttributes, exception);
        addPath(exceptionAttributes, request);

        return exceptionAttributes;
    }

    private Map<String, Object> addHttpStatus(Map<String, Object> exceptionAttributes, HttpStatus httpStatus){

        exceptionAttributes.put(STATUS, httpStatus.value());
        exceptionAttributes.put(ERROR, httpStatus.getReasonPhrase());

        return exceptionAttributes;
    }

    private Map<String, Object> addExceptionDetails(Map<String, Object> exceptionAttributes, Exception exception){

        exceptionAttributes.put(EXCEPTION, exception.getClass().getName());
        exceptionAttributes.put(MESSAGE, exception.getMessage());

        return exceptionAttributes;
    }

    private Map<String, Object> addPath(Map<String, Object> exceptionAttributes, HttpServletRequest httpServletRequest){

        exceptionAttributes.put(PATH, httpServletRequest.getServletPath());
        return exceptionAttributes;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getERROR() {
        return ERROR;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public String getEXCEPTION() {
        return EXCEPTION;
    }

    public String getPATH() {
        return PATH;
    }
}




















