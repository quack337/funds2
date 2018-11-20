package fund.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import fund.dto.Log;
import fund.dto.pagination.Pagination;

@Mapper
public interface LogMapper {
    Log selectById(int id);
    List<Log> selectPage(Pagination pagination);
    int selectCount(Pagination pagination);
    void insert(Log log);
    void delete(int id);
}
