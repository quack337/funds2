package fund.dto;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Integer getCommitmentId() {
        return commitmentId;
    }

    public void setCommitmentId(Integer commitmentId) {
        this.commitmentId = commitmentId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public int getDonationPurposeId() {
        return donationPurposeId;
    }

    public void setDonationPurposeId(int donationPurposeId) {
        this.donationPurposeId = donationPurposeId;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public int getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(int corporateId) {
        this.corporateId = corporateId;
    }

    public String getDonationPurposeName() {
        return donationPurposeName;
    }

    public void setDonationPurposeName(String donationPurposeName) {
        this.donationPurposeName = donationPurposeName;
    }


}
