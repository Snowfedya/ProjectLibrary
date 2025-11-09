package org.example.library.models.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookResponse extends RepresentationModel<BookResponse> {
    private Long bookId;
    private String title;
    private String isbn;
    private int publicationYear;
    private String description;
    private String publisher;
    private String status;
    private List<String> authorNames;
    private double averageRating;
    private Integer userRating;
    private Long addedById;
}
