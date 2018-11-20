package fund.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginErrorMapper {
    int selectCount(String loginName);
    void insert(String loginName);
    void deleteOld();
    void deleteAll(String loginName);
}