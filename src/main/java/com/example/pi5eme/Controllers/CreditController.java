package com.example.pi5eme.Controllers;

import com.example.pi5eme.Entities.Credit;
import com.example.pi5eme.services.CreditService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/Credit")
public class CreditController {
 private CreditService creditService;

 @PostMapping("/add")
    Credit createCredit(@RequestBody Credit credit, @PathVariable Integer analyse_id){
     return creditService.createCredit(credit,analyse_id);
 }
}
