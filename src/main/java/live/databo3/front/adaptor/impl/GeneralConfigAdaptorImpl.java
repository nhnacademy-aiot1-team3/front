package live.databo3.front.adaptor.impl;

import live.databo3.front.adaptor.GeneralConfigAdaptor;
import live.databo3.front.admin.dto.request.OrganizationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeneralConfigAdaptorImpl implements GeneralConfigAdaptor {
    @Override
    public String createGeneralConfig(OrganizationRequest organizationRequest) {
        return "";
    }
}
