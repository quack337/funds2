package fund.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import fund.dto.Log;
import fund.dto.User;
import fund.mapper.LogMapper;
import fund.mapper.UserMapper;

@Service
public class LogService {

    static Pattern errorMsgParrern = Pattern.compile("@&([^&]+)&@");
    @Autowired LogMapper logMapper;
    @Autowired UserMapper userMapper;

    public String logError(Exception e) {
        String errorMsg = this.insert(e);
        return errorMsg;
    }

    public String logErrorAndReturn(Model model, Exception e, String returnStr) {
        String errorMsg = this.insert(e);
        model.addAttribute("errorMsg", errorMsg);
        return returnStr;
    }

    static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        if (queryString == null) return requestURL.toString();
        return requestURL.append('?').append(queryString).toString();
    }

    public String insert(Exception e) {
        try {
            Log log = new Log();
            log.setBody(ExceptionUtils.getStackTrace(e));
            log.setCategory(e.getClass().getName());
            setEtc(log);
            logMapper.insert(log);

            String message = e.getMessage();
            Matcher m = errorMsgParrern.matcher(message);
            if (m.find()) return m.group(1);
        } catch (Exception e2) {
            e.printStackTrace(System.out);
            e2.printStackTrace(System.out);
        }
        return "작업 실패";
    }

    private void setEtc(Log log) {
        setEtc(log, null);
    }

    private void setEtc(Log log, User user) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.setUrl(getFullURL(request));
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = request.getRemoteAddr();
        log.setIp(ip);

        if (user == null)
            user = UserService.getCurrentUser();
        log.setCurrentUser(user == null ? null : user.getLoginName());
    }

    public void userDelete(int id) {
        User user = userMapper.selectById(id);
        StringBuilder builder = new StringBuilder();
        사용자정보요약(builder, user);
        현재사용자정보(builder);
        String body = builder.toString();
        Log log = new Log();
        log.setCategory("사용자 계정 삭제");
        log.setBody(body);
        setEtc(log);
        logMapper.insert(log);
    }

    public void userCreate(User user) {
        StringBuilder builder = new StringBuilder();
        사용자정보요약(builder, user);
        현재사용자정보(builder);
        String body = builder.toString();
        Log log = new Log();
        log.setCategory("사용자 계정 생성");
        log.setBody(body);
        setEtc(log);
        logMapper.insert(log);
    }

    public void userInfoChange(User user) {
        User user0 = null;
        String category = null;
        user0 = userMapper.selectById(user.getId());
        if (user0 == null)
            category = "사용자 계정 생성";
        else {
            if (user0.getUserType().equals(user.getUserType()) == false)
                category = "사용자 계정 권한 변경";
        }
        if (category == null) {
            if (user0.isEnabled() != user.isEnabled())
                category = "사용자 계정 상태 변경";
        }
        if (category != null) {
            StringBuilder builder = new StringBuilder();
            if (category.contains("변경")) {
                builder.append("---- 변경 전 ---\n");
                사용자정보요약(builder, user0);
                builder.append("\n\n")
                       .append("---- 변경 후 ---\n");
            }
            사용자정보요약(builder, user);
            현재사용자정보(builder);
            String body = builder.toString();
            Log log = new Log();
            log.setCategory(category);
            log.setBody(body);
            setEtc(log);
            logMapper.insert(log);
        }
    }

    private void 사용자정보요약(StringBuilder builder, User user) {
        builder.append("사용자 계정: ").append(user.getLoginName()).append("\n")
               .append("사용자 이름: ").append(user.getName()).append("\n")
               .append("사용자 유형: ").append(user.getUserType()).append("\n")
               .append("계정 활성화: ").append(user.isEnabled()).append("\n");
    }

    private void 현재사용자정보(StringBuilder builder) {
        User user = UserService.getCurrentUser();
        builder.append("\n\n\n--- 작업자 ---\n")
               .append("사용자 계정: ").append(user.getLoginName()).append("\n")
               .append("사용자 이름: ").append(user.getName()).append("\n")
               .append("사용자 유형: ").append(user.getUserType()).append("\n");
    }

    public void userPasswordChange(User user) {
        String category = "비밀번호 변경";
        StringBuilder builder = new StringBuilder();
        사용자정보요약(builder, user);
        현재사용자정보(builder);
        String body = builder.toString();
        Log log = new Log();
        log.setCategory(category);
        log.setBody(body);
        setEtc(log);
        logMapper.insert(log);
    }

    public void userMenuAccessRightAdd(User user, String menuTitle) {
        StringBuilder builder = new StringBuilder();
        builder.append(menuTitle).append("\n\n");
        사용자정보요약(builder, user);
        현재사용자정보(builder);
        String body = builder.toString();
        Log log = new Log();
        log.setCategory("메뉴 권한 추가");
        log.setBody(body);
        setEtc(log);
        logMapper.insert(log);
    }

    public void userMenuAccessRightRemove(User user, String menuTitle) {
        StringBuilder builder = new StringBuilder();
        builder.append(menuTitle).append("\n\n");
        사용자정보요약(builder, user);
        현재사용자정보(builder);
        String body = builder.toString();
        Log log = new Log();
        log.setCategory("메뉴 권한 제거");
        log.setBody(body);
        setEtc(log);
        logMapper.insert(log);
    }

    public void actionLog(String category, String action, int id, String no) {
        actionLog(category, action, id, no, null);
    }

    public void actionLog(String category, String action, int id, String no, User user) {
        Log log = new Log();
        log.setCategory(category);
        StringBuilder builder = new StringBuilder();
        builder.append("action: ").append(action).append("\n")
               .append("    ID: ").append(id).append("\n")
               .append("    no: ").append(no).append("\n");
        log.setBody(builder.toString());
        setEtc(log, user);
        logMapper.insert(log);
    }
}
