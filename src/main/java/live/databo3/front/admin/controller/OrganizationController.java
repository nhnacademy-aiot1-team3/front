package live.databo3.front.admin.controller;

import live.databo3.front.admin.adaptor.OrganizationAdaptor;
import live.databo3.front.admin.adaptor.PlaceAdaptor;
import live.databo3.front.admin.adaptor.SensorAdaptor;
import live.databo3.front.admin.dto.*;
import live.databo3.front.admin.dto.request.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
public class OrganizationController {
    private static final String ALERT="alert";
    private static final String ALERT_MESSAGE="message";
    private static final String ALERT_URL="searchUrl";

    private final OrganizationAdaptor organizationAdaptor;
    private final SensorAdaptor sensorAdaptor;
    private final PlaceAdaptor placeAdaptor;

    @GetMapping("/admin/my-page")
    public String getAdminMyPage(){
        return "admin/my_page";
    }

    @GetMapping("/admin/notification")
    public String getNotification(){
        return "admin/notification";
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

    @GetMapping("/admin/organization-list")
    public String getOrganizationList(Model model){
        try{
            List<OrganizationDto> organizationDtoList = organizationAdaptor.getOrganizations();
            model.addAttribute("organizationDtoList",organizationDtoList);
            return "/admin/organization_list";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "조직 목록 페이지를 불러오지 못하였습니다");
            model.addAttribute(ALERT_URL,"/");
        }
        return ALERT;
    }

    @GetMapping("/admin/organization-management")
    public String getOrganization(Model model, int organizationId){
        try{
            OrganizationDto organization = organizationAdaptor.getOrganization(organizationId);
            List<SensorDto> sensorList = sensorAdaptor.getSensorsByOrganization(organizationId);
            int sensorAmount = sensorList.size();
            List<MemberOrganizationDto> ownerMemberList = organizationAdaptor.getMemberByState(organizationId, 2, "ROLE_OWNER");
            List<MemberOrganizationDto> viewerMemberList = organizationAdaptor.getMemberByState(organizationId, 2, "ROLE_VIEWER");
            List<MemberOrganizationDto> ownerRequestMemberList = organizationAdaptor.getMemberByState(organizationId, 1, "ROLE_OWNER");
            int ownerMemberAmount = ownerMemberList.size();
            int viewerMemberAmount = viewerMemberList.size();
            List<PlaceDto> placeList = placeAdaptor.getPlaces(organizationId);

            model.addAttribute("organization",organization);
            model.addAttribute("sensorList",sensorList);
            model.addAttribute("sensorAmount",sensorAmount);
            model.addAttribute("ownerMemberList", ownerMemberList);
            model.addAttribute("viewerMemberList", viewerMemberList);
            model.addAttribute("ownerRequestMemberList", ownerRequestMemberList);
            model.addAttribute("ownerMemberAmount",ownerMemberAmount);
            model.addAttribute("viewerMemberAmount",viewerMemberAmount);
            model.addAttribute("placeList",placeList);

            return "/admin/organization_management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "조직 목록 페이지를 불러오지 못하였습니다");
            model.addAttribute(ALERT_URL,"/");
        }
        return ALERT;
    }

    @PostMapping("/admin/organization")
    public String createOrganization(Model model, OrganizationRequest request){
        try{
            organizationAdaptor.createOrganization(request);
            return "/admin/organization_list";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "조직 생성에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        }
        return ALERT;
    }

    @PostMapping("/admin/modifyOrganization")
    public String modifyOrganization(Model model, RedirectAttributes redirectAttributes, OrganizationRequest request, int organizationId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            organizationAdaptor.modifyOrganization(organizationId, request);
            return "redirect:/admin/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "부서명 변경에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        }
        return ALERT;
    }

    @PostMapping("/admin/modifyOrganizationSerialNumber")
    public String modifySerialNumber(Model model, RedirectAttributes redirectAttributes, OrganizationRequest request, int organizationId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            organizationAdaptor.modifySerialNumber(organizationId, request);
            return "redirect:/admin/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "SN 변경에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        }
        return ALERT;
    }

    @PostMapping("/admin/deleteOrganization")
    public String deleteOrganization(Model model, OrganizationIdRequest request){
        try{
            organizationAdaptor.deleteOrganization(request.getOrganizationId());
            return "/admin/member_list";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "조직 삭제에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        }
        return ALERT;
    }

    @PostMapping("/admin/modifyMemberState")
    public String modifyMemberState(Model model, RedirectAttributes redirectAttributes, int organizationId, String memberId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            organizationAdaptor.modifyMemberState(organizationId, memberId, 2);
            return "redirect:/admin/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "OWNER 수락에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        }
        return ALERT;
    }

    @PostMapping("/admin/createSensor")
    public String createSensor(Model model, RedirectAttributes redirectAttributes, SensorRequest request, int organizationId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            sensorAdaptor.createSensor(request, organizationId);
            return "redirect:/admin/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/");
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "센서 생성에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/");
        }
        return ALERT;
    }

    @PostMapping("/admin/deleteSensor")
    public String deleteSensor(Model model,RedirectAttributes redirectAttributes, int organizationId, String sensorSn){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            sensorAdaptor.deleteSensor(organizationId, sensorSn);
            return "redirect:/admin/organization-management";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management");
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "센서 삭제에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management");
        }
        return ALERT;
    }

    @PostMapping("/admin/createPlace")
    public String createPlace(Model model, RedirectAttributes redirectAttributes, PlaceRequest request, int organizationId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            placeAdaptor.createPlace(request, organizationId);
            return "redirect:/admin/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management");
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "Place 생성에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management");
        }
        return ALERT;
    }

    @PostMapping("/admin/modifyPlace")
    public String modifyPlace(Model model, RedirectAttributes redirectAttributes, PlaceRequest request, int organizationId, int placeId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            placeAdaptor.modifyPlace(organizationId, placeId, request);
            return "redirect:/admin/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "place 변경에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-list");
        }
        return ALERT;
    }

    @PostMapping("/admin/deletePlace")
    public String deletePlace(Model model,RedirectAttributes redirectAttributes, @RequestParam("organizationId") int organizationId, @RequestParam("placeId") int placeId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            placeAdaptor.deletePlace(organizationId, placeId);
            return "redirect:/admin/organization-management";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management");
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "Place 삭제에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management");
        }
        return ALERT;
    }
}
