package com.example.petclinic;

import com.example.petclinic.models.Notes;
import com.example.petclinic.repository.NotesRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PetClinicVisit.class)
public class NotesRepositoryTests {

    @Autowired
    NotesRepository notesRepository;

    @Test
    public void notesRepo_savesToCassandra(){
        //arrange
        Notes notes = new Notes(1L,"Dr. Strange","Gadget is strong and healthy for her age.  Showing resiliency to common ailments inherent to her breed");

        //act
        notesRepository.save(notes);

        //assert
        Notes result = notesRepository.findById("Dr. Strange").orElse(null);
        Assert.assertEquals("Dr. Strange",result.getAttending_physician());

    }
}
