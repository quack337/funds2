package fund.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import fund.dto.Receipt;
import fund.dto.pagination.Pagination;

@Mapper
public interface ReceiptMapper {
    Receipt selectById(int id);
    List<Map<String,Object>> selectPage(Pagination pagination);
    int selectCount(Pagination pagination);
    List<HashMap<String,Object>> selectSum(Pagination pagination);
    void insert(Receipt receipt);
    String generateReceiptNo(String createDate);
	void delete(int id);
}
