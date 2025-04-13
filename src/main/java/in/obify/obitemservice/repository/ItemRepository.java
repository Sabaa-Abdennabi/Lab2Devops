package in.obify.obitemservice.repository;

import in.obify.obitemservice.model.ItemModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<ItemModel, String> {
    public void deleteAllByCategoryId(String categoryId);
    //public List<ItemModel> findAllByCategoryId(String categoryId);
}
