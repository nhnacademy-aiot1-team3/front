package live.databo3.front.admin.controller;

import live.databo3.front.adaptor.OrganizationAdaptor;
import live.databo3.front.adaptor.PlaceAdaptor;
import live.databo3.front.adaptor.SensorAdaptor;
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
public class AdminOrganizationController {
    private static final String ALERT="alert";
    private static final String ALERT_MESSAGE="message";
    private static final String ALERT_URL="searchUrl";

    private final OrganizationAdaptor organizationAdaptor;
    private final SensorAdaptor sensorAdaptor;
    private final PlaceAdaptor placeAdaptor;

    private void alertHandler(Model model, String message, String url) {
        model.addAttribute(ALERT_MESSAGE, message);
        model.addAttribute(ALERT_URL, url);
    }

    @GetMapping("/admin/my-page")
    public String getAdminMyPage(){
        return "admin/my_page";
    }

    @GetMapping("/admin/organization-list")
    public String getOrganizationList(Model model){
        try{
            List<OrganizationDto> organizationDtoList = organizationAdaptor.getOrganizations();
            model.addAttribute("organizationDtoList",organizationDtoList);
            return "admin/organization_list";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "조직 목록 페이지를 불러오지 못하였습니다", "/");
        }
        return ALERT;
    }

    @GetMapping("/admin/organization-management")
    public String getOrganization(Model model, int organizationId){
        try{
            OrganizationDto organization = organizationAdaptor.getOrganization(organizationId);
            List<SensorDto> sensorList = sensorAdaptor.getSensorsByOrganization(organizationId);
            int sensorAmount = sensorList.size();
            List<DeviceDto> deviceList = sensorAdaptor.getDevice(organizationId);
            List<SensorTypeDto> sensorTypeList = sensorAdaptor.getSensorType();
            List<SensorTypeMappingListDto> sensorTypeMappingList = sensorAdaptor.getGetSensorTypeMappingByOrganization(organizationId);
            List<MemberOrganizationDto> ownerMemberList = organizationAdaptor.getMemberByState(organizationId, 2, "ROLE_OWNER");
            List<MemberOrganizationDto> viewerMemberList = organizationAdaptor.getMemberByState(organizationId, 2, "ROLE_VIEWER");
            List<MemberOrganizationDto> ownerRequestMemberList = organizationAdaptor.getMemberByState(organizationId, 1, "ROLE_OWNER");
            int ownerMemberAmount = ownerMemberList.size();
            int viewerMemberAmount = viewerMemberList.size();
            List<PlaceDto> placeList = placeAdaptor.getPlaces(organizationId);

            model.addAttribute("organization",organization);
            model.addAttribute("sensorList",sensorList);
            model.addAttribute("sensorAmount",sensorAmount);
            model.addAttribute("sensorTypeList",sensorTypeList);
            model.addAttribute("sensorTypeMappingList",sensorTypeMappingList);
            model.addAttribute("deviceList",deviceList);
            model.addAttribute("ownerMemberList", ownerMemberList);
            model.addAttribute("viewerMemberList", viewerMemberList);
            model.addAttribute("ownerRequestMemberList", ownerRequestMemberList);
            model.addAttribute("ownerMemberAmount",ownerMemberAmount);
            model.addAttribute("viewerMemberAmount",viewerMemberAmount);
            model.addAttribute("placeList",placeList);

            return "admin/organization_management";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/admin/organization-list");
        } catch (Exception e) {
            alertHandler(model, "조직창 불러오기를 실패하였습니다", "/admin/organization-list");
        }
        return ALERT;
    }

    @PostMapping("/admin/organization")
    public String createOrganization(Model model, OrganizationRequest request){
        try{
            organizationAdaptor.createOrganization(request);
            return "redirect:/admin/organization-list";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/admin/organization-list");
        } catch (Exception e) {
            alertHandler(model, "조직 생성에 실패하였습니다", "/admin/organization-list");
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
            alertHandler(model, e.getMessage(), "/admin/organization-management?organizationId=" + organizationId);
        } catch (Exception e) {
            alertHandler(model, "부서명 변경에 실패하였습니다", "/admin/organization-management?organizationId=" + organizationId);
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
            alertHandler(model, e.getMessage(), "/admin/organization-management?organizationId=" + organizationId);
        } catch (Exception e) {
            alertHandler(model, "SN 변경에 실패하였습니다", "/admin/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }

    @PostMapping("/admin/deleteOrganization")
    public String deleteOrganization(Model model, int organizationId){
        try{
            organizationAdaptor.deleteOrganization(organizationId);
            return "redirect:/admin/organization-list";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "조직 삭제에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }

    @PostMapping("/admin/deleteOrganizationOwner")
    public String deleteOrganizationOwner(Model model, RedirectAttributes redirectAttributes, int organizationId, String memberId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            organizationAdaptor.deleteOrganizationOwner(organizationId, memberId);
            return "redirect:/admin/organization-management";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "owner 조직에서 삭제에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }

    @PostMapping("/admin/deleteOrganizationViewer")
    public String deleteOrganizationViewer(Model model, RedirectAttributes redirectAttributes, int organizationId, String memberId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            organizationAdaptor.deleteOrganizationOwner(organizationId, memberId);
            return "redirect:/admin/organization-management";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "viewer를 조직에서 삭제에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
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
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "OWNER 수락에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
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
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "센서 생성에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }

    @PostMapping("/admin/modifySensor")
    public String modifyDevice(Model model, RedirectAttributes redirectAttributes, SensorRequest request, int organizationId, String sensorSn){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            sensorAdaptor.modifySensor(organizationId, sensorSn, request);
            return "redirect:/admin/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "센서 변경에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
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
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "센서 삭제에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }

    @PostMapping("/admin/createSensorTypeMapping")
    public String createSensorTypeMapping(Model model, RedirectAttributes redirectAttributes, int organizationId, String sensorSn, int sensorTypeId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            sensorAdaptor.createSensorType(organizationId, sensorSn, sensorTypeId);
            return "redirect:/admin/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "센서 type 추가에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }

    @PostMapping("/admin/deleteSensorType")
    public String deleteSensorType(Model model,RedirectAttributes redirectAttributes, int organizationId, String sensorSn, int sensorTypeId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            sensorAdaptor.deleteSensorType(organizationId, sensorSn, sensorTypeId);
            return "redirect:/admin/organization-management";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "센서 type 삭제에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }

    @PostMapping("/admin/createDevice")
    public String createDevice(Model model, RedirectAttributes redirectAttributes, DeviceRequest request, int organizationId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            sensorAdaptor.createDevice(request, organizationId);
            return "redirect:/admin/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "디바이스 생성에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }

    @PostMapping("/admin/modifyDevice")
    public String modifyDevice(Model model, RedirectAttributes redirectAttributes, DeviceRequest request, int organizationId, String deviceId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            sensorAdaptor.modifyDevice(organizationId, deviceId, request);
            return "redirect:/admin/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "디바이스 변경에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }

    @PostMapping("/admin/deleteDevice")
    public String deleteDevice(Model model,RedirectAttributes redirectAttributes, int organizationId, String deviceSn){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            sensorAdaptor.deleteDevice(organizationId, deviceSn);
            return "redirect:/admin/organization-management";
        }catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "디바이스 삭제에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
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
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "Place 생성에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
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
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "place 변경에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
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
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }catch(Exception e) {
            model.addAttribute(ALERT_MESSAGE, "Place 삭제에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/admin/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }
}
