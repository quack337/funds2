package fund.dto;

import java.util.Date;

public class Xfer {

    String accountNo;
    Date date;
    int amount;
    String etc1;
    String etc2;
    String commitmentNo;
    boolean valid;

    public Xfer(String accountNo, Date date, int amount, String etc1, String etc2, boolean valid) {
        this.accountNo = accountNo;
        this.date = date;
        this.amount = amount;
        this.etc1 = etc1;
        this.etc2 = etc2;
        this.valid = valid;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getEtc1() {
        return etc1;
    }

    public void setEtc1(String etc1) {
        this.etc1 = etc1;
    }

    public String getEtc2() {
        return etc2;
    }

    public void setEtc2(String etc2) {
        this.etc2 = etc2;
    }

    public String getCommitmentNo() {
        return commitmentNo;
    }

    public void setCommitmentNo(String commitmentNo) {
        this.commitmentNo = commitmentNo;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d %s %s\n", accountNo, date, amount, etc1, etc2);
    }

}
