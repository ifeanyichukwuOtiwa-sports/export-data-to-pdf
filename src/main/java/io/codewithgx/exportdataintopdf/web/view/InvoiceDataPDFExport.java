package io.codewithgx.exportdataintopdf.web.view;

import java.awt.Color;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import io.codewithgx.exportdataintopdf.entity.Invoice;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 10/10/2022
 */

public class InvoiceDataPDFExport extends AbstractPdfView {
    @Override
    protected void buildPdfMetadata(final Map<String, Object> model,
                                    final Document document,
                                    final HttpServletRequest request) {

        final Font headerFont = new Font(Font.TIMES_ROMAN, 20, Font.BOLD, Color.MAGENTA);
        final HeaderFooter header = new HeaderFooter(new Phrase("All Invoices PDF view", headerFont), false);
        header.setAlignment(Element.ALIGN_CENTER);
        document.setHeader(header);

        Font dateFont = new Font(Font.COURIER, 12, Font.NORMAL, Color.BLUE);

        HeaderFooter footer = new HeaderFooter(new Phrase("PDF Export Executed on: %s".formatted(LocalDate.now()), dateFont),
                true);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.setFooter(footer);

    }


    @Override
    protected void buildPdfDocument(final Map<String, Object> model,
                                    final Document document,
                                    final PdfWriter writer,
                                    final HttpServletRequest request,
                                    final HttpServletResponse response) throws Exception {


        //download PDF with a given filename
        response.addHeader("Content-Disposition", "attachment;filename=Invoices.pdf");

        //read data from controller
        List<Invoice> list = (List<Invoice>) model.get("list");

        //create element
        Font titleFont = new Font(Font.TIMES_ROMAN, 24, Font.BOLD, Color.blue);
        Paragraph title = new Paragraph("ALL INVOICES DATA", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(20.0f);
        title.setSpacingAfter(25.0f);
        //add to document
        document.add(title);

        Font tableHead = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.blue);
        PdfPTable table = new PdfPTable(4);// no.of columns
        table.addCell(new Phrase("ID",tableHead));
        table.addCell(new Phrase("NAME",tableHead));
        table.addCell(new Phrase("LOCATION",tableHead));
        table.addCell(new Phrase("AMOUNT",tableHead));

        for(Invoice invoice : list ) {
            table.addCell(invoice.getId().toString());
            table.addCell(invoice.getName());
            table.addCell(invoice.getLocation());
            table.addCell(invoice.getAmount().toString());
        }
        //add table data to document
        document.add(table);
    }
}
