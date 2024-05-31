package live.databo3.front.admin.controller;

import live.databo3.front.adaptor.MemberAdaptor;
import live.databo3.front.dto.MemberDto;
import live.databo3.front.dto.request.MemberModifyStateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminMemberController {
    private static final String ALERT="alert";

    private final MemberAdaptor memberAdaptor;

    private void alertHandler(Model model, String message, String url) {
        model.addAttribute("message", message);
        model.addAttribute("searchUrl", url);
    }

    @GetMapping("/admin/member-list")
    public String getMemberList(Model model){
        try{
            List<MemberDto> memberList = memberAdaptor.getMembers();
            model.addAttribute("memberList", memberList);
            return "admin/member_list";
        }catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "멤버 리스트 페이지를 불러오지 못하였습니다", "/");
        }
        return ALERT;
    }

    @GetMapping("/admin/owner-register-request")
    public String getOwnerStateWaitList(Model model){
        try{
            List<MemberDto> stateWaitOwnerList = memberAdaptor.getMembersByRoleAndState(2,1);
            model.addAttribute("memberList", stateWaitOwnerList);
            return "admin/owner_register_request";
        }catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "OWNER 회원가입 요청 페이지를 불러오지 못하였습니다", "/");
        }
        return ALERT;
    }

    @PostMapping("/admin/owner-register-request")
    public String modifyOwnerState(Model model, MemberModifyStateRequest request){
        try{
            memberAdaptor.modifyMemberState(request);
            return "redirect:/admin/owner-register-request";
        }catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "회원가입 승인 요청을 실패하였습니다", "/");
        }
        return ALERT;
    }

}
