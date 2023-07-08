package com.example.nuclearnote.domain.model

import androidx.compose.ui.graphics.Color.Companion.White
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nuclearnote.core.ui.theme.BabyBlue
import com.example.nuclearnote.core.ui.theme.LightGreen
import com.example.nuclearnote.core.ui.theme.RedOrange
import com.example.nuclearnote.core.ui.theme.RedPink
import com.example.nuclearnote.core.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    @ColumnInfo("imageData") val noteAttachments: ArrayList<ByteArray> = arrayListOf(),
    @PrimaryKey val id: Int? = null,
) {

    companion object {
        val noteColors = listOf(White, RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }

}

class InvalidNoteException(message: String): Exception(message)
