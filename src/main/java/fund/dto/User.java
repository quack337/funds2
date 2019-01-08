package fund.dto;

import java.util.List;

import lombok.Data;

@Data
public class User {
    int id;
    String loginName;
    String password;
    String name;
    String email;
    String userType;
    Integer corporateId;
    boolean enabled;

    String password1;
    String password2;
    String corporateName;

    List<Integer> menuIds;
}
