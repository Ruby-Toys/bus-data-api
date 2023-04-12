package ruby.busdataapi.busstation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ruby.busdataapi.busstation.dto.BusDataDTO;
import ruby.busdataapi.busstation.dto.BusSearchConditionDTO;
import ruby.busdataapi.busstation.dto.BusStationListDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/bus-stations")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class BusStationController {

    private final BusStationService busStationService;
    private final BusDataFileService busDataFileService;
    private final KaKaoMapService kaKaoMapService;

    @PostMapping("/renewal")
    public void renewal(@RequestPart MultipartFile busDataFile) throws ExecutionException, InterruptedException {
        List<BusDataDTO> busData = busDataFileService.getBusDataByFile(busDataFile);
        List<BusStation> busStations = kaKaoMapService.getBusStationsByKakaoApi(busData);
        busStationService.renewBusStations(busStations);
        log.info("갱신 완료!");
    }

    @GetMapping
    public BusStationListDTO getBusStations(BusSearchConditionDTO searchCondition) {
        Page<BusStation> busStationPage = busStationService.getBusStationsBySearchCondition(searchCondition);
        return new BusStationListDTO(busStationPage);
    }
}


