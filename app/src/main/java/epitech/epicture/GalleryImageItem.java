package epitech.epicture;

import java.io.Serializable;

public class GalleryImageItem implements Serializable {
    private String _id;
    private String _type;
    private String _link;

    GalleryImageItem(String id, String type, String link) {
        _id = id;
        _type = type;
        _link = link;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getType() {
        return _type;
    }

    public void setType(String _type) {
        this._type = _type;
    }

    public String getLink() {
        return _link;
    }

    public void setLink(String _link) {
        this._link = _link;
    }
}
