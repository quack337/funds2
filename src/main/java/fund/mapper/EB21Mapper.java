package fund.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import fund.dto.EB21;

@Mapper
public interface EB21Mapper {
    List<EB21> selectByPaymentDate(Date paymentDate);
    EB21 selectByCommitmentNo12(@Param("commitmentNo12") String commitmentNo12, @Param("paymentDate") Date paymentDate);
    List<EB21> selectCmsResult(Map<String,Object> map);
	void insert(EB21 eb21);
    void update(EB21 eb21);
    void deleteByPaymentDate(Date paymentDate);
}
