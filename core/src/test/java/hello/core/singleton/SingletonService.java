package hello.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance(){ return instance; }

    private SingletonService() {
    }
    
    public static void main(String[] args) {
        SingletonService singletonService1 = new SingletonService();
        SingletonService singletonService2 = new SingletonService();

    }



}


