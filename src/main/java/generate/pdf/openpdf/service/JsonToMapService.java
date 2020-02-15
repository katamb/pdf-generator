package generate.pdf.openpdf.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.google.gson.JsonParser.parseString;

@Service
public class JsonToMapService {

    public Map<String, Object> getMapFromJson(String json) {
        JsonObject jsonObject = parseString(json).getAsJsonObject();
        // TODO: Currently all number are converted to double
        return new Gson().fromJson(jsonObject, Map.class);
    }

}
