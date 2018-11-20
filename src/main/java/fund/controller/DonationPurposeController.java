package fund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Strings;

import fund.dto.DonationPurpose;
import fund.dto.pagination.Pagination;
import fund.mapper.CodeMapper;
import fund.mapper.CorporateMapper;
import fund.mapper.DonationPurposeMapper;
import fund.mapper2.DataFileMapper;
import fund.service.C;
import fund.service.LogService;
import fund.service.UserService;

@Controller
public class DonationPurposeController extends BaseController{

	@Autowired DonationPurposeMapper donationPurposeMapper;
	@Autowired CorporateMapper corporateMapper;
	@Autowired CodeMapper codeMapper;
    @Autowired LogService logService;
    @Autowired DataFileMapper dataFileMapper;

	@RequestMapping("/donationPurpose/list.do")
	public String list(Model model, Pagination pagination) {
	    if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
	    if (Strings.isNullOrEmpty(pagination.getSrchText())) pagination.setSrchType(0);
        pagination.setRecordCount(donationPurposeMapper.selectCount(pagination));
        model.addAttribute("list", donationPurposeMapper.selectPage(pagination));
		return "donationPurpose/list";
	}

	@RequestMapping(value="/donationPurpose/create.do", method=RequestMethod.GET)
	public String create(Model model) {
        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
	    model.addAttribute("donationPurpose", new DonationPurpose());
		addCodesToModel(model);
		return "donationPurpose/edit";
	}

    private void addCodesToModel(Model model) {
        model.addAttribute("corporateList", corporateMapper.selectAll());
		model.addAttribute("organizationList",codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_기관종류));
    }

	@RequestMapping(value="/donationPurpose/create.do", method=RequestMethod.POST)
	public String create(DonationPurpose donationPurpose, Model model) {
	    try {
	        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
    	    donationPurposeMapper.insert(donationPurpose);
    		return "redirect:/donationPurpose/list.do";
        } catch (Exception e) {
            addCodesToModel(model);
            return logService.logErrorAndReturn(model, e, "donationPurpose/edit");
        }
	}

	@RequestMapping(value="/donationPurpose/edit.do", method=RequestMethod.GET)
	public String edit(Model model,@RequestParam("id") int id) {
        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
        model.addAttribute("donationPurpose", donationPurposeMapper.selectById(id));
        model.addAttribute("files", dataFileMapper.selectByForeignId("donationPurpose", id));
		addCodesToModel(model);
		return "donationPurpose/edit";
	}

	@RequestMapping(value="/donationPurpose/edit.do", method=RequestMethod.POST, params="cmd=save")
	public String edit(DonationPurpose donationPurpose, Model model) {
	    try {
	        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
    	    donationPurposeMapper.update(donationPurpose);
    		return "redirect:/donationPurpose/list.do";
        } catch (Exception e) {
            addCodesToModel(model);
            return logService.logErrorAndReturn(model, e, "donationPurpose/edit");
        }
	}

    @RequestMapping(value="/donationPurpose/edit.do", method=RequestMethod.POST, params="cmd=delete")
	public String delete(Model model, @RequestParam("id") int id) {
        if (!UserService.canAccess(C.메뉴_기초정보관리)) return "redirect:/home/logout.do";
		donationPurposeMapper.delete(id);
		return "redirect:/donationPurpose/list.do";
	}

}
