package com.example.backend.controllers;

import com.example.backend.utils.PTUtil;
import com.example.backend.utils.ResponseMessage;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/pytronado")
@Api(value = "example")
public class PTController {
private PTUtil ptUtil;
    private static final Logger logger = LoggerFactory.getLogger(PTController.class);


@Autowired
public PTController(PTUtil ptUtil){
    this.ptUtil=ptUtil;

}

    @ApiOperation(value = "Settings file (entry point for pytornado)")
    @GetMapping("/settings")
    public ResponseEntity<String> setFile() {
    try {
        ptUtil.callBashCommand("pytornado -v --run /Users/xingao/pytornado/settings/template.json");
        ptUtil.callBashCommand("cp /Users/xingao/pytornado/aircraft/aircraft_template.json /Users/xingao/pytornado/aircraft_input.json");
    }catch (Exception e) {
        e.printStackTrace();
    }
        return ResponseEntity.status(HttpStatus.OK).body(" Settings file (entry point for pytornado).");
    }

    @ApiOperation(value = "Generate a minimal working example")
    @GetMapping("/example")
    public ResponseEntity<String> generateExample() throws IOException {
        ptUtil.callBashCommand("pytornado --make-example ");
        return ResponseEntity.status(HttpStatus.OK).body(" Generate a minimal working example.");
    }


    @ApiOperation(value = "List example aircraft in the database")
    @GetMapping("/exampleslist")
    public ResponseEntity<List<String>> listExamples() throws IOException {
        List<String> result=ptUtil.callBashCommand("pytornado --list-example-from-db ");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("aircraft") MultipartFile aircraft, @RequestPart("state") MultipartFile state) {
        String message = "";
        try {
            if(aircraft==null){
                state.transferTo(new File("/Users/xingao/pytornado/pytornado/statetest/template.json"));
                message = "No file uploaded! " ;
            }
            else{
                state.transferTo(new File("/Users/xingao/pytornado/pytornado/state/template.json"));
                aircraft.transferTo(new File("/Users/xingao/pytornado/pytornado/aircraft/template_aircraft.json"));
                message = "Uploaded the file successfully: " +aircraft.getOriginalFilename();
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " +  "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

    }


    @ApiOperation(value = "find aircraft_template file")
    @GetMapping("/aircraft_template")
    public ResponseEntity<Object> findAircraftTemplate() {
        File result= ptUtil.findFile("/Users/xingao/pytornado/pytornado/aircraft");
        Object obj;
        if(result==null) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("The file does not exist!");
        }
        else{
            obj = ptUtil.parseJson(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }


    @ApiOperation(value = "find state template file")
    @GetMapping("/state")
    public ResponseEntity<Object> findStateTemplate() {
        File result= ptUtil.findFile("/Users/xingao/pytornado/pytornado/state");
        Object obj;
        if(result==null) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("The file does not exist!");
        }
        else{
            obj = ptUtil.parseJson(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }


}
