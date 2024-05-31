package live.databo3.front.adaptor;

import live.databo3.front.owner.dto.MainConfigurationCreateDto;
import live.databo3.front.owner.dto.MainConfigurationDto;

import java.util.List;

public interface MainConfigurationAdaptor {

    List<MainConfigurationDto> getMainConfiguration();

    String createMainConfiguration(MainConfigurationCreateDto mainConfigurationCreateDto);

}
