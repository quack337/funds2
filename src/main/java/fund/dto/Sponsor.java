package fund.dto;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJuminNo() {
        return juminNo;
    }

    public void setJuminNo(String juminNo) {
        this.juminNo = juminNo;
    }

    public int getSponsorType1Id() {
        return sponsorType1Id;
    }

    public void setSponsorType1Id(int sponsorType1Id) {
        this.sponsorType1Id = sponsorType1Id;
    }

    public int getSponsorType2Id() {
        return sponsorType2Id;
    }

    public void setSponsorType2Id(int sponsorType2Id) {
        this.sponsorType2Id = sponsorType2Id;
    }

    public String getSponsorTypeDetail() {
        return sponsorTypeDetail;
    }

    public void setSponsorTypeDetail(String sponsorTypeDetail) {
        this.sponsorTypeDetail = sponsorTypeDetail;
    }

    public int getChurchId() {
        return churchId;
    }

    public void setChurchId(int churchId) {
        this.churchId = churchId;
    }

    public String getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(String signUpDate) {
        this.signUpDate = signUpDate;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getRecommenderRelation() {
        return recommenderRelation;
    }

    public void setRecommenderRelation(String recommenderRelation) {
        this.recommenderRelation = recommenderRelation;
    }

    public int getMailTo() {
        return mailTo;
    }

    public void setMailTo(int mailTo) {
        this.mailTo = mailTo;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomeRoadAddress() {
        return homeRoadAddress;
    }

    public void setHomeRoadAddress(String homeRoadAddress) {
        this.homeRoadAddress = homeRoadAddress;
    }

    public String getHomeDetailAddress() {
        return homeDetailAddress;
    }

    public void setHomeDetailAddress(String homeDetailAddress) {
        this.homeDetailAddress = homeDetailAddress;
    }

    public String getHomePostCode() {
        return homePostCode;
    }

    public void setHomePostCode(String homePostCode) {
        this.homePostCode = homePostCode;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getOfficeRoadAddress() {
        return officeRoadAddress;
    }

    public void setOfficeRoadAddress(String officeRoadAddress) {
        this.officeRoadAddress = officeRoadAddress;
    }

    public String getOfficeDetailAddress() {
        return officeDetailAddress;
    }

    public void setOfficeDetailAddress(String officeDetailAddress) {
        this.officeDetailAddress = officeDetailAddress;
    }

    public String getOfficePostCode() {
        return officePostCode;
    }

    public void setOfficePostCode(String officePostCode) {
        this.officePostCode = officePostCode;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public int getMailReceiving() {
        return mailReceiving;
    }

    public void setMailReceiving(int mailReceiving) {
        this.mailReceiving = mailReceiving;
    }

    public int getEmailReceiving() {
        return emailReceiving;
    }

    public void setEmailReceiving(int emailReceiving) {
        this.emailReceiving = emailReceiving;
    }

    public int getSmsReceiving() {
        return smsReceiving;
    }

    public void setSmsReceiving(int smsReceiving) {
        this.smsReceiving = smsReceiving;
    }

    public int getPiuaRequiredItem() {
        return piuaRequiredItem;
    }

    public void setPiuaRequiredItem(int piuaRequiredItem) {
        this.piuaRequiredItem = piuaRequiredItem;
    }

    public int getPiuaOptionalItem() {
        return piuaOptionalItem;
    }

    public void setPiuaOptionalItem(int piuaOptionalItem) {
        this.piuaOptionalItem = piuaOptionalItem;
    }

    public int getPiuaIdentification() {
        return piuaIdentification;
    }

    public void setPiuaIdentification(int piuaIdentification) {
        this.piuaIdentification = piuaIdentification;
    }

    public String getSponsorType1() {
        return sponsorType1;
    }

    public void setSponsorType1(String sponsorType1) {
        this.sponsorType1 = sponsorType1;
    }

    public String getSponsorType2() {
        return sponsorType2;
    }

    public void setSponsorType2(String sponsorType2) {
        this.sponsorType2 = sponsorType2;
    }

    public String getChurch() {
        return church;
    }

    public void setChurch(String church) {
        this.church = church;
    }

    public boolean isDmError() {
        return dmError;
    }

    public void setDmError(boolean dmError) {
        this.dmError = dmError;
    }

    public String getDmErrorEtc() {
        return dmErrorEtc;
    }

    public void setDmErrorEtc(String dmErrorEtc) {
        this.dmErrorEtc = dmErrorEtc;
    }

}