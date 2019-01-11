package fund.dto;

import lombok.Data;

@Data
public class Payment {
    int id;
    int sponsorId;
    Integer commitmentId;
    int amount;
    String paymentDate;
    String etc;
    Integer receiptId;
    int donationPurposeId;
    int paymentMethodId;

    int corporateId;
    String donationPurposeName;

}
