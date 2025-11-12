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
 * Gets or Sets Subject
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-12T00:16:50.186884498+03:00[Europe/Moscow]")
public enum Subject {
  
  MATHEMATICS("MATHEMATICS"),
  
  LITERATURE("LITERATURE"),
  
  SCIENCE("SCIENCE"),
  
  HISTORY("HISTORY");

  private String value;

  Subject(String value) {
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
  public static Subject fromValue(String value) {
    for (Subject b : Subject.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

