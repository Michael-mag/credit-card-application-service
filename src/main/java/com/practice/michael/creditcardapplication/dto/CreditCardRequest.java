package com.practice.michael.creditcardapplication.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel
public class CreditCardRequest {
  @NonNull
  @ApiModelProperty(notes = "Customer First Name")
  private String firstName;

  @NonNull
  @ApiModelProperty(notes = "Customer Last Name")
  private String lastName;

  @NonNull
  @ApiModelProperty(notes = "Customer Income")
  private Integer income;

  @NonNull
  @ApiModelProperty(notes = "Customer Address")
  private String address;
}
