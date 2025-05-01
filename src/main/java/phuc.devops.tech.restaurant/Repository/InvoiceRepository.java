package phuc.devops.tech.restaurant.Repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phuc.devops.tech.restaurant.Entity.Invoice;

import java.time.LocalDate;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    @Query("SELECT SUM(i.order.total) FROM Invoice i WHERE DATE(i.createdAt) = :date")
    Float getRevenueByDate(@Param("date") LocalDate date);

    @Query("SELECT SUM(i.order.total) FROM Invoice i WHERE MONTH(i.createdAt) = :month AND YEAR(i.createdAt) = :year")
    Float getRevenueByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(i.order.total) FROM Invoice i WHERE YEAR(i.createdAt) = :year")
    Float getRevenueByYear(@Param("year") int year);

//    Page<Invoice> findByOrder_OrderIDAndRatingIsNotNull(String orderID, Pageable pageable);
//
//    Page<Invoice> findByOrder_DiningTable_TableIDAndRatingIsNotNull(Long tableID, Pageable pageable);
//
//    Page<Invoice> findByReviewStatusAndRatingIsNotNull(TableStatus status, Pageable pageable);
//
//    Page<Invoice> findByRatingIsNotNull(Pageable pageable);

}
