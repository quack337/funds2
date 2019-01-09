package fund.dto;

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
}
