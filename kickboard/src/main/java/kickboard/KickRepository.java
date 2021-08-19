package kickboard;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="kicks", path="kicks")
public interface KickRepository extends PagingAndSortingRepository<Kick, Long>{


}
