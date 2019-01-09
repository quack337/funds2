package fund.dto;

import lombok.Data;

@Data
public class Sponsor {

    // member 변수가 추가되면 SponsorService 수정해야 함.
    int id;
    String sponsorNo;
    String name;
    String juminNo;
    int sponsorType1Id;
    int sponsorType2Id;
    String sponsorTypeDetail;
    int churchId;
    String signUpDate;
    String mobilePhone;
    String recommender;
    String recommenderRelation;
    int mailTo;
    String homeAddress;
    String homeRoadAddress;
    String homeDetailAddress;
    String homePostCode;
    String homePhone;
    String email;
    String company;
    String department;
    String position;
    String representative;
    String officePhone;
    String officeAddress;
    String officeRoadAddress;
    String officeDetailAddress;
    String officePostCode;
    String etc;
    int mailReceiving;
    int emailReceiving;
    int smsReceiving;
    int piuaRequiredItem;
    int piuaOptionalItem;
    int piuaIdentification;

    String sponsorType1;
    String sponsorType2;
    String church;

    boolean dmError;
    String dmErrorEtc;

    public String getAddress() {
        if (mailTo == 0)
            return homeRoadAddress + " " + homeDetailAddress;
        return officeRoadAddress + " " + officeDetailAddress;
    }

    public String getPostCode() {
        return (mailTo == 0) ? homePostCode : officePostCode;
    }

    public Sponsor() { // junit 테스트를 위해 초기화
        this.juminNo = "";
        this.mobilePhone = "";
        this.recommender = "";
        this.recommenderRelation = "";
        this.homePhone = "";
        this.email = "";
        this.company = "";
        this.department = "";
        this.position = "";
        this.officePhone = "";
        this.etc = "";
    }

}