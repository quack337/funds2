package fund.dto;

import fund.utils.NumberUtils;
import lombok.Data;

@Data
public class Certificate {

    int id;
    int type;
    String certificateNo;
    int userId;
    String createDate;
    String personNo;
    String personName;
    String department;
    int amount;
    String body;
    String userName;
    Integer corporateId;
    String corporateName;
    String representative;
    
    public String getAmountToKorean() {
    	return NumberUtils.toKoreanNumber(amount, true, true);
    }
}
