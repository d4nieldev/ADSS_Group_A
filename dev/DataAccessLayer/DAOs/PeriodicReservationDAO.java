package DataAccessLayer.DAOs;

public class PeriodicReservationDAO extends DAO<SpecificProductDTO> {

    private static PeriodicReservationDAO instance = null;

    public static PeriodicReservationDAO getInstance(){
        if(instance == null)
            instance = new PeriodicReservationDAO();
        return instance;
    }

    private PeriodicReservationDAO() {
        super("PeriodicReservation");
    }
    
    @Override
    public SpecificProductDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int specificId = rs.getInt("specificId");
        int generalId = rs.getInt("generalId");
        int branchId = rs.getInt("branchId");
        double buyPrice = rs.getDouble("buyPrice");
        double sellPrice = rs.getDouble("sellPrice");
        String status = rs.getString("status");
        String flaw = rs.getString("flaw");
        String expDate = rs.getString("expDate");

        return new SpecificProductDTO(specificId, generalId, branchId, buyPrice, sellPrice, status,flaw,expDate);
    }
}
