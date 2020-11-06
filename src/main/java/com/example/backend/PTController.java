package com.example.backend;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/pytronado")
@Api(value = "example")
public class PTController {
private PTUtil ptUtil;


@Autowired
public PTController(PTUtil ptUtil){
    this.ptUtil=ptUtil;
}

    @ApiOperation(value = "Settings file (entry point for pytornado)")
    @GetMapping("/settings")
    public ResponseEntity<String> setFile() throws IOException {
        ptUtil.callBashCommand("pytornado -v --run /Users/xingao/pytornado/settings/template.json");
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

    /*@ApiOperation(value = "Pick an aircraft from the database and generate a project directory")
    @GetMapping("/exampleDB")
    public ResponseEntity<String> generateExampleDB() throws IOException {
        ptUtil.callBashCommand("pytornado --make-example ");
        return ResponseEntity.status(HttpStatus.OK).body(" Pick an aircraft from the database and generate a project directory.");
    }*/



}
