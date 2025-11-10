package com.library.api.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.library.api.models.Book;
import com.library.api.models.EducationalLevel;
import com.library.api.models.Subject;
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
 * ReadingList
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-10T22:36:35.267612671+03:00[Europe/Moscow]")
public class ReadingList {

  private Long id;

  private Long teacherId;

  private String title;

  private String description;

  private Subject subject;

  private EducationalLevel educationalLevel;

  private String academicYear;

  @Valid
  private List<@Valid Book> books;

  public ReadingList id(Long id) {
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

  public ReadingList teacherId(Long teacherId) {
    this.teacherId = teacherId;
    return this;
  }

  /**
   * Get teacherId
   * @return teacherId
  */
  
  @Schema(name = "teacherId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("teacherId")
  public Long getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(Long teacherId) {
    this.teacherId = teacherId;
  }

  public ReadingList title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  
  @Schema(name = "title", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ReadingList description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ReadingList subject(Subject subject) {
    this.subject = subject;
    return this;
  }

  /**
   * Get subject
   * @return subject
  */
  @Valid 
  @Schema(name = "subject", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("subject")
  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public ReadingList educationalLevel(EducationalLevel educationalLevel) {
    this.educationalLevel = educationalLevel;
    return this;
  }

  /**
   * Get educationalLevel
   * @return educationalLevel
  */
  @Valid 
  @Schema(name = "educationalLevel", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("educationalLevel")
  public EducationalLevel getEducationalLevel() {
    return educationalLevel;
  }

  public void setEducationalLevel(EducationalLevel educationalLevel) {
    this.educationalLevel = educationalLevel;
  }

  public ReadingList academicYear(String academicYear) {
    this.academicYear = academicYear;
    return this;
  }

  /**
   * Get academicYear
   * @return academicYear
  */
  
  @Schema(name = "academicYear", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("academicYear")
  public String getAcademicYear() {
    return academicYear;
  }

  public void setAcademicYear(String academicYear) {
    this.academicYear = academicYear;
  }

  public ReadingList books(List<@Valid Book> books) {
    this.books = books;
    return this;
  }

  public ReadingList addBooksItem(Book booksItem) {
    if (this.books == null) {
      this.books = new ArrayList<>();
    }
    this.books.add(booksItem);
    return this;
  }

  /**
   * Get books
   * @return books
  */
  @Valid 
  @Schema(name = "books", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("books")
  public List<@Valid Book> getBooks() {
    return books;
  }

  public void setBooks(List<@Valid Book> books) {
    this.books = books;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReadingList readingList = (ReadingList) o;
    return Objects.equals(this.id, readingList.id) &&
        Objects.equals(this.teacherId, readingList.teacherId) &&
        Objects.equals(this.title, readingList.title) &&
        Objects.equals(this.description, readingList.description) &&
        Objects.equals(this.subject, readingList.subject) &&
        Objects.equals(this.educationalLevel, readingList.educationalLevel) &&
        Objects.equals(this.academicYear, readingList.academicYear) &&
        Objects.equals(this.books, readingList.books);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, teacherId, title, description, subject, educationalLevel, academicYear, books);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReadingList {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    teacherId: ").append(toIndentedString(teacherId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    educationalLevel: ").append(toIndentedString(educationalLevel)).append("\n");
    sb.append("    academicYear: ").append(toIndentedString(academicYear)).append("\n");
    sb.append("    books: ").append(toIndentedString(books)).append("\n");
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

