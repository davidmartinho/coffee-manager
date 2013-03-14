package pt.ist.bennu.coffee.manager.error;

import javax.ws.rs.core.Response.Status;

import pt.ist.bennu.bennu.core.rest.mapper.RestError;

public enum CoffeeManagerError implements RestError {

    CANNOT_CREATE_ORDER(Status.OK, 10000, "Cannot create order."), CANNOT_MODIFY_OTHERS_ORDERS(Status.FORBIDDEN, 10001,
            "Cannot modify others orders.");

    private Status status;
    private int internalErrorCode;
    private String message;

    private CoffeeManagerError(Status status, int internalErrorCode, String message) {
        this.status = status;
        this.internalErrorCode = internalErrorCode;
        this.message = message;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public int getInternalErrorCode() {
        return internalErrorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
