package io.codewithgx.exportdataintopdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.codewithgx.exportdataintopdf.entity.Invoice;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 09/10/2022
 */

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
