package com.library.api.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.net.URI;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * UploadBookCover200Response
 */

@JsonTypeName("uploadBookCover_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-10T21:02:26.062995258+03:00[Europe/Moscow]")
public class UploadBookCover200Response {

  private URI coverImageUrl;

  public UploadBookCover200Response coverImageUrl(URI coverImageUrl) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UploadBookCover200Response uploadBookCover200Response = (UploadBookCover200Response) o;
    return Objects.equals(this.coverImageUrl, uploadBookCover200Response.coverImageUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(coverImageUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UploadBookCover200Response {\n");
    sb.append("    coverImageUrl: ").append(toIndentedString(coverImageUrl)).append("\n");
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

