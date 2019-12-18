/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.bidirectional.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String title;
    private String filename;

    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Album> albums = new ArrayList<>();

    protected void setAuthor(Author author, boolean add) {
        Author oldAuthor = this.author;
        this.author = author;
        if (add) {
            if (author != null) {
                author.addPhoto(this, false);
            }
            else if (author == null && oldAuthor != null) {
                oldAuthor.removePhoto(this, false);
            }
        }
    }

    public void addAlbum(Album album) {
        addAlbum(album, true);
    }

    protected void addAlbum(Album album, boolean set) {
        if (album != null) {
            if (getAlbums().contains(album)) {
                getAlbums().set(getAlbums().indexOf(album), album);
            }
            else {
                getAlbums().add(album);
            }

            if (set) {
                album.addPhoto(this, false);
            }
        }
    }

    public void removeAlbum(Album album) {
        removeAlbum(album, true);
    }

    protected void removeAlbum(Album album, boolean remove) {
        getAlbums().remove(album);
        if (remove)
            album.removePhoto(this, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return getId() == photo.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /* Setters and getters */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        setAuthor(author, true);
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
