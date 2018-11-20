package fund.dto;

import java.util.Objects;

public class DonationPurpose {
    int id;
    int corporateId;
    int organizationId;
    String code;
    String name;
    String gubun;
    boolean closed;
    String etc;

    String organizationName;
    String corporateName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(int corporateId) {
        this.corporateId = corporateId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationID) {
        this.organizationId = organizationID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DonationPurpose == false) return false;
        DonationPurpose d = (DonationPurpose)o;
        return
            id == d.id &&
            corporateId == d.corporateId &&
            organizationId == d.organizationId &&
            Objects.equals(code, d.code) &&
            Objects.equals(name, d.name) &&
            Objects.equals(gubun, d.gubun) &&
            Objects.equals(closed, d.closed) &&
            Objects.equals(etc, d.etc);
    }
}
