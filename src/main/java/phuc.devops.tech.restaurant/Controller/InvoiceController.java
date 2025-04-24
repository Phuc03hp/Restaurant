package phuc.devops.tech.restaurant.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.Order;
import phuc.devops.tech.restaurant.Entity.User;
import phuc.devops.tech.restaurant.Service.InvoiceService;
import phuc.devops.tech.restaurant.Service.OrderService;
import phuc.devops.tech.restaurant.dto.request.UserCreateInvoice;
import phuc.devops.tech.restaurant.dto.response.InvoiceResponse;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;
    OrderService orderService;

    @PostMapping("/{tableID}")
    public InvoiceResponse payment(@PathVariable Long tableID, @RequestBody UserCreateInvoice request){
        Order order = orderService.getCurrentOrderForTable(tableID);
        order.setIsPaid(true);
        return invoiceService.getInvoice(order.getOrderID(),request);
    }

    @GetMapping("/recheck/{invoiceID}")
    public InvoiceResponse CheckInvoiceAgain(@PathVariable String invoiceID){
        return invoiceService.CheckInvoiceAgain(invoiceID);
    }

    @GetMapping("/revenue/day")
    public Float revenueByDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return invoiceService.getRevenueByDate(date);
    }

    @GetMapping("/revenue/month")
    public Float revenueByMonth(@RequestParam int month, @RequestParam int year) {
        return invoiceService.getRevenueByMonth(month, year);
    }

    @GetMapping("/revenue/year")
    public Float revenueByYear(@RequestParam int year) {
        return invoiceService.getRevenueByYear(year);
    }

}
