package com.api.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPutRentalDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private BigDecimal surface;
    private String description;
}
