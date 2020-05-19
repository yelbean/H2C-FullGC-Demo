package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableScheduling
public class HealthController {
    List<byte[]> list = new ArrayList<>();

    @GetMapping("/health")
    public  ResponseEntity<String> getHealth(@RequestParam(defaultValue = "health") String name){
        triggerGc();
        return new ResponseEntity<>(name, HttpStatus.OK);
    }


    @PostMapping("/health")
    public ResponseEntity<String> postHealth(@RequestBody String name){
        triggerGc();
        return new ResponseEntity<>(name, HttpStatus.OK);
    }



    private void triggerGc(){
        byte[] a = new byte[ 1024 * 8 ];
        list.add(a);
        /*try {

        }catch (Throwable throwable){
            System.out.println(throwable.getMessage());
        }*/
    }

    @Scheduled(fixedDelay=120000)
    private void scheduleGC(){
        System.out.println("Before clear, the size: " + list.size());
        list.clear();
        System.out.println("After clear, the size: " + list.size());
        System.out.println("~~~~~~~~~~~~~gc~~~~~~~~~~~~~");
        System.gc();
    }

}
