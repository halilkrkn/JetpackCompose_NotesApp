package com.halilkrkn.notesapp.feature_note.data.data_source

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.halilkrkn.notesapp.feature_note.domain.model.Note
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class NoteDaoTestTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("note_db")
    lateinit var database: NoteDatabase

    private lateinit var dao: NoteDao
    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.noteDao
    }

    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun insertNoteItem() = runBlocking {
        val note = Note(
            title = "title",
            content = "content",
            timestamp = 0L,
            color = 0,
            id = 0
        )
        dao.insertNote(note)
        val allNotes = dao.getNotes().first()
        assertThat(allNotes).contains(note)
    }

    @Test
    fun deleteNoteItem() = runBlocking {
        val note = Note(
            title = "title1",
            content = "content",
            timestamp = 0L,
            color = 0,
            id = 0
        )
        dao.insertNote(note)
        dao.deleteNote(note)

        val allNotes = dao.getNotes().first()
        assertThat(allNotes).doesNotContain(note)
    }

    @Test
    fun getAllNotes() = runBlocking {
        val note = Note(
            title = "title2",
            content = "content",
            timestamp = 0L,
            color = 0,
            id = 0
        )
        val note2 = Note(
            title = "title3",
            content = "content",
            timestamp = 0L,
            color = 0,
            id = 1
        )
        dao.insertNote(note)
        dao.insertNote(note2)

        val allNotes = dao.getNotes().first()
        println(allNotes)
        assertThat(allNotes).contains(note)
        assertThat(allNotes).contains(note2)
    }

    @Test
    fun getNoteById() = runBlocking {

        val note = Note(
            title = "title4",
            content = "content",
            timestamp = 0L,
            color = 0,
            id = 0
        )

        val note2 = Note(
            title = "title5",
            content = "content",
            timestamp = 0L,
            color = 0,
            id = 1
        )

        val note3 = Note(
            title = "title6",
            content = "content",
            timestamp = 0L,
            color = 0,
            id = 2
        )

        dao.insertNote(note)
        dao.insertNote(note2)
        dao.insertNote(note3)

        val allNotes = dao.getNotes().first()
        println(allNotes)
        val noteById = dao.getNoteById(2)
        println(noteById)
//        assertThat(noteById).isEqualTo(note)
        assertThat(allNotes).contains(noteById)
    }

}