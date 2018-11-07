package epitech.epicture;

import java.io.Serializable;

class GridSetting implements Serializable {
    private int _items;
    private boolean _mature;

    GridSetting() {
        _items = 20;
        _mature = false;
    }

    int getItemsNb() {
        return _items;
    }

    void setItemsNb(int _items) {
        if (_items < 0)
            _items = 0;
        this._items = _items;
    }

    boolean isMatureBool() {
        return _mature;
    }

    String isMatureString() {
        return String.valueOf(_mature);
    }

    void setMature(boolean _mature) {
        this._mature = _mature;
    }
}
