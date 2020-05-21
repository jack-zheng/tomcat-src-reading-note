package pattern.adaptor;

public class Duck implements Quack {
    @Override
    public void quack() {
        System.out.println("Quack...");
    }
}
