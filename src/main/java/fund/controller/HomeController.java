package fund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fund.mapper.TodoMapper;
import fund.service.UserService;

@Controller
public class HomeController extends BaseController {

	@Autowired TodoMapper todoMapper;

    @RequestMapping(value= {"/", "/home/index.do"}, method=RequestMethod.GET)
    public String index(Model model) {
        int userId = UserService.getCurrentUser().getId();
        model.addAttribute("todos", todoMapper.selectAlert(userId));
        return "home/index";
    }

    @RequestMapping(value="/home/confirm.do", method=RequestMethod.GET)
    public String confirm(Model model, @RequestParam("id") int id) {
        todoMapper.confirm(id);
        return "redirect:index.do";
    }

}