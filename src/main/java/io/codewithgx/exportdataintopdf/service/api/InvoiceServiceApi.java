package io.codewithgx.exportdataintopdf.service.api;

import java.util.List;

import org.springframework.lang.NonNull;

import io.codewithgx.exportdataintopdf.entity.Invoice;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 09/10/2022
 */

public interface InvoiceServiceApi {

    @NonNull
    Invoice saveInvoice(Invoice invoice);

    List<Invoice> getAllInvoices();
    List<Invoice> getAllInvoices(Integer skip, Integer take);

    @NonNull
    Invoice getInvoiceById(Long id);
    void deleteInvoiceById(Long id);
    void updateInvoice(Invoice invoice);
}
