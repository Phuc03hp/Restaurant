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

    public String setAvailableTable(Long tableID){
        DiningTable diningTable = diningTableRepository.findById(tableID).orElseThrow(()-> new RuntimeException("Cannot find tableID"));
        diningTable.setTableStatus(TableStatus.AVAILABLE);
        return "Table " + diningTable.getTableID() + " is available";
    }

    public String setUnavailableTable(Long tableID){
        DiningTable diningTable = diningTableRepository.findById(tableID).orElseThrow(()-> new RuntimeException("Cannot find tableID"));
        diningTable.setTableStatus(TableStatus.UNAVAILABLE);
        return "Table " + diningTable.getTableID() + " is unavailable";
    }
}
