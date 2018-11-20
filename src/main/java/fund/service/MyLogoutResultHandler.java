package fund.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import fund.dto.User;
import fund.service.MyAuthenticationProvider.MyAuthenticaion;

@Component
public class MyLogoutResultHandler implements LogoutSuccessHandler {

    @Autowired LogService logService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        User user = ((MyAuthenticaion)authentication).getUser();
        logService.actionLog("로그아웃", "logout", user.getId(), user.getLoginName() + " " + user.getName(), user);
        response.sendRedirect(request.getContextPath() + "/guest/login.do?out");
    }
}
