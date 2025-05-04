package phuc.devops.tech.restaurant.Controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Service.PaymentService;
import phuc.devops.tech.restaurant.dto.request.PaymentCreate;
import phuc.devops.tech.restaurant.dto.request.PaymentRefund;
import phuc.devops.tech.restaurant.dto.response.PaymentVnpayResponse;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;

    //tạo yêu cầu thanh toán và gửi đến VNPay
    @PostMapping("/create")
    public ResponseEntity<PaymentVnpayResponse> createPayment(
            @RequestBody PaymentCreate req,             // nhận object từ request body (JSON) { "amount": 100000, "bankCode": "NCB", "language": "vn" }
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            @RequestHeader(value = "X-Real-IP", required = false) String realIp
    ) {
        String clientIp = Optional.ofNullable(xff)
                .map(h -> h.split(",")[0].trim())
                .orElse(realIp);
        PaymentVnpayResponse resp = paymentService.createPayment(req, clientIp);    // gọi service để create request
        return ResponseEntity.ok(resp);    // trả về object (có URL để chuyển hướng, JSON) ví dụ { "code": "00", "message": "success", "data": "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?vnp_Amount=10000000&vnp_BankCode=NCB&vnp_Command=pay&vnp_CreateDate=20250504114639&vnp_CurrCode=VND&vnp_ExpireDate=20250504120139&vnp_IpAddr=127.0.0.1&vnp_Locale=vn&vnp_OrderInfo=Thanh+toan+don+hang%3A+06648413&vnp_OrderType=other&vnp_ReturnUrl=http%3A%2F%2Flocalhost%3A8081%2Frestaurant%2Fpayment%2Freturn&vnp_TmnCode=HYM5GGO9&vnp_TxnRef=06648413&vnp_Version=2.1.0&vnp_SecureHash=f2562a2bcced7283ca80a3e4d776f120303c149ef41da10c4a26906dd58ff032d097ea2f3e8905dc7a9ee2251b53a33f1cf1a91727d9afdcbc7b109000750020" }
    }

/*
    @PostMapping("/refund")
    public ResponseEntity<String> refund(
            @RequestBody PaymentRefund req,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            @RequestHeader(value = "X-Real-IP", required = false) String realIp
    ) {
        String clientIp = Optional.ofNullable(xff)
                .map(h -> h.split(",")[0].trim())
                .orElse(realIp);
        String result = paymentService.refund(req, clientIp);
        return ResponseEntity.ok(result);
    }
*/
    @GetMapping("/return")
    public ResponseEntity<String> onReturn(@RequestParam Map<String, String> params) {     // lấy các tham số trong URL
        boolean valid = paymentService.handleCallback(params);    // xác thực kết quả thanh toán

        String redirectUrl = "http://localhost:8081/restaurant/auth/signup/user/";    //điều hướng đến trang chủ hoặc trang rating hoặc trang in hóa đơn
        String message;

        if ("00".equals(params.get("vnp_ResponseCode"))) {
            message = "Payment Successful! TxnRef: " + params.get("vnp_TxnRef");
        } else if ("24".equals(params.get("vnp_ResponseCode"))) {
            message = "Payment Failed due to user canceling transaction!";
        } else {
            message = "Payment failed! Mã lỗi: " + params.get("vnp_ResponseCode");
        }

        String html = "<html>" +                 // tạo trang hiển thị kết quả và redirect user
                "<head>" +
                "<meta http-equiv='refresh' content='5;url=" + redirectUrl + "' />" +
                "<title>Kết quả thanh toán</title>" +
                "</head>" +
                "<body>" +
                "<p>" + message + "</p>" +
                "<p>Bạn sẽ được chuyển về trang chủ sau 5 giây...</p>" +
                "</body>" +
                "</html>";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);  // trả về html
        return new ResponseEntity<>(html, headers, HttpStatus.OK);

        /*
        boolean valid = paymentService.validateResponse(params);
        if ("00".equals(params.get("vnp_ResponseCode"))) {
            return ResponseEntity.ok("Thanh toán thành công! TxnRef: " + params.get("vnp_TxnRef"));
        }else if ("24".equals(params.get("vnp_ResponseCode"))) {
            return ResponseEntity.ok("Thanh toán thất bại do người dùng hủy giao dịch!");
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Thanh toán thất bại. Mã lỗi: " + params.get("vnp_ResponseCode"));
        }

        String redirect = "http://localhost:8081/restaurant/auth/signup/user/";

        String html = "<html>" +
                "<head>" +
                "<meta http-equiv='refresh' content='5;url=" + redirect + "' />" +
                "<title>Đang chuyển hướng...</title>" +
                "</head>" +
                "<body>" +
                "<p>Bạn sẽ được chuyển về trang chủ sau 5 giây...</p>" +
                "</body>" +
                "</html>";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(html, headers, HttpStatus.OK);*/
    }
}

/*

Ngân hàng	NCB
Số thẻ	9704198526191432198
Tên chủ thẻ	NGUYEN VAN A
Ngày phát hành	07/15
Mật khẩu OTP	123456
 */

/*
{
  "amount": 1000000,
  "invoiceId": "de305d54-75b4-431b-adb2-eb6b9e546014",
  "bankCode": "NCB",
  "language": "vn"
}
 */