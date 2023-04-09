package ruby.busdataapi.busstation;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ruby.busdataapi.busstation.dto.BusDataDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusDataFileService {

    public List<BusDataDTO> getBusDataByFile(MultipartFile busDataFile) {
        List<BusDataDTO> busData = new ArrayList<>();

        try (
                InputStream inputStream = busDataFile.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(inputStreamReader);
        ){
            String line;
            boolean isStartLine = true;
            while((line = br.readLine()) != null){
                if (isStartLine) {
                    isStartLine = false;
                    continue;
                }

                String[] cols = line.split(",");
                BusDataDTO busDataDTO = BusDataDTO.builder()
                        .stationId(cols[0])
                        .name(cols[1])
                        .lat(cols[2])
                        .lng(cols[3])
                        .build();

                busData.add(busDataDTO);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return busData;
    }
}
