package com.practice.michael.creditcardapplication.messaging.event.publising;

import com.practice.michael.creditcardapplication.messaging.event.NewCreditCardCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
@AllArgsConstructor
public class CreditCardEventPublisher {

  private EventPublisherService eventPublisherService;

  @Bean
  public Supplier<NewCreditCardCreatedEvent> publishNewCreditCardCreatedEvent() {
    return () -> {
      Optional<NewCreditCardCreatedEvent> newCreditCardCreatedEvent =
          eventPublisherService.publishEvent();
      return newCreditCardCreatedEvent.orElse(null);
    };
  }
}
