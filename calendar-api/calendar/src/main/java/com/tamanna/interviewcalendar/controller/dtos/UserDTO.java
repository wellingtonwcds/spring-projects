package com.tamanna.interviewcalendar.controller.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tamanna.interviewcalendar.domain.RoleType;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Represents an INTERVIEWER or a  CANDIDATE
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiParam(value = "The name of the user")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @ApiParam(value = "The role of the user")
    @NotNull(message = "roleType is mandatory")
    private RoleType roleType;

    @ApiModelProperty(hidden = true)
    @JsonInclude(Include.NON_NULL)
    private List<AvailableTimeSlotDTO> availableTimeSlotDTOS;
}
