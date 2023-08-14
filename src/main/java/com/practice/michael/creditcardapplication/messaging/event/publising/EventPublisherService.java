package com.practice.michael.creditcardapplication.messaging.event.publising;

import com.practice.michael.creditcardapplication.entity.CreditCard;
import com.practice.michael.creditcardapplication.entity.PublishStatus;
import com.practice.michael.creditcardapplication.messaging.event.CreditCardApplicationDetails;
import com.practice.michael.creditcardapplication.messaging.event.NewCreditCardCreatedEvent;
import com.practice.michael.creditcardapplication.repository.CreditCardApplicationRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EventPublisherService {

    private final CreditCardApplicationRepository creditCardApplicationRepository;

  public Optional<NewCreditCardCreatedEvent> publishEvent() {

        List<CreditCard> creditCards =
        creditCardApplicationRepository.findByPublishStatus(PublishStatus.NOT_PUBLISHED);

        List<CreditCardApplicationDetails> cardApplicationDetails =
            getCreditCardApplicationDetails(creditCards);

        log.info(
            "##### Checked for new credit card request. Available record " + "to push : {}",
            creditCards.size());

        return getNewCreditCardCreatedEvent(creditCards, cardApplicationDetails);
  }

  @NotNull
  private Optional<NewCreditCardCreatedEvent> getNewCreditCardCreatedEvent(
      List<CreditCard> creditCards, List<CreditCardApplicationDetails> cardApplicationDetails) {
    Optional<NewCreditCardCreatedEvent> eventToPublish = Optional.empty();

    if (!cardApplicationDetails.isEmpty()) {
      log.info("****** Updating Credit Card Status as published *******");
      eventToPublish = getCreditCardCreatedEvent(creditCards, cardApplicationDetails);
    }
    return eventToPublish;
  }

  @NotNull
  private Optional<NewCreditCardCreatedEvent> getCreditCardCreatedEvent(
      List<CreditCard> creditCards, List<CreditCardApplicationDetails> cardApplicationDetails) {
    Optional<NewCreditCardCreatedEvent> eventToPublish;
    NewCreditCardCreatedEvent newCreditCardCreatedEvent =
        NewCreditCardCreatedEvent.builder()
            .creditCardApplicationDetails(cardApplicationDetails)
            .build();
    creditCardApplicationRepository.saveAll(creditCards);
    eventToPublish = Optional.of(newCreditCardCreatedEvent);
    return eventToPublish;
  }

  @NotNull
  private static List<CreditCardApplicationDetails> getCreditCardApplicationDetails(
      List<CreditCard> creditCards) {
    return creditCards.stream()
        .map(
            creditCard -> {
              var creditCardApplicationDetails = CreditCardApplicationDetails.builder().build();
              BeanUtils.copyProperties(creditCard, creditCardApplicationDetails);
              creditCard.setPublishStatus(PublishStatus.IS_PUBLISHED);
              return creditCardApplicationDetails;
            })
        .toList();
  }
}
