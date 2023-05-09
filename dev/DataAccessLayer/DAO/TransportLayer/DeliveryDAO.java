package DataAccessLayer.DAO.TransportLayer;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.TransportLayer.DeliveryDTO;
import DataAccessLayer.Repository;

public class DeliveryDAO extends DAO<DeliveryDTO> {

    public DeliveryDAO() {
        this.tableName = "Deliveries";
    }

    @Override
    public int insert(DeliveryDTO Ob) {
        // TODO: Implement insert method
        return 0;
    }

    @Override
    public int update(DeliveryDTO updatedOb) {
        // TODO: Implement update method
        return 0;
    }

    @Override
    public DeliveryDTO makeDTO(ResultSet RS) {
        // TODO: Implement makeDTO method
        return null;
    }

    // TODO: Implement additional methods as needed, such as:
    // public List<DeliveryDTO> getDeliveriesByStatus(Status status);
    // public List<DeliveryDTO> getDeliveriesByWeight(int minWeight, int maxWeight);
    // etc.
}
