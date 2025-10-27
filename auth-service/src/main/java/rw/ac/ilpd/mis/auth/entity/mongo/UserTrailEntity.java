package rw.ac.ilpd.mis.auth.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 10/07/2025
 */

@Document(collection = "auth_user_trails")
public class UserTrailEntity {
    @Id
    private String id;
}
