package ruby.busdataapi.busstation;

import org.springframework.data.domain.Page;
import ruby.busdataapi.busstation.dto.BusSearchConditionDTO;

public interface BusStationRepositoryCustom {
    Page<BusStation> findBySearchCondition(BusSearchConditionDTO searchCondition);
}
