package BussinessLayer;

import java.util.HashMap;
import java.util.Map;

public class TransportFacade {
    private Map<String, Transport> transportMap;

    public TransportFacade() {
        transportMap = new HashMap<>();
    }

    public void addTransport(String name, Transport transport) {
        transportMap.put(name, transport);
    }

    public Transport getTransport(String name) {
        return transportMap.get(name);
    }

    public void removeTransport(String name) {
        transportMap.remove(name);
    }

    public Map<String, Transport> getAllTransport() {
        return transportMap;
    }
}
