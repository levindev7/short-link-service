package org.sls.shortlinkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequest {
    @NotNull(message = "field originalUrl can't be null")
    @NotBlank(message = "field originalUrl can't be empty")
    private String originalUrl;
}
