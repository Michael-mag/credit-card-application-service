package com.practice.michael.creditcardapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CREDIT_CARD_APPLICATION")
public class CreditCard {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;
  private Integer income;
  private String address;

  @Enumerated(EnumType.STRING)
  private PublishStatus publishStatus = PublishStatus.NOT_PUBLISHED;

  private String refId;
}
