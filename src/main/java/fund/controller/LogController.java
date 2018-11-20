package fund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fund.dto.pagination.Pagination;
import fund.mapper.LogMapper;
import fund.service.C;
import fund.service.UserService;

@Controller
public class LogController extends BaseController {

    @Autowired LogMapper logMapper;

    @RequestMapping("/log/list.do")
    public String list(Model model, Pagination pagination,
        @RequestParam(value="cmd", required=false) String cmd,
        @RequestParam(value="id", required=false) int[] id) {
        if (!UserService.canAccess(C.메뉴_시스템관리)) return "redirect:/home/logout.do";
        if ("delete".equals(cmd) && id != null) {
            for (int i : id) logMapper.delete(i);
            return "redirect:list.do?" + pagination.getQueryString();
        }
        pagination.setRecordCount(logMapper.selectCount(pagination));
        model.addAttribute("list", logMapper.selectPage(pagination));
        return "log/list";
    }

    @RequestMapping("/log/detail.do")
    public String detail(Model model, @RequestParam("id") int id, Pagination pagination) {
        if (!UserService.canAccess(C.메뉴_시스템관리)) return "redirect:/home/logout.do";
        model.addAttribute("log", logMapper.selectById(id));
        return "log/detail";
    }

    @RequestMapping("/log/delete.do")
    public String delete(Model model, @RequestParam("id") int id, Pagination pagination) {
        if (!UserService.canAccess(C.메뉴_시스템관리)) return "redirect:/home/logout.do";
        logMapper.delete(id);
        return "redirect:list.do?" + pagination.getQueryString();
    }


}
