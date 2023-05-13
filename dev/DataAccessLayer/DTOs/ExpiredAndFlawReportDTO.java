package DataAccessLayer.DTOs;

import java.util.List;

public class ExpiredAndFlawReportDTO extends ReportDTO {
    private List<SpecificProductDTO> expiredProducts;
    private List<SpecificProductDTO> flawProducts;
    private ReportDTO reportDTO;

    public ExpiredAndFlawReportDTO(ReportDTO reportDTO, List<SpecificProductDTO> expiredProducts,
            List<SpecificProductDTO> flawProducts) {
        super(reportDTO);
        this.expiredProducts = expiredProducts;
        this.flawProducts = flawProducts;
        this.reportDTO = reportDTO;
    }

    public List<SpecificProductDTO> getExpiredProducts() {
        return expiredProducts;
    }

    public List<SpecificProductDTO> getFlawProducts() {
        return flawProducts;
    }

    public ReportDTO getReportDTO(){ return reportDTO; }
}
