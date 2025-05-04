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

    @PostMapping("/create")
    public ResponseEntity<PaymentVnpayRe    sponse> createPayment(
            @RequestBody PaymentCreate req,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            @RequestHeader(value = "X-Real-IP", required = false) String realIp
    ) {
        String clientIp = Optional.ofNullable(xff)
                .map(h -> h.split(",")[0].trim())
                .orElse(realIp);
        PaymentVnpayResponse resp = paymentService.createPayment(req, clientIp);
        return ResponseEntity.ok(resp);
    }


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

    @GetMapping("/return")
    public ResponseEntity<String> onReturn(@RequestParam Map<String, String> params) {
        boolean valid = paymentService.validateResponse(params);

        String redirectUrl = "http://localhost:8081/restaurant/auth/signup/user/";
        String message;

        if ("00".equals(params.get("vnp_ResponseCode"))) {
            message = "Payment Successful! TxnRef: " + params.get("vnp_TxnRef");
        } else if ("24".equals(params.get("vnp_ResponseCode"))) {
            message = "Payment Failed due to user canceling transaction!";
        } else {
            message = "Payment failed! Mã lỗi: " + params.get("vnp_ResponseCode");
        }

        String html = "<html>" +
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
        headers.setContentType(MediaType.TEXT_HTML);
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