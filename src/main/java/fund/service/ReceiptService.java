package fund.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fund.dto.Payment;
import fund.dto.Receipt;
import fund.mapper.PaymentMapper;
import fund.mapper.ReceiptMapper;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ReceiptService {

	@Autowired PaymentMapper paymentMapper;
    @Autowired ReceiptMapper receiptMapper;

	public String createReceipt1(String createDate, int corporateId, int[] pid) {
	    if (pid.length <= 0) return "납입 내역을 선택하세요.";

        List<Payment> payments = new ArrayList<>();
        for (int i = 0; i < pid.length; ++i) {
            payments.add(paymentMapper.selectById(pid[i]));
            if (payments.get(0).getSponsorId() != payments.get(i).getSponsorId())
                return "하나의 회원만 선택해주세요.";
        }
        String receiptNo = receiptMapper.generateReceiptNo(corporateId, createDate);

        Receipt receipt = new Receipt();
        receipt.setNo(receiptNo);
        receipt.setSponsorId(payments.get(0).getSponsorId());
        receipt.setCreateDate(createDate);
        receiptMapper.insert(receipt);
        for (Payment p : payments) {
            p.setReceiptId(receipt.getId());
            paymentMapper.updateReceiptId(p);
        }
        return null;
	}

	public String deleteReceipt(int id) {
	    List<Payment> list = paymentMapper.selectByReceiptId(id);
	    for (Payment p : list) {
	        p.setReceiptId(null);
	        paymentMapper.updateReceiptId(p);
	    }
	    receiptMapper.delete(id);
	    return null;
	}

    public String createReceipt2(Map<String,Object> map) {
        int sponsorId = 0, corporateId = 0;
        Receipt receipt = null;
        String createDate = (String)map.get("createDate");
        List<Payment> payments = paymentMapper.selectForReceiptCreation2(map);

        for (Payment p : payments) {
            if (sponsorId != p.getSponsorId() || corporateId != p.getCorporateId()) {
                sponsorId = p.getSponsorId();
                corporateId = p.getCorporateId();
                receipt = new Receipt();
                receipt.setSponsorId(sponsorId);
                receipt.setCreateDate(createDate);
                receipt.setNo(receiptMapper.generateReceiptNo(corporateId, createDate));
                receiptMapper.insert(receipt);
            }
            //System.out.printf("receiptId = %d, paymentId = %d\n", receipt.getId(), p.getId());
            p.setReceiptId(receipt.getId());
            paymentMapper.updateReceiptId(p);
        }
        if (payments.size() == 0) return "조건에 해당하는 납입 건이 없습니다.";
        return null;
    }

}
