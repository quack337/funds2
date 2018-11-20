package fund.dto;

import java.util.Date;

public class EB21 {

    int id;
    int commitmentId;
    String commitmentNo12;
    Date paymentDate;
    String state;
    String errorCode;

    //
    String commitmentNo;
    String sponsorNo;
    String sponsorName;
    String donationPurposeName;
    String corporateName;
    String organizationName;
    String errorCodeMsg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommitmentId() {
        return commitmentId;
    }

    public void setCommitmentId(int commitmentId) {
        this.commitmentId = commitmentId;
    }

    public String getCommitmentNo12() {
        return commitmentNo12;
    }

    public void setCommitmentNo12(String commitmentNo12) {
        this.commitmentNo12 = commitmentNo12;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCommitmentNo() {
        return commitmentNo;
    }

    public void setCommitmentNo(String commitmentNo) {
        this.commitmentNo = commitmentNo;
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getDonationPurposeName() {
        return donationPurposeName;
    }

    public void setDonationPurposeName(String donationPurposeName) {
        this.donationPurposeName = donationPurposeName;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getErrorCodeMsg() {
        return errorCodeMsg;
    }

    public void setErrorCodeMsg(String errorCodeMsg) {
        this.errorCodeMsg = errorCodeMsg;
    }


}
