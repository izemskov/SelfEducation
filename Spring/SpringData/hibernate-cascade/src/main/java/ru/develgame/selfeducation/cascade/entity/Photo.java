/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.cascade.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Photo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String title;
    private String filename;

    @ManyToOne
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Album> albums = new ArrayList<>();

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
}
