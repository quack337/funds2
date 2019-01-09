package fund.dto.pagination;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class PaginationSponsor extends Pagination {

    int st1;
    int st2;
    int st3;

    @Override
    public String getQueryString() {
        String s = super.getQueryString();
        return String.format("%s&st1=%d&st2=%d&st3=%d", s, st1, st2, st3);
    }

    @Override
    public boolean notEmpty() {
        return super.notEmpty() || st1 > 0 || st2 > 0 || st3 > 0;
    }


}
