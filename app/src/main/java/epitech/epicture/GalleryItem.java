package epitech.epicture;

import java.io.Serializable;

public class GalleryItem implements Serializable {
    private String _id;
    private String _title;
    private String _name;
    private GalleryImageItem[] _images = null;

    GalleryItem(String id, String title, String name, GalleryImageItem[] images) {
        _id = id;
        _title = title;
        _name = name;
        _images = images;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public GalleryImageItem[] getImages() {
        return _images;
    }

    public void setImages(GalleryImageItem[] _images) {
        this._images = _images;
    }
}
