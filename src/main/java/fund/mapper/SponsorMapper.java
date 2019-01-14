package fund.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import fund.dto.Sponsor;
import fund.dto.pagination.Pagination;
import fund.dto.pagination.PaginationSponsor;

@Mapper
public interface SponsorMapper {

    String selectKey1();
    Sponsor selectById(int id);
    Sponsor selectBySponsorNo(String sponsorNo);
    List<Sponsor> selectPage(PaginationSponsor pagination);
    List<Sponsor> selectDuplicate(Sponsor sponsor);
    int selectCount(Pagination pagination);

    void update(Sponsor sponsor);
    void delete(int id);
    void insert(Sponsor sponsor);
    String generateSponsorNo();

    List<Sponsor> selectForDM(Pagination pagination);
    int selectCountForDM(Pagination pagination);
}