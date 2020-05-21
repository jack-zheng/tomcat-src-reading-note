package pattern.adaptor;

import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args) {
        List<Quack> quackList = Arrays.asList(new CatAdaptor(new Cat()), new Duck());
        for(Quack quack : quackList) {
            quack.quack();
        }
    }
}
