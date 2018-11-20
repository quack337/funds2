package fund.dto;

import java.util.Objects;

public class Corporate {

    int id;
    String name;
    String shortName;
    String corporateNo;
    String representative;
    String roadAddress;
    String detailAddress;
    String postCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCorporateNo() {
        return corporateNo;
    }

    public void setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Corporate == false) return false;
        Corporate c = (Corporate)o;
        return
            id == c.id &&
            Objects.equals(name, c.name) &&
            Objects.equals(shortName, c.shortName) &&
            Objects.equals(corporateNo, c.corporateNo) &&
            Objects.equals(representative, c.representative) &&
            Objects.equals(roadAddress, c.roadAddress) &&
            Objects.equals(detailAddress, c.detailAddress) &&
            Objects.equals(postCode, c.postCode);
    }

}
