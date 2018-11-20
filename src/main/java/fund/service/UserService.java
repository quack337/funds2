package fund.service;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fund.dto.MenuUser;
import fund.dto.User;
import fund.mapper.MenuUserMapper;
import fund.mapper.UserMapper;

@Service
public class UserService {

	@Autowired UserMapper userMapper;
    @Autowired MenuUserMapper menuUserMapper;
    @Autowired LogService logService;

	static final String 관리자 = "관리자";

    public static String encryptPasswd(String passwd) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = passwd.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++)
                sb.append(Integer.toHexString(0xff & digested[i]));
            return sb.toString();
        }
        catch (Exception e) {
            return passwd;
        }
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof MyAuthenticationProvider.MyAuthenticaion)
            return ((MyAuthenticationProvider.MyAuthenticaion) authentication).getUser();
        return null;
    }

    public static void setCurrentUser(User user) {
        ((MyAuthenticationProvider.MyAuthenticaion)
                SecurityContextHolder.getContext().getAuthentication()).setUser(user);
    }

    public static boolean isUserInRole(String... role) {
        User user = getCurrentUser();
        if (user == null) return false;
        for (String s : role)
            if (user.getUserType().equals(s))
                return true;
        return false;
    }

    public static boolean isCurrentUserAdmin() {
        return getCurrentUser() != null &&
                getCurrentUser().getUserType().equals(관리자);
    }

    public static boolean isAdmin(User user) {
        return user != null && user.getUserType().equals(관리자);
    }

    public boolean checkPassword(String s) {
        if (s.length() < 7) return false;
        int count = 0;
        if (s.matches(".*[^0-9a-zA-Z].*")) ++count;
        if (s.matches(".*[0-9].*")) ++count;
        if (s.matches(".*[a-z].*")) ++count;
        if (s.matches(".*[A-Z].*")) ++count;
        return count >= 3;
    }

    public boolean checkLoginId(User user) {
        User user1 = userMapper.selectByLoginName(user.getLoginName());
        return user1 == null || user1.getId() == user.getId();
    }

    public static boolean canAccess(int menuId) {
        User user = getCurrentUser();
        if (user == null) return false;
        if (isCurrentUserAdmin()) return true;
        return user.getMenuIds().contains(menuId);
    }

    public List<MenuUser> selectMenuUserByUserId(int userId) {
        List<MenuUser> list = menuUserMapper.selectMenuUserByUserId(userId);
        User user = userMapper.selectById(userId);
        if (isAdmin(user)) {
            for (MenuUser menu : list)
                menu.setEnabled(true);
        }
        return list;
    }

    public void saveMenuUser(User user, Integer[] menuId) {
        if (isAdmin(user)) return;
        List<MenuUser> list0 = menuUserMapper.selectMenuUserByUserId(user.getId());
        List<Integer> list1 = Arrays.asList(menuId);
        for (MenuUser m : list0) {
            if (m.isEnabled() && list1.contains(m.getMenuId()) == false) {
                logService.userMenuAccessRightRemove(user, m.getTitle());
                menuUserMapper.delete(m.getMenuId(), user.getId());
            }
            if (m.isEnabled() == false && list1.contains(m.getMenuId())) {
                logService.userMenuAccessRightAdd(user, m.getTitle());
                menuUserMapper.insert(m.getMenuId(), user.getId());
            }
        }
    }
}
