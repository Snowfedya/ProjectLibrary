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
@EqualsAndHashCode(callSuper = false)
public class BookDTO extends RepresentationModel<BookDTO> {
    private Long bookId;
    private String title;
    private String isbn;
    private int publicationYear;
    private String description;
    private String publisher;
    private String status;
    private List<String> authorNames;
    private double averageRating; // Добавлено среднее значение рейтинга
    private Integer userRating; // Рейтинг текущего пользователя (если есть)
}
