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
    
    public String getRepresentativeWithBlank() {
    	StringBuilder sb = new StringBuilder();
    	
    	for(int i=0; i<representative.length(); i++) {
    		sb.append(representative.charAt(i));
    		sb.append(" ");
    	}
    	return sb.toString();
    }
}
