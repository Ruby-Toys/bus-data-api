package ruby.busdataapi.busstation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ruby.busdataapi.busstation.dto.BusDataDTO;

import java.util.List;

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
    public void renewal(@RequestPart MultipartFile busDataFile) {

        /**
         * TODO
         *  1. 클라이언트로부터 파일을 받는다.
         *  2. 파일로부터 좌표 정보를 받는다.
         *  3. 카카오맵 api 를 통해 주소 정보를 얻는다.
         *  4. DB 에서 기존 정보를 조회한다.
         *  5. Update 또는 Insert
         */

        List<BusDataDTO> busData = busDataFileService.getBusDataByFile(busDataFile);
        List<BusStation> busStations = kaKaoMapService.getBusStationsByKakaoApi(busData);
        busStationService.renewBusStations(busStations);
        log.info("갱신 완료!");
    }
}


