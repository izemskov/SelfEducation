/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.bidirectional.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develgame.selfeducation.bidirectional.entities.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
