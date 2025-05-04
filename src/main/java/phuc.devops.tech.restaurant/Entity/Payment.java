package phuc.devops.tech.restaurant.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @Column(name = "txn_ref", nullable = false)
    private String txnRef;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "bank_tran_no")
    private String bankTranNo;

    @Column(name = "order_info")
    private String orderInfo;

    @Column(name = "transaction_no")
    private String transactionNo;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "pay_date")
    private String payDate;

    @Column(name = "success")
    private Boolean success;

    @OneToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoiceID")
    private Invoice invoice;

}
