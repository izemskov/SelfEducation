/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019 Ilya Zemskov */

package ru.develgame.selfeducation.bidirectional.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.develgame.selfeducation.bidirectional.entities.Album;
import ru.develgame.selfeducation.bidirectional.entities.Author;
import ru.develgame.selfeducation.bidirectional.entities.Photo;
import ru.develgame.selfeducation.bidirectional.repositories.AlbumRepository;
import ru.develgame.selfeducation.bidirectional.repositories.AuthorRepository;
import ru.develgame.selfeducation.bidirectional.repositories.PhotoRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BiDirectionalRelationshipTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    public void test1() {
        Author author = new Author();
        author.setFirstName("author1");
        author.setSecondName("author2");

        Photo photo = new Photo();
        photo.setFilename("filename");
        photo.setTitle("title");
        photo.setAuthor(author);

        Assert.assertTrue(author.getPhotos().contains(photo));
        Assert.assertTrue(photo.getAuthor().equals(author));

        photoRepository.save(photo);

        List<Author> allAuthors = authorRepository.findAll();
        Assert.assertFalse(allAuthors.isEmpty());
        Assert.assertEquals(allAuthors.get(0).getFirstName(), "author1");
        Assert.assertTrue(allAuthors.get(0).getPhotos().contains(photo));

        List<Photo> allPhotos = photoRepository.findAll();
        Assert.assertFalse(allPhotos.isEmpty());
        Assert.assertEquals(allPhotos.get(0).getFilename(), "filename");
    }

    @Test
    public void test2() {
        Album album = new Album();
        album.setName("album");

        Photo photo = new Photo();
        photo.setFilename("filename");
        photo.setTitle("title");
        photo.addAlbum(album);

        Assert.assertTrue(album.getPhotos().contains(photo));
        Assert.assertTrue(photo.getAlbums().contains(album));

        photoRepository.save(photo);

        List<Photo> allPhotos = photoRepository.findAll();
        Assert.assertFalse(allPhotos.isEmpty());
        Assert.assertEquals(allPhotos.get(0).getFilename(), "filename");

        List<Album> allAlbums = albumRepository.findAll();
        Assert.assertFalse(allAlbums.isEmpty());
        Assert.assertEquals(allAlbums.get(0).getName(), "album");
    }

    @Test
    public void test3() {
        Album album = new Album();
        album.setName("album");

        Photo photo = new Photo();
        photo.setFilename("filename");
        photo.setTitle("title");
        photo.addAlbum(album);

        photoRepository.save(photo);

        List<Photo> allPhotos = photoRepository.findAll();
        Assert.assertFalse(allPhotos.isEmpty());
        Assert.assertEquals(allPhotos.get(0).getFilename(), "filename");

        List<Album> allAlbums = albumRepository.findAll();
        Assert.assertFalse(allAlbums.isEmpty());
        Assert.assertEquals(allAlbums.get(0).getName(), "album");

        // cannot remove album without unlink it in photo
        albumRepository.delete(album);
        allAlbums = albumRepository.findAll();
        Assert.assertFalse(allAlbums.isEmpty());

        photo.removeAlbum(album);
        photoRepository.save(photo);

        albumRepository.delete(album);
        allAlbums = albumRepository.findAll();
        Assert.assertTrue(allAlbums.isEmpty());

        allPhotos = photoRepository.findAll();
        Assert.assertFalse(allPhotos.isEmpty());
        Assert.assertTrue(allPhotos.get(0).getAlbums().isEmpty());
    }

    @Test
    public void test4() {
        Album album = new Album();
        album.setName("album");

        Photo photo = new Photo();
        photo.setFilename("filename");
        photo.setTitle("title");
        photo.addAlbum(album);

        Assert.assertTrue(album.getPhotos().contains(photo));
        Assert.assertTrue(photo.getAlbums().contains(album));

        photoRepository.save(photo);

        List<Photo> allPhotos = photoRepository.findAll();
        Assert.assertFalse(allPhotos.isEmpty());
        Assert.assertEquals(allPhotos.get(0).getFilename(), "filename");

        List<Album> allAlbums = albumRepository.findAll();
        Assert.assertFalse(allAlbums.isEmpty());
        Assert.assertEquals(allAlbums.get(0).getName(), "album");

        photoRepository.delete(photo);

        allAlbums = albumRepository.findAll();
        Assert.assertTrue(allAlbums.isEmpty());
    }
}
