package live.databo3.front.admin.controller;

import live.databo3.front.admin.adaptor.AdminAdaptor;
import live.databo3.front.admin.dto.OrganizationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * admin 페이지 관련 controller
 *
 * @author 나채현
 * @version 1.0.2
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminAdaptor adminAdaptor;

    @GetMapping("/admin/my-page")
    public String getAdminMyPage(){
        return "admin/my_page";
    }

    @GetMapping("/admin/notification")
    public String getNotification(){
        return "/admin/notification";
    }
    /**
     * 공지사항 작성란 페이지로 이동하는 method
     * @param noticeNum 공지사항 번호
     * @param model 공지사항 객체를 담을 공간
     * @return 공지사항 작성란 페이지로 이동
     */
    @GetMapping("/admin/notice-writer")
    public String getNoticeWriter(@RequestParam(value = "number", required = false) String noticeNum, Model model){
        return "/admin/notice_writer";
    }

    @GetMapping("/admin/organization-management")
    public String getOrganization(){
        return "/admin/organization_management";
    }

    @GetMapping("/admin/owner-register-request")
    public String getOwnerRegisterRequest(){
        return "/admin/owner_register_request";
    }

    @GetMapping("/admin/organization-list")
    public String getOrganizationList(Model model){
//        List<OrganizationDto> organizationDtoList = adminAdaptor.getOrganizations();

        return "/admin/organization_list";
    }

    @GetMapping("/admin/member-list")
    public String getMemberList(){
        return "/admin/member_list";
    }
}
