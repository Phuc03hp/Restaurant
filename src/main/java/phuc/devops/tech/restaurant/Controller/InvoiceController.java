package phuc.devops.tech.restaurant.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.Order;
import phuc.devops.tech.restaurant.Entity.User;
import phuc.devops.tech.restaurant.Service.InvoiceService;
import phuc.devops.tech.restaurant.dto.request.UserCreateInvoice;
import phuc.devops.tech.restaurant.dto.response.InvoiceResponse;

import java.util.Optional;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/{orderID}")
    public InvoiceResponse getInvoice(@PathVariable String orderID, @RequestBody UserCreateInvoice request){
        return invoiceService.getInvoice(orderID,request);
    }

    @GetMapping("/recheck/{invoiceID}")
    public InvoiceResponse CheckInvoiceAgain(@PathVariable String invoiceID){
        return invoiceService.CheckInvoiceAgain(invoiceID);
    }

}
