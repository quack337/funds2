package fund.mapper;

import org.apache.ibatis.annotations.Mapper;

import fund.dto.PaymentInKind;

@Mapper
public interface PaymentInKindMapper {

    PaymentInKind selectById(int id);
    PaymentInKind selectByPaymentId(int paymentId);
    void update(PaymentInKind paymentKind);
    void delete(int id);
    void insert(PaymentInKind paymentKind);


}
