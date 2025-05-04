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
import phuc.devops.tech.restaurant.dto.request.UserUpdateTable;

import java.util.List;

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
        diningTable.setTableStatus(TableStatus.AVAILABLE);
        return  diningTableRepository.save(diningTable);
    }

    public DiningTable updateTable(Long tableID, UserUpdateTable request) {
        DiningTable diningTable = diningTableRepository.findById(tableID).orElseThrow();
        diningTable.setMaxCapacity(request.getMaxCapacity());
        diningTable.setTableStatus(TableStatus.AVAILABLE);

        // Nếu request có thêm tableStatus, có thể update luôn
//        if (request.getTableStatus() != null) {
//            diningTable.setTableStatus(request.getTableStatus());
//        }

        return diningTableRepository.save(diningTable);
    }

    public void setTableAvailable(Long tableID) {
        DiningTable diningTable = diningTableRepository.findById(tableID).orElseThrow();
        diningTable.setTableStatus(TableStatus.AVAILABLE);

        diningTableRepository.save(diningTable);
    }

    public void deleteTable(Long tableID) {
        if (!diningTableRepository.existsById(tableID)) {
            throw new RuntimeException("Table with ID " + tableID + " not found.");
        }
        diningTableRepository.deleteById(tableID);
    }

}
