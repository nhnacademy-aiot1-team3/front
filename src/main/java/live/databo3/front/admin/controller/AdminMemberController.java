package live.databo3.front.admin.controller;

import live.databo3.front.admin.adaptor.AdminMemberAdaptor;
import live.databo3.front.admin.dto.MemberDto;
import live.databo3.front.admin.dto.MemberModifyStateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminMemberController {

    private static final String ALERT="alert";
    private static final String ALERT_MESSAGE="message";
    private static final String ALERT_URL="searchUrl";

    private final AdminMemberAdaptor adminMemberAdaptor;

    @GetMapping("/admin/member-list")
    public String getMemberList(Model model){
        try{
            List<MemberDto> memberList = adminMemberAdaptor.getMembers();
            model.addAttribute("memberList", memberList);
            return "admin/member_list";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "멤버 리스트 페이지를 불러오지 못하였습니다");
            model.addAttribute(ALERT_URL,"/admin/");
        }
        return ALERT;
    }

    @GetMapping("/admin/owner-register-request")
    public String getOwnerStateWaitList(Model model){
        try{
            List<MemberDto> stateWaitOwnerList = adminMemberAdaptor.getMembersByRoleAndState(2,1);
            model.addAttribute("memberList", stateWaitOwnerList);
            return "/admin/owner_register_request";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "OWNER 회원가입 요청 페이지를 불러오지 못하였습니다");
            model.addAttribute(ALERT_URL,"/admin/");
        }
        return ALERT;
    }

    @PostMapping("/admin/owner-register-request")
    public String modifyOwnerState(Model model, MemberModifyStateRequest request){
        try{
            adminMemberAdaptor.modifyMember(request);
            return "redirect:/admin/owner-register-request";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/owner_register_request");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "회원가입 승인 요청을 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/owner_register_request");
        }
        return ALERT;
    }

}
