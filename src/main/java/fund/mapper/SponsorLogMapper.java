package fund.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import fund.dto.SponsorLog;

@Mapper
public interface SponsorLogMapper {
    SponsorLog selectById(int id);
    List<SponsorLog> selectBySponsorId(int sponsorId);
    void insert(SponsorLog log);
    void delete(int id);
    void deleteBySponsorId(int sponsorId);
}
