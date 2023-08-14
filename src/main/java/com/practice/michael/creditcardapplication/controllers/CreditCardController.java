package com.practice.michael.creditcardapplication.controllers;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.practice.michael.creditcardapplication.dto.CreditCardRequest;
import com.practice.michael.creditcardapplication.service.CreditCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/applyCreditCard/v1")
@Tag(name = "Credit Card Applications")
@Api(tags = "Credit Card Applications")
public class CreditCardController {

  private final CreditCardService creditCardService;

  @ApiOperation(value = "Create a credit card application for new customer")
  @ApiResponses(
      value = {
        @ApiResponse(code = 500, message = "Internal Server Error"),
        @ApiResponse(code = 200, message = "OK", response = String.class)
      })
  @PostMapping("/applyCreditCard")
  public ResponseEntity<String> createNewCreditCardRequest(
      @RequestBody CreditCardRequest creditCardRequest) {

    if (isInvalidRequest(creditCardRequest)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    Optional<String> creditCardApplicationRefId =
        creditCardService.saveApplication(creditCardRequest);

    return creditCardApplicationRefId
        .map(
            refId ->
                ResponseEntity.status(HttpStatus.CREATED)
                    .body("Your application reference number is: " + refId))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }

  private boolean isInvalidRequest(CreditCardRequest creditCardRequest) {
    return (isEmpty(creditCardRequest.getFirstName())
        || isEmpty(creditCardRequest.getLastName())
        || isEmpty(creditCardRequest.getAddress()));
  }
}
