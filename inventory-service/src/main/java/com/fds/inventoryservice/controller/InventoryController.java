package com.fds.inventoryservice.controller;

import com.fds.inventoryservice.dto.InventoryRespon;
import com.fds.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private  InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryRespon> isInStock(@RequestParam List<String> skuCode ){
       return inventoryService.isInStock(skuCode);
    }



}
