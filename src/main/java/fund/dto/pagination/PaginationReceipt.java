package fund.dto.pagination;

public class PaginationReceipt extends Pagination {
    int corporateId;

    public int getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(int corporateId) {
        this.corporateId = corporateId;
    }

    @Override
    public String getQueryString() {
        String s = super.getQueryString();
        return String.format("%s&corporateId=%d", s, corporateId);
    }
}
