package io.codewithgx.exportdataintopdf.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.codewithgx.exportdataintopdf.custom.InvoiceNotFoundException;
import io.codewithgx.exportdataintopdf.entity.Invoice;
import io.codewithgx.exportdataintopdf.service.api.InvoiceServiceApi;
import io.codewithgx.exportdataintopdf.web.view.InvoiceDataPDFExport;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 10/10/2022
 */


@Controller
@RequestMapping({"/api/v1/invoice", "/invoice"})
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceServiceApi invoiceServiceApi;

    @GetMapping("/")
    public String showHomepage() {
        return "home";
    }

    @GetMapping("/register")
    public String registration(){
        return "register";
    }

    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute Invoice invoice, Model model) {
        final Invoice savedInvoice = invoiceServiceApi.saveInvoice(invoice);
        String message = "Record " + savedInvoice.toString() + " is saved successfully!";
        model.addAttribute("message", message);
        return "register";
    }

    @GetMapping("/getAllInvoice")
    public String getAllInvoice(@RequestParam(value = "message", required = false) String message, Model model) {
        final List<Invoice> allInvoices = invoiceServiceApi.getAllInvoices();
        model.addAttribute("list", allInvoices);
        model.addAttribute("message", message);
        return "allInvoices";
    }

    @GetMapping("/edit")
    public String getEditPage(Model model, RedirectAttributes redirectAttributes, @RequestParam Long id) {
        String page = "";
        try {
            Invoice invoice = invoiceServiceApi.getInvoiceById(id);
            model.addAttribute("invoice", invoice);
            page = "editInvoice";
        } catch (InvoiceNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("message", e.getMessage());
            page = "redirect:getAllInvoice";
        }
        return page;
    }

    @PostMapping("/update")
    public String updateInvoice(@ModelAttribute("invoice") Invoice invoice, RedirectAttributes redirectAttributes) {
        invoiceServiceApi.updateInvoice(invoice);
        Long id = invoice.getId();
        redirectAttributes.addAttribute("message", "Invoice with id: '%d' is updated successfully !".formatted(id));
        return "redirect:getAllInvoice";
    }

    @GetMapping("/delete")
    public String deleteInvoice(
            @RequestParam Long id,
            RedirectAttributes attributes
    ) {
        try {
            invoiceServiceApi.deleteInvoiceById(id);
            attributes.addAttribute("message", "Invoice with Id : '%d' is removed successfully!".formatted(id));
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addAttribute("message", e.getMessage());
        }
        return "redirect:getAllInvoice";
    }

    /***
     * Export data to pdf file
     */
    @GetMapping("/pdf")
    public ModelAndView exportToPdf() {
        ModelAndView mav = new ModelAndView();
        mav.setView(new InvoiceDataPDFExport());
        //read data from DB
        List<Invoice> list= invoiceServiceApi.getAllInvoices();
        //send to pdfImpl class
        mav.addObject("list", list);
        return mav;
    }
}
