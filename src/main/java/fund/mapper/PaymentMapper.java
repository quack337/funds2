package fund.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import fund.dto.Commitment;
import fund.dto.Payment;

@Mapper
public interface PaymentMapper {

    Payment selectById(int id);
    List<Map<String,Object>> selectPaymentList1(@Param("sponsorId") int sponsorId, @Param("commitmentId") int commitmentId);
    List<Map<String,Object>> selectPaymentList2(int sponsorId);
    List<Map<String,Object>> selectPaymentList3(int sponsorId);

    void update(Payment payment);
    void updateDonationPurposeId(Commitment commitment);  // 정기 납입 변경
    void updateDonationPurposeId2(Commitment commitment); // 비정기 납입 변경
    void delete(int id);
    void insert(Payment payment);

    List<Payment> selectByReceiptId(int rid);
    List<Map<String,Object>> selectForReceiptCreation1(Map<String,Object> map);
    List<Payment> selectForReceiptCreation2(Map<String,Object> map);
    Map<String,Object> getSumByReceiptId(int rid);
    List<Map<String,Object>> selectForTaxData(Map<String,Object> map);

    List<Map<String,Object>> selectReport1a(Map<String,Object> param);
    List<Map<String,Object>> selectReport1b(Map<String,Object> param);
    List<Map<String,Object>> selectReport2a(Map<String,Object> param);
    List<Map<String,Object>> selectReport2b(Map<String,Object> param);
    List<Map<String,Object>> selectReport2c(Map<String,Object> param);
    List<Map<String,Object>> selectReport2d(Map<String,Object> param);
}
