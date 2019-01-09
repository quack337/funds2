package fund.dto.pagination;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class PaginationDM extends Pagination {
    int et;

    @Override
    public String getQueryString() {
        String s = super.getQueryString();
        return String.format("%s&et=%d", s, et);
    }
}
