package com.bookworm.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiaryRoyaltyDTO {
    private Integer beneficiaryId;
    private BigDecimal royaltyPercentage;
}