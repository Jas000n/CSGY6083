package edu.nyu.pro2.controller;

import edu.nyu.pro2.service.DeviceTypeService;
import edu.nyu.pro2.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devicetype")
@CrossOrigin
public class DeviceTypeController {
    @Autowired
    private DeviceTypeService deviceTypeService;

    @GetMapping("/name")
    public R getAllName() {
        try {
            List<String> name = deviceTypeService.getAllName();

            if (name != null) {
                return R.ok().message("Name retrieved successfully").data("name",name);
            } else {
                return R.error().message("Names not found");
            }
        } catch (Exception e) {
            return R.error().message("Error retrieving address: " + e.getMessage());
        }
    }
    @GetMapping("/model")
    public R getAllModel() {
        try {
            List<String> model = deviceTypeService.getAllModel();

            if (model != null) {
                return R.ok().message("Model retrieved successfully").data("model",model);
            } else {
                return R.error().message("Models not found");
            }
        } catch (Exception e) {
            return R.error().message("Error retrieving model: " + e.getMessage());
        }
    }

    @GetMapping("/{type}/models")
    public R getModelsByType(@PathVariable String type) {
        try {
            List<String> models = deviceTypeService.getModelsByType(type);

            if (!models.isEmpty()) {
                return R.ok().message("Models retrieved successfully").data("models", models);
            } else {
                return R.error().message("No models found for the specified type");
            }
        } catch (Exception e) {
            return R.error().message("Error retrieving models: " + e.getMessage());
        }
    }



}

