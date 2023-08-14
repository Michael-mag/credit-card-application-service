package com.practice.michael.creditcardapplication.service;

import com.practice.michael.creditcardapplication.dto.CreditCardRequest;
import com.practice.michael.creditcardapplication.entity.CreditCard;
import com.practice.michael.creditcardapplication.entity.PublishStatus;
import com.practice.michael.creditcardapplication.repository.CreditCardApplicationRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CreditCardService {

  private final CreditCardApplicationRepository creditCardApplicationRepository;

  public Optional<String> saveApplication(CreditCardRequest creditCardRequest) {
    try {
      CreditCard creditCard = new CreditCard();
      BeanUtils.copyProperties(creditCardRequest, creditCard);
      creditCard.setPublishStatus(PublishStatus.NOT_PUBLISHED);
      creditCard.setRefId(UUID.randomUUID().toString());
      CreditCard savedCredit = creditCardApplicationRepository.save(creditCard);
      return Optional.of(savedCredit.getRefId());
    } catch (Exception exception) {
      log.error("Error saving new credit card: {}", exception.getMessage());
      return Optional.empty();
    }
  }
}
