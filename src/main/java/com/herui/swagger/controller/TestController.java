package com.herui.swagger.controller;

import com.herui.swagger.domain.model.TestData;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("test")
public class TestController {
    private static AtomicInteger increaseId = new AtomicInteger(0);
    private static Map<String, TestData> repository = new ConcurrentHashMap<>();

    @ApiOperation(value = "post data")
    @PostMapping("data")
    public BaseRespone<TestData> postData(@RequestParam("key") String key,
                                          @RequestParam("value") String value) {
        if(repository.containsKey(key)) {
            return new BaseRespone<>("1", "key is already exist.", null);
        }
        TestData testData = new TestData();
        testData.setId(increaseId());
        testData.setKey(key);
        testData.setValue(value);
        repository.put(key, testData);
        return new BaseRespone<>("0", "success", testData);
    }

    @ApiOperation(value = "put data")
    @PutMapping("data")
    public BaseRespone<TestData> putData(@RequestParam("key") String key,
                                          @RequestParam("value") String value) {
        if(!repository.containsKey(key)) {
            return new BaseRespone<>("2", "key is not exist.", null);
        }
        TestData testData = repository.get(key);
        testData.setValue(value);
        repository.put(key, testData);
        return new BaseRespone<>("0", "success", testData);
    }

    @ApiOperation(value = "get data")
    @GetMapping("data")
    public BaseRespone<TestData> getData(@RequestParam("key") String key) {
        if(!repository.containsKey(key)) {
            return new BaseRespone<>("2", "key is not exist.", null);
        }
        return new BaseRespone<>("0", "success", repository.get(key));
    }

    @ApiOperation(value = "delete data")
    @DeleteMapping("data")
    public BaseRespone<TestData> delData(@RequestParam("key") String key) {
        return new BaseRespone<>("0", "success", repository.remove(key));
    }

    private static Integer increaseId() {
        return increaseId.incrementAndGet();
    }
}
