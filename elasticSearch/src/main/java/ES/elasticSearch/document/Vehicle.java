package ES.elasticSearch.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
//@Setting(settingPath = "static/mappings/vehicle.json")
public class Vehicle {
    private String id;
    private String number;

}
