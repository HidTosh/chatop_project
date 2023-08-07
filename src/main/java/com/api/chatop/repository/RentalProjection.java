package com.api.chatop.repository;

import java.math.BigDecimal;

public interface RentalProjection {
    Integer getId();
    String getName();
    String getDescription();
    String getPicture();
    BigDecimal getPrice();
    BigDecimal getSurface();
    Integer getOwner_id();
    String getCreated_at();
    String getUpdated_at();
}
