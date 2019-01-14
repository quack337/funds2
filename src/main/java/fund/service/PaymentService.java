package fund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fund.dto.PaymentInKind;
import fund.dto.PaymentJSON;
import fund.mapper.PaymentInKindMapper;
import fund.mapper.PaymentMapper;

@Service
public class PaymentService {

    @Autowired PaymentMapper paymentMapper;
    @Autowired PaymentInKindMapper paymentInKindMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(PaymentJSON payment) {
        payment.setPaymentMethodId(C.코드ID_현물);
        paymentMapper.insert(payment);
        paymentInKindMapper.deleteByPaymentId(payment.getId());
        for (PaymentInKind p : payment.getItems()) {
            p.setPaymentId(payment.getId());
            paymentInKindMapper.insert(p);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(PaymentJSON payment) {
        payment.setPaymentMethodId(C.코드ID_현물);
        paymentMapper.update(payment);
        paymentInKindMapper.deleteByPaymentId(payment.getId());
        for (PaymentInKind p : payment.getItems()) {
            p.setPaymentId(payment.getId());
            paymentInKindMapper.insert(p);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(int id) {
        paymentInKindMapper.deleteByPaymentId(id);
        paymentMapper.delete(id);
    }

}
