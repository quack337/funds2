package fund.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import fund.dto.PaymentInKind;

@Mapper
public interface PaymentInKindMapper {

    PaymentInKind selectById(int id);
    List<PaymentInKind> selectByPaymentId(int paymentId);
    void update(PaymentInKind paymentKind);
    void delete(int id);
    void deleteByPaymentId(int paymentId);
    void insert(PaymentInKind paymentKind);

}
