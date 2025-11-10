package com.library.api.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BorrowRecord
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-10T21:02:26.062995258+03:00[Europe/Moscow]")
public class BorrowRecord {

  private Long id;

  private Long userId;

  private Long bookId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime borrowDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime dueDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime returnDate;

  private Boolean renewed;

  private Integer fineAmount;

  public BorrowRecord id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BorrowRecord userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  
  @Schema(name = "userId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userId")
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public BorrowRecord bookId(Long bookId) {
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

  public BorrowRecord borrowDate(OffsetDateTime borrowDate) {
    this.borrowDate = borrowDate;
    return this;
  }

  /**
   * Get borrowDate
   * @return borrowDate
  */
  @Valid 
  @Schema(name = "borrowDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("borrowDate")
  public OffsetDateTime getBorrowDate() {
    return borrowDate;
  }

  public void setBorrowDate(OffsetDateTime borrowDate) {
    this.borrowDate = borrowDate;
  }

  public BorrowRecord dueDate(OffsetDateTime dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  /**
   * Get dueDate
   * @return dueDate
  */
  @Valid 
  @Schema(name = "dueDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dueDate")
  public OffsetDateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(OffsetDateTime dueDate) {
    this.dueDate = dueDate;
  }

  public BorrowRecord returnDate(OffsetDateTime returnDate) {
    this.returnDate = returnDate;
    return this;
  }

  /**
   * Get returnDate
   * @return returnDate
  */
  @Valid 
  @Schema(name = "returnDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("returnDate")
  public OffsetDateTime getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(OffsetDateTime returnDate) {
    this.returnDate = returnDate;
  }

  public BorrowRecord renewed(Boolean renewed) {
    this.renewed = renewed;
    return this;
  }

  /**
   * Get renewed
   * @return renewed
  */
  
  @Schema(name = "renewed", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("renewed")
  public Boolean getRenewed() {
    return renewed;
  }

  public void setRenewed(Boolean renewed) {
    this.renewed = renewed;
  }

  public BorrowRecord fineAmount(Integer fineAmount) {
    this.fineAmount = fineAmount;
    return this;
  }

  /**
   * Get fineAmount
   * @return fineAmount
  */
  
  @Schema(name = "fineAmount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fineAmount")
  public Integer getFineAmount() {
    return fineAmount;
  }

  public void setFineAmount(Integer fineAmount) {
    this.fineAmount = fineAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BorrowRecord borrowRecord = (BorrowRecord) o;
    return Objects.equals(this.id, borrowRecord.id) &&
        Objects.equals(this.userId, borrowRecord.userId) &&
        Objects.equals(this.bookId, borrowRecord.bookId) &&
        Objects.equals(this.borrowDate, borrowRecord.borrowDate) &&
        Objects.equals(this.dueDate, borrowRecord.dueDate) &&
        Objects.equals(this.returnDate, borrowRecord.returnDate) &&
        Objects.equals(this.renewed, borrowRecord.renewed) &&
        Objects.equals(this.fineAmount, borrowRecord.fineAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, bookId, borrowDate, dueDate, returnDate, renewed, fineAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BorrowRecord {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    bookId: ").append(toIndentedString(bookId)).append("\n");
    sb.append("    borrowDate: ").append(toIndentedString(borrowDate)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    returnDate: ").append(toIndentedString(returnDate)).append("\n");
    sb.append("    renewed: ").append(toIndentedString(renewed)).append("\n");
    sb.append("    fineAmount: ").append(toIndentedString(fineAmount)).append("\n");
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

