package com.tinubu.assurance.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Possible statuses of an insurance policy")
public enum PolicyStatusDto {
    ACTIVE,INACTIVE
}
