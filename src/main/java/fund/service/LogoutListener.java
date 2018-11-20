package fund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import fund.dto.User;
import fund.service.MyAuthenticationProvider.MyAuthenticaion;

@Component
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {

    @Autowired LogService logService;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
        List<SecurityContext> lstSecurityContext = event.getSecurityContexts();
        for (SecurityContext securityContext : lstSecurityContext)
        {
            User user = ((MyAuthenticaion)securityContext.getAuthentication()).getUser();
            logService.actionLog("로그아웃", "logout", user.getId(), user.getLoginName() + " " + user.getName(), user);
        }
    }

}