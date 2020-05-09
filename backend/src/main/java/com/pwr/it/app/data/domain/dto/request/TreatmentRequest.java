package com.pwr.it.app.data.domain.dto.request;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Introspected
public class TreatmentRequest {

    @NotBlank(message = "Treatment title is required")
    private String title;
    private String doctorName;
    @NotNull(message = "Start date is required")
    private Date startDate;
    private Date endDate;
    private String description;
    private BigDecimal price;
    private String place;

}
