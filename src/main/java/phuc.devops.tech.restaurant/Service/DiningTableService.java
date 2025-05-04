package phuc.devops.tech.restaurant.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.Entity.DiningTable;
import phuc.devops.tech.restaurant.Repository.DiningTableRepository;
import phuc.devops.tech.restaurant.dto.request.TableStatus;
import phuc.devops.tech.restaurant.dto.request.UserCreateTable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DiningTableService {

    @Autowired
    private DiningTableRepository diningTableRepository;

    public List<DiningTable> getTable() {
        return diningTableRepository.findAll();
    }

    public DiningTable createTable(UserCreateTable request){
        DiningTable diningTable = new DiningTable();
        diningTable.setMaxCapacity(request.getMaxCapacity());
        return  diningTableRepository.save(diningTable);
    }

    public DiningTable updateTable(Long tableID, UserCreateTable request) {
        Optional<DiningTable> optionalDiningTable = diningTableRepository.findById(tableID);
        if (optionalDiningTable.isEmpty()) {
            throw new RuntimeException("Table with ID " + tableID + " not found.");
        }

        DiningTable diningTable = optionalDiningTable.get();
        diningTable.setMaxCapacity(request.getMaxCapacity());

        // Nếu request có thêm tableStatus, có thể update luôn
        if (request.getTableStatus() != null) {
            diningTable.setTableStatus(request.getTableStatus());
        }

        return diningTableRepository.save(diningTable);
    }

    public void deleteTable(Long tableID) {
        if (!diningTableRepository.existsById(tableID)) {
            throw new RuntimeException("Table with ID " + tableID + " not found.");
        }
        diningTableRepository.deleteById(tableID);
    }

}
