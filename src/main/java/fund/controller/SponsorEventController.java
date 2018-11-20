package fund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fund.dto.SponsorEvent;
import fund.dto.pagination.PaginationSponsor;
import fund.mapper.SponsorEventMapper;
import fund.mapper.SponsorMapper;
import fund.service.C;
import fund.service.LogService;
import fund.service.UserService;

@Controller
public class SponsorEventController extends BaseController {

    @Autowired SponsorMapper sponsorMapper;
    @Autowired SponsorEventMapper sponsorEventMapper;
    @Autowired LogService logService;

    @ModelAttribute
    void modelAttr1(Model model, @RequestParam("sid") int sid, @ModelAttribute("pagination") PaginationSponsor pagination) throws Exception {
        model.addAttribute("sponsor", sponsorMapper.selectById(sid));
    }

    @RequestMapping(value="/sponsor/event/list.do", method=RequestMethod.GET)
    public String list2(@RequestParam("sid") int sid, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("sponsor", sponsorMapper.selectById(sid));
        model.addAttribute("list", sponsorEventMapper.selectBySponsorId(sid));
        return "sponsor/event/list";
    }

    @RequestMapping(value="/sponsor/event/edit.do", method=RequestMethod.GET)
    public String edit2(Model model, @RequestParam("sid") int sid, @RequestParam("id") int id) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("sponsorEvent", sponsorEventMapper.selectById(id));
        return "sponsor/event/edit";
    }

    private String redirectToList(Model model, int sid) {
        PaginationSponsor pagination = (PaginationSponsor)model.asMap().get("pagination");
        String qs = String.format("sid=%d&%s", sid, pagination.getQueryString());
        return "redirect:list.do?" + qs;
    }

    @RequestMapping(value="/sponsor/event/edit.do", method=RequestMethod.POST, params="cmd=save")
    public String edit2save(Model model, @RequestParam("sid") int sid, SponsorEvent sponsorEvent) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            sponsorEventMapper.update(sponsorEvent);
            return redirectToList(model, sid);
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "sponsor/sponsorEvent/edit");
        }
    }

    @RequestMapping(value="/sponsor/event/edit.do", method=RequestMethod.POST, params="cmd=delete")
    public String edit2delete(Model model, @RequestParam("sid") int sid, @RequestParam("id") int id) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        sponsorEventMapper.delete(id);
        return redirectToList(model, sid);
    }

    @RequestMapping(value="/sponsor/event/create.do", method=RequestMethod.GET)
    public String create2(Model model, @RequestParam("sid") int sid) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("sponsorEvent", new SponsorEvent());
        return "sponsor/event/edit";
    }

    @RequestMapping(value="/sponsor/event/create.do", method=RequestMethod.POST)
    public String create2(Model model, @RequestParam("sid") int sid, SponsorEvent sponsorEvent) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            sponsorEvent.setSponsorId(sid);
            sponsorEvent.setUserId(UserService.getCurrentUser().getId());
            sponsorEventMapper.insert(sponsorEvent);
            return redirectToList(model, sid);
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "sponsor/event/edit");
        }
    }

}
