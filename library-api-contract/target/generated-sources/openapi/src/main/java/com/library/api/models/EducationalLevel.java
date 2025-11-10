package com.library.api.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets EducationalLevel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-10T22:36:35.267612671+03:00[Europe/Moscow]")
public enum EducationalLevel {
  
  PRIMARY("PRIMARY"),
  
  SECONDARY("SECONDARY"),
  
  HIGHER("HIGHER"),
  
  PROFESSIONAL("PROFESSIONAL");

  private String value;

  EducationalLevel(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static EducationalLevel fromValue(String value) {
    for (EducationalLevel b : EducationalLevel.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

