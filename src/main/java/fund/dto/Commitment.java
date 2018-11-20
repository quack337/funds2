package fund.dto;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;

public class Commitment {
    int id;
    int sponsorId;
    String commitmentNo;
    int donationPurposeId;
    int paymentMethodId;
    String commitmentDate;
    String startDate;
    String endDate;
    int amountPerMonth;
    int paymentDay;
    int bankId;
    String accountNo;
    String accountHolder;
    String birthDate;
    String etc;
    String eb13State;
    Date eb13Date;
    String eb13ErrorCode;

    //
    String sponsorNo;
    String name;
    String juminNo;
    String bankName;
    String bankCode;
    String eb13ErrorCodeMsg;

    String paymentMethodName;
    String donationPurposeName;
    String corporateName;
    String organizationName;
    String state;
    int months;

    //
    public boolean isValid() {
        int length = (birthDate + "").length();
        return StringUtils.isNotBlank(bankName) && StringUtils.isNotBlank(birthDate) && StringUtils.isNotBlank(accountNo) && (length == 6 || length == 10);
    }
    //

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

    public String getCommitmentNo() {
        return commitmentNo;
    }

    public void setCommitmentNo(String commitmentNo) {
        this.commitmentNo = commitmentNo;
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

    public String getCommitmentDate() {
        return commitmentDate;
    }

    public void setCommitmentDate(String commitmentDate) {
        this.commitmentDate = commitmentDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getAmountPerMonth() {
        return amountPerMonth;
    }

    public void setAmountPerMonth(int amountPerMonth) {
        this.amountPerMonth = amountPerMonth;
    }

    public int getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(int paymentDay) {
        this.paymentDay = paymentDay;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getEb13State() {
        return eb13State;
    }

    public void setEb13State(String eb13State) {
        this.eb13State = eb13State;
    }

    public Date getEb13Date() {
        return eb13Date;
    }

    public void setEb13Date(Date eb13Date) {
        this.eb13Date = eb13Date;
    }

    public String getEb13ErrorCode() {
        return eb13ErrorCode;
    }

    public void setEb13ErrorCode(String eb13ErrorCode) {
        this.eb13ErrorCode = eb13ErrorCode;
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJuminNo() {
        return juminNo;
    }

    public void setJuminNo(String juminNo) {
        this.juminNo = juminNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getEb13ErrorCodeMsg() {
        return eb13ErrorCodeMsg;
    }

    public void setEb13ErrorCodeMsg(String eb13ErrorCodeMsg) {
        this.eb13ErrorCodeMsg = eb13ErrorCodeMsg;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

}