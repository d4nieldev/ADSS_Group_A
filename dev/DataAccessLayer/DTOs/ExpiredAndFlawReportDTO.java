package DataAccessLayer.DTOs;

import java.util.List;

public class ExpiredAndFlawReportDTO extends ReportDTO {
    private List<SpecificProductDTO> expiredProducts;
    private List<SpecificProductDTO> flawProducts;

    public ExpiredAndFlawReportDTO(ReportDTO report, List<SpecificProductDTO> expiredProducts,
            List<SpecificProductDTO> flawProducts) {
        super(report);
        this.expiredProducts = expiredProducts;
        this.flawProducts = flawProducts;
    }

    public List<SpecificProductDTO> getExpiredProducts() {
        return expiredProducts;
    }

    public List<SpecificProductDTO> getFlawProducts() {
        return flawProducts;
    }
}
