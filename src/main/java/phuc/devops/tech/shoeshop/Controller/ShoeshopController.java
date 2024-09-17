package phuc.devops.tech.shoeshop.Controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.shoeshop.Entity.Model;
import phuc.devops.tech.shoeshop.Service.ModelService;
import phuc.devops.tech.shoeshop.dto.UserCreateModel;
import phuc.devops.tech.shoeshop.dto.UserUpdateModel;

import java.util.List;

@RestController
@RequestMapping("/model")
public class ShoeshopController {
    @Autowired
    private ModelService modelService;

    @PostMapping
    Model createModel(@RequestBody UserCreateModel request){
        return modelService.createModel(request);
    }

    @GetMapping
    List<Model> getModel(){
        return modelService.getModels();
    }

    @GetMapping("/{modelId}")
    Model getModel(@PathVariable("modelId") Long modelId){
        return  modelService.getModel(modelId);
    }

    @PutMapping
    Model updateModel(@PathVariable Long modelId, @RequestBody UserUpdateModel request){
        return modelService.updateModel(modelId,request);
    }

    @DeleteMapping("/{modelId}")
    String deleteModel(@PathVariable Long modelId){
        modelService.deleteModel(modelId);
        return "Model has been deleted";
    }
}
