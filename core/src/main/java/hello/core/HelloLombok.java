package hello.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class HelloLombok {

    private final String name;
    private final int age;

    public static void main(String[] args) {
        HelloLombok hl = new HelloLombok("test", 20);
        System.out.println("hl = " + hl.getAge());    }

}
