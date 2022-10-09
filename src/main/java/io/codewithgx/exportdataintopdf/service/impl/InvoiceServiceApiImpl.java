package io.codewithgx.exportdataintopdf.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.codewithgx.exportdataintopdf.custom.InvoiceNotFoundException;
import io.codewithgx.exportdataintopdf.entity.Invoice;
import io.codewithgx.exportdataintopdf.repository.InvoiceRepository;
import io.codewithgx.exportdataintopdf.service.api.InvoiceServiceApi;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 09/10/2022
 */


@Service
@RequiredArgsConstructor
public class InvoiceServiceApiImpl implements InvoiceServiceApi {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice saveInvoice(final Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> getAllInvoices(final Integer skip, final Integer take) {
        return this.getAllInvoices().stream()
                .skip(skip)
                .limit(take)
                .toList();
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        final Optional<Invoice> opt = invoiceRepository.findById(id);
        return opt.orElseThrow(() -> new InvoiceNotFoundException("Invoice with id: %s Not Found".formatted(id)));
    }

    @Override
    public void deleteInvoiceById(Long id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public void updateInvoice(final Invoice invoice) {
        invoiceRepository.save(invoice);
    }
}
