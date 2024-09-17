package phuc.devops.tech.shoeshop.Service;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import phuc.devops.tech.shoeshop.Entity.Model;
import phuc.devops.tech.shoeshop.Repository.ModelRepository;
import phuc.devops.tech.shoeshop.dto.UserCreateModel;
import phuc.devops.tech.shoeshop.dto.UserUpdateModel;

import java.util.Iterator;
import java.util.List;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;
    public Model createModel(UserCreateModel request){
        Model model = new Model();
        model.setAmount(request.getAmount());
        model.setDes(request.getDes());
        model.setName(request.getName());
        model.setPrice(request.getPrice());

        return modelRepository.save(model);
    }

    public Model updateModel(Long modelId, UserUpdateModel request){
        Model model = modelRepository.getById(modelId);
        model.setAmount(request.getAmount());
        model.setDes(request.getDes());
        model.setName(request.getName());
        model.setPrice(request.getPrice());

        return modelRepository.save(model);
    }

    public void deleteModel(Long modelId){
        modelRepository.deleteById(modelId);
    }

    public List<Model> getModels(){
        return modelRepository.findAll();
    }

    public Model getModel(Long modelId){
        return modelRepository.findById(modelId).orElseThrow();
    }
}
