package sliit.foodDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sliit.foodDelivery.entity.Address;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
    List<Address> findTop3ByUserIdOrderByUpdatedAtDesc(Long userId);
    long countByUserId(Long userId);
}