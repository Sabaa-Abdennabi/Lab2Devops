package in.obify.obitemservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemModel {
    private String id;
    private String name;
    private String description;
    private Double price;
    private String categoryId;
}
