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

class GetNoteUseCaseTest {

    private lateinit var getNotes: GetNotesUseCase
    private lateinit var findNotes: GetNoteUseCase
    private lateinit var repository: FakeNoteRepository
    private val noteToInsert = mutableListOf<Note>()

    @Before
    fun setUp() {
        repository = FakeNoteRepository()
        findNotes = GetNoteUseCase(repository)
        getNotes = GetNotesUseCase(repository)

        ('a'..'z').forEachIndexed { index, c ->

            noteToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index,
                    id = index
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
    }


    @Test
    fun `get note item , return true`() = runBlocking {
        val note = noteToInsert.find {
            it.id == 25
        }

        val findNotes = findNotes.invoke(note?.id!!)
        println(findNotes)

        assertThat(findNotes).isEqualTo(note)


//        val notes = getNotes.invoke(
//            NoteOrder.Title(OrderType.Descending)
//        ).first()
//        println(notes)
//
//        assertThat(notes).contains(note)
    }


}