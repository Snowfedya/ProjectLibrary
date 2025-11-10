package com.library.api.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BorrowBookRequest
 */

@JsonTypeName("borrowBook_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-10T21:02:26.062995258+03:00[Europe/Moscow]")
public class BorrowBookRequest {

  private Long bookId;

  public BorrowBookRequest bookId(Long bookId) {
    this.bookId = bookId;
    return this;
  }

  /**
   * Get bookId
   * @return bookId
  */
  
  @Schema(name = "bookId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bookId")
  public Long getBookId() {
    return bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BorrowBookRequest borrowBookRequest = (BorrowBookRequest) o;
    return Objects.equals(this.bookId, borrowBookRequest.bookId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bookId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BorrowBookRequest {\n");
    sb.append("    bookId: ").append(toIndentedString(bookId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

