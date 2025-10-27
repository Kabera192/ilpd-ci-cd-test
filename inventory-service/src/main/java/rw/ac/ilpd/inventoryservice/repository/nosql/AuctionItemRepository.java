package rw.ac.ilpd.inventoryservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.inventoryservice.model.nosql.document.AuctionItem;

public interface AuctionItemRepository extends MongoRepository<AuctionItem, String> {
}
