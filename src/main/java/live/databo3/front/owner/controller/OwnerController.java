package live.databo3.front.owner.controller;

import live.databo3.front.adaptor.*;
import live.databo3.front.dto.SettingFunctionTypeDto;
import live.databo3.front.owner.dto.ErrorLogResponseDto;
import live.databo3.front.owner.dto.SensorListDto;
import live.databo3.front.admin.dto.*;

import live.databo3.front.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * owner 페이지 관련 controller
 *
 * @author 나채현
 * @version 1.0.2
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OwnerController {

    private static final String ALERT="alert";
    private static final String ALERT_MESSAGE="message";
    private static final String ALERT_URL="searchUrl";

    private final OrganizationAdaptor organizationAdaptor;
    private final SensorAdaptor sensorAdaptor;
    private final SettingFunctionTypeAdaptor settingFunctionTypeAdaptor;
    private final PlaceAdaptor placeAdaptor;
    private final ErrorLogAdaptor errorLogAdaptor;

    private void alertHandler(Model model, String message, String url) {
        model.addAttribute(ALERT_MESSAGE, message);
        model.addAttribute(ALERT_URL, url);
    }


    @GetMapping("/owner/my-page")
    public String getOwnerMyPage(){
        return "owner/my_page";
    }

    @GetMapping("/owner/sensor-list")
    public String getSensorList(){
        return "owner/sensor_list";
    }


    @GetMapping("/owner/sensor-page")
    public String getSensorPage(Model model, int sensorType, String type, HttpServletRequest request){
        String access_token = CookieUtil.findCookie(request, "access_token").getValue();
        try{
            List<SensorListDto> sensorList = sensorAdaptor.getOrganizationListBySensorType(sensorType);
            List<SettingFunctionTypeDto> settingFunctionTypeList = settingFunctionTypeAdaptor.getSettingFunctionTypes();
            model.addAttribute("sensorList", sensorList);
            model.addAttribute("settingFunctionTypeList", settingFunctionTypeList);
            model.addAttribute("type", type);
            model.addAttribute("get_access_token", access_token);
            return "owner/sensor_page";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "센서 페이지를 불러오지 못하였습니다", "/");
        }
        return ALERT;
    }

    @GetMapping("/owner/battery-level")
    public String getBatteryLevel(){
        return "owner/battery_level";
    }

    @GetMapping("/owner/setting")
    public String getSensorSetting(Model model, String type){
        try{
            List<SensorListDto> temperatureSensorList = sensorAdaptor.getOrganizationListBySensorType(1);
            List<SensorListDto> humiditySensorList = sensorAdaptor.getOrganizationListBySensorType(2);
            List<SensorListDto> co2SensorList = sensorAdaptor.getOrganizationListBySensorType(3);
            List<SettingFunctionTypeDto> settingFunctionTypeList = settingFunctionTypeAdaptor.getSettingFunctionTypes();
            model.addAttribute("temperatureSensorList", temperatureSensorList);
            model.addAttribute("humiditySensorList", humiditySensorList);
            model.addAttribute("co2SensorList", co2SensorList);
            model.addAttribute("settingFunctionTypeList", settingFunctionTypeList);
            return "owner/setting";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "센서 페이지를 불러오지 못하였습니다", "/");
        }
        return ALERT;
    }

    @GetMapping("/owner/organization-list")
    public String getOrganizationList(Model model){
        try{
            List<OrganizationDto> organizationWithoutList = organizationAdaptor.getOrganizationsWithoutMember();
            List<OrganizationDto> organizationList = organizationAdaptor.getOrganizationsByMember();

            model.addAttribute("organizationWithoutList",organizationWithoutList);
            model.addAttribute("organizationList",organizationList);
            return "owner/organization_list";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "조직 목록 페이지를 불러오지 못하였습니다", "/");
        }
        return ALERT;
    }

    @GetMapping("/owner/organization-management")
    public String getOrganization(Model model, int organizationId){
        try{
            OrganizationDto organization = organizationAdaptor.getOrganization(organizationId);
            List<SensorDto> sensorList = sensorAdaptor.getSensorsByOrganization(organizationId);
            int sensorAmount = sensorList.size();
            List<DeviceDto> deviceList = sensorAdaptor.getDevice(organizationId);
            List<SensorTypeMappingListDto> sensorTypeMappingList = sensorAdaptor.getGetSensorTypeMappingByOrganization(organizationId);
            List<MemberOrganizationDto> ownerMemberList = organizationAdaptor.getMemberByState(organizationId, 2, "ROLE_OWNER");
            List<MemberOrganizationDto> viewerMemberList = organizationAdaptor.getMemberByState(organizationId, 2, "ROLE_VIEWER");
            List<MemberOrganizationDto> viewerRequestMemberList = organizationAdaptor.getMemberByState(organizationId, 1, "ROLE_VIEWER");
            int ownerMemberAmount = ownerMemberList.size();
            int viewerMemberAmount = viewerMemberList.size();
            List<PlaceDto> placeList = placeAdaptor.getPlaces(organizationId);

            model.addAttribute("organization",organization);
            model.addAttribute("sensorAmount",sensorAmount);
            model.addAttribute("sensorTypeMappingList",sensorTypeMappingList);
            model.addAttribute("deviceList",deviceList);
            model.addAttribute("ownerMemberList", ownerMemberList);
            model.addAttribute("viewerMemberList", viewerMemberList);
            model.addAttribute("viewerRequestMemberList", viewerRequestMemberList);
            model.addAttribute("ownerMemberAmount",ownerMemberAmount);
            model.addAttribute("viewerMemberAmount",viewerMemberAmount);
            model.addAttribute("placeList",placeList);

            return "owner/organization_management";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/owner/organization-list");
        } catch (Exception e) {
            alertHandler(model, "조직창 불러오기를 실패하였습니다", "/owner/organization-list");
        }
        return ALERT;
    }

    @GetMapping("/owner/error")
    public String getErrorLogs(Model model){
        try {
            Map<OrganizationDto, List<ErrorLogResponseDto>> errorLog = new HashMap<>();
            List<OrganizationDto> organizationList = organizationAdaptor.getOrganizationsByMember();
            organizationList.forEach(org -> {
                Integer organizationId = org.getOrganizationId();
                errorLog.put(organizationAdaptor.getOrganization(organizationId), errorLogAdaptor.getErrorLog(organizationId));
            });

            model.addAttribute("errorLog", errorLog);

            return "owner/error";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "에러 목록 불러오기를 실패하였습니다", "/");
        }
        return ALERT;
    }

    @PostMapping("/owner/postMemberOrgs")
    public String postMemberOrgs(Model model, int organizationId){
        try{
            organizationAdaptor.postMemberOrgs(organizationId);
            return "redirect:/owner/organization-list";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/owner/organization-list");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "조직 신청에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/owner/organization-list");
        }
        return ALERT;
    }

    @PostMapping("/owner/modifyMemberState")
    public String modifyMemberState(Model model, RedirectAttributes redirectAttributes, int organizationId, String memberId){
        redirectAttributes.addAttribute("organizationId", organizationId);
        try{
            organizationAdaptor.modifyMemberState(organizationId, memberId, 2);
            return "redirect:/owner/organization-management";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/owner/organization-management?organizationId=" + organizationId);
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "VIEWER 수락에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/owner/organization-management?organizationId=" + organizationId);
        }
        return ALERT;
    }
}
