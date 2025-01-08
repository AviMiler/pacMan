package model.elements;

import services.Consts;
import services.Services;

public class Prise extends Element {

    int[] priseScores = {10, 50, 100, 300, 500, 700, 1000, 2000, 3000, 5000};
    int time = 10000;

    public Prise(int type) {
        super(0, 0);
        this.type = type;
        this.score = priseScores[this.type];
        this.imagesPath = Consts.ELEMENT_PATH + "prises/" + this.type + ".png";
        this.time = this.time / (type + 1);
    }

    public Prise() {
        super(0, 0);
        this.getIndexPoint().x = 9;
        this.getIndexPoint().y = 9;
        this.type = calculateType();
        this.score = priseScores[type];
        this.imagesPath = Consts.ELEMENT_PATH+"prises/" + type + ".png";
        this.time = this.time / (type + 1);

    }

    private int calculateType() {
        int n = Services.getRandomInt(0, 100);
        for (int i = 9; i > 2; i--) {
            if (n % i == 0) {
                return i;
            }
        }
        return 2;
    }
}
