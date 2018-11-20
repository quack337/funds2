package fund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import fund.dto.Code;
import fund.mapper.CodeMapper;
import fund.service.C;
import fund.service.LogService;
import fund.service.UserService;

@Controller
public class CodeController extends BaseController{

    @Autowired CodeMapper codeMapper;
    @Autowired LogService logService;

    @RequestMapping("/code/list.do")
    public String list(Model model, @RequestParam("gid") int codeGroupId) {
        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
        model.addAttribute("list", codeMapper.selectAllByCodeGroupId(codeGroupId));
        addCodesToModel(model, codeGroupId);
        return "code/list";
    }

    @RequestMapping(value="/code/create.do", method=RequestMethod.GET)
    public String create(Model model, @RequestParam("gid") int codeGroupId) {
        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
        Code code = new Code();
        code.setCodeGroupId(codeGroupId);
        model.addAttribute("code", code);
        addCodesToModel(model, codeGroupId);
        return "code/edit";
    }

    private void addCodesToModel(Model model, int codeGroupId) {
        model.addAttribute("codeGroup", codeMapper.selectCodeGroupById(codeGroupId));
    }

    @RequestMapping(value="/code/create.do", method=RequestMethod.POST)
    public String create(Model model, Code code, @RequestParam("gid") int codeGroupId) {
        try {
            if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
            codeMapper.insert(code);
            return "redirect:list.do?gid=" + codeGroupId;
        } catch (Exception e) {
            addCodesToModel(model, codeGroupId);
            return logService.logErrorAndReturn(model, e, "code/edit");
        }
    }

    @RequestMapping(value="/code/edit.do", method=RequestMethod.GET)
    public String edit(Model model, @RequestParam("id") int id, @RequestParam("gid") int codeGroupId) {
        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
        model.addAttribute("code", codeMapper.selectById(id));
        addCodesToModel(model, codeGroupId);
        return "code/edit";
    }

    @RequestMapping(value="/code/edit.do", method=RequestMethod.POST, params="cmd=save")
    public String edit(Model model, Code code, @RequestParam("gid") int codeGroupId) {
        try {
            if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
            codeMapper.update(code);
            return "redirect:list.do?gid=" + codeGroupId;
        } catch (Exception e) {
            addCodesToModel(model, codeGroupId);
            return logService.logErrorAndReturn(model, e, "code/edit");
        }
    }

    @RequestMapping(value="/code/edit.do", method=RequestMethod.POST, params="cmd=delete")
    public String delete(Model model, @RequestParam("id") int id, @RequestParam("gid") int codeGroupId) {
        try {
            if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
            codeMapper.delete(id);
            return "redirect:list.do?gid=" + codeGroupId;
        } catch (Exception e) {
            //model.addAttribute("code", codeMapper.selectById(id));
            addCodesToModel(model, codeGroupId);
            return logService.logErrorAndReturn(model, e, "code/edit");
        }
    }

}
