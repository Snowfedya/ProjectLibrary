package com.library.api.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.library.api.models.EducationalLevel;
import com.library.api.models.Subject;
import java.net.URI;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Book
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-05T21:20:19.639692599+03:00[Europe/Moscow]")
public class Book {

  private Long id;

  private String title;

  private String author;

  private String isbn13;

  private String isbn10;

  private String genre;

  private Integer publicationYear;

  private Integer quantity;

  private Integer availableQuantity;

  private String description;

  private URI coverImageUrl;

  private String publisher;

  private String language;

  private Integer pagesCount;

  private EducationalLevel educationalLevel;

  private Subject subject;

  public Book id(Long id) {
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

  public Book title(String title) {
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

  public Book author(String author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  */
  
  @Schema(name = "author", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("author")
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Book isbn13(String isbn13) {
    this.isbn13 = isbn13;
    return this;
  }

  /**
   * Get isbn13
   * @return isbn13
  */
  @Pattern(regexp = "^978[0-9]{10}$") 
  @Schema(name = "isbn13", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isbn13")
  public String getIsbn13() {
    return isbn13;
  }

  public void setIsbn13(String isbn13) {
    this.isbn13 = isbn13;
  }

  public Book isbn10(String isbn10) {
    this.isbn10 = isbn10;
    return this;
  }

  /**
   * Get isbn10
   * @return isbn10
  */
  @Pattern(regexp = "^[0-9]{9}[0-9X]$") 
  @Schema(name = "isbn10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isbn10")
  public String getIsbn10() {
    return isbn10;
  }

  public void setIsbn10(String isbn10) {
    this.isbn10 = isbn10;
  }

  public Book genre(String genre) {
    this.genre = genre;
    return this;
  }

  /**
   * Get genre
   * @return genre
  */
  
  @Schema(name = "genre", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("genre")
  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public Book publicationYear(Integer publicationYear) {
    this.publicationYear = publicationYear;
    return this;
  }

  /**
   * Get publicationYear
   * @return publicationYear
  */
  
  @Schema(name = "publicationYear", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("publicationYear")
  public Integer getPublicationYear() {
    return publicationYear;
  }

  public void setPublicationYear(Integer publicationYear) {
    this.publicationYear = publicationYear;
  }

  public Book quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
  */
  
  @Schema(name = "quantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Book availableQuantity(Integer availableQuantity) {
    this.availableQuantity = availableQuantity;
    return this;
  }

  /**
   * Get availableQuantity
   * @return availableQuantity
  */
  
  @Schema(name = "availableQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("availableQuantity")
  public Integer getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(Integer availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public Book description(String description) {
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

  public Book coverImageUrl(URI coverImageUrl) {
    this.coverImageUrl = coverImageUrl;
    return this;
  }

  /**
   * Get coverImageUrl
   * @return coverImageUrl
  */
  @Valid 
  @Schema(name = "coverImageUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("coverImageUrl")
  public URI getCoverImageUrl() {
    return coverImageUrl;
  }

  public void setCoverImageUrl(URI coverImageUrl) {
    this.coverImageUrl = coverImageUrl;
  }

  public Book publisher(String publisher) {
    this.publisher = publisher;
    return this;
  }

  /**
   * Get publisher
   * @return publisher
  */
  
  @Schema(name = "publisher", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("publisher")
  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public Book language(String language) {
    this.language = language;
    return this;
  }

  /**
   * Get language
   * @return language
  */
  
  @Schema(name = "language", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("language")
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Book pagesCount(Integer pagesCount) {
    this.pagesCount = pagesCount;
    return this;
  }

  /**
   * Get pagesCount
   * @return pagesCount
  */
  
  @Schema(name = "pagesCount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pagesCount")
  public Integer getPagesCount() {
    return pagesCount;
  }

  public void setPagesCount(Integer pagesCount) {
    this.pagesCount = pagesCount;
  }

  public Book educationalLevel(EducationalLevel educationalLevel) {
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

  public Book subject(Subject subject) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(this.id, book.id) &&
        Objects.equals(this.title, book.title) &&
        Objects.equals(this.author, book.author) &&
        Objects.equals(this.isbn13, book.isbn13) &&
        Objects.equals(this.isbn10, book.isbn10) &&
        Objects.equals(this.genre, book.genre) &&
        Objects.equals(this.publicationYear, book.publicationYear) &&
        Objects.equals(this.quantity, book.quantity) &&
        Objects.equals(this.availableQuantity, book.availableQuantity) &&
        Objects.equals(this.description, book.description) &&
        Objects.equals(this.coverImageUrl, book.coverImageUrl) &&
        Objects.equals(this.publisher, book.publisher) &&
        Objects.equals(this.language, book.language) &&
        Objects.equals(this.pagesCount, book.pagesCount) &&
        Objects.equals(this.educationalLevel, book.educationalLevel) &&
        Objects.equals(this.subject, book.subject);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, isbn13, isbn10, genre, publicationYear, quantity, availableQuantity, description, coverImageUrl, publisher, language, pagesCount, educationalLevel, subject);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Book {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    isbn13: ").append(toIndentedString(isbn13)).append("\n");
    sb.append("    isbn10: ").append(toIndentedString(isbn10)).append("\n");
    sb.append("    genre: ").append(toIndentedString(genre)).append("\n");
    sb.append("    publicationYear: ").append(toIndentedString(publicationYear)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    availableQuantity: ").append(toIndentedString(availableQuantity)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    coverImageUrl: ").append(toIndentedString(coverImageUrl)).append("\n");
    sb.append("    publisher: ").append(toIndentedString(publisher)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    pagesCount: ").append(toIndentedString(pagesCount)).append("\n");
    sb.append("    educationalLevel: ").append(toIndentedString(educationalLevel)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
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

