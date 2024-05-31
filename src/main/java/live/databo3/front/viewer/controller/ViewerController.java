package live.databo3.front.viewer.controller;

import live.databo3.front.adaptor.*;
import live.databo3.front.dto.OrganizationDto;
import live.databo3.front.dto.OrganizationListDto;
import live.databo3.front.dto.request.UpdatePasswordRequest;
import live.databo3.front.dto.SettingFunctionTypeDto;
import live.databo3.front.member.dto.MemberRequestDto;
import live.databo3.front.owner.dto.*;
import live.databo3.front.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * viewer 페이지 관련 controller
 *
 * @author 나채현
 * @version 1.0.2
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewerController {
    private static final String ALERT="alert";
    private static final String ALERT_MESSAGE="message";
    private static final String ALERT_URL="searchUrl";

    private final MemberAdaptor memberAdaptor;
    private final SettingFunctionTypeAdaptor settingFunctionTypeAdaptor;
    private final OrganizationAdaptor organizationAdaptor;
    private final MainConfigurationAdaptor mainConfigurationAdaptor;
    private final SensorAdaptor sensorAdaptor;
    private final PlaceAdaptor placeAdaptor;
    private final ErrorLogAdaptor errorLogAdaptor;
    private final DeviceLogAdaptor deviceLogAdaptor;

    private void alertHandler(Model model, String message, String url) {
        model.addAttribute(ALERT_MESSAGE, message);
        model.addAttribute(ALERT_URL, url);
    }

    @GetMapping("/viewer/my-page")
    public String getViewerMyPage(){
        return "viewer/my_page";
    }

    @PostMapping("/viewer/modifyPassword")
    public String modifyPassword(Model model, String memberId, String currentPassword, String checkPassword, String modifyPassword){
        try{
            if(!checkPassword.equals(modifyPassword)){
                model.addAttribute(ALERT_MESSAGE, "New Password와 Confirm Password가 다릅니다");
                model.addAttribute(ALERT_URL,"/viewer/my-page");
                return ALERT;
            }
            memberAdaptor.doLogin(new MemberRequestDto(memberId, currentPassword));
            memberAdaptor.modifyPassword(memberId, new UpdatePasswordRequest(modifyPassword));
            model.addAttribute(ALERT_MESSAGE, "비밀번호 변경에 성공하였습니다 다시 로그인해주세요");
            model.addAttribute(ALERT_URL,"/login");
            return "pre_login/login";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/viewer/my-page");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "비밀번호 변경에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/viewer/my-page");
        }
        return ALERT;
    }

    @GetMapping("/viewer/sensor-list")
    public String getSensorList(){
        return "viewer/sensor_list";
    }

    @GetMapping("/viewer/battery-level")
    public String getBatteryLevel(){
        return "viewer/battery_level";
    }


    /**
     * 공지사항으로 이동하는 method
     * @return 공지사항 페이지로 이동
     */
    @GetMapping("/viewer/notification")
    public String getNotification(){
        return "/viewer/notification";
    }

    @GetMapping("/viewer/organization-list")
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

            return "viewer/organization_list";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "조직 목록 페이지를 불러오지 못하였습니다", "/");
        }
        return ALERT;
    }

    @PostMapping("/viewer/postMemberOrgs")
    public String postMemberOrgs(Model model, int organizationId){
        try{
            organizationAdaptor.postMemberOrgs(organizationId);
            return "redirect:/viewer/organization-list";
        } catch(HttpClientErrorException e){
            model.addAttribute(ALERT_MESSAGE, e.getStatusText());
            model.addAttribute(ALERT_URL,"/viewer/organization-list");
        } catch (Exception e) {
            model.addAttribute(ALERT_MESSAGE, "조직 신청에 실패하였습니다");
            model.addAttribute(ALERT_URL,"/viewer/organization-list");
        }
        return ALERT;
    }

    @GetMapping("/viewer/sensor-page")
    public String getSensorPage(Model model, int sensorType, String type, HttpServletRequest request){
        String access_token = CookieUtil.findCookie(request, "access_token").getValue();
        try{
            List<SensorListDto> sensorList = sensorAdaptor.getOrganizationListBySensorType(sensorType);
            List<SettingFunctionTypeDto> settingFunctionTypeList = settingFunctionTypeAdaptor.getSettingFunctionTypes();
            model.addAttribute("sensorList", sensorList);
            model.addAttribute("settingFunctionTypeList", settingFunctionTypeList);
            model.addAttribute("type", type);
            model.addAttribute("get_access_token", access_token);
            return "viewer/sensor_page";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "센서 페이지를 불러오지 못하였습니다", "/");
        }
        return ALERT;
    }

    @GetMapping("/viewer/mainConfigurations")
    public String getMainConfigurationInfo(Model model, HttpServletRequest request) {
        String accessToken = Objects.requireNonNull(CookieUtil.findCookie(request, "access_token")).getValue();
        try {

            List<OrganizationListDto> organizationDtoList = organizationAdaptor.getOrganizationsByMember();
            model.addAttribute("organizationList", organizationDtoList);
            model.addAttribute("get_access_token", accessToken);

            List<MainConfigurationDto> mainConfigurationDtoList = mainConfigurationAdaptor.getMainConfiguration();
            model.addAttribute("mainConfigurationList", mainConfigurationDtoList);

            return "/viewer/main_configuration";
        } catch (HttpClientErrorException e) {
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "홈 설정 불러오기를 실패했습니다.", "/");
        }

        return ALERT;
    }


    @PostMapping("/viewer/mainConfigurations")
    public String createMainConfigurationInfo(MainConfigurationCreateDto mainConfigurationCreateDto, Model model) {

        String result = mainConfigurationAdaptor.createMainConfiguration(mainConfigurationCreateDto);

        if (result.contains("success")) {
            alertHandler(model, "추가 성공!", "/viewer/mainConfigurations");
        } else {
            alertHandler(model, "추가 실패!", "/viewer/mainConfigurations");
        }
        return ALERT;
    }

    @GetMapping("/viewer/device-log")
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

            return "viewer/device_log";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "센서 장비 목록 불러오기를 실패하였습니다", "/");
        }
        return ALERT;
    }

    @GetMapping("/viewer/error")
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

            return "viewer/error";
        } catch(HttpClientErrorException e){
            alertHandler(model, e.getMessage(), "/");
        } catch (Exception e) {
            alertHandler(model, "에러 목록 불러오기를 실패하였습니다", "/");
        }
        return ALERT;
    }
}
