package com.fds.inventoryservice.service;

import com.fds.inventoryservice.dto.InventoryRespon;
import com.fds.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    @SneakyThrows
   @Transactional(readOnly = true)
    public List<InventoryRespon> isInStock(List<String> skuCode){
       log.info("bắt đầu");
      Thread.sleep(2000);
       log.info("kết thúc");
       return  inventoryRepository.findBySkuCodeIn(skuCode)
               .stream()
               .map(inventory ->
                   InventoryRespon.builder()
                           .skuCode(inventory.getSkuCode())
                           .isInStock(inventory.getQuantity()>0)
                           .build()
               ).collect(Collectors.toList());

    }

}
