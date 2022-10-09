package io.codewithgx.exportdataintopdf.custom;

import java.io.Serial;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 09/10/2022
 */

public class InvoiceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvoiceNotFoundException() {
        super();
    }

    public InvoiceNotFoundException(final String message) {
        super(message);
    }
}
