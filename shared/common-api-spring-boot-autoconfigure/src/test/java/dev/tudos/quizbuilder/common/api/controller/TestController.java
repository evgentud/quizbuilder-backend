package dev.tudos.quizbuilder.common.api.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/")
public class TestController {
    @GetMapping("/with-required-param")
    public String withRequiredParameter(@RequestParam(value = "param") Integer requiredParameter) {
        return "ok";
    }

    @GetMapping("/sample-object")
    public SampleObject sampleObject(@RequestParam(value = "int") Integer intValue,
                                     @RequestParam(value = "string") String stringValue) {
        return new SampleObject(intValue, stringValue);
    }

    @PostMapping("/post-sample-object")
    public SampleObject sampleObject(@RequestBody SampleObject sampleObject) {
        return new SampleObject(sampleObject.getIntVariable() + 1, sampleObject.getStringVariable() + "_updated");
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SampleObject {
        private int intVariable;
        private String stringVariable;
    }
}
