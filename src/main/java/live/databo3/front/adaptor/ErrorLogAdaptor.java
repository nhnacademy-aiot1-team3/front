package live.databo3.front.adaptor;

import live.databo3.front.owner.dto.ErrorLogResponseDto;

import java.util.List;

public interface ErrorLogAdaptor {
    List<ErrorLogResponseDto> getErrorLog(Integer organizationId);
}
