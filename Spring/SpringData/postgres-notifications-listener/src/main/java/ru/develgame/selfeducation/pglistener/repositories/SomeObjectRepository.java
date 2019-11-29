/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.pglistener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develgame.selfeducation.pglistener.entities.SomeObject;

public interface SomeObjectRepository extends JpaRepository<SomeObject, Long> {
}
