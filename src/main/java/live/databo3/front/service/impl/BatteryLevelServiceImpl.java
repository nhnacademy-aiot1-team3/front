package live.databo3.front.service.impl;

import live.databo3.front.adaptor.BatteryLevelAdaptor;
import live.databo3.front.adaptor.OrganizationAdaptor;
import live.databo3.front.dto.OrganizationListDto;
import live.databo3.front.owner.dto.BatteryLevelDto;
import live.databo3.front.owner.dto.BatteryLevelListDto;
import live.databo3.front.service.BatteryLevelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BatteryLevel 관련 Service
 * @author jihyeon
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BatteryLevelServiceImpl implements BatteryLevelService {

    private final OrganizationAdaptor organizationAdaptor;

    private final BatteryLevelAdaptor batteryLevelAdaptor;

    /**
     * 본인이 속한 회사들에 있는 sensor들의 베터리 잔량들을 조회하는 메소드
     * @return BatteryLevelDto(place, device, branch, value) 리스트를 회사별로 리스트로 묶어서 반환한다
     * @since 1.0.0
     */
    @Override
    public List<List<BatteryLevelDto>> getBatteryLevels() {

        List<OrganizationListDto> organizationListDtoList = organizationAdaptor.getOrganizationsByMember();
        List<List<BatteryLevelDto>> batteryLevelDtoList = new ArrayList<>();
        for(OrganizationListDto dto : organizationListDtoList) {
            if(dto.getState() == 2) {
                BatteryLevelListDto listDto = batteryLevelAdaptor.getBatteryLevels(dto.getOrganizationName());
                if(listDto.getData().size() >0) {
                    batteryLevelDtoList.add(listDto.getData());
                    log.info("list of list size : {}", batteryLevelDtoList.size());
                    log.info("list size: {}", listDto.getData().size());
                }
            }
        }
        return batteryLevelDtoList;
    }
}
