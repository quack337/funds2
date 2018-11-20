package fund.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fund.dto.User;
import fund.dto.pagination.Pagination;
import fund.mapper.UserMapper;
import fund.service.C;
import fund.service.LogService;
import fund.service.UserService;


@Controller
public class UserController extends BaseController{

	@Autowired UserMapper userMapper;
	@Autowired UserService userService;
    @Autowired LogService logService;

    @RequestMapping(value="/user/myinfo.do", method=RequestMethod.GET)
    public String myinfo(Model model) {
        int id = UserService.getCurrentUser().getId();
        model.addAttribute("user", userMapper.selectById(id));
        model.addAttribute("menu", userService.selectMenuUserByUserId(id));
        return "user/myinfo";
    }

    @RequestMapping(value="/user/myinfo.do", method=RequestMethod.POST, params="cmd=saveInfo")
    public String saveMyInfo(Model model, User user) {
        try {
            user.setId(UserService.getCurrentUser().getId());
            if (user.getUserType() == null) user.setUserType(UserService.getCurrentUser().getUserType());
            if (userService.checkLoginId(user)) {
                logService.userInfoChange(user);
                userMapper.update(user);
                model.addAttribute("successMsg", "저장되었습니다.");
                if (UserService.getCurrentUser().getId() == user.getId())
                    UserService.setCurrentUser(user);
                model.addAttribute("menu", userService.selectMenuUserByUserId(user.getId()));
            } else model.addAttribute("errorMsg", "로그인 아이디가 중복됩니다.");
            return "user/myinfo";
        } catch (Exception e) {
            model.addAttribute("menu", userService.selectMenuUserByUserId(user.getId()));
            return logService.logErrorAndReturn(model, e, "user/myinfo");
        }
    }

    @RequestMapping(value="/user/myinfo.do", method=RequestMethod.POST, params="cmd=savePassword")
    public String saveMyPassword(Model model, User user) {
        try {
            user.setId(UserService.getCurrentUser().getId());
            if (userService.checkPassword(user.getPassword1())) {
                if (comparePassword(user)) {
                    user.setPassword(UserService.encryptPasswd(user.getPassword1()));
                    logService.userPasswordChange(user);
                    userMapper.updatePassword(user);
                    model.addAttribute("successMsg", "저장되었습니다.");
                } else model.addAttribute("errorMsg", "비밀번호가 일치하지 않습니다.");
            } else model.addAttribute("errorMsg", "비밀번호 작성 규칙에 어긋납니다.");
            model.addAttribute("menu", userService.selectMenuUserByUserId(user.getId()));
            return "user/myinfo";
        } catch (Exception e) {
            model.addAttribute("menu", userService.selectMenuUserByUserId(user.getId()));
            return logService.logErrorAndReturn(model, e, "user/myinfo");
        }
    }

    @RequestMapping("/user/list.do")
    public String list(Model model) {
        if (!UserService.canAccess(C.메뉴_시스템관리)) return "redirect:/home/logout.do";
        model.addAttribute("list", userMapper.selectAll());
        return "user/list";
    }

    @RequestMapping(value="/user/edit.do", method=RequestMethod.GET)
    public String edit(Model model, @RequestParam("id") int id) {
        if (UserService.canAccess(C.메뉴_시스템관리) == false) return "redirect:/home/logout.do";
        model.addAttribute("user", userMapper.selectById(id));
        model.addAttribute("menu", userService.selectMenuUserByUserId(id));
        return "user/edit";
    }

    @RequestMapping(value="/user/edit.do", method=RequestMethod.POST, params="cmd=saveInfo")
    public String saveInfo(Model model, User user) {
        try {
            if (UserService.canAccess(C.메뉴_시스템관리) == false) return "redirect:/home/logout.do";
            if (user.getUserType() == null) user.setUserType(UserService.getCurrentUser().getUserType());
            if (userService.checkLoginId(user)) {
                logService.userInfoChange(user);
                userMapper.update(user);
                model.addAttribute("successMsg", "저장되었습니다.");
                if (UserService.getCurrentUser().getId() == user.getId())
                    UserService.setCurrentUser(user);
                model.addAttribute("menu", userService.selectMenuUserByUserId(user.getId()));
            } else model.addAttribute("errorMsg", "로그인 아이디가 중복됩니다.");
            return "user/edit";
        } catch (Exception e) {
            model.addAttribute("menu", userService.selectMenuUserByUserId(user.getId()));
            return logService.logErrorAndReturn(model, e, "user/edit");
        }
    }

    @RequestMapping(value="/user/edit.do", method=RequestMethod.POST, params="cmd=savePassword")
    public String savePassword(Model model, User user) {
        try {
            if (UserService.canAccess(C.메뉴_시스템관리) == false) return "redirect:/home/logout.do";
            if (userService.checkPassword(user.getPassword1())) {
                if (comparePassword(user)) {
                    user.setPassword(UserService.encryptPasswd(user.getPassword1()));
                    logService.userPasswordChange(user);
                    userMapper.updatePassword(user);
                    model.addAttribute("successMsg", "저장되었습니다.");
                } else model.addAttribute("errorMsg", "비밀번호가 일치하지 않습니다.");
            } else model.addAttribute("errorMsg", "비밀번호 작성 규칙에 어긋납니다.");
            model.addAttribute("menu", userService.selectMenuUserByUserId(user.getId()));
            return "user/edit";
        } catch (Exception e) {
            model.addAttribute("menu", userService.selectMenuUserByUserId(user.getId()));
            return logService.logErrorAndReturn(model, e, "user/edit");
        }
    }

    private boolean comparePassword(User user) {
        return StringUtils.isBlank(user.getPassword1()) == false && user.getPassword1().equals(user.getPassword2());
    }

    @RequestMapping(value="/user/edit.do", method=RequestMethod.POST, params="cmd=saveMenu")
    public String saveInfo(Model model, User user, @RequestParam("menuId") Integer[] menuId) {
        try {
            if (UserService.canAccess(C.메뉴_시스템관리) == false) return "redirect:/home/logout.do";
            if (user.getUserType() == null) user.setUserType(UserService.getCurrentUser().getUserType());
            userService.saveMenuUser(user, menuId);
            model.addAttribute("menu", userService.selectMenuUserByUserId(user.getId()));
            model.addAttribute("successMsg", "저장되었습니다.");
            return "user/edit";
        } catch (Exception e) {
            model.addAttribute("menu", userService.selectMenuUserByUserId(user.getId()));
            return logService.logErrorAndReturn(model, e, "user/edit");
        }
    }

    @RequestMapping(value="/user/edit.do", method=RequestMethod.POST, params="cmd=delete")
    public String delete(Model model, @RequestParam("id") int id, Pagination pagination) {
        if (UserService.canAccess(C.메뉴_시스템관리) == false) return "redirect:/home/logout.do";
        logService.userDelete(id);
        userMapper.delete(id);
        return "redirect:list.do";
    }

    @RequestMapping(value="/user/create.do", method=RequestMethod.GET)
    public String create(Model model) {
        if (UserService.canAccess(C.메뉴_시스템관리) == false) return "redirect:/home/logout.do";
        model.addAttribute("user", new User());
        return "user/create";
    }

    @RequestMapping(value="/user/create.do", method=RequestMethod.POST)
    public String create(Model model, User user) {
        try {
            if (UserService.canAccess(C.메뉴_시스템관리) == false) return "redirect:/home/logout.do";
            if (userService.checkPassword(user.getPassword1())) {
                if (comparePassword(user)) {
                    if (userService.checkLoginId(user)) {
                        user.setPassword(UserService.encryptPasswd(user.getPassword1()));
                        logService.userCreate(user);
                        userMapper.insert(user);
                        return "redirect:list.do";
                    } else model.addAttribute("errorMsg", "로그인 아이디가 중복됩니다.");
                } else model.addAttribute("errorMsg", "비밀번호가 일치하지 않습니다.");
            } else model.addAttribute("errorMsg", "비밀번호 작성 규칙에 어긋납니다.");
            return "user/create";
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "user/create");
        }
    }
}