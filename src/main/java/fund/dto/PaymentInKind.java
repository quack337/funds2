package fund.dto;

import lombok.Data;

@Data
public class PaymentInKind {

    int id;
    int paymentId;
    String title;
    int quantity;
    int unitCost;
}
