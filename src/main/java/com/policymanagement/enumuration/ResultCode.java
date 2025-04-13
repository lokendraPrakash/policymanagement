package com.policymanagement.enumuration;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(1200, "SUCCESS", "Operation was successful"),
    FAILED(1201, "FAILED", "Something went wrong. Please try after sometime"),
    BAD_REQUEST(1300, "BAD REQUEST", "The request is invalid or malformed"),
    NOT_FOUND(1301, "NOT FOUND", "The requested resource was not found"),
    UNAUTHORIZED(1302, "UNAUTHORIZED", "Access is denied due to invalid credentials"),
    FORBIDDEN(1303, "FORBIDDEN", "You do not have permission to access this resource"),
    FILE_MISSING(1304, "FILE MISSING", "Required file part is missing in the request"),
    INVALID_FILE_TYPE(1305, "INVALID FILE TYPE", "Uploaded file type is not supported"),
    INTERNAL_SERVER_ERROR(1500, "INTERNAL SERVER ERROR", "An unexpected error occurred on the server"),
    FILE_READ_ERROR(1302, "FILE READ ERROR", "Unable to read the file"),
    FILTER_RESULT_NOT_FOUND(1401, "FILTER RESULT NOT FOUND", "No documents found matching the filter criteria"),
    FILE_SIZE_EXCEEDED(1301, "FILE SIZE EXCEEDED", "Uploaded file size exceeds the allowed limit"),
;

    private final int code;
    private final String status;
    private final String message;

    ResultCode(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
