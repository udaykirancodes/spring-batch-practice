package com.db.migrator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Controller {

    @Autowired
    DataMigrator dataMigrator;

    @PostMapping("/migrate")
    public ResponseEntity<String>migrate(){
        try{
            log.info("Starting with DataMigration");
            dataMigrator.migrateData();
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error Occured in Data Migration", e);
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
