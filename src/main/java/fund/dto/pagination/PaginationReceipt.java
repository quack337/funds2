package fund.dto.pagination;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class PaginationReceipt extends Pagination {
    int corporateId;

    @Override
    public String getQueryString() {
        String s = super.getQueryString();
        return String.format("%s&corporateId=%d", s, corporateId);
    }
}
