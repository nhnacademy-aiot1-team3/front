package live.databo3.front.admin.adaptor;

import live.databo3.front.admin.dto.PlaceDto;
import live.databo3.front.admin.dto.PlaceRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PlaceAdaptor {
    List<PlaceDto> getPlaces(int organizationId);

    PlaceDto createPlace(PlaceRequest placeRequest, int organizationId);

    PlaceDto modifyPlace(PlaceRequest placeRequest, int organizationId, int placeId);

    void deletePlace(int organizationId, int placeId);
}
