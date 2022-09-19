package toy1.toy1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String Hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String hellomvc(@RequestParam(value = "name", required = true) String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello" + name;
    }

    @GetMapping("hello-test")
    @ResponseBody
    public String helloTest(@RequestParam("name") String name) { return "testest" + name; }



    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    @GetMapping("hello-api2")
    @ResponseBody
    public Hello helloapi2(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    @GetMapping("hello-api3")
    @ResponseBody
    public List helloapi3(@RequestParam("test") int forInstance){
        ForMake forMake = new ForMake();
        forMake.setForInstance(forInstance);
        List<Integer> list = forMake.makeFor();
        return list;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class ForMake{

        private int forInstance;

        public int getforInstance() { return forInstance; }

        public void setForInstance(int forInstance) { this.forInstance = forInstance;}

        public List makeFor(){
            List<Integer> list = new ArrayList<Integer>();
            for (int i=0; i<forInstance; i++){ list.add(i); }
            return list;
        }
    }

}
