package fund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fund.mapper.SponsorMapper;
import fund.service.Util;

@Controller
public class GuestController extends BaseController {

	@Autowired SponsorMapper sponsorMapper;
    @RequestMapping(value="/guest/login.do", method=RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("test", Util.hasIP("192.168.61.10") ? "(테스트 서버)" : "");
        return "guest/login";
    }
}