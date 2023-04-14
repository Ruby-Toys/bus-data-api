package ruby.busdataapi.busstation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ruby.busdataapi.busstation.dto.BusDataDTO;
import ruby.busdataapi.properties.KakaoProperties;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class KaKaoMapService {

    private final KakaoProperties kakaoProperties;
    private final String KAKAO_MAP_API = "https://dapi.kakao.com/v2/local/geo/coord2address.json";

    @Getter
    static class AddressData {

        String address;
        String roadAddress;

        AddressData(JSONArray documents) {
            JSONObject addressAllInfo = (JSONObject) documents.get(0);
            JSONObject roadAddressInfo = (JSONObject) addressAllInfo.get("road_address");
            JSONObject addressInfo = (JSONObject) addressAllInfo.get("address");

            this.address = addressInfo != null ? (String) addressInfo.get("address_name") : null;
            this.roadAddress = roadAddressInfo != null ? (String) roadAddressInfo.get("address_name") : null;
        }
    }

    public List<BusStation> getBusStationsByKakaoApi(List<BusDataDTO> busData) {
        String restApiKey = kakaoProperties.getKey();

        // 카카오맵 하루 최대 10만 건 조회 가능
        busData = busData.subList(0, Math.min(busData.size(), 1000));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + restApiKey);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        JSONParser jsonParser = new JSONParser();
        return busData.stream()
                .map(busDataDTO -> {
                    String queryString = getQueryString(busDataDTO.getLng(), busDataDTO.getLat());
                    URI uri = URI.create(KAKAO_MAP_API + queryString);
                    RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.GET, uri);
                    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

                    try {
                        JSONObject body = (JSONObject) jsonParser.parse(Objects.requireNonNull(response.getBody()));
                        JSONArray documents = (JSONArray) body.get("documents");
                        if (!hasDocuments(documents)) {
                            return null;
                        }

                        log.info("data : {}", documents);

                        AddressData addressData = new AddressData(documents);
                        return BusStation.builder()
                                .stationId(busDataDTO.getStationId())
                                .name(busDataDTO.getName())
                                .address(addressData.getAddress())
                                .roadAddress(addressData.getRoadAddress())
                                .lat(Double.parseDouble(busDataDTO.getLat()))
                                .lng(Double.parseDouble(busDataDTO.getLng()))
                                .build();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private String getQueryString(String lng, String lat) {
        return "?x=" +lng + "&y=" + lat;
    }

    private boolean hasDocuments(JSONArray documents) {
        return documents != null && documents.size() > 0;
    }
}
