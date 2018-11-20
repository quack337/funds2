package fund.dto;

import java.util.Objects;

public class Code {
    int id;
    int codeGroupId;
    String codeName;
    boolean state;
    String etc1;
    String etc2;
    String etc3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodeGroupId() {
        return codeGroupId;
    }

    public void setCodeGroupId(int codeGroupId) {
        this.codeGroupId = codeGroupId;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getEtc1() {
        return etc1;
    }

    public void setEtc1(String etc1) {
        this.etc1 = etc1;
    }

    public String getEtc2() {
        return etc2;
    }

    public void setEtc2(String etc2) {
        this.etc2 = etc2;
    }

    public String getEtc3() {
        return etc3;
    }

    public void setEtc3(String etc3) {
        this.etc3 = etc3;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Code == false) return false;
        Code c = (Code)o;
        return id == c.id &&
               codeGroupId == c.codeGroupId &&
               Objects.equals(codeName, c.codeName) &&
               state == c.state &&
               Objects.equals(etc1, c.etc1) &&
               Objects.equals(etc2, c.etc2) &&
               Objects.equals(etc3, c.etc3);
    }

}
