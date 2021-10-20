package ru.qnocks.trsis.lab2.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.qnocks.trsis.lab2.dao.PurchasesDao;
import ru.qnocks.trsis.lab2.domain.Purchase;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchasesDao purchasesDao;

    @Autowired
    public PurchaseController(PurchasesDao purchasesDao) {
        this.purchasesDao = purchasesDao;
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> list() {
        return new ResponseEntity<>(purchasesDao.findALl(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Purchase> show(@PathVariable("id") Long id) {
        Purchase purchase = purchasesDao.findById(id);
        if (purchase == null) {
            throw new IllegalArgumentException("Cannot find purchase with id " + id);
        }
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Purchase> save(@Valid @RequestBody Purchase purchase) {
        return new ResponseEntity<>(purchasesDao.save(purchase), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    private ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        purchasesDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
