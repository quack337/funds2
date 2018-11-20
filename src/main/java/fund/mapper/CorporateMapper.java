package fund.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import fund.dto.Corporate;

@Mapper
public interface CorporateMapper {
	List<Corporate> selectAll();
	Corporate selectById(int ID);
    void insert(Corporate corporate);
    void update(Corporate corporate);
    void delete(int ID);
}
