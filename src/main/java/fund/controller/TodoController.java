package fund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fund.dto.Todo;
import fund.dto.pagination.Pagination;
import fund.mapper.SponsorMapper;
import fund.mapper.TodoMapper;
import fund.service.C;
import fund.service.LogService;
import fund.service.UserService;

@Controller
public class TodoController extends BaseController {

    @Autowired SponsorMapper sponsorMapper;
    @Autowired TodoMapper todoMapper;
    @Autowired LogService logService;

    @RequestMapping(value="/todo/list.do", method=RequestMethod.GET)
    public String index(Model model, Pagination pagination) {
        if (!UserService.canAccess(C.메뉴_기타_일정관리)) return "redirect:/home/logout.do";
        int userId = UserService.getCurrentUser().getId();
        pagination.setSs(userId);
        pagination.setRecordCount(todoMapper.selectCount(pagination));
        model.addAttribute("list", todoMapper.selectPage(pagination));
        return "todo/list";
    }

    @RequestMapping(value="/todo/edit.do", method=RequestMethod.GET)
    public String edit(Model model, Pagination pagination, @RequestParam("id") int id) {
        if (!UserService.canAccess(C.메뉴_기타_일정관리)) return "redirect:/home/logout.do";
        model.addAttribute("todo", todoMapper.selectById(id));
        return "todo/edit";
    }

    @RequestMapping(value="/todo/edit.do", method=RequestMethod.POST, params="cmd=save")
    public String save(Model model, Pagination pagination, Todo todo) {
        try {
            if (!UserService.canAccess(C.메뉴_기타_일정관리)) return "redirect:/home/logout.do";
            todoMapper.update(todo);
            model.addAttribute("successMsg", "저장했습니다.");
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "todo/edit");
        }
        model.addAttribute("todo", todoMapper.selectById(todo.getId()));
        return "todo/edit";
    }

    @RequestMapping(value="/todo/edit.do", method=RequestMethod.POST, params="cmd=delete")
    public String delete(Model model, Pagination pagination, @RequestParam("id") int id) {
        try {
            if (!UserService.canAccess(C.메뉴_기타_일정관리)) return "redirect:/home/logout.do";
            todoMapper.delete(id);
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "todo/edit");
        }
        return "redirect:list.do?" + pagination.getQueryString();
    }

    @RequestMapping(value="/todo/edit.do", method=RequestMethod.POST, params="cmd=cancelConfirm")
    public String cancelConfirm(Model model, Pagination pagination, Todo todo) {
        try {
            if (!UserService.canAccess(C.메뉴_기타_일정관리)) return "redirect:/home/logout.do";
            todoMapper.cancelConfirm(todo.getId());
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "todo/edit");
        }
        model.addAttribute("todo", todoMapper.selectById(todo.getId()));
        return "todo/edit";
    }

    @RequestMapping(value="/todo/create.do", method=RequestMethod.GET)
    public String create(Model model, Pagination pagination) {
        if (!UserService.canAccess(C.메뉴_기타_일정관리)) return "redirect:/home/logout.do";
        model.addAttribute("todo", new Todo());
        return "todo/edit";
    }

    @RequestMapping(value="/todo/create.do", method=RequestMethod.POST)
    public String create(Model model, Pagination pagination, Todo todo) {
        try {
            if (!UserService.canAccess(C.메뉴_기타_일정관리)) return "redirect:/home/logout.do";
            todo.setUserId(UserService.getCurrentUser().getId());
            todoMapper.insert(todo);
            model.addAttribute("successMsg", "저장했습니다.");
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "todo/edit");
        }
        return "redirect:list.do";
    }

}
