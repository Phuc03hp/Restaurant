package phuc.devops.tech.restaurant.Service;

import phuc.devops.tech.restaurant.Config.PaymentConfig;
import phuc.devops.tech.restaurant.Entity.Payment;
import phuc.devops.tech.restaurant.Repository.PaymentRespository;
import phuc.devops.tech.restaurant.dto.request.PaymentCreate;
import phuc.devops.tech.restaurant.dto.request.PaymentRefund;
import phuc.devops.tech.restaurant.dto.response.PaymentVnpayResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {

    PaymentConfig config;
    RestTemplate restTemplate = new RestTemplate();
    PaymentRespository paymentRespository;
    JdbcTemplate jdbcTemplate;

    // tạo url thanh toán VNpay để client get request
    public PaymentVnpayResponse createPayment(PaymentCreate req, String clientIp) {
        Map<String,String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", config.getTmnCode());
        params.put("vnp_Amount", String.valueOf(req.getAmount() * 100));
        params.put("vnp_CurrCode", "VND");
        if (req.getBankCode() != null) params.put("vnp_BankCode", req.getBankCode());

        String txnRef = PaymentConfig.getRandomNumber(8);    // mã giao dịch random
        params.put("vnp_TxnRef", txnRef);
       // params.put("vnp_OrderInfo", "Thanh toan don hang: " + txnRef);
        params.put("vnp_OrderInfo", "Thanh toan don hang: " + req.getInvoiceId());  // thanh toan đơn hàng + mã đơn


        // chen truoc payment_id = txn_ref vao payment table để tránh lỗi xung đột khóa ngoại từ bảng quan hệ
        String temp = "INSERT INTO payment (txn_ref) VALUES (?)";
        jdbcTemplate.update(temp, txnRef);

        // chèn invoiceid và paymentid cùng 1 lúc vào bang quan hệ (txn_ref = payment_id)
        String sql = "INSERT INTO invoice_payment (invoice_id, payment_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, req.getInvoiceId(), txnRef);


        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", Optional.ofNullable(req.getLanguage()).filter(s->!s.isEmpty()).orElse("vn"));
        params.put("vnp_ReturnUrl", config.getReturnUrl());
        params.put("vnp_IpAddr", clientIp);


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        String createDate = fmt.format(cal.getTime());
        cal.add(Calendar.MINUTE, 15);
        String expireDate = fmt.format(cal.getTime());
        params.put("vnp_CreateDate", createDate);
        params.put("vnp_ExpireDate", expireDate);


        StringBuilder query = new StringBuilder();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String k = keys.get(i);
            String v = URLEncoder.encode(params.get(k), StandardCharsets.US_ASCII);
            query.append(k).append("=").append(v);
            if (i < keys.size() - 1) query.append("&");
        }
        String hashData = query.toString();
        String secureHash = PaymentConfig.hmacSHA512(config.getSecretKey(), hashData);
        query.append("&vnp_SecureHash=").append(secureHash);

        // trả về URL thanh toán
        String paymentUrl = config.getPayUrl() + "?" + query;
        PaymentVnpayResponse resp = new PaymentVnpayResponse();
        resp.setCode("00");
        resp.setMessage("success");
        resp.setData(paymentUrl);
        return resp;
    }

    /*
    public String refund(PaymentRefund req, String clientIp) {
        Map<String,Object> body = new HashMap<>();
        String requestId = PaymentConfig.getRandomNumber(8);
        body.put("vnp_RequestId", requestId);
        body.put("vnp_Version", "2.1.0");
        body.put("vnp_Command", "refund");
        body.put("vnp_TmnCode", config.getTmnCode());
        body.put("vnp_TransactionType", req.getTranType());
        body.put("vnp_TxnRef", req.getOrderId());
        body.put("vnp_Amount", req.getAmount()*100);
        body.put("vnp_OrderInfo", "Hoan tien Order: " + req.getOrderId());
        body.put("vnp_TransactionDate", req.getTransDate());
        body.put("vnp_CreateBy", req.getUser());
        body.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss")
                .format(Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7")).getTime()));
        body.put("vnp_IpAddr", clientIp);


        String hashData = String.join("|",
                (String) body.get("vnp_RequestId"),
                (String) body.get("vnp_Version"),
                (String) body.get("vnp_Command"),
                (String) body.get("vnp_TmnCode"),
                (String) body.get("vnp_TransactionType"),
                (String) body.get("vnp_TxnRef"),
                String.valueOf(body.get("vnp_Amount")),
                "", // vnp_TransactionNo
                (String) body.get("vnp_TransactionDate"),
                (String) body.get("vnp_CreateBy"),
                (String) body.get("vnp_CreateDate"),
                (String) body.get("vnp_IpAddr"),
                (String) body.get("vnp_OrderInfo")
        );
        String secureHash = PaymentConfig.hmacSHA512(config.getSecretKey(), hashData);
        body.put("vnp_SecureHash", secureHash);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity(config.getApiUrl(), entity, String.class);
        return response.getBody();
    }
*/

    // xác thực tính hợp lệ của response từ VNPAY server
    public boolean validateResponse(Map<String,String> params) {
        String receivedHash = params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");


        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String k = keys.get(i);
            String v = params.get(k);
            if (v != null && !v.isEmpty()) {
                sb.append(k).append("=").append(v);
                if (i < keys.size() - 1) sb.append("&");
            }
        }
        String data = sb.toString();


        String computedHash = PaymentConfig.hmacSHA512(config.getSecretKey(), data);
        return computedHash.equals(receivedHash);
    }


    // xử lý callback từ VNPAY sau khi ngưới dùng thanhg toán
    public boolean handleCallback(Map<String, String> params) {
        String receivedHash = params.get("vnp_SecureHash");
        String computedHash = PaymentConfig.hashAllFields(params, config.getSecretKey());

        boolean valid = receivedHash != null && receivedHash.equalsIgnoreCase(computedHash);
        boolean success = "00".equals(params.get("vnp_ResponseCode"));

        Payment payment = new Payment();
        payment.setTxnRef(params.get("vnp_TxnRef"));
        payment.setAmount(Long.parseLong(params.get("vnp_Amount")) / 100);
        payment.setBankCode(params.get("vnp_BankCode"));
        payment.setBankTranNo(params.get("vnp_BankTranNo"));
        payment.setCardType(params.get("vnp_CardType"));
        payment.setOrderInfo(params.get("vnp_OrderInfo"));
        payment.setPayDate(params.get("vnp_PayDate"));
        payment.setResponseCode(params.get("vnp_ResponseCode"));
        payment.setTransactionNo(params.get("vnp_TransactionNo"));
        payment.setTransactionStatus(params.get("vnp_TransactionStatus"));
        payment.setSuccess(success);

        paymentRespository.save(payment);
        return success;
    }

}
