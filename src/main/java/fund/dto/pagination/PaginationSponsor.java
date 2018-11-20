package fund.dto.pagination;

public class PaginationSponsor extends Pagination {

    int st1;
    int st2;
    int st3;

    public int getSt1() {
        return st1;
    }

    public void setSt1(int st1) {
        this.st1 = st1;
    }

    public int getSt2() {
        return st2;
    }

    public void setSt2(int st2) {
        this.st2 = st2;
    }

    public int getSt3() {
        return st3;
    }

    public void setSt3(int st3) {
        this.st3 = st3;
    }

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
