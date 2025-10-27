package rw.ac.ilpd.hostelservice.repository.nosql;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelRoomType;
import rw.ac.ilpd.sharedlibrary.enums.ClientType;
import rw.ac.ilpd.sharedlibrary.enums.PricingStatus;
import java.util.List;
import java.util.Optional;

@Repository
public interface HostelRoomTypeRepository extends MongoRepository<HostelRoomType, String> {

    Optional<HostelRoomType> findByType(String name);
    @Query(value = "{ 'hostelRoomPrices.pricingStatus': 'ACTIVE', 'hostelRoomPrices.clientType': ?0 }")
    List<HostelRoomType> findByActiveClientType(ClientType clientType);

    @Aggregation(pipeline = {
            "{ $project: { " +
                    "id: 1, " +
                    "type: 1, " +
                    "maxCapacity: 1, " +
                    "hostelRoomImages: 1, " +
                    "hostelRoomPrices: { " +
                    "$filter: { " +
                    "input: '$hostelRoomPrices', " +
                    "as: 'price', " +
                    "cond: { $and: [ " +
                    "{ $eq: ['$$price.pricingStatus',?0] }, " +
                    "{ $eq: ['$$price.clientType', ?1] } " +
                    "] } " +
                    "} " +
                    "} " +
                    "} }",
            "{ $match: { 'hostelRoomPrices.0': { $exists: true } } }"
    })
    List<HostelRoomType> findRoomTypesWithActiveClientPrices(PricingStatus pricingStatus,ClientType clientType);
//    @Aggregation(pipeline = {
//            """
//            {
//              $project: {
//                id: 1,
//                type: 1,
//                maxCapacity: 1,
//                hostelRoomImages: 1,
//                hostelRoomPrices: {
//                  $cond: {
//                    if: { $isArray: "$hostelRoomPrices" },
//                    then: {
//                      $filter: {
//                        input: "$hostelRoomPrices",
//                        as: "price",
//                        cond: {
//                          $and: [
//                            { $eq: ["$$price.pricingStatus", ?0] },
//                            { $ne: ["$$price", null] }
//                          ]
//                        }
//                      }
//                    },
//                    else: []
//                  }
//                }
//              }
//            },
//            {
//              $match: {
//                "hostelRoomPrices.0": { $exists: true }
//              }
//            }
//            """
//    })
//    List<HostelRoomType> findAvailablePriceOnParticularHostelType(PricingStatus pricingStatus);
@Aggregation(pipeline = {
        """
        {
          $project: {
            id: 1,
            type: 1,
            maxCapacity: 1,
            hostelRoomImages: 1,
            hostelRoomPrices: {
              $cond: {
                if: { $isArray: "$hostelRoomPrices" },
                then: {
                  $let: {
                    vars: {
                      filteredPrices: {
                        $filter: {
                          input: "$hostelRoomPrices",
                          as: "price",
                          cond: {
                            $and: [
                              { $eq: ["$$price.pricingStatus", ?0] },
                              { $ne: ["$$price", null] }
                            ]
                          }
                        }
                      }
                    },
                    in: {
                      $sortArray: {
                        input: "$$filteredPrices",
                        sortBy: {
                          "createdAt": -1,
                          "pricingStatus": 1,
                          "clientType": 1
                        }
                      }
                    }
                  }
                },
                else: []
              }
            }
          }
        },
        {
          $match: {
            "hostelRoomPrices.0": { $exists: true }
          }
        }
        """
})
List<HostelRoomType> findAvailablePriceOnParticularHostelType(PricingStatus pricingStatus);
}
