package com.forpawchain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adopt")
public class AdoptController {

    @GetMapping("/ad")
    public ResponseEntity<?> getAdoptAd() {


        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAdoptList(@RequestParam("pageno") int pageNo, @RequestParam("type") int type, @RequestParam("kind") int kind, @RequestParam("sex") int sex) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/{pid}")
    public ResponseEntity<?> getAdoptDetail(@RequestHeader("Access-Token") String accessToken) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registAdopt(@RequestHeader("Access-Token") String accessToken) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modifyAdopt(@RequestHeader("Access-Token") String accessToken) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity<?> removeAdopt(@RequestHeader("Access-Token") String accessToken) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/article")
    public ResponseEntity<?> getAdoptMyList(@RequestHeader("Access-Token") String accessToken) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
