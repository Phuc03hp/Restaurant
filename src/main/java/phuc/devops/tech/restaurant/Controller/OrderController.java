package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.DiningTable;
import phuc.devops.tech.restaurant.Entity.Invoice;
import phuc.devops.tech.restaurant.Service.DiningTableService;
import phuc.devops.tech.restaurant.Service.InvoiceService;
import phuc.devops.tech.restaurant.dto.request.Cart;
import phuc.devops.tech.restaurant.dto.request.CheckoutRequest;
import phuc.devops.tech.restaurant.dto.request.FoodOrderItem;
import phuc.devops.tech.restaurant.dto.request.UserCreateTable;
import phuc.devops.tech.restaurant.dto.response.InvoiceResponse;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private DiningTableService diningTableService;

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/createTable")
    public DiningTable createTable(@RequestBody UserCreateTable request){
        return diningTableService.createTable(request);
    }

    @GetMapping("/tables")
    public List<DiningTable> getTables() {
        return diningTableService.getTable();
    }

    @PostMapping("/cart")
    public void addtoCart(@RequestBody FoodOrderItem request){
        invoiceService.addToCart(request);
    }

    @PostMapping("/checkout")
    public InvoiceResponse checkout(@RequestBody CheckoutRequest request){
        return invoiceService.createInvoice(request);
    }

    @DeleteMapping("/invoice/delete/{invoiceId}")
    public String deleteInvoice(@PathVariable String invoiceId){
        return invoiceService.deleteInvoice(invoiceId);
    }

    @GetMapping("/invoice/{invoiceId}")
    public InvoiceResponse getInvoice(@PathVariable String invoiceId){
        return invoiceService.getInvoice(invoiceId);
    }
}
