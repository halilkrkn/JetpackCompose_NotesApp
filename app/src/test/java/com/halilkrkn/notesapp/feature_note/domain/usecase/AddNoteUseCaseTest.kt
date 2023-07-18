package com.halilkrkn.notesapp.feature_note.domain.usecase

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.halilkrkn.notesapp.feature_note.data.repository.FakeNoteRepository
import com.halilkrkn.notesapp.feature_note.domain.model.Note
import com.halilkrkn.notesapp.feature_note.domain.util.NoteOrder
import com.halilkrkn.notesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteUseCaseTest{



    private lateinit var getNotes: GetNotesUseCase
    private lateinit var addNote: AddNoteUseCase
    private lateinit var repository: FakeNoteRepository
    private val noteToInsert = mutableListOf<Note>()

    @Before
    fun setUp() {
        repository = FakeNoteRepository()
        addNote = AddNoteUseCase(repository)
        getNotes = GetNotesUseCase(repository)

/*
        ('a'..'z').forEachIndexed { index, c ->

            noteToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        noteToInsert.shuffle()
        noteToInsert.forEach {
            runBlocking {
                repository.insertNote(
                    it
                )
            }
        }
*/
    }



    @Test
    fun `add note item, return true`() = runBlocking {

        val note = Note(
            title = "a",
            content = "a",
            timestamp = 0L,
            color = 0
        )
        addNote.invoke(note)
//        repository.insertNote(note)

        val notes = getNotes.invoke(
            NoteOrder.Title(OrderType.Descending)
        ).first()

        assertThat(notes).contains(note)

    }

    @Test
    fun `add note item without title name, should be title`() = runBlocking {

        val note = Note(
            title = "",
            content = "a",
            timestamp = 0L,
            color = 0
        )
        repository.insertNote(note)

        val notes = getNotes.invoke(
            NoteOrder.Title(OrderType.Descending)
        ).first()
        println(notes)

        assertThat(notes).doesNotContain(note.title)
        assertThat(notes[0].title).isEmpty()

    }

    @Test
    fun `add note item without content name, should be content`() = runBlocking {

        val note = Note(
            title = "a",
            content = "",
            timestamp = 0L,
            color = 0
        )
        repository.insertNote(note)

        val notes = getNotes.invoke(
            NoteOrder.Title(OrderType.Descending)
        ).first()
        println(notes)

        assertThat(notes).doesNotContain(note.content)
        assertThat(notes[0].content).isEmpty()
    }
}