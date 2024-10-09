package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.DiffLine;

import java.util.List;

public class JsonFormatter {
    public static String format(List<DiffLine> diff) throws JsonProcessingException {
        var om = new ObjectMapper();
        return om.writeValueAsString(diff);
    }
}
