package sliit.foodDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sliit.foodDelivery.entity.Address;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // READ: Get all addresses by user
    List<Address> findByUserId(Long userId);
    
    // READ: Get 3 recent addresses by user
    List<Address> findTop3ByUserIdOrderByUpdatedAtDesc(Long userId);
    
    // READ: Count addresses by user
    long countByUserId(Long userId);
}