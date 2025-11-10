package com.library.api.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.library.api.models.Book;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BookPage
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-10T21:02:26.062995258+03:00[Europe/Moscow]")
public class BookPage {

  @Valid
  private List<@Valid Book> content;

  private Object pageable;

  private Integer totalPages;

  private Integer totalElements;

  public BookPage content(List<@Valid Book> content) {
    this.content = content;
    return this;
  }

  public BookPage addContentItem(Book contentItem) {
    if (this.content == null) {
      this.content = new ArrayList<>();
    }
    this.content.add(contentItem);
    return this;
  }

  /**
   * Get content
   * @return content
  */
  @Valid 
  @Schema(name = "content", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("content")
  public List<@Valid Book> getContent() {
    return content;
  }

  public void setContent(List<@Valid Book> content) {
    this.content = content;
  }

  public BookPage pageable(Object pageable) {
    this.pageable = pageable;
    return this;
  }

  /**
   * Get pageable
   * @return pageable
  */
  
  @Schema(name = "pageable", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pageable")
  public Object getPageable() {
    return pageable;
  }

  public void setPageable(Object pageable) {
    this.pageable = pageable;
  }

  public BookPage totalPages(Integer totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Get totalPages
   * @return totalPages
  */
  
  @Schema(name = "totalPages", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalPages")
  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public BookPage totalElements(Integer totalElements) {
    this.totalElements = totalElements;
    return this;
  }

  /**
   * Get totalElements
   * @return totalElements
  */
  
  @Schema(name = "totalElements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalElements")
  public Integer getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Integer totalElements) {
    this.totalElements = totalElements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookPage bookPage = (BookPage) o;
    return Objects.equals(this.content, bookPage.content) &&
        Objects.equals(this.pageable, bookPage.pageable) &&
        Objects.equals(this.totalPages, bookPage.totalPages) &&
        Objects.equals(this.totalElements, bookPage.totalElements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, pageable, totalPages, totalElements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookPage {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    pageable: ").append(toIndentedString(pageable)).append("\n");
    sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
    sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
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

