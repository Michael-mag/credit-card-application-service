package com.practice.michael.creditcardapplication.repository;

import com.practice.michael.creditcardapplication.entity.CreditCard;
import com.practice.michael.creditcardapplication.entity.PublishStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardApplicationRepository extends JpaRepository<CreditCard, Long> {
  List<CreditCard> findByPublishStatus(PublishStatus publishStatus);
}
