//package com.example.chattproject.controller;
//
//import com.example.chattproject.service.SampleService;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Controller
//@Log4j2
//public class SampleController {
//
//    @Autowired
//    private SampleService sampleService;
//
//    @GetMapping("/hello")
//    public void hello(Model model){
//
//        log.info("hello-0------------------------------------");
//
//        model.addAttribute("msg","HELLO WORLD");
//    }
//
//    @GetMapping("ex/ex1")
//    public void ex1(Model model){
//
//        List<String> list = Arrays.asList("AAA","BBB","CCC","DDD");
//
//        model.addAttribute("list",list);
//    }
//
//    class SampleDTO{
//        private String p1,p2,p3;
//
//        public String getP1(){
//            return p1;
//        }
//        public String getP2(){
//            return p2;
//        }
//        public String getP3(){
//            return p3;
//        }
//    }
//
//    @GetMapping("ex/ex2")
//    public void ex2(Model model){
//
//        log.info("ex/ex2........................................................");
//
//        List<String> strList = IntStream.range(1,10)
//                .mapToObj(i -> "Data"+i)
//                .collect(Collectors.toList());
//
//        model.addAttribute("list",strList);
//
//        Map<String, String> map = new HashMap<>();
//        map.put("A","AAAA");
//        map.put("B","BBBB");
//
//        model.addAttribute("map",map);
//
//        SampleDTO sampleDTO = new SampleDTO();
//        sampleDTO.p1 = "Value -- p1";
//        sampleDTO.p2 = "Value -- p2;";
//        sampleDTO.p3 = "Value -- p3;";
//
//        model.addAttribute("dto",sampleDTO);
//    }
//
//    @GetMapping("/ex/ex3")
//    public void ex3(Model model){
//        System.out.println("hi");
//        model.addAttribute("arr",new String[]{"AAA","BBB","CCC"});
//    }
//
//    @RequestMapping(value="/sample", method = {RequestMethod.POST, RequestMethod.GET})
//    public String index() {
//        System.out.println("인덱스 페이지 호출");
//        String test = sampleService.selectTest();
//        System.out.println("조회 테스트 : "+test);
//        return "index";
//    }
//}
