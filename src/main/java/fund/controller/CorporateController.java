package fund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import fund.dto.Corporate;
import fund.mapper.CorporateMapper;
import fund.service.C;
import fund.service.LogService;
import fund.service.UserService;

@Controller
public class CorporateController extends BaseController{

	@Autowired CorporateMapper corporateMapper;
    @Autowired LogService logService;

	@RequestMapping("/corporate/list.do")
	public String list(Model model) {
        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
		model.addAttribute("list",corporateMapper.selectAll());
		return "corporate/list";
	}

	@RequestMapping(value="/corporate/create.do", method=RequestMethod.GET)
	public String create(Model model) {
        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
	    model.addAttribute("corporate", new Corporate());
		return "corporate/edit";
	}

	@RequestMapping(value="/corporate/create.do", method=RequestMethod.POST)
	public String create(Model model, Corporate corporate) {
	    try {
	        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
	        corporateMapper.insert(corporate);
	        return "redirect:/corporate/list.do";
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "corporate/edit");
        }
	}

	@RequestMapping(value="/corporate/edit.do", method=RequestMethod.GET)
	public String edit(Model model,@RequestParam("id") int id) {
        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
		model.addAttribute("corporate",corporateMapper.selectById(id));
		return "corporate/edit";
	}

	@RequestMapping(value="/corporate/edit.do", method=RequestMethod.POST, params="cmd=save")
    public String edit(Model model, Corporate corporate) {
	    try {
	        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
    		corporateMapper.update(corporate);
            return "redirect:/corporate/list.do";
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "corporate/edit");
        }
	}

    @RequestMapping(value="/corporate/edit.do", method=RequestMethod.POST, params="cmd=delete")
    public String delete(Model model, @RequestParam("id") int id) {
        try {
            if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
            corporateMapper.delete(id);
            return "redirect:/corporate/list.do";
        } catch (Exception e) {
            model.addAttribute("corporate",corporateMapper.selectById(id));
            return logService.logErrorAndReturn(model, e, "corporate/edit");
        }
    }
}
