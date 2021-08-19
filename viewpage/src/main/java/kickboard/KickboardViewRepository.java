package kickboard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KickboardViewRepository extends CrudRepository<KickboardView, Long> {

    List<KickboardView> findByPaymentId(Long paymentId);

}