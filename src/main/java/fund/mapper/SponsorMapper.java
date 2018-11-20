package fund.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import fund.dto.Sponsor;
import fund.dto.pagination.Pagination;

@Mapper
public interface SponsorMapper {

    String selectKey1();
    Sponsor selectById(int id);
    Sponsor selectBySponsorNo(String sponsorNo);
    List<Sponsor> selectPage(Pagination pagination);
    List<Sponsor> selectAll();
    List<Sponsor> selectDuplicate(Sponsor sponsor);
    int selectCount(Pagination pagination);

    void update(Sponsor sponsor);
    void delete(int id);
    void insert(Sponsor sponsor);
    String generateSponsorNo();

    List<Sponsor> selectForDM(Pagination pagination);
    int selectCountForDM(Pagination pagination);
}