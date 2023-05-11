package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.InventoryReportEntryDTO;

public class InventoryReportEntryDAO extends DAO<InventoryReportEntryDTO> {
    protected InventoryReportEntryDAO() {
        super("InventoryReportEntries");
    }

    @Override
    public InventoryReportEntryDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int reportId = rs.getInt("reportId");
        int productId = rs.getInt("productId");
        int shelfAmount = rs.getInt("shelfAmount");
        int storageAmount = rs.getInt("storageAmount");

        return new InventoryReportEntryDTO(reportId, productId, shelfAmount, storageAmount);
    }

}
