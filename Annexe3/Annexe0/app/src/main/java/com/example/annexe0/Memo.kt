package com.example.annexe0

import java.io.Serializable
import java.time.LocalDate

data class Memo( val memoTexte: String, val dateEcheance: LocalDate) : Serializable
{

}