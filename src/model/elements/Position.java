package model.elements;

public class Position {

    boolean wall;
    Prise prise;
    boolean wasCoin;
    boolean gate;

    public Position(boolean wall, boolean gate, int priseType) {
        this.wall = wall;
        this.gate = gate;
        if (isPath()) {
            setPrise(priseType);
        }
        wasCoin = false;
    }

    public boolean isWall() {
        return wall;
    }

    public boolean isGate() {
        return gate;
    }

    public boolean isPrise() {
        return prise != null;
    }

    public boolean isPath() {
        return !gate && !wall;
    }

    public boolean isSpacialPrise() {
        return prise != null && prise.getType() > 2;
    }

    public Prise getPrise() {
        return prise;
    }

    public void setPrise(int type) {
        if (type >= 0) {
            this.prise = new Prise(type);
            wasCoin = false;
        } else if (type == -1) {
            if (prise != null)
                if (prise.type == 0)
                    wasCoin = true;
            this.prise = new Prise();
        }
    }

    public void deletePrise() {
        this.prise = null;
    }

    public boolean wasCoin() {
        return wasCoin;
    }
}
