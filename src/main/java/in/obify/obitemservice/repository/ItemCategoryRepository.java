package in.obify.obitemservice.repository;

import in.obify.obitemservice.model.ItemCategoryModel;
import in.obify.obitemservice.model.ItemModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemCategoryRepository extends MongoRepository<ItemCategoryModel, String> {
}
