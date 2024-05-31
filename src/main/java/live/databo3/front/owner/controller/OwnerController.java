package live.databo3.front.owner.controller;

import live.databo3.front.adaptor.*;
 
import live.databo3.front.dto.*;
import live.databo3.front.dto.request.GeneralConfigRequest;
import live.databo3.front.dto.request.UpdatePasswordRequest;
import live.databo3.front.dto.request.ValueConfigRequest;
import live.databo3.front.member.dto.MemberRequestDto;
import live.databo3.front.owner.dto.DeviceLogResponseDto;

import live.databo3.front.owner.dto.ErrorLogResponseDto;
import live.databo3.front.owner.dto.MainConfigurationCreateDto;
import live.databo3.front.owner.dto.MainConfigurationDto;
import live.databo3.front.owner.dto.SensorListDto;



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

import java.util.Objects;
import java.util.stream.Collectors;



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

    private final MemberAdaptor memberAdaptor;
    private final OrganizationAdaptor organizationAdaptor;
    private final SensorAdaptor sensorAdaptor;
    private final SettingFunctionTypeAdaptor settingFunctionTypeAdaptor;
    private final PlaceAdaptor placeAdaptor;
    private final ErrorLogAdaptor errorLogAdaptor;
    private final MainConfigurationAdaptor mainConfigurationAdaptor;
    private final DeviceLogAdaptor deviceLogAdaptor;
    private final GeneralConfigAdaptor generalConfigAdaptor;
    private final ValueConfigAdaptor valueConfigAdaptor;


    private void alertHandler(Model model, String message, String url) {
        model.addAttribute(ALERT_MESSAGE, message);
        model.addAttribute(ALERT_URL, url);
    }


    @GetMapping("/owner/my-page")
    public String getOwnerMyPage(){
        return "owner/my_page";
    }

    @PostMapping("/owner/modifyPassword")
    public String modifyPassword(Model model, String memberId, String currentPassword, String checkPassword, String modifyPassword){
        try{
            if(!checkPassword.equals(modifyPassword)){
                alertHandler(model, "New Password와 Confirm Password가 다릅니다", "/owner/my-page");
                return ALERT;
            }
            memberAdaptor.doLogin(new MemberRequestDto(memberId, currentPassword));
            memberAdaptor.modifyPassword(memberId, new UpdatePasswordRequest(modifyPassword));
            return "owner/my_page";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getStatusText(), "/owner/my-page");
        } catch (Exception e) {
            alertHandler(model, "비밀번호 변경에 실패하였습니다", "/owner/my-page");
        }
        return ALERT;
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
    public String getSensorSetting(Model model){
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
            alertHandler(model, "설정 페이지를 불러오지 못하였습니다", "/");
        }
        return ALERT;
    }

    @GetMapping("/owner/get-device")
    public String getSensorSettingClick(Model model, int organizationId, String organizationName, String placeName, String sensorSn, int sensorTypeId){
        try{
            List<SensorListDto> temperatureSensorList = sensorAdaptor.getOrganizationListBySensorType(1);
            List<SensorListDto> humiditySensorList = sensorAdaptor.getOrganizationListBySensorType(2);
            List<SensorListDto> co2SensorList = sensorAdaptor.getOrganizationListBySensorType(3);
            List<SettingFunctionTypeDto> settingFunctionTypeList = settingFunctionTypeAdaptor.getSettingFunctionTypes();
            List<DeviceDto> deviceList = sensorAdaptor.getDevice(organizationId);
            GeneralConfigDto generalConfig = generalConfigAdaptor.getGeneralConfig(organizationId, sensorSn, sensorTypeId);
            ConfigsDto valueConfig = valueConfigAdaptor.getValueConfig(organizationId,sensorSn,sensorTypeId);
            model.addAttribute("temperatureSensorList", temperatureSensorList);
            model.addAttribute("humiditySensorList", humiditySensorList);
            model.addAttribute("co2SensorList", co2SensorList);
            model.addAttribute("settingFunctionTypeList", settingFunctionTypeList);
            model.addAttribute("deviceList", deviceList);
            model.addAttribute("organizationId", organizationId);
            model.addAttribute("organizationName", organizationName);
            model.addAttribute("placeName", placeName);
            model.addAttribute("sensorSn", sensorSn);
            model.addAttribute("sensorTypeId", sensorTypeId);
            model.addAttribute("generalConfig", generalConfig);
            model.addAttribute("valueConfig", valueConfig);
            return "owner/setting";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "설정 센서 페이지를 불러오지 못하였습니다", "/");
        }
        return ALERT;
    }

    @GetMapping("/owner/organization-list")
    public String getOrganizationList(Model model){
        try{
            List<OrganizationDto> organizationWithoutList = organizationAdaptor.getOrganizationsWithoutMember();
            List<OrganizationListDto> organizationList = organizationAdaptor.getOrganizationsByMember();
            List<OrganizationListDto> state2Organizations = organizationList.stream()
                    .filter(org -> org.getState() == 2)
                    .collect(Collectors.toList());

            List<OrganizationListDto> state1Organizations = organizationList.stream()
                    .filter(org -> org.getState() == 1)
                    .collect(Collectors.toList());

            model.addAttribute("organizationWithoutList",organizationWithoutList);
            model.addAttribute("organizationList",organizationList);
            model.addAttribute("state2Organizations",state2Organizations);
            model.addAttribute("state1Organizations",state1Organizations);

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

    @GetMapping("/owner/device-log")
    public String getDeviceLogs(Model model){
        try {
            Map<OrganizationDto, List<DeviceLogResponseDto>> deviceLog = new HashMap<>();
            List<OrganizationListDto> organizationList = organizationAdaptor.getOrganizationsByMember();
            organizationList.forEach(org -> {
                if(org.getState() == 2){
                    Integer organizationId = org.getOrganizationId();
                    deviceLog.put(organizationAdaptor.getOrganization(organizationId), deviceLogAdaptor.getDeviceLog(organizationId));
                }
            });

            model.addAttribute("deviceLog", deviceLog);

            return "owner/device_log";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "센서 장비 목록 불러오기를 실패하였습니다", "/");
        }
        return ALERT;
    }

    @GetMapping("/owner/error")
    public String getErrorLogs(Model model){
        try {
            Map<OrganizationDto, List<ErrorLogResponseDto>> errorLog = new HashMap<>();
            List<OrganizationListDto> organizationList = organizationAdaptor.getOrganizationsByMember();
            organizationList.forEach(org -> {
                if(org.getState() == 2) {
                    Integer organizationId = org.getOrganizationId();
                    errorLog.put(organizationAdaptor.getOrganization(organizationId), errorLogAdaptor.getErrorLog(organizationId));
                }
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



    @GetMapping("/owner/mainConfigurations")
    public String getMainConfigurationInfo(Model model, HttpServletRequest request) {
        String accessToken = Objects.requireNonNull(CookieUtil.findCookie(request, "access_token")).getValue();
        try {

            List<OrganizationListDto> organizationDtoList = organizationAdaptor.getOrganizationsByMember();
            model.addAttribute("organizationList", organizationDtoList);
            model.addAttribute("get_access_token", accessToken);

            List<MainConfigurationDto> mainConfigurationDtoList = mainConfigurationAdaptor.getMainConfiguration();
            model.addAttribute("mainConfigurationList", mainConfigurationDtoList);

            return "/owner/main_configuration";
        } catch (HttpClientErrorException e) {
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "홈 설정 불러오기를 실패했습니다.", "/");
        }

        return ALERT;
    }

    @PostMapping("/owner/mainConfigurations")
    public String createMainConfigurationInfo(MainConfigurationCreateDto mainConfigurationCreateDto, Model model) {

        String result = mainConfigurationAdaptor.createMainConfiguration(mainConfigurationCreateDto);

        if (result.contains("success")) {
            alertHandler(model, "추가 성공!", "/owner/mainConfigurations");
        } else {
            alertHandler(model, "추가 실패!", "/owner/mainConfigurations");
        }
        return ALERT;
    }


    @PostMapping("/owner/modifyGeneralConfig")
    public String modifyGeneralConfig(Model model, RedirectAttributes redirectAttributes, GeneralConfigRequest request, int organizationId, String sensorSn, int sensorTypeId, String organizationName, String placeName){
        redirectAttributes.addAttribute("organizationId", organizationId);
        redirectAttributes.addAttribute("organizationName", organizationName);
        redirectAttributes.addAttribute("placeName", placeName);
        redirectAttributes.addAttribute("sensorSn", sensorSn);
        redirectAttributes.addAttribute("sensorTypeId", sensorTypeId);
        try{
            generalConfigAdaptor.modifyGeneralConfig(request, organizationId, sensorSn, sensorTypeId);
            return "redirect:/owner/get-device";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/owner/setting");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "설정 변경에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/owner/setting");
        }
        return ALERT;
    }

    @PostMapping("/owner/createValueConfig")
    public String createValueConfig(Model model, RedirectAttributes redirectAttributes, ValueConfigRequest request, int organizationId, String sensorSn, int sensorTypeId, String organizationName, String placeName){
        redirectAttributes.addAttribute("organizationId", organizationId);
        redirectAttributes.addAttribute("organizationName", organizationName);
        redirectAttributes.addAttribute("placeName", placeName);
        redirectAttributes.addAttribute("sensorSn", sensorSn);
        redirectAttributes.addAttribute("sensorTypeId", sensorTypeId);
        try{
            valueConfigAdaptor.createValueConfig(request, organizationId, sensorSn, sensorTypeId);
            return "redirect:/owner/get-device";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/owner/setting");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "설정 변경에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/owner/setting");
        }
        return ALERT;
    }

    @PostMapping("/owner/modifyValueConfig")
    public String modifyValueConfig(Model model, RedirectAttributes redirectAttributes, ValueConfigRequest request, int organizationId, String sensorSn, int sensorTypeId, String organizationName, String placeName){
        redirectAttributes.addAttribute("organizationId", organizationId);
        redirectAttributes.addAttribute("organizationName", organizationName);
        redirectAttributes.addAttribute("placeName", placeName);
        redirectAttributes.addAttribute("sensorSn", sensorSn);
        redirectAttributes.addAttribute("sensorTypeId", sensorTypeId);
        try{
            valueConfigAdaptor.modifyValueConfig(request, organizationId, sensorSn, sensorTypeId, 1);
            return "redirect:/owner/get-device";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/owner/setting");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "설정변경에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/owner/setting");
        }

        return ALERT;
    }

}
