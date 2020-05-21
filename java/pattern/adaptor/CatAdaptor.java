package pattern.adaptor;

public class CatAdaptor implements Quack {
    private Cat cat;
    public CatAdaptor(Cat cat) {
        this.cat = cat;
    }

    @Override
    public void quack() {
        cat.miao();
    }
}
