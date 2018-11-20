package fund.dto.param;

import java.util.HashMap;
import java.util.Map;

public class Wrapper {
    Map<String,Object> map = new HashMap<String,Object>();

    public Map<String,Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String,Object> map) {
        this.map = map;
    }

}
