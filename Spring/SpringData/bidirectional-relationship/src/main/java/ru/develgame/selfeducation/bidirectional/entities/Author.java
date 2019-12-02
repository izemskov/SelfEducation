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
public class Author {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String firstName;
    private String secondName;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Photo> photos = new ArrayList<>();

    public void addPhoto(Photo photo) {
        addPhoto(photo, true);
    }

    protected void addPhoto(Photo photo, boolean set) {
        if (photo != null) {
            if (getPhotos().contains(photo)) {
                getPhotos().set(getPhotos().indexOf(photo), photo);
            }
            else {
                getPhotos().add(photo);
            }

            if (set) {
                photo.setAuthor(this, false);
            }
        }
    }

    public void removePhoto(Photo photo) {
        removePhoto(photo, true);
    }

    protected void removePhoto(Photo photo, boolean remove) {
        getPhotos().remove(photo);
        if (remove)
            photo.setAuthor(null, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return getId() == author.getId();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
